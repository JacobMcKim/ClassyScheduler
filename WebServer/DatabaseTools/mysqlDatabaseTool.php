<?php
/* --------------------------------------------------------------------*
 * MySqlDataBaseTool.php                                               *
 * --------------------------------------------------------------------*
 * Description - This class is used as a default parent class for any  *
 * MySQL based DB Tools to use as their foundation. It implements all  *
 * methods defined in the IDatabaseTool inteface and is out of the box *
 * ready to go for tayloring to specific Connections.                  *
 * --------------------------------------------------------------------*
 * Project: Classy Scheduler Web Server                                *
 * Author : McKim A. Jacob                                             *
 * Date Of Creation: 10 - 01 - 2015                                    *
 * ------------------------------------------------------------------- */

//===================================================================//
//  NOTES & BUGS AS OF 10-01-2015                                     //
//===================================================================//
/*
 *
 */

 //===================================================================//
 // Class Definition                                                  //
 //===================================================================//
class MySqlDatabaseTool Implements IDatabaseTool {

  //---------------------------------------------------------------//
  // Class Atributes                                               //
  //---------------------------------------------------------------//

  /* @var $dbConnect (MySQLi) The connection to the DB service.    */
  private $dbConnect = NULL;

  /* The database name were connecting to.                         */
  private $database = "classy";

  /* The host address of the database service.                     */
  private $host = "localhost";#"127.0.0.01:3306";

  /* @var $isOpen (Boolean) Whether or not the connection is open. */
  private $isOpen = false;

  /* The account password to use when signing into the service.    */
  private $password = NULL;

  /* The account username to use when signing into the service.    */
  private $user = NULL;

  /* The command to be executed by dbconnect object .              */
  private $queryStmt = NULL;

  /* The number of results we have.                                */
  private $resultCount = 0;

  /* The number of results we have.                                */
  private $resultArray = NULL;

  //---------------------------------------------------------------//
  // Constructor/Destructors                                       //
  //---------------------------------------------------------------//
  /******************************************************************
   * @Description - Called to generate a MySqlDatabase Tool.
   *
   * @param $requestData - The json request data required to make the
   * request.
   *
   * @return None
   *
   *****************************************************************/
  function __construct($connectionType) {

    if ($connectionType == "studentClient") {
      $this->user = "student";
      $this->password = "JvDBDzQ6TTwQxXU9";
    }
    elseif ($connectionType == "adminClient") {
      $this->user = "admin";
      $this->password = "nPmCPeWu3nC84xCF";
    }
    else {
      throw new Exception ("MySqlDataBaseTool (Constructor): "
              . "invalid connection type.");
    }

      // Attempt to open the connection to the database.
      if ( !$this->openConnection() ) {
          throw new Exception ("MySqlDataBaseTool (Constructor): "
                  . "Failed to connect.");
      }
      else {
          $this->isOpen = true;
      }

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

      // Attempt to open the connection to the database.
      try {
          $this->dbConnect = NULL;
          $this->isOpen = false;
      }
      catch (Exception $e) {
          throw new Exception ("MySqlDataBaseTool (Destructor): "
                  . "Failed to close connection.");
      }

  }
  //---------------------------------------------------------------//
  // Class Methods                                                 //
  //---------------------------------------------------------------//

  /* closes the connection to the DB service. */
  public function closeConnection() {

      // --- Variable Declarations  -------------------------------//

      /* N/A */

      // --- Main Routine -----------------------------------------//

      // If the database is open close it.
      if ($this->isOpen) {
        $this->dbConnect = NULL;
        return true;
      }
      else {
        return false;
      }

  }


  /* Issues a query to the DB service as well fetches results.   */
  public function getResults () {

    // --- Variable Declarations  -------------------------------//

    /* (N/A) */

    // --- Main Routine -----------------------------------------//
    if ($this->resultArray != NULL && $this->resultCount > 0) {
      return $this->resultArray;
    }
    else {
      return NULL;
    }

  }

  /* Execute a sql command to the database. */
  public function executeQuery ($RequestString,$RequestAtributes) {

      // --- Variable Declarations  -------------------------------//

      /* N/A */

      // --- Main Routine -----------------------------------------//

      // 1. Construct the query.
      if ($RequestString != null && mb_substr_count($RequestString,"?") == count ($RequestAtributes) ) {

        $this->queryStmt = $this->dbConnect->prepare($RequestString);
        $this->resultCount = $this->queryStmt->execute($RequestAtributes);
        if ($this->resultCount > 0) {
          $this->resultArray = $this->queryStmt->fetchAll(PDO::FETCH_ASSOC);
        }
        return true;
      }
      else {
        return false;
      }

  }

  /******************************************************************
   * @Description - An accessor method stating whether or not the
   * connection is open to the database or not.
   *
   * @param None
   *
   * @return Whether or not a connection exist (Boolean).
   *
   *****************************************************************/
  public function getIsOpen () {
      return $this->isOpen;

  }

  /* Opens a connection to the DB service. */
  public function openConnection() {

      // --- Variable Declarations  -------------------------------//
      /* @var $success (boolean) The success of openConnection. */
      $success = true;

      // --- Main Routine -----------------------------------------//

      // Make sure we haven't already opened the service.
      if (!$this->isOpen)
      {
          // Attempt opening the service.
          try {
            $this->dbConnect = new PDO ('mysql:host=localhost;dbname=classy;charset=utf8',$this->user,$this->password);
          }

          catch (Exception $e) {
              $success = false;
              throw new Exception ("In MySqlDataBaseTool(openConnection)"
                . " - connection not established.");
          }
      }

      // Return the execution result.
      return $success;

  }

  //---------------------------------------------------------------//

}

?>
