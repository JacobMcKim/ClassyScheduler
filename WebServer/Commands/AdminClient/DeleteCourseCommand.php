<?php
/* --------------------------------------------------------------------*
 * TODO DeleteCourseCommand.php                                   *
 * --------------------------------------------------------------------*
 * Description - Command allows administrator to delete a class        *
 * --------------------------------------------------------------------*
 * Project: Classy Scheduler Web Server                                *
 * Author : Kristopher Trevino                                     *
 * Date Of Creation: 10-13-2015                       *
 * ------------------------------------------------------------------- */

//===================================================================//
//  NOTES & BUGS AS OF 10-13-2015                     //
//===================================================================//
/*
 *
 */

class DeleteClassCommand extends Command {

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

         // type.
        $this->dbAccess = new  ();
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
        $commandResult = new commandResult();

        /* @var $result (object) The output of PDO sql executes.     */
        $result = NULL;

        /* @var $sqlQuery (object) The query to execute on service.  */
        $sqlQuery = NULL;

        // --- Main Routine ------------------------------------------//

        // Check if the request contains all necessary parameters.
        if ( $this->isValidContent ($this->requestContent, $commandParams) ) {

          // TODO: 3. Brief Description of what is going to happen.
          try {
            //
            $sqlQuery = "SELECT title FROM course WHERE cID = ? AND dNum = ?";
            $sqlParams = array($requestContent["CourseID"],$requestContent["departmentID"]);
            $dbAccess -> executeQuery ($sqlQuery,"ii",$sqlParams);

            //
            if ($dbAccess->getResultSize() > 0) {
               $sqlQuery = "DELETE FROM course WHERE cID = ? AND dNum = ?";
               if ($dbAccess -> executeQuery ($sqlQuery,"ii",$sqlParams)) {
                 $result = new commandResult ("success");
               }
               else {
                 $result = new commandResult ("failed");
                 $result->addValuePair ("Description","class already exists.");
               }
          }

          catch (Exception $e) {
            $result = new commandResult ("systemError");
            $result->addValuePair ("Description","Database failure.");
          }

          // Return the result of the command.
          return $commandResult;
        }
    }

    //---------------------------------------------------------------//
}