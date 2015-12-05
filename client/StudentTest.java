import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by zack on 04/12/15.
 */
public class StudentTest {


    @Test
    public void testConstructor() {

        Student student;
        APICommunicator comm = new APICommunicator();
        APIRequest req = new APIRequest(ServicesEnum.LOGIN);

        JSONObject data;
        APIResponse resp;

        req.addRequestProperty("email", "mckimj@mail.gvsu.edu");
        req.addRequestProperty("password", "jacobm");

        if(comm.sendRequest(req)) {

            resp = comm.getRequestResult();
            data = resp.getResponseList();

            try {

                String s = data.getString("Response");
                if(s.equals("success")){
                    student = new Student(data);
                } else{
                    student = new Student();
                }

                assertEquals(student.getStuID(), 1);
                assertEquals(student.getfName(), "Jacob");
                assertEquals(student.getlName(), "McKim");

            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }

}