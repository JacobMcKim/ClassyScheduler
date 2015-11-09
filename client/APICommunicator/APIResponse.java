/* --------------------------------------------------------------------*
 * APIResponse.java                                                    *
 * --------------------------------------------------------------------*
 *  Description - This class is used to get the response back from the *
 *  server and display the information in more usable form.
 * --------------------------------------------------------------------*
 * Project: Classy Scheduler Client                                    *
 * Author : McKim A. Jacob                                             *
 * Date Of Creation: 10 - 26 - 2015                                    *
 * ------------------------------------------------------------------- */
 
//===================================================================//
//  NOTES & BUGS AS OF 10-26-2015                                    //
//===================================================================//
/*
 *  N/A
 */

//=====================================================================//

//===================================================================//
//  Imports                                                          //
//===================================================================//
import org.json.*;
import java.util.ArrayList;
import java.util.List;

//===================================================================//

public class APIResponse
{
    //---------------------------------------------------------------//
    // Class Atributes                                               //
    //---------------------------------------------------------------//
        
    /* The json list of property attributes that are apart of request.*/
    private JSONObject keyValueList = new JSONObject();
    
    /* The list of data returned by the request from the server.      */ 
    private ArrayList<Object> responseData;
    
    /* Whether or not the request was successful or not.              */ 
    private boolean responseSuccess = false;
    
    //----------------------------------------------------------------//
    //  Class Constructor Definitions                                 //
    //----------------------------------------------------------------//

    /******************************************************************
     * Called to initialise a response object created by the 
     * APICommunicator object. 
     * 
     * @param The response code of the http Request.
     * 
     * @param The data read in from the response of the request.
     *
     *****************************************************************/
    public APIResponse(int responseCode, byte [] responseData) 
    {
 
    }
    
    //----------------------------------------------------------------//
    //  Public Class Get/Set Method Definitions                       //
    //----------------------------------------------------------------//
    
    /******************************************************************
    * A get method used to determine whether or not the response was 
    * successfuly completed at the server end.
    * 
    * @return The type of HTTP Request that will be preformed.
    *
    ******************************************************************/
    public boolean getResponseSuccess () {
        
        return false;
        
    }
    
    /******************************************************************
    * A get method used to determine whether or not the response was 
    * successfuly completed at the server end.
    * 
    * @return The type of HTTP Request that will be preformed.
    *
    ******************************************************************/
    public ArrayList<Object> getResponseList () {
        
        return null;
    }
    
    //----------------------------------------------------------------//
    //  Private Class Method Definitions                              //
    //----------------------------------------------------------------//
    
    /******************************************************************
    * A method used to convert the byte array data into an array list. 
    * 
    * @return Whether or not the creation of information was successful.
    *
    ******************************************************************/
    private boolean compileResponseList (byte [] input) {
        try {

            return true;
        }
        
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        
    }
    
}
