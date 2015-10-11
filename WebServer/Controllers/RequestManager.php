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
                    case "AddMajorCommand" : // Creates a course.
                        $iCommand = new AddMajorCommand ($requestData);
                        break;
                    case "DeleteMajorCommand" : // Signs user in and creates a session.
                        $iCommand = new DeleteMajorCommand ($requestData);
                        break;
                    case "UpdateMajorCommand" : // Signs user out and destroys session.
                        $iCommand = new UpdateMajorCommand ($requestData);
                        break;
                    case "UpdateMajorsCommand" : // Signs user out and destroys session.
                        $iCommand = new UpdateMajorsCommand ($requestData);
                        break;
                    case "GetMajorCommand" : // Updates a users credentials.
                        $iCommand = new GetMajorCommand ($requestData);
                        break;
                    case "GetMajorsCommand" : // Gets Data about the user.
                        $iCommand = new GetMajorsCommand ($requestData);
                        break;
                    case "AddClassCommand" : // Configures a password change.
                        $iCommand = new AddClassCommand ($requestData);
                        break;
                    case "DeleteClassCommand" : // Changes a users password.
                        $iCommand = new DeleteClassCommand ($requestData);
                        break;
                    case "DeleteClassesCommand" : // Changes a users password.
                        $iCommand = new DeleteClassesCommand ($requestData);
                        break;
                    case "UpdateClassCommand" : // Change the profile picture.
                        $iCommand = new UpdateClassCommand ($requestData);
                        break;
                    case "GetClassCommand" : // Gets the profile picture.
                        $iCommand = new GetClassCommand ($requestData);
                        break;
                    case "GetClassesCommand"
                        $iCommand = new GetClassesCommand ($requestData);
                        break;
                    case "AddSelectionCommand"
                        $iCommand = new AddSelectionCommand ($requestData);
                        break;
                    case "DeleteSectionCommand"
                        $iCommand = new DeleteSectionCommand ($requestData);
                        break;
                    case "DeleteSectionsCommand"
                        $iCommand = new DeleteSectionsCommand ($requestData);
                        break;
                    case "UpdateSectionCommand"
                        $iCommand = new UpdateSectionCommand ($requestData);
                        break;
                    case "GetSectionCommand"
                        $iCommand = new GetSectionCommand ($requestData);
                        break;
                    case "GetSectionsCommand"
                        $iCommand = new GetSectionsCommand ($requestData);
                        break;
                    case "LoginCommand"
                        $iCommand = new LoginCommand ($requestData);
                        break;
                    case "LogoutCommand"
                        $iCommand = new LogoutCommand ($requestData);
                        break;
                    case "ForgotPasswordsCommand"
                        $iCommand = new ForgotPasswordsCommand ($requestData);
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
