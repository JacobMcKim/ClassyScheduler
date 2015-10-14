<?php
/* --------------------------------------------------------------------*
 * ICommandResult.php                                                  *
 * --------------------------------------------------------------------*
 * Description - This class contains the foundation for all response   *
 *   model objects used to send back information to the client and     *
 *                  other objects within the server.                   *
 * --------------------------------------------------------------------*
 * Project: Classy Scheduler Web Server                                *
 * Author : McKim A. Jacob                                             *
 * Date Of Creation: 10 - 02 - 2015                                    *
 * ------------------------------------------------------------------- */

 interface ICommandResult {

     //----------------------------------------------------------------//
     //  Interface Function Declerations                               //
     //----------------------------------------------------------------//

     /******************************************************************
     * @Description - A method called to add a value to the result data.
     *
     * @param $key - The key value to use to define the data (string).
     *
     * @param $value - The value data to assigned to the key (string).
     *
     * @return Whether or not the addition was successful (boolean).
     *
     *****************************************************************/
    public function addValuePair ($key, $value);

    /******************************************************************
    * @Description - A method called to clear all data stored by the
    * request object.
    *
    * @param (N/A)
    *
    * @return (N/A)
    *
    *****************************************************************/
    public function clearData ();

    /******************************************************************
    * @Description - A method called to convert the response to a json
    * object to send back to the client.
    *
    * @param (N/A)
    *
    * @return The json object as a (string).
    *
    *****************************************************************/
    public function convertToJSON ();

    /******************************************************************
    * @Description - A method used to get the type of result we want
    *   to give back to the client.
    *
    * @param $resultType - The name of the result type we want to set.
    *
    * @return The result type the class is presently set to (string).
    *
    *****************************************************************/
   public function  getResultType ();

    /******************************************************************
    * @Description - A method used to set the type of result we want
    *   to give back to the client.
    *
    * @param $resultType - The name of the result type we want to set.
    *
    * @return The success of setting the result type (boolean).
    *
    *****************************************************************/
    public function  setResultType ($resultType);

   //----------------------------------------------------------------//

 }

 ?>
