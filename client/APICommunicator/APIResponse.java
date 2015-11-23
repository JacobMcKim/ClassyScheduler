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
    private JSONObject keyValueList = null;
    
    /* The type of success the service had.                           */ 
    private APIResultEnum responseSuccess = APIResultEnum.UNKNOWN;
    
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
    public APIResponse (int responseCode, String responseData) 
    {
        if (responseCode >= 200 && responseCode < 300) {
            try {
                keyValueList = new JSONObject(responseData);
                
                switch (keyValueList.getString("Response")) {
                    case "success":
                        responseSuccess = APIResultEnum.SUCCESS;
                    break;
                    
                    case "failed":
                        responseSuccess = APIResultEnum.FAILURE;
                    break;
                    
                    case "invalidData":
                        responseSuccess = APIResultEnum.INVALID_DATA;
                    break;
                    
                    case "systemError":
                        responseSuccess = APIResultEnum.SYSTEM_ERROR;
                    break;
                    
                    case "sessionInvalid": 
                        responseSuccess = APIResultEnum.SESSION_INVALID;
                    break;
                    
                    default:
                        responseSuccess = APIResultEnum.UNKNOWN;
                    break;
                    
                }
            }
            catch (Exception e) {
                keyValueList = null;
                
            }
        }
        
        else if (responseCode >= 400 && responseCode < 600) {
            responseSuccess = APIResultEnum.SYSTEM_ERROR;
        }
        
            
 
    }
    
    //----------------------------------------------------------------//
    //  Public Class Get/Set Method Definitions                       //
    //----------------------------------------------------------------//
    
    /******************************************************************
    * A get method used to determine whether or not the response was 
    * successfuly completed at the server end.
    * 
    * @return The enumerated response that was given back. 
    *
    ******************************************************************/
    public APIResultEnum getResponseType () {
        
        return responseSuccess;
        
    }
    
    /******************************************************************
    * A get method used to get the response data from the web server.
    * 
    * @return A JSONObject containing the data of the request.
    *
    ******************************************************************/
    public JSONObject getResponseList () {
  
        return keyValueList;
        
    }
    
    //----------------------------------------------------------------//
    //  Private Class Method Definitions                              //
    //----------------------------------------------------------------//
    
    /* N/A                                                            */
    
    //----------------------------------------------------------------//
}
