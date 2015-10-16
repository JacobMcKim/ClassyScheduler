<?php
/* --------------------------------------------------------------------*
 * Command.php                                                         *
 * --------------------------------------------------------------------*
 * Description - This abstract class implements the ICommand interface *
 * to lay the foundational elements for all command classes that are   *
 * derived from it. It contains a generic isValidContent method that   *
 * can check if all reqired parameters exist. It can be overridden.    *
 * --------------------------------------------------------------------*
 * Project: Classy Scheduler Web Server                                *
 * Author : McKim A. Jacob                                             *
 * Date Of Creation: 10 - 01 - 2015                                    *
 * ------------------------------------------------------------------- */
//===================================================================//
//  NOTES & BUGS AS OF 10-01-2015                                     //
//===================================================================//
/*
 * TODO - Convert user ID over query over to classy Scheduler. (Line 102)
 */
//===================================================================//
//  Includes                                                         //
//===================================================================//
/* N/A */

//===================================================================//
// Class Definition                                                  //
//===================================================================//
abstract class Command implements ICommand {
    //---------------------------------------------------------------//
    // Absract Methods                                               //
    //---------------------------------------------------------------//
    /* Executes the command defined for the service implementation. */
    abstract public function executeCommand ();
    
    //---------------------------------------------------------------//
    // Concrete Class Methods                                        //
    //---------------------------------------------------------------//
    /* Validates the commands parameters before execution. */
    public function isValidContent( $Content, $arrayParams ) {

        // --- Variable Declarations  -------------------------------//

        /* String The current parameter being checked.               */
        $param = NULL;

        // --- Main Routine -----------------------------------------//
        // Check each parameter and insure they are there.
        foreach ($arrayParams as $param)
        {
            if (!array_key_exists ($param,$Content)) {
                return false;
            }
        }
        return true;

    }

    //---------------------------------------------------------------//
}

?>
