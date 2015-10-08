<?php
/* --------------------------------------------------------------------*
 * TODO (Fill in file name here).php                                   *
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

class exampleCommand extends Command {

    //---------------------------------------------------------------//
    // Class Atributes                                               //
    //---------------------------------------------------------------//
    /* @var $dbAccess () The database access object linking to DB.   */
    private $dbAccess;

    /* @var $requestContent (Array) The content of the user request. */
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
        $this->dbAccess = new  (); // TODO: 1. Determine which Database class to use based off command type.
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
        $commandParams = array ("username", "password"); // TODO: 2. Fill out required field items for command.

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
                // TODO 4: Implement code.
            }

            catch (PDOException $pdoE) {
                // TODO 5: Perform fault out code and return failure.
            }

            // Return the result of the command.
            return $commandResult;
        }
    }

    //---------------------------------------------------------------//
}
