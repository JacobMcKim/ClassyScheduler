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

      /* Whether or not the command was found.                   */
      $commandFound = True;

      /* @var $serviceResult Array Contains the command results. */
      $serviceResult = NULL;

      // --- Main Routine -----------------------------------------//

      // Make sure the serviceID element exists if so execute.
      if ($requestData != NULL && array_key_exists("ServiceID",$requestData)
                                      && $requestData ["ServiceID"] != NULL) {

        // Parse for the right command to be displayed.
        switch ( $requestData ["ServiceID"] ) { /*
          case "AddMajor" : // Creates a course.
            $iCommand = new AddMajorCommand ($requestData);
            break;
          case "DeleteMajor" : // Signs user in and creates a session.
            $iCommand = new DeleteMajorCommand ($requestData);
            break;
          case "UpdateMajor" : // Signs user out and destroys session.
            $iCommand = new UpdateMajorCommand ($requestData);
            break;
          case "UpdateMajors" : // Signs user out and destroys session.
            $iCommand = new UpdateMajorsCommand ($requestData);
            break;
          case "GetMajor" : // Updates a users credentials.
            $iCommand = new GetMajorCommand ($requestData);
            break;
          case "GetMajors" : // Gets Data about the user.
            $iCommand = new GetMajorsCommand ($requestData);
            break; */
          case "AddCourse" : // Configures a password change.
            $iCommand = new AddCourseCommand ($requestData);
            break;
          case "DeleteCourse" : // Changes a users password.
            $iCommand = new DeleteCourseCommand ($requestData);
            break;
          /*case "DeleteCourses" : // Changes a users password.
            $iCommand = new DeleteCoursesCommand ($requestData);
            break;*/
          case "UpdateCourse" : // Change the profile picture.
            $iCommand = new UpdateCourseCommand ($requestData);
            break;
          /*
          case "GetCourse" : // Gets the profile picture.
            $iCommand = new GetCourseCommand ($requestData);
            break;
          case "GetCourses":
            $iCommand = new GetCoursesCommand ($requestData);
            break;
          case "AddSelection" :
            $iCommand = new AddSectionCommand ($requestData);
            break;
          case "DeleteSection" :
            $iCommand = new DeleteSectionCommand ($requestData);
            break;
          case "DeleteSections" :
            $iCommand = new DeleteSectionsCommand ($requestData);
            break;
          case "UpdateSection" :
            $iCommand = new UpdateSectionCommand ($requestData);
            break;
          case "GetSection" :
            $iCommand = new GetSectionCommand ($requestData);
            break;
          case "GetSections" :
            $iCommand = new GetSectionsCommand ($requestData);
            break;
          case "Login" :
            $iCommand = new LoginCommand ($requestData);
            break;
          case "Logout" :
            $iCommand = new LogoutCommand ($requestData);
            break;
          case "ForgotPasswords" :
            $iCommand = new ForgotPasswordsCommand ($requestData);
            break; */
          case "SearchCourse" :
            $iCommand = new SearchCourseCommand ($requestData);
            break;
          case "UpdateCourse":
            $iCommand = new UpdateScheduleCommand ($requestData);
            break;
          default: // Service requested not found.
            $commandFound = false;
        }

        // Execute command.
        if ($commandFound) {
          $serviceResult = $iCommand -> executeCommand();
        }
        else {
          $serviceResult = new commandResult ("invalidData");
          $serviceResult->addValuePair ("Description","Service requested not found.");
        }
      }

      else // Improperly formated request.
      {
        $serviceResult = new commandResult ("invalidData");
        $serviceResult->addValuePair ("Description","Improper request format.");
      }

      // give back the result.
      return $serviceResult;

  }

  /* A method called to send back a result from the executed command on the
  server with the results.*/
  public static function returnResult ($resultObj) {
    if ($resultObj != NULL) {
      echo ($resultObj->convertToJSON());
    }

  }

  //----------------------------------------------------------------//

}

?>
