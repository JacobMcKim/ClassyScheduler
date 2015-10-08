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
Include "controllers\RequestManager.php";
require '/kint/Kint.class.php';

//===================================================================//
// Variables                                                         //
//===================================================================//

/* The instance of the request manager controller that dispatches.   */
$rqManager = null;

/* The input data.                                                   */
$inputData = Null;

//===================================================================//
// Main Routine                                                      //
//===================================================================//

Kint::dump( $_SERVER);

// Construct the request manager.
$rqManager = new RequestManager ();

// Determine the incoming request type.
if ($rqManager != Null) {

  switch($_SERVER['REQUEST_METHOD'])
  {
  case 'GET':
    $inputData = &$_GET;
    break;

  case 'POST':
    $inputData = &$_POST;
    break;

  default:
    echo "{ \"Response\" : -1 }";
    exit();
    break;
  }

  $rqManager -> callService();
  $rqManager -> returnResult();
}

else {
  echo "{ \"Response\" : -1 }";
}

//-------------------------------------------------------------------//

?>
