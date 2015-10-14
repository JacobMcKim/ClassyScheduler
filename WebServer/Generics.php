<?php
/* --------------------------------------------------------------------*
 * Generics.php                                                        *
 * --------------------------------------------------------------------*
 * Description - This php file contains generic tools/ resources that  *
 * are for standard use across all components of this web service.     *
 * --------------------------------------------------------------------*
 * Project: Classy Scheduler Web Server                                *
 * Author : McKim A. Jacob                                             *
 * Date Of Creation: 10 - 02 - 2015                                    *
 * ------------------------------------------------------------------- */
 require_once 'kint/Kint.class.php';
require_once 'Models/CommandResult.php';
 require_once 'DatabaseTools/MySqlDatabaseTool.php';


//===================================================================//
//  NOTES & BUGS AS OF 10-01-2015                                    //
//===================================================================//
/*
 * TODO- CLEAN UP DEBUG 10/2/15
 */
//===================================================================//
//  Global Variables/Constants                                       //
//===================================================================//

/* N/A */

//===================================================================//
// Helper Methods                                                    //
//===================================================================//
/******************************************************************
  * @Description - This method is used to randomly generate string
  *  values to be used in security parameters and other various
  * implmentations in the API service.
  *
  * @param Length - The length of the string to be generated, By
  * default it's 10 characters (Integer).
  *
  * @return The randomly generated string (String).
  *
  *****************************************************************/
function keygen($length=10)
{

    // --- Variable Declarations  -------------------------------//

    /* @var $key (String) - The output key generated. */
    $key = '';

    /* @var $inputs (String) List of all possible characters to use. */
    $inputs = array_merge(range('z','a'),range(0,9),range('A','Z'));

    // --- Main Routine ------------------------------------------//

    // Randomly seed the random generator.
    list($usec, $sec) = explode(' ', microtime());
    mt_srand((float) $sec + ((float) $usec * 100000));

    // Pick elements from the list and append to the key.
    for($i=0; $i<$length; $i++) {
        $key .= $inputs{mt_rand(0,61)};
    }

    // Return the result.
    return $key;
}


//===================================================================//

?>
