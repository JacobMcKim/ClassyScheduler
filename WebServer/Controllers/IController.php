
<?php
/* --------------------------------------------------------------------*
 * IController.php                                                     *
 * --------------------------------------------------------------------*
 * Description - This interface is used as the foundation for service  *
 *    controllers that manage incoming requests and dispatch the       *
 *              appropriate command to handle the request.             *
 * --------------------------------------------------------------------*
 * Project: Classy Scheduler Web Server                                *
 * Author : McKim A. Jacob                                             *
 * Date Of Creation: 10 - 01 - 2015                                    *
 * ------------------------------------------------------------------- */

//===================================================================//
//  Includes                                                         //
//===================================================================//
include '../Generics.php';

interface IController {

    //----------------------------------------------------------------//
    //  Interface Function Declerations                               //
    //----------------------------------------------------------------//

     /******************************************************************
     * @Description - A method called to select the API service to be
     * executed by the incoming request.
     *
     * @param $requestData - The request JSON array to be fead in to the
     * API command.
     *
     * @return The result of the API request as a JSON Array.
     *
     *****************************************************************/
    public static function callService ( $requestData );

    //----------------------------------------------------------------//

}
