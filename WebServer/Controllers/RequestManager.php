<?php
/* --------------------------------------------------------------------*
 * RequestManager.php                                                  *
 * --------------------------------------------------------------------*
 * Description - This class is used to provide access to all the       *
 *    Classy Scheduler services. This class dispatches the required    *
 *     command to execute and return a result back to the client.      *
 * --------------------------------------------------------------------*
 * Project: Classy Scheduler Web Server                                *
 * Author : McKim A. Jacob                                             *
 * Date Of Creation: 10 - 02 - 2015                                    *
 * ------------------------------------------------------------------- */

//===================================================================//
//  NOTES & BUGS AS OF 10-02-2015                                    //
//===================================================================//
/*
 *  TODO - Update list and way of returning result.
 */

//===================================================================//
class RequestManager Implements IController {

    //----------------------------------------------------------------//
    //  Class Function Defintions                                     //
    //----------------------------------------------------------------//

    /* This method is used to call out a command and give a response. */
    public static function callService ($requestData) {

        // --- Variable Declarations  -------------------------------//

        /* @var $serviceResult Array Contains the command results. */
        $serviceResult = NULL;

        // --- Main Routine -----------------------------------------//

        // Make sure the serviceID element exists if so execute.
        if ($requestData ["serviceID"] != NULL) {

            // Catch any exceptions that arise from the commands.
            try {

                // Parse for the right command to be displayed.
                switch ( $requestData ["ServiceID"] ) {
                    case "CreateAccount" : // Creates an account.
                        $iCommand = new CreateAccount ($requestData);
                        break;
                    case "Login" : // Signs user in and creates a session.
                        $iCommand = new Login ($requestData);
                        break;
                    case "Logout" : // Signs user out and destroys session.
                        $iCommand = new Logout ($requestData);
                        break;
                    case "UpdateUserData" : // Updates a users credentials.
                        $iCommand = new UpdateUserData ($requestData);
                        break;
                    case "GetUserData" : // Gets Data about the user.
                        $iCommand = new GetUserData ($requestData);
                        break;
                    case "ForgotPassword" : // Configures a password change.
                        $iCommand = new ForgotPassword ($requestData);
                        break;
                    case "ChangePassword" : // Changes a users password.
                        $iCommand = new ChangePassword ($requestData);
                        break;
                    case "ChangeProfilePicture" : // Change the profile picture.
                        $iCommand = new ChangeProfilePicture ($requestData);
                        break;
                    case "GetProfilePicture" : // Gets the profile picture.
                        $iCommand = new GetProfilePicture ($requestData);
                        break;
                    default: // Service requested not found.
                        $serviceResult = ["response" => -1,"debug =>"
                            ."ERROR IN CODESTORMCOMMANDER: INVALID "
                            . "COMMAND TYPE"];
                        break;
                }
                // Execute command.
                $serviceResult = $iCommand -> executeCommand();
            }

            catch (PDOException $pdoE) {
               $serviceResult = ["response" => -1,"debug =>"
                    ."ERROR IN REQUESTMANAGER: error in "
                    . "db Service :\n"+$pdoE->getMessage()];
            }

        }

        // Improperly formated request.
        else
        {
            $serviceResult = ["response" => -1,"debug =>"
                ."ERROR IN CODESTORMCOMMANDER: Improper "
                . "request format."];
        }

        // give back the result.
        return $serviceResult;

    }
    //----------------------------------------------------------------//

}
