<?php
/* --------------------------------------------------------------------*
 * SearchCoursesCommand.php                                            *
 * --------------------------------------------------------------------*
 * Description - This class allows students to search for courses by a *
 *                           given input string.                       *
 * --------------------------------------------------------------------*
 * Project: Classy Scheduler Web Server                                *
 * Author : McKim A. Jacob                                             *
 * Date Of Creation: 10 - 25 - 2015                                    *
 * ------------------------------------------------------------------- */

//===================================================================//
//  NOTES & BUGS AS OF 10- 25 -2015                                  //
//===================================================================//
/*
 *
 */


  /* --------------------------------------------------------------------*
   * Sample Request JSON                                                 *
   * --------------------------------------------------------------------*
   {
     "ServiceID": "SearchCourse",
     "searchPhrase": "CIS 350",
     "semesterID" : 1
   }
  */

class SearchCourseCommand extends Command {

    //---------------------------------------------------------------//
    // Class Atributes                                               //
    //---------------------------------------------------------------//

    /* The database access object linking to DB.                     */
    private $dbAccess;

    /* The content of the user request.                              */
    private $requestContent = array();

    //---------------------------------------------------------------//
    // Constructor/Destructors                                       //
    //---------------------------------------------------------------//
    /******************************************************************
     * @Description - Called to build the command, It takes in the
     * command parameters and saves them locally to the class.
     *
     * @param $requestData - The json request data required to make the
     * request.
     *
     * @return None
     *
     *****************************************************************/
    function __construct($requestData) {

      // Set the content locally.
      $this->requestContent = $requestData;

      // Create the new required database objects to preform task.
      $this->dbAccess = new MySqlDatabaseTool("studentClient");

    }

    /******************************************************************
     * @Description - Called when the command has finished executing
     * and its time to tear down all the command's resources.
     *
     * @param None
     *
     * @return None
     *
     *****************************************************************/
    function __destruct() {
        $this->dbAccess = NULL;
        $this->requestContent = NULL;

    }

    //---------------------------------------------------------------//
    // Class Methods                                                 //
    //---------------------------------------------------------------//

    /* Executes the command defined for the service implementation. */
    public function executeCommand() {

        // --- Variable Declarations  -------------------------------//

        /* @var $commands (Array) Used to cross check the request.   */
        $commandParams = array ("searchPhrase","semesterID");

        /* @var $commandResult (commandResult) The result model.     */
        $commandResult;

        /* @var $courseList (Array) Used to grab sections data.      */
        $courseList;

        /* @var $result (object) The output of PDO sql executes.     */
        $result = NULL;

        /* @var $sqlQuery (object) The query to execute on service.  */
        $sqlQuery = NULL;

        /* @var $searchPhrase (String) The phrase to do the search.  */
        $searchPhrase = NULL;

        /* @var $resultTable (array) The result table to return data.*/
        $resultTable;

        // --- Main Routine ------------------------------------------//

        // Check if the request contains all necessary parameters.
        if ( $this->isValidContent ($this->requestContent, $commandParams) ) {

          try {

            // Get the search phrase.
            $searchPhrase = $this->requestContent["searchPhrase"];

            // 1. Select the type of data were working with.
            if ($searchPhrase == "*") { // Select everything.
              $sqlQuery = 'SELECT courseCode FROM course WHERE 1';
              $sqlParams = array();
            }
            else if (preg_match('~^[a-zA-Z]{3} [0-9]{3}$~',$searchPhrase)) { // XXX XXX: CIS 350
              $sqlQuery = 'SELECT c.courseCode FROM course AS c JOIN department AS d ON d.depName = ? WHERE c.cID = ?';
              $sqlParams = array(substr($searchPhrase,0,3),substr($searchPhrase,4,3));
            }
            else if (preg_match('~^[a-zA-Z]{3}$~',$searchPhrase)) { // XXX: CIS
              $sqlQuery = 'SELECT c.courseCode FROM course AS c JOIN department AS d ON d.depName = ? WHERE c.dNum = d.dID';
              $sqlParams = array($searchPhrase);
            }
            else if ($searchPhrase != "") {
              $sqlQuery = "SELECT courseCode FROM course WHERE title LIKE ? OR description LIKE ?";
              $searchPhrase = "%" . $searchPhrase . "%";
              $sqlParams = array($searchPhrase,$searchPhrase);
            }
            else {
              $commandResult = new commandResult ("failed");
              $commandResult->addValuePair ("Description","No classes found.");
              return $commandResult;
            }

            // 2. Run the statement.
            if ($this->dbAccess->executeQuery($sqlQuery,$sqlParams)) {

              // 3. get the results and make sure we have something to return.
              $courseList = $this->dbAccess->getResults();

              if ($courseList != null) {
                $sqlQuery = "SELECT c.*, d.depName, s.sectionCode, s.sectionID, s.seats,s.seatsOpen,
                            f.firstName,f.lastName, b.buildingName, l.classroom, t.meetDays, t.creditHours, t.startTime, t.endTime
                            FROM course AS c
                                   JOIN department AS d
                                       ON c.dNum = d.dID
                                   JOIN section AS s
                                       ON s.courseCode = c.courseCode AND s.semesterCode = ?
                                   JOIN faculty AS f
                                       ON f.facultyID = s.facultyID
                                   JOIN location AS l
                                       ON l.locationID = s.locationID
                                   JOIN building AS b
                                       ON b.buildingID = l.buildingID
                                   JOIN timeblock AS t
                                       ON t.timeblockID = s.timeblockID
                            WHERE c.courseCode = ?";

                $courseResults = array();

                // 4. Per result pull all the sections related to the course.
                foreach ($courseList as &$courseCode) {

                  // Execute the search query.
                  $sqlParams = array ($this->requestContent["semesterID"],$courseCode["courseCode"]);
                  if ($this->dbAccess->executeQuery($sqlQuery,$sqlParams)) {

                    // 5. Check and see if we have sections.
                    $sectionList = $this->dbAccess->getResults();
                    $sectionArray = array();
                    if ($sectionList != null) {

                      // 6. Per section populate section data.
                      foreach($sectionList as &$resSec) {
                         $section = array ();
                         $section["sectionID"] = $resSec["sectionID"];
                         $section["sectionCode"] = $resSec["sectionCode"];
                         $section["profFirst"] = $resSec["firstName"];
                         $section["profLast"] = $resSec["lastName"];
                         $section["startTime"] = $resSec["startTime"];
                         $section["endTime"] = $resSec["endTime"];
                         $section["meetDays"] = $resSec["meetDays"];
                         $section["building"] = $resSec["buildingName"];
                         $section["room"] = $resSec["classroom"];
                         $section["seats"] = $resSec["seats"];
                         $section["seatsOpen"] = $resSec["seatsOpen"];
                         array_push($sectionArray,$section);
                      }

                      // Populate the course data.
                      $course["department"] = $sectionList[0]["depName"];
                      $course["courseID"] = $sectionList[0]["cID"];
                      $course["title"] = $sectionList[0]["title"];
                      $course["Description"] = $sectionList[0]["description"];
                      $course["creditHours"] = $sectionList[0]["creditHours"];
                      $course["sections"] = $sectionArray;
                      array_push($courseResults,$course);
                    } // end if check for null semester result.

                  } // end if check for bad request for sections.
                  else {
                    $commandResult = new commandResult ("systemError");
                    $commandResult->addValuePair ("Description","Database failure.");
                  }

                } // end of for each for course.

                // Append the data back if we have something.
                if (count($courseResults) > 0) {
                  $commandResult = new commandResult ("success");
                  $commandResult->addValuePair ("courseList",$courseResults);
                }
                else {
                  $commandResult = new commandResult ("failed");
                  $commandResult->addValuePair ("Description","No classes found.");
                }

              } // end if check for null courses.
              else {
                $commandResult = new commandResult ("failed");
                $commandResult->addValuePair ("Description","No classes found.");
              }
            } // end if courses exist.
            else {
              $commandResult = new commandResult ("systemError");
              $commandResult->addValuePair ("Description","Database failure.");
            }

          }

          catch (Exception $e) {
            $commandResult = new commandResult ("systemError");
            $commandResult->addValuePair ("Description","Database failure.");
          }
        }

        else {
        $commandResult = new commandResult ("invalidData");
        $commandResult->addValuePair ("Description","Invalid input parameters for SearchCourses service.");
        }

       // Return the command result.
       return $commandResult;

    }

    //---------------------------------------------------------------//
}

?>
