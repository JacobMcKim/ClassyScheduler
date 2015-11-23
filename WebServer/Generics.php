<?php
/* --------------------------------------------------------------------*
 * Generics.php                                                        *
 * --------------------------------------------------------------------*
 * Description - This php file contains generic tools/ resources that  *
 * are for standard use across all components of this web service.     *
 * --------------------------------------------------------------------*
 * Project: Classy Scheduler Web Server                                *
 * Author : McKim A. Jacob                                             *
 * Date Of Creation: 10 - 02 - 2015                                    *
 * ------------------------------------------------------------------- */

//===================================================================//
//  Includes                                                         //
//===================================================================//

// Data Models
require_once 'Models/ICommandResult.php';
require_once 'Models/CommandResult.php';

// Database Tools
require_once 'DatabaseTools/IDatabaseTool.php';
require_once 'DatabaseTools/MySqlDatabaseTool.php';

// Commands
require_once "Commands/ICommand.php";
require_once "Commands/Command.php";

// ## Account Management
require_once "Commands/AccountManagement/LoginCommand.php";
require_once "Commands/AccountManagement/LogoutCommand.php";
require_once "Commands/AccountManagement/CheckSessionCommand.php";

// ## Admin Panel commands
require_once "Commands/AdminClient/AddCourseCommand.php";
require_once "Commands/AdminClient/DeleteCourseCommand.php";
require_once "Commands/AdminClient/UpdateClassCommand.php";

// ## Student Panel commands
require_once "Commands/StudentClient/SearchCourseCommand.php";
require_once "Commands/StudentClient/UpdateScheduleCommand.php";
require_once "Commands/StudentClient/GetScheduleCommmand.php";
require_once "Commands/StudentClient/GetSemestersCommand.php";
require_once "Commands/StudentClient/GetFacRatingCommand.php";

// Controllers
require_once "Controllers/IController.php";
require_once "Controllers/RequestManager.php";

//===================================================================//
//  NOTES & BUGS AS OF 10-01-2015                                    //
//===================================================================//
/*
 * TODO- CLEAN UP DEBUG 10/2/15
 */
//===================================================================//
//  Global Variables/Constants                                       //
//===================================================================//

/* N/A */

//===================================================================//
// Helper Methods                                                    //
//===================================================================//

/******************************************************************
  * @Description - This method is used to randomly generate string
  *  values to be used in security parameters and other various
  * implmentations in the API service.
  *
  * @param Length - The length of the string to be generated, By
  * default it's 10 characters (Integer).
  *
  * @return The randomly generated string (String).
  *
  *****************************************************************/
function keygen($length=10)
{

    // --- Variable Declarations  -------------------------------//

    /* @var $key (String) - The output key generated. */
    $key = '';

    /* @var $inputs (String) List of all possible characters to use. */
    $inputs = array_merge(range('z','a'),range(0,9),range('A','Z'));

    // --- Main Routine ------------------------------------------//

    // Randomly seed the random generator.
    list($usec, $sec) = explode(' ', microtime());
    mt_srand((float) $sec + ((float) $usec * 100000));

    // Pick elements from the list and append to the key.
    for($i=0; $i<$length; $i++) {
        $key .= $inputs{mt_rand(0,61)};
    }

    // Return the result.
    return $key;
}


//===================================================================//

?>
