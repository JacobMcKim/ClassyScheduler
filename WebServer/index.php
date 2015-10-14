<?php
/* -------------------------------------------------------------------*
* index.php                                                           *
* --------------------------------------------------------------------*
* Description - This script is the primary enterance point for all    *
*        requests made to the web service backend of the Classy       *
*                         Scheduler application.                      *
* --------------------------------------------------------------------*
* Project: Classy Scheduler Web Server                                *
* Author : McKim A. Jacob                                             *
* Date Of Creation: 10 - 02 - 2015                                    *
* ------------------------------------------------------------------- */

//===================================================================//
//  NOTES & BUGS AS OF 10-02-2015                                    //
//===================================================================//
/*
*  TODO - Add debug utility.
*/

//===================================================================//
//  Includes                                                         //
//===================================================================//
require_once 'Generics.php';
require "Controllers/RequestManager.php";

//===================================================================//
// Variables                                                         //
//===================================================================//

/* The instance of the request manager controller that dispatches.   */
$rqManager = NULL;

/* The input data.                                                   */
$inputData = NULL;

/* The outbound data stored in the model object.                     */
$responseObj = NULL;

//===================================================================//
// Main Routine                                                      //
//===================================================================//

// Construct the request manager.
$rqManager = new RequestManager ();

// Determine the incoming request type.
if ($rqManager != Null) {

  switch($_SERVER['REQUEST_METHOD'])
  {
  case 'GET':
    $inputData = &$_GET["request"];
    break;

  case 'POST':
    $inputData = &$_POST["request"];
    break;

  default:
    echo "{ \"Response\" : -1 }";
    exit();
    break;
  }


$inputData = json_decode($inputData, true);
$responseObj = $rqManager -> callService($inputData);
$rqManager -> returnResult($responseObj);
Kint::dump( $_SERVER);

}

else {
  echo "{ \"Response\" : -1 }";
  exit();
}

//-------------------------------------------------------------------//

?>
