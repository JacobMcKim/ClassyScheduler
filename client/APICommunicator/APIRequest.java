/* --------------------------------------------------------------------*
 * APIRequest.java                                                     *
 * --------------------------------------------------------------------*
 *  Description - This class is used to build a request to send to the *
 *  web services for classy scheduler.                                 *
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

//===================================================================//

public class APIRequest
{
    
    //---------------------------------------------------------------//
    // Class Atributes                                               //
    //---------------------------------------------------------------//
    
    /* The type of method to used to send to the API Services.       */
    private RequestTypeEnum reqType = null;
    
    /* The Service name of the API were accessing.                   */
    private String serviceID = "";
    
    /* The json list of property attributes that are apart of request.*/
    private JSONObject keyValueList = new JSONObject();
    
    //----------------------------------------------------------------//
    //  Class Constructor Definitions                                 //
    //----------------------------------------------------------------//

    /******************************************************************
     * Called to initialise a request form object to be processed by 
     * the APICommunicator object. 
     *
     *****************************************************************/
    public APIRequest(ServicesEnum serviceType)
    {
        switch (serviceType) {
        
            case ADD_COURSE:
                reqType = RequestTypeEnum.POST;
                serviceID = "AddCourse";
            break;
            
            case DELETE_COURSE:
                reqType = RequestTypeEnum.POST;
                serviceID = "DeleteCourse";
            break;
            
            case SEARCH_COURSES:
                reqType = RequestTypeEnum.POST;
                serviceID = "SearchCourse";
            break;
            
            case UPDATE_COURSE: 
                reqType = RequestTypeEnum.POST;
                serviceID = "UpdateCourse";
            break;
            
            case UPDATE_SCHEDULE: 
                reqType = RequestTypeEnum.POST;
                serviceID = "UpdateSchedule";
            break;
            
            default:
                // TODO: figure out how to handle this.
            break;
            
        }
        
        // Finally add the service id to the request string. 
        addRequestProperty("ServiceID",serviceID);
    }

    //----------------------------------------------------------------//
    //  Public Class Get/Set Method Definitions                       //
    //----------------------------------------------------------------//
    
    /******************************************************************
    * A get method used to get the Request type.
    * 
    * @return The type of HTTP Request that will be preformed.
    *
    ******************************************************************/
    public RequestTypeEnum getRequestType () {
        return reqType;
        
    }
    
    /******************************************************************
    * A get method used to extract the name of the service to be called.
    * 
    * @return The name of the service we want to call.
    *
    ******************************************************************/
    public String getServiceName () {
        return serviceID;
        
    }
    
    //----------------------------------------------------------------//
    //  Public Class Method Definitions                               //
    //----------------------------------------------------------------//

    /******************************************************************
    * A method used to store a property key value pair into the request.
    * 
    * @param The name of the key being storing.
    * 
    * @param The value for the key being stored.
    * 
    * @return Whether or not the key value was added. 
    *
    ******************************************************************/
    public boolean addRequestProperty(String keyName, Object value)
    {
        try {
            keyValueList.append(keyName,value);
            return true;
        }
        
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        
    }
    
    /******************************************************************
    * A method used to remove a property key value pair from the 
    * request object.
    * 
    * @param The name of the key being removed.
    * 
    * @return Whether or not the key value was removed. 
    *
    ******************************************************************/
    public boolean RemoveProperty (String keyName)
    {
        try {
            keyValueList.remove(keyName);
            return true;
        }
        
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        
    }
    
    /******************************************************************
    * A method used to get a json string of the data to be communicated
    * to the API webservices. 
    * 
    * @return The request formated as a JSON string. Returns null if it
    * could not compile into string. 
    *
    ******************************************************************/
    public String getSerializedJSONRequest() {
        
        try {
            return keyValueList.toString();
        }
        
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }
    
    //----------------------------------------------------------------//
    //  Private Class Method Definitions                              //
    //----------------------------------------------------------------//
    
    /* N/A                                                            */
   
    //----------------------------------------------------------------//
}
