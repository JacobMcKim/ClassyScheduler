/* --------------------------------------------------------------------*
 * APICommunicatorTest.java                                            *
 * --------------------------------------------------------------------*
 *  Description - This class provides a sample implmentation of how to *
 *  use the APICommunicator class.                                     *
 * --------------------------------------------------------------------*
 * Project: Classy Scheduler Client                                    *
 * Author : McKim A. Jacob                                             *
 * Date Of Creation: 10 - 26 - 2015                                    *
 * ------------------------------------------------------------------- */
 
 
//===================================================================//
//  Imports                                                          //
//===================================================================//
import org.json.*;
import java.util.ArrayList;
import java.util.List;

//===================================================================//
 
public class APICommunicatorTest
{
    
    public static void main (String [] args) {
        
        // --- Variable Declarations  -------------------------------//
        
        /* The communicator object that talks to web server API.   */
        APICommunicator APICom = new APICommunicator ();
        
        /* The request form we fill out to send to the web server. */
        APIRequest addCourseReq = new APIRequest (ServicesEnum.ADD_COURSE);
        
        /* The response from the web server.                       */
        APIResponse addCourseResp = null;
        
        /* The data result from the server.                        */
        JSONObject theData; 
        
        // --- Main Routine ------------------------------------------//
        
        // 1. Build request form information. 
        addCourseReq.addRequestProperty("CourseID",242);
        addCourseReq.addRequestProperty("DepartmentID",1);
        addCourseReq.addRequestProperty("Title","Test Course");
        addCourseReq.addRequestProperty("Description","");
        
        // 2. Execute Request.
        if (APICom.sendRequest(addCourseReq)) {
            addCourseResp = APICom.getRequestResult();
            
            // 3. Get the response.
            theData = addCourseResp.getResponseList();
            
            // This is the server code just to make sure things went well.
            System.out.print("Response Code:");
            System.out.println(addCourseResp.getResponseType().toString());
            
            // Checks the success based off one of the 4 types of responses.
            if (addCourseResp.getResponseType() == APIResultEnum.SUCCESS) {
                System.out.println("System was able to add course.");
            }
            else {
                
                // Attempts to grab a peice of information from the data object.
                try {
                    System.out.println(theData.getString("Description"));
                }
                catch (Exception e) {
                    System.out.println("Unable to read the error Description.");
                }
            }
            
        }
        else {
            System.out.println("The Request failed.");
        }
              
    }
    
}
