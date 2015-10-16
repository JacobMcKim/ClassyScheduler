<?php
/* --------------------------------------------------------------------*
 * ICommand.php                                                        *
 * --------------------------------------------------------------------*
 * Description - This interface is used as the foundation for all      *
 * web services in use for the Classy Scheduling tool. All services    *
 * must model after this interface in order to be utilized.            *
 * --------------------------------------------------------------------*
 * Project: Classy Scheduler Web Server                                *
 * Author : McKim A. Jacob                                             *
 * Date Of Creation: 10 - 01 - 2015                                    *
 * ------------------------------------------------------------------- */

//===================================================================//
//  Includes                                                         //
//===================================================================//
#include '../Generics.php';
#include '../DatabaseTools/dbClientConnection.php';
#include '../DatabaseTools/dbPanelConnection.php';

interface ICommand {

    //---------------------------------------------------------------//
    //  Interface Function Declerations                              //
    //---------------------------------------------------------------//

    /******************************************************************
    * @Description - A method called to execute the request API
    * command. This is where the magic happens in the command object.
    *
    * @return A (ICommandResult) object with the result of the query.
    *
    *****************************************************************/
    public function executeCommand ();

    /******************************************************************
    * @Description - A method called to validate that an incomming API
    * request has all the nessisary parameters to preform the request
    * at hand.
    *
    * @param $Content - The json content of the request to be cross
    * referenced with the expected result.
    *
    * @param $arrayParams - The array of keys that should be expected
    * in the json request object.
    *
    * @return Whether the request parameters are all there for
    * executing the request. (True - all there. False - Not all there)
    *
    *****************************************************************/
    public function isValidContent ( $Content, $arrayParams );

    //----------------------------------------------------------------//

}

?>
