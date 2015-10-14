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
 //  Includes                                                         //
 //===================================================================//
 require_once 'IDatabaseTool.php';

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
  const _database = "classy";

  /* The host address of the database service.                     */
  const _host = "127.00.00.01:3306";

  /* @var $isOpen (Boolean) Whether or not the connection is open. */
  private $isOpen = false;

  /* The account password to use when signing into the service.    */
  private $password = NULL;

  /* The account username to use when signing into the service.    */
  private $user = NULL;

    /* The command to be executed by dbconnect object .            */
  private $quertStmt = NULL;

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
      $user = "root";
      $password = "password";
    }
    elseif ($connectionType == "adminClient") {
      $user = "root";
      $password = "password";
    }
    else {
      throw new Exception ("MySqlDataBaseTool (Constructor): "
              . "invalid connection type.");
    }

      // Attempt to open the connection to the database.
      if ( !openConnection () ) {
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
      if ( !closeConnection () ) {
          throw new Exception ("MySqlDataBaseTool (Destructor): "
                  . "Failed to close connection.");
      }

      else {
          $this->isOpen = false;
      }

  }
  //---------------------------------------------------------------//
  // Class Methods                                                 //
  //---------------------------------------------------------------//

  /* closes the connection to the DB service. */
  public function closeConnection() {

      // --- Variable Declarations  -------------------------------//
      /* @var $success (boolean) The success of openConnection. */
      $success = true;

      // --- Main Routine -----------------------------------------//

      // If the database is open close it.
      if ($this->isOpen) {
          try {
              $this->dbConnect->close();
              $this->dbConnect = NULL;
          }
          catch (Exception $e) {
              $success = false;
          }
      }

      // Return the execution result.
      return $success;

  }


  /* Issues a query to the DB service as well fetches results.   */
  public function getResults () {

    // --- Variable Declarations  -------------------------------//

    /* @var $results The output of the executed command in raw form. */
    $resultsRaw = NULL;

    /* A row from the result of query.                            */
    $row = NULL;

    /* The result array.                                          */
    $resultArray = array();

    // --- Main Routine -----------------------------------------//

    // 1. Pull the results.
    $resultsRaw = $stmt->get_result();
    if ($resultsRaw != false) {

      // 2. Build the array.
      while ($row = $resultsRaw->fetch_array(MYSQLI_NUM)) {
        array_push($resultArray,$row);
      }

    }
    else {
      return $resultsRaw;
    }

  }


  /* Execute a sql command to the database. */
  public function executeQuery ($RequestString,$atributeTypes,$RequestAtributes) {

      // --- Variable Declarations  -------------------------------//

      /* @var $query - The command to be executed by PDO. */
      $query = NULL;

      // --- Main Routine -----------------------------------------//

      // 1. Construct the query.
      if ($RequestString != null && mb_substr_count ($RequestAtributes, ':')
                                                  == count ($RequestAtributes) ) {
        $quertStmt = $this->dbConnect->stmt_init();
        if ($quertStmt->mysqli_prepare($RequestString)) {
          $quertStmt = $quertStmt->mysqli_bind_param($atributeTypes,$RequestAtributes);

          // 2. Execute the query.
          return $quertStmt->execute();
        }

        else {
          return false;
        }

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

  /******************************************************************
   * @Description - An accessor method stating how many rows if any
   * are in the result set of the query.
   *
   * @param None
   *
   * @return the number of rows that are in the result (Boolean).
   *
   *****************************************************************/
  public function getResultSize () {
    return count($resultArray);
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
            $this->dbConnect = $mysqli->connect(_host,$username,$password,_database);
            if ($this->dbConnect->connect_errno) {
              $success = false;
              return $success;
            }
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
