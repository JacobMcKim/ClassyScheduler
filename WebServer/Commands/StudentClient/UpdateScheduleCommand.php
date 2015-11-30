<?php
/* --------------------------------------------------------------------*
 * UpdateScheduleCommand.php                                           *
 * --------------------------------------------------------------------*
 * Description - This class allows stuendts to  add and remove courses *
 *  from their schedule.                                               *
 * --------------------------------------------------------------------*
 * Project: Classy Scheduler Web Server                                *
 * Author : Jacob A. McKim                                             *
 * Date Of Creation: 10 - 25 - 2015                                    *
 * ------------------------------------------------------------------- */

//===================================================================//
//  NOTES & BUGS AS OF 10- 25 -2015                                  //
//===================================================================//
/*
 *
 */


 /* --------------------------------------------------------------------*
  * Sample Request JSON                                                 *
  * --------------------------------------------------------------------*
  {
    "ServiceID": "UpdateSchedule",
    "ScheduleID": 1,
    "SectionCodeID": 1,
    "Operation" : "add"
  }
 */


 class UpdateScheduleCommand extends Command {

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
       $this->dbAccess = new MySqlDatabaseTool("studentClient");

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
        $commandParams = array ("scheduleID","sectionCodeID", "operation");

        /* @var $commandResult (commandResult) The result model.     */
        $commandResult;

        /* @var $result (object) The output of PDO sql executes.     */
        $result = NULL;

        /* @var $sqlQuery (object) The query to execute on service.  */
        $sqlQuery = NULL;

        /* @var $updateSQL (array) The update query specifics.       */
        $updateSQL;

        // --- Main Routine ------------------------------------------//

        // Check if the request contains all necessary parameters.
        if ( $this->isValidContent ($this->requestContent, $commandParams) ) {

          // Depending on the operation, either add or drop course from schedule.
          try {
              $sqlQuery = 'SELECT * FROM scheduleitem WHERE scheduleID = ? AND sectionCode = ?';
              $sqlParams = array($this->requestContent["scheduleID"],$this->requestContent["sectionCodeID"]);
              if ($this->dbAccess->executeQuery($sqlQuery,$sqlParams)) {
                $result = $this->dbAccess->getResults();
              }
              else {
                $commandResult = new commandResult ("systemError");
                $commandResult->addValuePair ("Description","Database failure.");
                return $commandResult;
              }

             // Determine which activity were preforming.
             if ($this->requestContent["operation"] == "add") {
               if ($result == 0) {
               $sqlQuery = 'INSERT INTO scheduleitem (scheduleID,sectionCode) VALUES(?,?)';
               $updateSQL = 's.seatsOpen = s.seatsOpen - 1, ss.creditHours = ss.creditHours + t.creditHours';
               }
               else {
                   $commandResult = new commandResult ("failed");
                   $commandResult->addValuePair ("Description","Scheduled course already exists.");
                   return $commandResult;
               }
             }
             else if ($this->requestContent["operation"] == "drop") {
               if ($result > 0) {
                 $sqlQuery = 'DELETE FROM scheduleitem WHERE scheduleID = ? AND sectionCode = ?';
                 $sqlParams = array($this->requestContent["scheduleID"],$this->requestContent["sectionCodeID"]);
                 $updateSQL = 's.seatsOpen = s.seatsOpen + 1, ss.creditHours = ss.creditHours - t.creditHours';
               }
               else {
                 $commandResult = new commandResult ("failed");
                 $commandResult->addValuePair ("Description","Scheduled course doesn't exist.");
                 return $commandResult;
               }
             }
             else {
               $commandResult = new commandResult ("invalidData");
               $commandResult->addValuePair ("Description","Invalid operation defined for command.");
               return $commandResult;
             }

             // Update the seat and hours recorded for the schedule.
             if ($this->dbAccess->executeQuery($sqlQuery,$sqlParams)) {
               $result = $this->dbAccess->getResults();

               if ($result > 0) {
                 $sqlQuery = 'UPDATE studentschedule AS ss
                    JOIN section AS s ON
                      s.sectionCode = ?
                    JOIN timeblock AS t
                      ON t.timeblockID = s.timeblockID
                  SET ' . $updateSQL . ' WHERE ss.scheduleID = ?';
                  $sqlParams = array($this->requestContent["sectionCodeID"],$this->requestContent["scheduleID"]);

                  // Respond with a pass.
                  if ($this->dbAccess->executeQuery($sqlQuery,$sqlParams)) {
                    $commandResult = new commandResult ("success");
                  }
                  else {
                    $commandResult = new commandResult ("systemError");
                    $commandResult->addValuePair ("Description","Database failure.");
                  }
                }

                else {
                  $commandResult = new commandResult ("failed");
                  $commandResult->addValuePair ("Description","Desired Schedule or Section ID doesn't exist.");
                }
              }

              else {
                $commandResult = new commandResult ("systemError");
                $commandResult->addValuePair ("Description","Database failure.");
              }
          }

          catch (Exception $e) {
            $commandResult = new commandResult ("systemError");
            $commandResult->addValuePair ("Description","Database failure.");
          }
        }
        else {
          $commandResult = new commandResult ("invalidData");
          $commandResult->addValuePair ("Description","Invalid input parameters for UpdateSchedule Service.");
        }

        return $commandResult;

    }

    //---------------------------------------------------------------//
}

?>
