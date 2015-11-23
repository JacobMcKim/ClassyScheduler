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

      /* @var $iCommand The command object to execute.          */
      $iCommand = NULL;

      /* @var $sessionCheckCommand The command to check session*/
      $sessionCheckCommand;

      // --- Main Routine -----------------------------------------//

      // Make sure the serviceID element exists if so execute.
      if ($requestData != NULL && array_key_exists("ServiceID",$requestData)
                                      && $requestData ["ServiceID"] != NULL) {

        // check session if needed.
        if ($requestData ["ServiceID"] != "Login") {
          $sessionCheckCommand = new CheckSessionCommand ($requestData);
          $serviceResult = $sessionCheckCommand -> executeCommand();

          // Invalid session data flag it.
          if ($serviceResult->getResultType() != "success") {
            return $serviceResult;
          }
        }

        // Parse for the right command to be displayed.
        switch ( $requestData ["ServiceID"] ) {

          // --- Account Management commands --- //

          case "Login" :
            $iCommand = new LoginCommand ($requestData);
            break;
          case "Logout" :
            $iCommand = new LogoutCommand ($requestData);
            break;

          // --- Admin panel commands --- //

          case "AddCourse" :
            $iCommand = new AddCourseCommand ($requestData);
            break;
          case "DeleteCourse" :
            $iCommand = new DeleteCourseCommand ($requestData);
            break;
          case "UpdateCourse" :
            $iCommand = new UpdateCourseCommand ($requestData);
            break;

          // --- Student panel commands --- //

          case "SearchCourse" :
            $iCommand = new SearchCourseCommand ($requestData);
            break;
          case "UpdateSchedule":
            $iCommand = new UpdateScheduleCommand ($requestData);
            break;
          case "GetSchedule" :
            $iCommand = new GetScheduleCommmand ($requestData);
            break;
          case "GetSemesters" :
            $iCommand = new GetSemestersCommand ($requestData);
            break;
          case "GetFacRatings":
            $iCommand = new GetFacRatingCommand ($requestData);
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
