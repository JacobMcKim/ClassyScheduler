<?php
/* --------------------------------------------------------------------*
 * LogoutCommand.php                                                   *
 * --------------------------------------------------------------------*
 * Description - This class allows users to log out of their account.  *
 * --------------------------------------------------------------------*
 * Project: Classy Scheduler Web Server                                *
 * Author : Jacob A. McKim                                             *
 * Date Of Creation: 11 - 22 - 2015                                    *
 * ------------------------------------------------------------------- */

//===================================================================//
//  NOTES & BUGS AS OF 11 - 22 - 2015                                //
//===================================================================//
/*
 *
 */

class LogoutCommand extends Command {

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
        $commandParams = array ("email", "password", "sessionID");

        /* @var $commandResult (commandResult) The result model.     */
        $commandResult;

        /* @var $result (object) The output of PDO sql executes.     */
        $result = NULL;

        /* @var $sqlQuery (object) The query to execute on service.  */
        $sqlQuery = NULL;

        // --- Main Routine ------------------------------------------//

        // Check if the request contains all necessary parameters.
        if ( $this->isValidContent ($this->requestContent, $commandParams) ) {

          // TODO: 3. Brief Description of what is going to happen.
          try {
            // TODO 4: Implement code.
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

        // Return the command result.
        return $commandResult;

    }

    //---------------------------------------------------------------//
}

?>
