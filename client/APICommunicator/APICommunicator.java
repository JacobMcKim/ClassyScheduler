/* --------------------------------------------------------------------*
 * APICommunicator.java                                                *
 * --------------------------------------------------------------------*
 *  Description - This class is used to communicate with the classy    *
 *  Scheduler API web service that exists on a remote server on AWS.   *
 *       Data for the application is provided through this class.      * 
 * --------------------------------------------------------------------*
 * Project: Classy Scheduler Client                                    *
 * Author : McKim A. Jacob                                             *
 * Date Of Creation: 10 - 26 - 2015                                    *
 * ------------------------------------------------------------------- */
 
//===================================================================//
//  Imports                                                          //
//===================================================================//
 import java.util.*;
 import org.apache.http.client.*;
 import org.apache.http.*;
 import org.apache.http.impl.client.*;
 import org.apache.http.client.methods.*;
 import org.apache.http.message.*;
 import org.apache.http.client.entity.*;
 import org.apache.http.impl.client.HttpClientBuilder;
 
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
 
//===================================================================//
//  NOTES & BUGS AS OF 10-26-2015                                    //
//===================================================================//
/*
 *  sample of how to http request pulled from:
 *  http://www.mkyong.com/java/apache-httpclient-examples/
 */

//==================================================================//

public class APICommunicator
{
    
    //---------------------------------------------------------------//
    // Class Atributes                                               //
    //---------------------------------------------------------------//
    
    /* The URL of the web service this class is designed to target.  */
    private String webServiceURL = "http://classyscheduler.com/index.php";
    
    /* The http connection to the web services.                      */
    private HttpClient connect = HttpClientBuilder.create().build();
    
    /* The post connection utility.                                  */
    private HttpEntityEnclosingRequestBase reqObject = null;
    
    /* The Http response that came back as a result of the request.  */
    private HttpResponse httpResp = null; 
    
    //----------------------------------------------------------------//
    //  Class Constructor Definitions                                 //
    //----------------------------------------------------------------//
    
    /******************************************************************
     * Called to initialise the communicator object to allow for HTTP
     * requests to be made to the web services. 
     *
     * @return None
     *
     *****************************************************************/
    public APICommunicator()
    {


    }
    
    //----------------------------------------------------------------//
    //  Public Class Get/Set Method Definitions                       //
    //----------------------------------------------------------------//
    
    /* N/A                                                            */
    
    //----------------------------------------------------------------//
    //  Public Class Method Definitions                               //
    //----------------------------------------------------------------//
    
    /******************************************************************
    * A method used to send data to the web service API.
    * 
    * @return Whether or not the request was successful started.
    *
    ******************************************************************/
    public boolean sendRequest (APIRequest req) {
        
        // --- Variable Declarations  -------------------------------//
        
        /* The json serialized data to pass off to the webservices.  */
        String requestData = null;
        
        /* The json serialized data to pass off to the webservices.  */
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        
        // --- Main Routine ------------------------------------------//
        
        // 1. Make sure we have a request object.
        if (req != null) {
            
            // 2. get the request data.
            requestData = req.getSerializedJSONRequest();
            if (requestData == null || requestData == "") {
                return false;
            }
            
            // 2. Determine which tatic we want to use and run it. 
            try {
                switch (req.getRequestType()) {
                    
                    case POST:
                        reqObject = new HttpPost (webServiceURL);
                        params.add(new BasicNameValuePair("request",requestData.toString()));
                        reqObject.setEntity(new UrlEncodedFormEntity(params));
                        httpResp = connect.execute (reqObject);
                    break;
                    
                    case GET:
                        // TODO: Add this in 10/26/2015
                        return false;
                }
                
                return true;
            }
        
            catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        
        else {
            return false;
        } 
    }

    
    /******************************************************************
    * A method used to fetch the request result of a http request
    * made from this object. 
    *
    * @return The response object with all meta and physical response
    * data from the server if any. Returns null otherwise. 
    *
    *****************************************************************/
    public APIResponse getRequestResult () {
        
        // --- Variable Declarations  -------------------------------//
        
        /* The buffer used to read in the response data.             */
        StringBuffer bufferData = null;
                
        /* The input buffer of data the result was packaged in.      */
        BufferedReader inDataReader = null;
        
        /* The a text line of input data.                            */
        String line = "";
        
        /* The status code read back from the response.              */
        int responseCode = 0;
        
        // --- Main Routine ------------------------------------------//
        
        // 1. Attempt to read in the data into the buffer.
        if (httpResp != null) {
            try {
                inDataReader = new BufferedReader(new InputStreamReader
                                    (httpResp.getEntity().getContent()));
                bufferData = new StringBuffer();
                
                
                while ((line = inDataReader.readLine()) != null) {
                    bufferData.append(line);
                }
                
                // 2. Create the response API object and return it.
                responseCode = httpResp.getStatusLine().getStatusCode();
                return new APIResponse(responseCode,bufferData.toString());
            }
        
            catch (Exception e) {
                return null;
            }
        }
        
        else {
            return null;
        }
        
    }
    
    //----------------------------------------------------------------//
    //  Private Class Method Definitions                              //
    //----------------------------------------------------------------//
    
    /* N/A                                                            */
    
    //----------------------------------------------------------------//
}
