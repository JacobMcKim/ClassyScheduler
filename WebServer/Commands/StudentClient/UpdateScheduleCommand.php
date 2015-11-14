<?php
/* --------------------------------------------------------------------*
 * AddToScheduleCommand.php                                            *
 * --------------------------------------------------------------------*
 * Description - This class allows students to add a section of a      *
 * course to their schedule and validates that the action is possible. *
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
                var_dump($result);
              }
              else {
                $commandResult = new commandResult ("systemError");
                $commandResult->addValuePair ("Description","Database failure.");
                return $commandResult;
              }

             // Determine which activity were preforming.
             if ($this->requestContent["operation"] == "add") {
               if (count($result) == 0) {
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
               if (count($result) > 0) {
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
