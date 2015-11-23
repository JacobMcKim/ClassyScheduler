<?php
/* --------------------------------------------------------------------*
 * GetSemestersCommand.php                                             *
 * --------------------------------------------------------------------*
 * Description - This command returns all the semesters avalible in    *
 *    the system.                                                      *
 * --------------------------------------------------------------------*
 * Project: Classy Scheduler Web Server                                *
 * Author : Jacob A. McKim                                             *
 * Date Of Creation: 11- 22 -2015                                      *
 * ------------------------------------------------------------------- */

//===================================================================//
//  NOTES & BUGS AS OF 11- 22 -2015                                  //
//===================================================================//
/*
 *
 */

class GetSemestersCommand extends Command {

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
        $commandParams = array ();

        /* @var $commandResult (commandResult) The result model.     */
        $commandResult;

        /* @var $result (object) The output of PDO sql executes.     */
        $result = NULL;

        /* @var $sqlQuery (object) The query to execute on service.  */
        $sqlQuery = NULL;

        /* @var $semester (array) The semester list from the query.  */
        $semester;

        /* @var $semesterItem (array) A row from the query result.   */
        $semesterItem;

        /* @var $semesterList (array) the result to return to client.*/
        $semesterList = array();

        // --- Main Routine ------------------------------------------//

        // Check if the request contains all necessary parameters.
        if ( $this->isValidContent ($this->requestContent, $commandParams) ) {

          // TODO: 3. Brief Description of what is going to happen.
          try {
            $sqlQuery = 'SELECT * FROM semester WHERE ?=?';
            $sqlParams = array (1,1);
            if ($this->dbAccess->executeQuery($sqlQuery,$sqlParams)) {
              $result = $this->dbAccess->getResults();
              if ($result != null) {
                foreach ($result as &$semester) {
                  $semesterItem ["semesterID"] = $semester["semesterID"];
                  $semesterItem ["year"] = $semester["year"];
                  $semesterItem ["season"] = $semester["season"];
                  array_push($semesterList,$semesterItem);
                }

                // Return the result.
                $commandResult = new commandResult ("success");
                $commandResult->addValuePair ("semesters",$semesterList);
              }

              else {
                $commandResult = new commandResult ("failed");
                $commandResult->addValuePair ("Description","No semesters found.");
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
          $commandResult->addValuePair ("Description","Invalid input parameters for GetSemesters.");
        }

        // Return the command result.
        return $commandResult;

    }

    //---------------------------------------------------------------//
}

?>
