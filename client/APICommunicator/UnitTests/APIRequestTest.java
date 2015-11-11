/* --------------------------------------------------------------------*
 * APIRequestTest.java                                                 *
 * --------------------------------------------------------------------*
 *  Description - This class is used to test the request object type.  *
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

public class APIRequestTest
{
    
    //---------------------------------------------------------------//
    // Class Atributes                                               //
    //---------------------------------------------------------------//
    
    /* The type of method to used to send to the API Services.       */
    private APIRequest request = null;
    
    //----------------------------------------------------------------//
    //  Class Constructor Definitions                                 //
    //----------------------------------------------------------------//
    
    /**
     * Default constructor for test class APIRequestTest
     */
    public APIRequestTest()
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
    public void testAddCourseRequestConstruct () {
        request = new APIRequest(ServicesEnum.ADD_COURSE);
        assertEquals(request.getServiceName(), "AddCourse");
        assertEquals(request.getRequestType(), RequestTypeEnum.POST);
        request = null;
        
    }
  
    // 2.
    @Test
    public void testDeleteCourseRequestConstruct () {
        request = new APIRequest(ServicesEnum.DELETE_COURSE);
        assertEquals(request.getServiceName(), "DeleteCourse");
        assertEquals(request.getRequestType(), RequestTypeEnum.POST);
        request = null;
        
    }
    
    // 3.
    @Test
    public void testSearchCourseRequestConstruct () {
        request = new APIRequest(ServicesEnum.SEARCH_COURSES);
        assertEquals(request.getServiceName(), "SearchCourse");
        assertEquals(request.getRequestType(), RequestTypeEnum.POST);
        request = null;
        
    }
    
    // 4.
    @Test
    public void testUpdateCourseRequestConstruct () {
        request = new APIRequest(ServicesEnum.UPDATE_COURSE);
        assertEquals(request.getServiceName(), "UpdateCourse");
        assertEquals(request.getRequestType(), RequestTypeEnum.POST);
        request = null;
        
    }
    
    // 5.
    @Test
    public void testUpdateScheduleRequestConstruct () {
        request = new APIRequest(ServicesEnum.UPDATE_SCHEDULE);
        assertEquals(request.getServiceName(), "UpdateSchedule");
        assertEquals(request.getRequestType(), RequestTypeEnum.POST);
        request = null;
        
    }
    
    // 6.
    @Test
    public void testEmptyRequestConstruct () {
        request = new APIRequest(null);
        assertEquals(request.getServiceName(), "");
        assertEquals(request.getRequestType(), null);
        request = null;
        
    }
    
        
    //----------------------------------------------------------------//
    //  Property Creation Test Definitions                            //
    //----------------------------------------------------------------//
    
    // 7.
    @Test
    public void testStringaddRequestProperty () {
        request = new APIRequest(ServicesEnum.UPDATE_SCHEDULE);
        assertTrue(request.addRequestProperty("data1","abc"));
        assertEquals(request.getSerializedJSONRequest(),"{\"data1\":\"abc\",\"ServiceID\":\"UpdateSchedule\"}");
        request = null;
        
    }
    
    // 8.
    @Test
    public void testIntegeraddRequestProperty () {
        request = new APIRequest(ServicesEnum.UPDATE_SCHEDULE);
        assertTrue(request.addRequestProperty("data1",123));
        assertEquals(request.getSerializedJSONRequest(),"{\"data1\":123,\"ServiceID\":\"UpdateSchedule\"}");
        request = null;
        
    }
    
    // 9.
    @Test
    public void testFloataddRequestProperty () {
        request = new APIRequest(ServicesEnum.UPDATE_SCHEDULE);
        assertTrue(request.addRequestProperty("data1",1.2));
        assertEquals(request.getSerializedJSONRequest(),"{\"data1\":1.2,\"ServiceID\":\"UpdateSchedule\"}");
        request = null;
        
    }
    
    // 10.
    @Test
    public void testNullAddRequestProperty () {
        request = new APIRequest(ServicesEnum.UPDATE_SCHEDULE);
        assertFalse(request.addRequestProperty("data1",null));
        request = null;
        
    }
    
    // 11.
    @Test
    public void testNullAllAddRequestProperty () {
        request = new APIRequest(ServicesEnum.UPDATE_SCHEDULE);
        assertFalse(request.addRequestProperty(null,null));
        request = null;
        
    }
    
    //----------------------------------------------------------------//
    //  Property Deletion Test Definitions                            //
    //----------------------------------------------------------------//
    
    // 12.
    @Test
    public void testDeleteRequestProperty () {
        request = new APIRequest(ServicesEnum.UPDATE_SCHEDULE);
        assertTrue(request.addRequestProperty("data1","abc"));
        assertEquals(request.getSerializedJSONRequest(),"{\"data1\":\"abc\",\"ServiceID\":\"UpdateSchedule\"}");
        assertTrue(request.RemoveProperty("data1"));
        request = null;
        
    }
    
    // 13.
    @Test
    public void testDeleteRequestPropertyNull () {
        request = new APIRequest(ServicesEnum.UPDATE_SCHEDULE);
        assertTrue(request.addRequestProperty("data1","abc"));
        assertEquals(request.getSerializedJSONRequest(),"{\"data1\":\"abc\",\"ServiceID\":\"UpdateSchedule\"}");
        assertFalse(request.RemoveProperty(""));
        request = null;
        
    }
    
    //----------------------------------------------------------------//
    //  JSON Conversion Test Definitions                              //
    //----------------------------------------------------------------//
    
    // 14.
    @Test
    public void testToJSON () {
        request = new APIRequest(ServicesEnum.ADD_COURSE);
        request.addRequestProperty("CourseID",223);
        request.addRequestProperty("DepartmentID",1);
        request.addRequestProperty("Title","Test Course");
        request.addRequestProperty("Description","");
       
        assertEquals(request.getSerializedJSONRequest(),"{\"CourseID\":223,\"Description\":\"\",\"Title\":\"Test Course\",\"DepartmentID\":1,\"ServiceID\":\"AddCourse\"}");

        request = null;
        
    }
    
    
    // 15.
    @Test
    public void testToJSONRequestEmpty () {
        request = new APIRequest(null);
        assertEquals(request.getSerializedJSONRequest(),"{\"ServiceID\":\"\"}");
        request = null;
        
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
