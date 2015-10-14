<?php
/* --------------------------------------------------------------------*
 * IDatabaseTool.php                                                   *
 * --------------------------------------------------------------------*
 * Description - This interface is used to lay the foundation for all  *
 *   database connections that have to be made in the server. These    *
 *   classes will insure that database access is encapsulated in an    *
 *  isolated enviorment while still providing a robust and flexible    *
 *                 enviorment for accessing content.                   *
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

interface IDatabaseTool {
    //----------------------------------------------------------------//
    //  Interface Function Declerations                               //
    //----------------------------------------------------------------//
    /******************************************************************
    * @Description - This method is to be internally used by the
    * class implementing IDatabaseTool to close an established
    *  connection with the web db service it's connected to.
    *
    * @param None
    *
    * @return Whether or not it was successful at closing the
    * connection (boolean).
    *
    *****************************************************************/
    function closeConnection ();


    /******************************************************************
    * @Description - This method is used to fetch a set of results
    * from a command issued to the data base service.
    *
    * @param (N/A)
    *
    * @return Returns what results are contained if any from query.
    *
    *****************************************************************/
    function getResults ();


    /******************************************************************
    * @Description - This method is used to fire off command queries to
    * the database services its accessing.
    *
    * @param $RequestString (String)- The command string to be
    * executed inside the DB service.
    *
    * @param $RequestAtributes (associativeArray) - The atributes to
    * be included into the query if any.
    *
    * @return The success or failure of the query executed (Boolean).
    *
    *****************************************************************/
    function executeQuery ($RequestString,$atributeTypes,$RequestAtributes);


    /******************************************************************
    * @Description - This method is to be internally used by the
    * class implementing IDatabaseTool to establish a connection with
    * the web db service it's attempting to connect to.
    *
    * @param None
    *
    * @return Whether or not it was successful at establishing a
    * connection with the web service (boolean).
    *
    *****************************************************************/
    function openConnection ();


    //----------------------------------------------------------------//

}

?>
