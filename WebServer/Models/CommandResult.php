<?php
/* --------------------------------------------------------------------*
 * CommandResult.php                                                   *
 * --------------------------------------------------------------------*
 * Description - This class implements ICommandResult to provide a     *
 *   standardized convention for communicating results back from the   *
 *                    server to the client devices.                    *
 * --------------------------------------------------------------------*
 * Project: Classy Scheduler Web Server                                *
 * Author : McKim A. Jacob                                             *
 * Date Of Creation: 10 - 09 - 2015                                    *
 * ------------------------------------------------------------------- */

//===================================================================//
//  NOTES & BUGS AS OF 10-09-2015                                     //
//===================================================================//
/*
 *
 */

//===================================================================//
//  Includes                                                         //
//===================================================================//
require_once 'ICommandResult.php';

//===================================================================//
// Class Definition                                                  //
//===================================================================//
class CommandResult implements ICommandResult {

  //---------------------------------------------------------------//
  // Class Atributes                                               //
  //---------------------------------------------------------------//

  /* The result code associated with type.                         */
  $resultCode;

  /* The content of the user request.                              */
  $resultData;

  /* The result type that is going back.                           */
  $resultName;

  //---------------------------------------------------------------//
  // Constructor/Destructors                                       //
  //---------------------------------------------------------------//
  /******************************************************************
   * @Description - Called to build the response object.
   *
   * @param $resultType - takes in the result type and sets it (string).
   *
   * @return None
   *
   *****************************************************************/
  function __construct($resultType) {
    $this->setResultType($resultType);
    $this->$resultData = array('Response' => $this->getResultType());

  }

  /******************************************************************
   * @Description - Called when the data is no longer needed.
   *
   * @param None
   *
   * @return None
   *
   *****************************************************************/
  function __destruct() {
      $this->clearData();

  }

    //---------------------------------------------------------------//
    // Class Methods                                                 //
    //---------------------------------------------------------------//

    /* A method called to add a value to the result data.*/
   public function addValuePair ($key, $value) {
     if ($key != NULL && $value != NULL) {
       array_push($this->$resultData, array($key => $value));
     }

   }

   /* A method called to clear all data stored by the request object.*/
   public function clearData () {
     $this->$resultName = Null;
     $this->$resultData = Null;
     $this->$resultCode = Null;

   }

   /*A method called to convert the response to a json object to send
   back to the client.*/
   public function convertToJSON () {
      if (count($this->$resultData) > 0) {
        return json_encode($this->$resultData);
      }
      else {
        return "";
      }

   }

   /* A method used to get the type of result we want to give back to the
   client. */
  public function  getResultType () {
    return $this->$resultName;

  }

   /* A method used to set the type of result we want to give back
   to the client.*/
   public function  setResultType ($resultType) {
      switch ($resultType) {
        case 'success':
          $this->$resultCode = 1;
          $this->$resultName = $resultType;
          break;

        case 'failed':
          $this->$resultCode = -1;
          $this->$resultName = $resultType;
          break;

        case 'invalidData':
          $this->$resultCode = -2;
          $this->$resultName = $resultType;
          break;

        case 'systemError':
          $this->$resultCode = -3;
          $this->$resultName = $resultType;
          break;

        default:
          $this->$resultCode = 0;
          $this->$resultName = "";
          return false;
          break;
      }

      return true;

   }

    //---------------------------------------------------------------//

}

?>
