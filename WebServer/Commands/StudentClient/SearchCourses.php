<?php
/* --------------------------------------------------------------------*
 * SearchCoursesCommand.php                                            *
 * --------------------------------------------------------------------*
 * Description - This class allows students to search for courses by a *
 *                           given input string.                       *
 * --------------------------------------------------------------------*
 * Project: Classy Scheduler Web Server                                *
 * Author : McKim A. Jacob                                             *
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
     "searchPhrase": "CIS 350",
   }
  */

class SearchCoursesCommand extends Command {

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
        $commandParams = array ("searchPhrase");

        /* @var $commandResult (commandResult) The result model.     */
        $commandResult;

        /* @var $result (object) The output of PDO sql executes.     */
        $result = NULL;

        /* @var $sqlQuery (object) The query to execute on service.  */
        $sqlQuery = NULL;

        /* @var $searchPhrase (String) The phrase to do the search.  */
        $searchPhrase = NULL;

        // --- Main Routine ------------------------------------------//

        // Check if the request contains all necessary parameters.
        if ( $this->isValidContent ($this->requestContent, $commandParams) ) {

          try {

            $searchPhrase = $this->requestContent["searchPhrase"];

            // 1. Select the type of data were working with.
            if ($searchPhrase == "*") {
              $sqlQuery = 'SELECT * FROM courses';
            }
            else if (preg_match('~^[a-zA-Z]{3} [0-9]{3}$~',$searchPhrase)) { // XXX XXX: CIS 350
              $sqlQuery = 'SELECT * FROM courses WHERE cID = ? AND depID = ?';
              $sqlParams = array(substr($searchPhrase,0,3),substr($searchPhrase,4,3));
            }
            else if ($searchPhrase != "") {
              $sqlQuery = 'SELECT * FROM courses WHERE title LIKE ? OR description LIKE ?';
              $sqlParams = array($searchPhrase,$searchPhrase);
            }

            // 2. Run the statement.
            if ($this->dbAccess->executeQuery($sqlQuery,$sqlParams)) {

              // 3. get the results.

              // 4. Per result pull all the sections related to the course.

              // 5. Build the response of classes.

            }

          }

          catch (Exception $e) {
            $commandResult = new commandResult ("systemError");
            $commandResult->addValuePair ("Description","Database failure.");
          }
        }

        else {
        $commandResult = new commandResult ("invalidData");
        $commandResult->addValuePair ("Description","Invalid input parameters for SearchCourses service.");
        }

    }

    //---------------------------------------------------------------//
}

?>
