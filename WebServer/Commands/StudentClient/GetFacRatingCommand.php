<?php
/* --------------------------------------------------------------------*
 * GetFacRatingCommand.php                                             *
 * --------------------------------------------------------------------*
 * Description - This class gets the ratings for a faculty member for  *
 *    a particular class.                                              *
 * --------------------------------------------------------------------*
 * Project: Classy Scheduler Web Server                                *
 * Author : Jacob A. McKim                                             *
 * Date Of Creation: 11- 22 -2015                                      *
 * ------------------------------------------------------------------- */

//===================================================================//
//  NOTES & BUGS AS OF 11- 22 -2015                                  //
//===================================================================//
/*
 *
 */

class GetFacRatingCommand extends Command {

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
        $commandParams = array ("facultyID", "courseID");

        /* @var $commandResult (commandResult) The result model.     */
        $commandResult;

        /* @var $result (object) The output of PDO sql executes.     */
        $result = NULL;

        /* @var $sqlQuery (object) The query to execute on service.  */
        $sqlQuery = NULL;

        /* @var $rating (array) The rating list from the query.      */
        $rating;

        /* @var $ratingItem (array) A row from the query result.     */
        $ratingItem;

        /* @var $ratingList (array) the result to return to client.  */
        $ratingList = array();

        // --- Main Routine ------------------------------------------//

        // Check if the request contains all necessary parameters.
        if ( $this->isValidContent ($this->requestContent, $commandParams) ) {

          // Request for all the ratings for a particular faculty memeber.
          try {
            $sqlQuery = 'SELECT f.description, f.rating, f.time,s.firstName,s.lastName,s.classStanding
                            FROM facultyratings AS f
                            	JOIN studentinfo AS s
                                	ON s.studentID = f.studentID
                            WHERE f.facultyID = ? AND f.courseCode = ?';
            $sqlParams = array($this->requestContent["facultyID"],$this->requestContent["courseID"]);

            if ($this->dbAccess->executeQuery($sqlQuery,$sqlParams)) {
              $result = $this->dbAccess->getResults();
              if ($result != null) {
                foreach ($result as &$rating) {
                  $ratingItem["rating"] = $rating["rating"];
                  $ratingItem["description"] = $rating["description"];
                  $ratingItem["revClassStanding"] = $rating["classStanding"];
                  $ratingItem["revFirstName"] = $rating["firstName"];
                  $ratingItem["revLastName"] = $rating["lastName"];
                  array_push($ratingList,$ratingItem);
                }

                // Return the ratings.
                $commandResult = new commandResult ("success");
                $commandResult->addValuePair ("ratings",$ratingList);
              }
              else {
                $commandResult = new commandResult ("failed");
                $commandResult->addValuePair ("Description","No ratings found.");
              }
            }
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
          $commandResult->addValuePair ("Description","Invalid input parameters for GetFacRatings.");
        }

        // Return the command result.
        return $commandResult;

    }

    //---------------------------------------------------------------//
}

?>
