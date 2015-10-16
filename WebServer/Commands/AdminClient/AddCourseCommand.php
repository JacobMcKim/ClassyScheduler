<?php
/* --------------------------------------------------------------------*
 * AddCourseCommand.php                                                *
 * --------------------------------------------------------------------*
 * Description - This class adds a course to the table of courses      *
 *                     available at the institution.                   *
 * --------------------------------------------------------------------*
 * Project: Classy Scheduler Web Server                                *
 * Author : Jacob A. McKim                                             *
 * Date Of Creation: 10 - 8 - 2015                                     *
 * ------------------------------------------------------------------- */

//===================================================================//
//  NOTES & BUGS AS OF 10 - 8 - 2015                                 //
//===================================================================//
/*
 *
 */

 /* --------------------------------------------------------------------*
  * Sample Request JSON                                                 *
  * --------------------------------------------------------------------*
  {
    "ServiceID": "AddCourse",
    "DepartmentID": 1,
    "CourseID": 225,
    "Title": "Intro To Film",
    "Description": "test"
  }
 */

class AddCourseCommand extends Command {

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
        $commandParams = array ("DepartmentID", "CourseID", "Title", "Description");

        /* @var $commandResult (commandResult) The result model.     */
        $commandResult;

        /* @var $result (object) The output of PDO sql executes.     */
        $result = NULL;

        /* @var $sqlQuery (object) The query to execute on service.  */
        $sqlQuery = NULL;

        // --- Main Routine ------------------------------------------//

        // Check if the request contains all necessary parameters.
        if ( $this->isValidContent ($this->requestContent, $commandParams) ) {

            // 3. Brief Description of what is going to happen.
            try {
                // A. validate that the course doesn't exist already.
                $sqlQuery = "SELECT * FROM course WHERE cID = ? AND dNum = ?";
                $sqlParams = array($this->requestContent["CourseID"],$this->requestContent["DepartmentID"]);
                $this->dbAccess->executeQuery ($sqlQuery,$sqlParams);

                // B. If the course doesnt exist
                if ($this->dbAccess->getResults() == NULL) {

                   $sqlQuery = "INSERT INTO course (`dNum`, `cID`, `title`, `description`) VALUES (?,?,?,?)";
                   $sqlParams = array($this->requestContent["DepartmentID"],$this->requestContent["CourseID"],
                   $this->requestContent["Title"],$this->requestContent["Description"]);
                   if ($this->dbAccess->executeQuery ($sqlQuery,$sqlParams)) {
                     $commandResult = new commandResult ("success");
                   }
                   else {
                     $commandResult = new commandResult ("failed");
                     $commandResult->addValuePair ("Description","Database error creating class.");
                   }
                }
                else {
                  $commandResult = new commandResult ("failed");
                  $commandResult->addValuePair ("Description","class already exists.");
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

        // Return the result of the command.
        return $commandResult;
    }

    //---------------------------------------------------------------//
}
