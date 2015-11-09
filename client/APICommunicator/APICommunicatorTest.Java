/* --------------------------------------------------------------------*
 * APICommunicatorTest.php                                             *
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
        
        ArrayList<Object> theData; 
        
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
            
            if (addCourseResp.getResponseSuccess()) {
                System.out.println("System was able to add course.");
            }
            else {
                System.out.println("Course already exists.");
            }
            
        }
        else {
            System.out.println("The Request failed.");
        }
        
               
    }
    
}
