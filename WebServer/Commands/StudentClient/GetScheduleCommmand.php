<?php
/* --------------------------------------------------------------------*
 * GetScheduleCommmand.php                                             *
 * --------------------------------------------------------------------*
 * Description - TODO (Class Description here)                         *
 * --------------------------------------------------------------------*
 * Project: Classy Scheduler Web Server                                *
 * Author : TODO ( Your name here)                                     *
 * Date Of Creation: 10 - TODO (day here) - 2015                       *
 * ------------------------------------------------------------------- */

//===================================================================//
//  NOTES & BUGS AS OF 10- TODO (day here) -2015                     //
//===================================================================//
/*
 *
 */

class GetScheduleCommmand extends Command {

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
      $this->dbAccess = new MySqlDatabaseTool("adminClient");

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
        $commandParams = array ("studentID", "semesterID");

        /* @var $commandResult (commandResult) The result model.     */
        $commandResult;

        /* @var $result (object) The output of PDO sql executes.     */
        $result = NULL;

        /* @var $sqlQuery (object) The query to execute on service.  */
        $sqlQuery = NULL;

        // --- Main Routine ------------------------------------------//

        // Check if the request contains all necessary parameters.
        if ( $this->isValidContent ($this->requestContent, $commandParams) ) {

          // Try to match a schedule up to a student and pull it down.
          try {

            // 1. Get schedule meta data if it exists so we can pull details.
            $sqlQuery = 'SELECT scheduleID, creditHours WHERE
              studentID = ? AND semesterID = ? LIMIT 1';
            $sqlParams = array($this->requestContent["studentID"],$this->requestContent["semesterID"]);

            // 2. If data could be found pull class data.
            if ($this->dbAccess->executeQuery($sqlQuery,$sqlParams)) {
              $result = $this->dbAccess->getResults();
              var_dump($result);
              if ($result != null) {
                  $sqlQuery = "SELECT s.semesterCode,s.sectionCode,s.seats,s.seatsOpen,s.sectionID,
                                d.depName, f.firstName, f.lastName, b.buildingName, l.classroom, t.meetDays,
                                t.creditHours, t.startTime, t.endTime, c.title, c.courseCode, c.description
                                FROM scheduleitem AS si
                                	JOIN section AS s
                                    	ON s.sectionCode = si.SectionCode
                                    JOIN course AS c
                                    	ON c.courseCode = s.courseCode
                                	JOIN department AS d
                                    	ON d.dID = c.dNum
                                    JOIN faculty AS f
                                    	ON f.facultyID = s.facultyID
                                    JOIN location AS l
                                    	ON l.locationID = s.locationID
                                    JOIN building AS b
                                    	ON b.buildingID = l.buildingID
                                    JOIN timeblock AS t
                                    	ON t.timeblockID = s.timeblockID
                                WHERE si.scheduleID = ?";
                  //$sqlParams = array($result[""]);
                }
            }
            else {

            }

          }

          catch (Exception $e) {
            $commandResult = new commandResult ("systemError");
            $commandResult->addValuePair ("Description","Database failure.");
          }
        }

        else {
        $commandResult = new commandResult ("invalidData");
        $commandResult->addValuePair ("Description","Invalid input parameters for AddCourse.");
        }

    }

    //---------------------------------------------------------------//
}

?>
