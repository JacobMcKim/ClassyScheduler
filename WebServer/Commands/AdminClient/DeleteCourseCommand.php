<?php
/* --------------------------------------------------------------------*
 * DeleteCourseCommand.php                                             *
 * --------------------------------------------------------------------*
 * Description - Command allows administrator to delete a course.      *
 * --------------------------------------------------------------------*
 * Project: Classy Scheduler Web Server                                *
 * Author : Kristopher Trevino                                         *
 * Date Of Creation: 10-13-2015                                        *
 * ------------------------------------------------------------------- */

//===================================================================//
//  NOTES & BUGS AS OF 10-13-2015                                    //
//===================================================================//
/*
 *
 */

class DeleteCourseCommand extends Command {

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
        $commandParams = array ("ServiceID", "DepartmentID", "CourseID");

        /* @var $commandResult (commandResult) The result model.     */
        $commandResult;

        /* @var $sqlQuery (object) The query to execute on service.  */
        $sqlQuery = NULL;

        // --- Main Routine ------------------------------------------//

        // Check if the request contains all necessary parameters.
        if ( $this->isValidContent ($this->requestContent, $commandParams) ) {

          // TODO: 3. Brief Description of what is going to happen.
          try {

            // Check if the class exists.
            $sqlQuery = "SELECT * FROM course WHERE cID = ? AND dNum = ?";
            $sqlParams = array($this->requestContent["CourseID"],$this->requestContent["DepartmentID"]);
            $this->dbAccess->executeQuery ($sqlQuery,$sqlParams);

            // Delete the class if it does exist.
            if ($this->dbAccess->getResults() != NULL) {
               $sqlQuery = "DELETE FROM course WHERE cID = ? AND dNum = ?";
               if ($this->dbAccess->executeQuery ($sqlQuery,$sqlParams)) {
                 $commandResult = new commandResult ("success");
               }

               else {
                 $commandResult = new commandResult ("failed");
                 $commandResult->addValuePair ("Description","Falied to delete class.");
               }
             }

             else {
               $commandResult = new commandResult ("failed");
               $commandResult->addValuePair ("Description","Course doesn't exist.");
             }
           }

          catch (Exception $e) {
            $commandResult = new commandResult ("systemError");
            $commandResult->addValuePair ("Description","Database failure.");
          }

          // Return the result of the command.
          return $commandResult;
        }

    }

    //---------------------------------------------------------------//
}
