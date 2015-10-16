<?php

/* --------------------------------------------------------------------*
 * MySQLDatabaseToolTest.php                                           *
 * --------------------------------------------------------------------*
 * Description - This class is used to test the execution of the       *
 *    MySQLDatabseTool used to communicate with the database used to   *
 *            store all the information for the application.           *
 * --------------------------------------------------------------------*
 * Project: Classy Scheduler Web Server                                *
 * Author : McKim A. Jacob                                             *
 * Date Of Creation: 10 - 15 - 2015                                    *
 * ------------------------------------------------------------------- */

//===================================================================//
//  NOTES & BUGS AS OF 10-15-2015                                    //
//===================================================================//
/*
 *
 */

 //===================================================================//
 // Class Definition                                                  //
 //===================================================================//


class MySQLDatabaseToolTest extends PHPUnit_Framework_TestCase {

  /******************************************************************
   * @Description - A test method to validate the creation of the
   *  database object.
   *
   * @param None
   *
   * @return None
   *
   *****************************************************************/
  public function testConfiguration () {

    // --- Variable Declarations  -------------------------------//

    /* The database tool were going to validate.                 */
    $testDb = NULL;

    // --- Main Routine -----------------------------------------//

    // A. Student Account Connection.
    $testDb = new MySqlDatabaseTool ("studentClient");
    $this->assertTrue ($testDb->getIsOpen());
    $testDb = NULL;

    // B. Admin Account Connection.
    $testDb = new MySqlDatabaseTool ("adminClient");
    $this->assertTrue ($testDb->getIsOpen());
    $testDb = NULL;

    // C. Test for failure.
    try {
      $testDb = new MySqlDatabaseTool ("");
    }
    catch (Exception $e) {
      $this->assertEquals ("Failed to connect.",$e->getMessage());
    }
  }

  /******************************************************************
   * @Description - A test method to the execution of queries.
   *
   * @param None
   *
   * @return None
   *
   *****************************************************************/
  public function testExecuteQuery () {

    // --- Variable Declarations  -------------------------------//

    /* The database tool were going to validate.                 */
    $testDb = NULL;

    /* The database tool were going to validate.                 */
    $result = NULL;

    // --- Main Routine -----------------------------------------//

    // Pre-Config. Configure Connection.
    $testDb = new MySqlDatabaseTool ("adminClient");
    $this->assertTrue ($testDb->getIsOpen());

    // A. Test normal operation.
    $result = $testDb->executeQuery("SELECT * FROM courses WHERE cID = ?", array('350'));
    $this->assertTrue ($result);


    // B. Test parameter Miss Match.
    $result = $testDb->executeQuery("SELECT * FROM courses WHERE cID = ? AND dNum = ?", array('350'));
    $this->assertFalse ($result);

    $result = $testDb->executeQuery("SELECT * FROM courses WHERE cID = ?", array('350','343'));
    $this->assertFalse ($result);


    // C. Test Null input.
    $result = $testDb->executeQuery(NULL, array('350'));
    $this->assertFalse ($result);

    $result = $testDb->executeQuery(NULL, NULL);
    $this->assertFalse ($result);


    // D. Test same value count.
    $result = $testDb->executeQuery("SELECT * FROM courses", array());
    $this->assertTrue ($result);

  }

  /******************************************************************
   * @Description - A test method for the retrival of results.
   *
   * @param None
   *
   * @return None
   *
   *****************************************************************/
  public function testGetResult () {

    // --- Variable Declarations  -------------------------------//

    /* The database tool were going to validate.                 */
    $testDb = NULL;

    /* The database tool were going to validate.                 */
    $result = NULL;

    // --- Main Routine -----------------------------------------//

    // Pre-Config. Configure Connection.
    $testDb = new MySqlDatabaseTool ("adminClient");
    $this->assertTrue ($testDb->getIsOpen());

    // A. Test normal operation.
    $result = $testDb->getResults();
    $this->assertFalse($result);

  }

}


 ?>
