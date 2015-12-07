/*****************************************************************************
 * ClassyHandler
 * <p>
 * A Handles actions of searching courses in search bar, and adding/removing
 * sections to and from the users current schedule.
 * <p>
 * Created by Zack Drescher
 * Last edited on 11.14.15 ZD
 * *****************************************************************************
 * Todo:
 * Integrate with UI
 * Implement Communication with server
 * implement loadDefaultSearchBuff
 * Implement loadSearchedCourseBuffer
 * Implement removeSectionFromSched
 * error resolution in addSectionToSched
 * Constructor
 * -Intergrate loadDefaultSearchBuff
 * loadDefaultSearchBuff
 * - Load resoinse data
 * - Implement server communication erroe handling.
 ******************************************************************************/

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ClassyHandler {

    private ArrayList<Course> searchBuffer;
    private ArrayList<Section> studentSched;
    private int scheduleID;
    private ArrayList<Semester> semesters;
    private Semester currSem;
    private Student student;
    private String sessionID;

    //Creates an a handler with empty feilds
    public ClassyHandler() {

        searchBuffer = new ArrayList<Course>();
        studentSched = new ArrayList<Section>();
        semesters = new ArrayList<Semester>();


    }

    // Creates a handler set for the current semester
    public ClassyHandler(Semester s) {

        searchBuffer = new ArrayList<Course>();
        studentSched = new ArrayList<Section>();
        currSem = s;
    }

    //Creates a hadler with the given searchBuffer and studentSched
    public ClassyHandler(ArrayList<Course> courseL, ArrayList<Section> sched) {

        searchBuffer = courseL;
        studentSched = sched;
        currSem = new Semester(SemEnum.FALL, 16, 1);
    }

    //Temporary predefined Constructor
    public ClassyHandler(int i) {

        Course cis350 = new Course(1, 350, "Software Engineering",
                "This is a class.");
        Course cis263 = new Course(1, 263, "Data Structures", "This is a class.");


        Section s1 = new Section(cis350, 1, 1000, 1050, "MWF");
        Section s2 = new Section(cis350, 2, 1100, 1150, "MWF");
        Section s3 = new Section(cis263, 1, 1000, 1050, "MWF");
        Section s4 = new Section(cis263, 2, 1100, 1150, "MWF");

        cis350.addSection(s1);
        cis350.addSection(s2);
        cis263.addSection(s3);
        cis263.addSection(s4);

        searchBuffer = new ArrayList<Course>();
        searchBuffer.add(cis350);
        searchBuffer.add(cis263);

        studentSched = new ArrayList<Section>();
        studentSched.add(s1);
    }

    public ArrayList<Course> getSearchBuffer() {
        return searchBuffer;
    }

    public ArrayList<Section> getStudentSched() {
        return studentSched;
    }

    public Semester getCurrSem() {
        return currSem;
    }
    
    public ArrayList<Semester> getSemseters() {
        return semesters;
    }

    public Student getStudent() {return student;}

    // Getter methods that return search buffer and student sched in the form of
    // arrays
    public Course[] getArraySearchBuf() {

        Course[] temp = new Course[searchBuffer.size()];

        for (int i = 0; i < searchBuffer.size(); i++) {
            temp[i] = searchBuffer.get(i);
        }
        return temp;
    }

    public Section[] getArrayStuSched() {

        Section[] temp = new Section[studentSched.size()];

        for (int i = 0; i < studentSched.size(); i++) {
            temp[i] = studentSched.get(i);
        }
        return temp;
    }

    // Set the current Semester
    public void setCurrSem(Semester s) {

        currSem = s;

        loadDefaultSearchBuff();
    }

    public void login(String email, String pass) {
        // TODO: Login

        APICommunicator comm = new APICommunicator();
        APIRequest req = new APIRequest(ServicesEnum.LOGIN);

        JSONObject data;
        APIResponse resp;

        req.addRequestProperty("email", email);
        req.addRequestProperty("password", pass);

        if(comm.sendRequest(req)) {

            resp = comm.getRequestResult();
            data = resp.getResponseList();

            try {

                String s = data.getString("Response");
                if(s.equals("success")){
                    student = new Student(data);
                    sessionID = data.getString("sessionID");

                    loadSemesters();
                    currSem = semesters.get(0);
                    loadDefaultSearchBuff();
                    loadStudentSched();
                } else{
                    // TODO: handle response failure
                }

            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void logout() {

        APICommunicator comm = new APICommunicator();
        APIRequest req = new APIRequest(ServicesEnum.LOGOUT);

        JSONObject data;
        APIResponse resp;

        req.addRequestProperty("studentID", student.getStuID());
        req.addRequestProperty("sessionID", sessionID);

        if(comm.sendRequest(req)) {

            resp = comm.getRequestResult();
            data = resp.getResponseList();

            try {
                String s = data.getString("Response");
                if(s.equals("success")){
                    studentSched.clear();
                    student = null;
                }else{
                    // TODO: handle
                }
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void loadStudentSched() {

        studentSched.clear();

        APICommunicator comm = new APICommunicator();
        APIRequest req = new APIRequest(ServicesEnum.GET_SCHEDULE);

        JSONObject data;
        APIResponse resp;

        req.addRequestProperty("studentID", student.getStuID());
        req.addRequestProperty("semesterID", currSem.getID());
        req.addRequestProperty("sessionID", sessionID);


        if(comm.sendRequest(req)) {

            resp = comm.getRequestResult();
            data = resp.getResponseList();

            try{

                String s = data.getString("Response");
                if(s.equals("success")){

                    JSONObject schedTemp = data.getJSONObject("scheduleData");
                    scheduleID = schedTemp.getInt("scheduleID");
                    JSONArray schedList =schedTemp.getJSONArray("classes");

                    for(int i = 0; i <schedList.length(); i++){
                        JSONObject sTemp = schedList.getJSONObject(i);

                        String dep = sTemp.getString("departmentName");
                        int dID;
                        if (dep.equals("CIS"))
                            dID = 1;
                        else if (dep.equals("CFV"))
                            dID = 2;
                        else
                            dID = 3;

                        int c = sTemp.getInt("courseID");
                        String t = sTemp.getString("courseTitle");
                        String des = sTemp.getString("courseDescription");

                        Course course = new Course(dID,c,t,des);

                        int sID = sTemp.getInt("sectionID");
                        int sCode = sTemp.getInt("sectionCode");
                        String p = sTemp.getString("profFirst") + " " +sTemp.getString("profLast");

                        String st = sTemp.getString("startTime");
                        st = st.substring(0, 2);
                        int stime = Integer.parseInt(st) * 100;
                        int etime = stime + 100;

                        String meet = sTemp.getString("meetDays");
                        String r = sTemp.getString("building") + " " + sTemp.getString("room");
                        int seat = sTemp.getInt("seats");
                        int o = sTemp.getInt("seatsOpen");

                        Section tSection = new Section(course, sID, stime, etime, meet);
                        tSection.setSCode(sCode);
                        tSection.setRoom(r);
                        tSection.setSeats(seat);
                        tSection.setOpenSeats(o);
                        tSection.setProf(p);

                        studentSched.add(tSection);
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            //TODO: Handling
        }
    }

    // Adds given section to student schedule checking for time conflicts
    // Returns index of inserted element if successful, -1 if  unsuccessful.
    public int addSectionToSched(Section s) {

        if (!studentSched.contains(s)) {
            for (int i = 0; i < studentSched.size(); i++) {
                if (s.checkTimeConflict(studentSched.get(i)))
                    return -1;
                else if (s.getCourse().compare(studentSched.get(i).getCourse()))
                    return -1;
            }
            if(updateSchedule(s,"add")) {
                studentSched.add(s);
                return studentSched.size() - 1;
            } else
                return -1;
        } else
            return -1;

    }

    public boolean removeSectionFromSched(Section s) {

        if(studentSched.contains(s)) {

            if(updateSchedule(s, "drop")) {

                studentSched.remove(s);
                return true;
            }
        }

        return false;
    }

    public boolean clearScehdule() {

        ArrayList<Section> schedTemp = studentSched;
        ArrayList<Section> removed = new ArrayList<Section>();

        while(!schedTemp.isEmpty()) {

            Section s = schedTemp.remove(0);

            if (removeSectionFromSched(s)){

                removed.add(s);
            }else{

                // TODO: Re-add courses on schedule
                return false;
            }
        }

        studentSched = schedTemp;
        return true;
    }

    public void loadDefaultSearchBuff() {

        searchBuffer.clear();

        APICommunicator comm = new APICommunicator();
        APIRequest req = new APIRequest(ServicesEnum.SEARCH_COURSES);

        JSONObject data;
        APIResponse resp;
        req.addRequestProperty("searchPhrase", "*");
        req.addRequestProperty("semesterID", currSem.getID());
        //req.addRequestProperty("sessionID", sessionID);
        req.addRequestProperty("studentID", student.getStuID());


        if (comm.sendRequest(req)) {

            resp = comm.getRequestResult();
            data = resp.getResponseList();

            parseSearchBuffer(data);
        }
    }

    public void loadSearchBuffer(String s) {

        searchBuffer.clear();

        APICommunicator comm = new APICommunicator();
        APIRequest req = new APIRequest(ServicesEnum.SEARCH_COURSES);

        JSONObject data;
        APIResponse resp;
        req.addRequestProperty("searchPhrase", s);
        req.addRequestProperty("semesterID", currSem.getID());
        //req.addRequestProperty("sessionID", sessionID);
        req.addRequestProperty("studentID", student.getStuID());


        if (comm.sendRequest(req)) {
            resp = comm.getRequestResult();
            data = resp.getResponseList();

            parseSearchBuffer(data);
        }
    }

    private boolean updateSchedule(Section s, String op) {

        APICommunicator comm = new APICommunicator();
        APIRequest req = new APIRequest(ServicesEnum.UPDATE_SCHEDULE);

        JSONObject data;
        APIResponse resp;

        req.addRequestProperty("studentID", student.getStuID());
        req.addRequestProperty("sessionID", sessionID);
        req.addRequestProperty("scheduleID", scheduleID);
        req.addRequestProperty("sectionCodeID",s.getsCode());
        req.addRequestProperty("operation", op);

        if(comm.sendRequest(req)) {

            resp =  comm.getRequestResult();
            data = resp.getResponseList();

            try {
                String r = data.getString("Response");

                if (r.equals("success")) {
                    return true;
                } else {
                    return false;
                }
            }catch (Exception e) {
                System.out.println(e.getMessage());
                return false;
            }
        }else
            return false;
    }

    private void loadSemesters() {

        APICommunicator comm = new APICommunicator();
        APIRequest req = new APIRequest(ServicesEnum.GET_SEMESTERS);

        JSONObject data;
        APIResponse resp;

        //req.addRequestProperty("sessionID", sessionID);
        req.addRequestProperty("studentID", student.getStuID());


        if(comm.sendRequest(req)) {
            
            resp =  comm.getRequestResult();
            data = resp.getResponseList();

            parseSemesters(data);
        }
    }

    private void parseSemesters(JSONObject obj) {

        try {

            String response = obj.getString("Response");
            if(response.equals("success")) {

                semesters.clear();
                JSONArray sList = obj.getJSONArray("semesters");

                for (int i = 0; i < sList.length(); i++) {

                    JSONObject sTemp = sList.getJSONObject(i);

                    int id = Integer.parseInt(sTemp.getString("semesterID"));
                    int y = Integer.parseInt(sTemp.getString("year"));
                    String season = sTemp.getString("season");

                    Semester s = new Semester(season, y, id);
                    semesters.add(s);
                }
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void parseSearchBuffer(JSONObject obj) {
        try {
            String response = obj.getString("Response");
            if (response.equals("success")) {
                searchBuffer.clear();
                JSONArray cList = obj.getJSONArray("courseList");

                // Parse all the received courses
                for (int i = 0; i < cList.length(); i++) {
                    JSONObject cTemp = cList.getJSONObject(i);

                    String dep = cTemp.getString("department");
                    int dID;
                    if (dep.equals("CIS"))
                        dID = 1;
                    else if (dep.equals("CFV"))
                        dID = 2;
                    else
                        dID = 3;

                    int cID = Integer.parseInt(cTemp.getString("courseID"));
                    String title = cTemp.getString("title");
                    String desc = cTemp.getString("Description");
                    int crd = cTemp.getInt("creditHours");

                    //Create course
                    Course tCourse = new Course(dID, cID, title, crd, desc);
                    JSONArray sList = cTemp.getJSONArray("sections");

                    // parse all the the sections of the course
                    for (int j = 0; j < sList.length(); j++) {
                        JSONObject sTemp = sList.getJSONObject(j);

                        int sID = Integer.parseInt(sTemp.getString("sectionID"));
                        int sCode = sTemp.getInt("sectionCode");

                        String p = sTemp.getString("profFirst") + " " + sTemp.getString("profLast");

                        String st = sTemp.getString("startTime");
                        st = st.substring(0, 2);
                        int stime = Integer.parseInt(st) * 100;
                        int etime = stime + 100;

                        String meet = sTemp.getString("meetDays");
                        String r = sTemp.getString("building") + " " + sTemp.getString("room");
                        int s = sTemp.getInt("seats");
                        int o = sTemp.getInt("seatsOpen");

                        //Create Section and add it to course's section list
                        Section tSection = new Section(tCourse, sID, stime, etime, meet);
                        tSection.setSCode(sCode);
                        tSection.setRoom(r);
                        tSection.setSeats(s);
                        tSection.setOpenSeats(o);
                        tSection.setProf(p);

                        tCourse.addSection(tSection);
                    }

                    searchBuffer.add(tCourse);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
