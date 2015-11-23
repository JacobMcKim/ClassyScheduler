/* --------------------------------------------------------------------*
 * APIResponseTest.java                                                *
 * --------------------------------------------------------------------*
 *  Description - This class is used to test the response object type. *
 * --------------------------------------------------------------------*
 * Project: Classy Scheduler Client                                    *
 * Author : McKim A. Jacob                                             *
 * Date Of Creation: 11 - 01 - 2015                                    *
 * ------------------------------------------------------------------- */
 
//===================================================================//
//  NOTES & BUGS AS OF 11-01-2015                                    //
//===================================================================//
/*
 *  N/A
 */

//=====================================================================//

//===================================================================//
//  Imports                                                          //
//===================================================================//

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

//===================================================================//

/**
 * The test class APIResponseTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class APIResponseTest
{
    //---------------------------------------------------------------//
    // Class Atributes                                               //
    //---------------------------------------------------------------//
    
    /* The type of method to used to send to the API Services.       */
    private APIResponse response = null;
    
    //----------------------------------------------------------------//
    //  Class Constructor Definitions                                 //
    //----------------------------------------------------------------//
    
    /**
     * Default constructor for test class APIResponseTest
     */
    public APIResponseTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
    }
    
    //----------------------------------------------------------------//
    //  Class Creation Test Definitions                               //
    //----------------------------------------------------------------//

    // 1. 
    @Test
    public void testSuccessResponseConstruct () {
        response = new APIResponse(200,"{\"Response\":\"success\"}");
        assertEquals(response.getResponseType(), APIResultEnum.SUCCESS);
        assertNotNull(response.getResponseList());
        response = null;
        
    }
    
    // 2.
    @Test
    public void testFailureResponseConstruct () {
        response = new APIResponse(200,"{\"Response\":\"failed\"}");
        assertEquals(response.getResponseType(), APIResultEnum.FAILURE);
        assertNotNull(response.getResponseList());
        response = null;
        
    }
    
    // 3.
    @Test
    public void testInvalidDataResponseConstruct () {
        response = new APIResponse(200,"{\"Response\":\"invalidData\"}");
        assertEquals(response.getResponseType(), APIResultEnum.INVALID_DATA);
        assertNotNull(response.getResponseList());
        response = null;
        
    }
    
    // 4.
    @Test
    public void testSystemErrorResponseConstruct () {
        response = new APIResponse(200,"{\"Response\":\"systemError\"}");
        assertEquals(response.getResponseType(), APIResultEnum.SYSTEM_ERROR);
        assertNotNull(response.getResponseList());
        response = null;
        
    }

    // 5. 
    @Test
    public void testSessionInvalidResponseConstruct () {
        response = new APIResponse(200,"{\"Response\":\"sessionInvalid\"}");
        assertEquals(response.getResponseType(), APIResultEnum.SESSION_INVALID);
        assertNotNull(response.getResponseList());
        response = null;
        
    }
    
    // 6.
    @Test
    public void testUnknownResponseConstruct () {
        response = new APIResponse(200,"{\"Response\":\"\"}");
        assertEquals(response.getResponseType(), APIResultEnum.UNKNOWN);
        assertNotNull(response.getResponseList());
        response = null;
        
    }
    
    // 7.
    @Test
    public void testBadCodeErrorResponseConstruct () {
        response = new APIResponse(400,"{\"Response\":\"systemError\"}");
        assertEquals(response.getResponseType(), APIResultEnum.SYSTEM_ERROR);
        assertNull(response.getResponseList());
        response = null;
        
        response = new APIResponse(599,"{\"Response\":\"systemError\"}");
        assertEquals(response.getResponseType(), APIResultEnum.SYSTEM_ERROR);
        assertNull(response.getResponseList());
        response = null;
        
    }
    
    // 8. 
    @Test
    public void testBadCodeUnknownResponseConstruct () {
        
        response = new APIResponse(199,"{\"Response\":\"systemError\"}");
        assertEquals(response.getResponseType(), APIResultEnum.UNKNOWN);
        response = null;
        
        response = new APIResponse(300,"{\"Response\":\"systemError\"}");
        assertEquals(response.getResponseType(), APIResultEnum.UNKNOWN);
        response = null;
        
        response = new APIResponse(399,"{\"Response\":\"systemError\"}");
        assertEquals(response.getResponseType(), APIResultEnum.UNKNOWN);
        response = null;
        
        response = new APIResponse(600,"{\"Response\":\"systemError\"}");
        assertEquals(response.getResponseType(), APIResultEnum.UNKNOWN);
        response = null;
        
    }
    
    
    //----------------------------------------------------------------//
    //  Property Creation Test Definitions                            //
    //----------------------------------------------------------------//
    
    // 9.
    @Test
    public void testgetResponseListGood() {
        response = new APIResponse(200,"{\"Response\":\"failed\",\"Description\":\"class already exists.\"}");
        assertEquals(response.getResponseType(), APIResultEnum.FAILURE);
        assertNotNull(response.getResponseList());
        response = null;
        
    }
    
    
    // 10.
    @Test
    public void testgetResponseListBad () {
        response = new APIResponse(200,null);
        assertEquals(response.getResponseType(), APIResultEnum.UNKNOWN);
        assertNull(response.getResponseList());
        response = null;
        
        response = new APIResponse(199,null);
        assertEquals(response.getResponseType(), APIResultEnum.UNKNOWN);
        assertNull(response.getResponseList());
        response = null;
        
        response = new APIResponse(400,null);
        assertEquals(response.getResponseType(), APIResultEnum.SYSTEM_ERROR);
        assertNull(response.getResponseList());
        response = null;
        
    }
    
    //----------------------------------------------------------------//
    //  End Test                                                      //
    //----------------------------------------------------------------//
    
    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }
    
    //----------------------------------------------------------------//
        
}
