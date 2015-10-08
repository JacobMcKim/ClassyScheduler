<?php
/* --------------------------------------------------------------------*
 * Command.php                                                         *
 * --------------------------------------------------------------------*
 * Description - This abstract class implements the ICommand interface *
 * to lay the foundational elements for all command classes that are   *
 * derived from it. It contains a generic isValidContent method that   *
 * can check if all reqired parameters exist. It can be overridden.    *
 * --------------------------------------------------------------------*
 * Project: Classy Scheduler Web Server                                *
 * Author : McKim A. Jacob                                             *
 * Date Of Creation: 10 - 01 - 2015                                    *
 * ------------------------------------------------------------------- */

//===================================================================//
//  NOTES & BUGS AS OF 10-01-2015                                     //
//===================================================================//
/*
 * TODO - Convert user ID over query over to classy Scheduler. (Line 102)
 */

//===================================================================//
//  Includes                                                         //
//===================================================================//

//===================================================================//
// Class Definition                                                  //
//===================================================================//
abstract class Command implements ICommand {

    //---------------------------------------------------------------//
    // Absract Methods                                               //
    //---------------------------------------------------------------//
    /* Executes the command defined for the service implementation. */
    abstract public function executeCommand ();

    //---------------------------------------------------------------//
    // Concrete Class Methods                                        //
    //---------------------------------------------------------------//

    /* Validates the commands parameters before execution. */
    protected function isValidContent( $Content, $arrayParams ) {

        // --- Variable Declarations  -------------------------------//

        /* @var $isValid Boolean The result if all values are there. */
        $isValid = true;

        /* @var $isValid String The current parameter being checked. */
        $param = NULL;

        // --- Main Routine -----------------------------------------//

        // Check each parameter and insure they are there.
        foreach ( $param as $arrayParams )
        {
            if ( $Content [$param] == NULL ) {
                $isValid = false;
                break;
            }
        }

        return $isValid;

    }


    /******************************************************************
     * @Description - This method is used to validate whether a
     * session for a given account actively exists in the sessions
     * database.
     *
     * @param $dbRequest - The IDatabaseTool used to communicate with
     * the database (IDatabaseTool).
     *
     * @param $userID - The user id to be used in the session search.
     *
     * @param $sessionID The session id used as a password to make sure
     * were talking to the right session.
     *
     * @return A boolean value depending on whether a session was found
     * matching the given criteria. (True - Yes / False - No)
     *
     * @Throws PDOException - If something goes wrong with the fetch
     * command this will throw this. It should be handled in the class
     * calling this method.
     *
     *****************************************************************/
    public static function checkSession ($dbRequest, $userID, $sessionID) {

        // --- Variable Declarations  -------------------------------//

        /* @var $sqlQuery (object) The query to execute on service. */
        $sqlQuery = "";

        /* @var $result (object) The output of PDO sql executes.  */
        $result = NULL;

        /* @var $commandResult (boolean) the result of this command. */
        $commandResult = false;

        // --- Main Routine ------------------------------------------//
        if ($userID != NULL) {

            // Search for any sessions that exist.
            $sqlQuery = "SELECT UserID FROM CodeStormUsers.Sessions "
                . "WHERE UserID = :userid AND SessionID = :sessionid "
                . "AND ExpireTime > CURRENT_TIMESTAMP LIMIT 1";
            $result = $dbRequest->executeFetch($sqlQuery,
                ["userid" => $userID, "sessionid" => $sessionID]);
            // Determine if we found the session were looking for.
            if ($commandResult["UserID"] != NULL) {
                $commandResult = true;
            }
        }

        // Return the result of the execution.
        return $commandResult;

    }

    //---------------------------------------------------------------//

}
