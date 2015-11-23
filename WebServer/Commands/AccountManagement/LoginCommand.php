<?php
/* --------------------------------------------------------------------*
 * LoginCommand.php                                                    *
 * --------------------------------------------------------------------*
 * Description - This class allows users to login to their account.    *
 * --------------------------------------------------------------------*
 * Project: Classy Scheduler Web Server                                *
 * Author : Jacob A. McKim                                             *
 * Date Of Creation: 11 - 22 - 2015                                    *
 * ------------------------------------------------------------------- */

//===================================================================//
//  NOTES & BUGS AS OF 11 - 22 - 2015                                //
//===================================================================//
/*
 *
 */

class LoginCommand extends Command {

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
      $this->dbAccess = new MySqlDatabaseTool("adminClient");

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
        $commandParams = array ("email", "password");

        /* @var $commandResult (commandResult) The result model.     */
        $commandResult;

        /* @var $result (object) The output of PDO sql executes.     */
        $result = NULL;

        /* @var $sqlQuery (object) The query to execute on service.  */
        $sqlQuery = NULL;


        $uniqueID = ; // TODO 

        // --- Main Routine ------------------------------------------//

        // Check if the request contains all necessary parameters.
        if ( $this->isValidContent ($this->requestContent, $commandParams) ) {

          // Attempt to check the password and user name. 
          try {

          	// Build sql check query. 
            $sqlQuery = 'SELECT s.studentID, s.password, s.salt, i.firstname, i.lastname, 
						    i.classStanding i.creditHours FROM student AS s 
			            	JOIN studentinfo AS i 
			            		ON s.studentID = i.studentID  
			            	WHERE email = ?';
            $sqlParams = array ($this->requestContent["email"]));

            if ($this->dbAccess->executeQuery ($sqlQuery,$sqlParams)) {
              $userDataRes = $this->dbAccess->getResults();

              // check the password to see if it matches.
              if ($result != null) {
                $checkPassword = $crypt(this->requestContent["password"]),
                                                '$2a$07' . $result[0]["salt"]);
                if ($checkPassword == $userDataRes[0]["password"]) {
                	
                    $sqlQuery = 'INSERT INTO session 
                    		(studentID, sessionKey, createTime, expireTime) 
                    		VALUES ?, ?, CURRENT_TIMESTAMP, NOW() + 30 MINUTE';
            		$sqlParams = array ($userDataRes[0]["studentID"],$uniqueID);

            		// Execute and build the login result. 
            		if ($this->dbAccess->executeQuery ($sqlQuery,$sqlParams)) {

            		}
            		else {
        				$commandResult = new commandResult ("systemError");
            			$commandResult->addValuePair ("Description","Database failure.");
            		}

                }

                // Invalid password. 
                else {
                  $commandResult = new commandResult ("failed");
                  $commandResult->addValuePair ("Description","Invalid email or password.");
                }


              }

              // Account not found. 
              else {
              	$commandResult = new commandResult ("failed");
                $commandResult->addValuePair ("Description","Invalid email or password.");
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
        $commandResult->addValuePair ("Description","Invalid input parameters for AddCourse.");
        }

        // Return the command result.
        return $commandResult;

    }

    //---------------------------------------------------------------//
}

?>
