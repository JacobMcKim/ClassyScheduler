/*****************************************************************************
* ClassyHandler
*
* A Handles actions of searching courses in search bar, and adding/removing
* sections to and from the users current schedule.
*
* Created by Zack Drescher
* Last edited on 11.14.15 ZD
******************************************************************************
* Todo:
* Integrate with UI
* Implement Communication with server
* implement loadDefaultSearchBuff
* Implement loadSearchedCourseBuffer
* Implement removeSectionFromSched
* error resolution in addSectionToSched
* Constructor
*   -Intergrate loadDefaultSearchBuff
* loadDefaultSearchBuff
*   - Load resoinse data
*   - Implement server communication erroe handling.
******************************************************************************/
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

public class ClassyHandler {

  private ArrayList<Course> searchBuffer;
  private ArrayList<Section> studentSched;
  private Semester currSem;

  //Creates an a handler with empty feilds
  public ClassyHandler () {

    searchBuffer = new ArrayList<Course>();
    studentSched = new ArrayList<Section>();
    currSem = new Semester(SemEnum.FALL, 16);
  }

  // Creates a handler set for the current semester
  public ClassyHandler (Semester s) {

      searchBuffer = new ArrayList<Course>();
      studentSched = new ArrayList<Section>();
      currSem = s;
  }

  //Creates a hadler with the given searchBuffer and studentSched
  public ClassyHandler(ArrayList<Course> courseL, ArrayList<Section> sched) {

    searchBuffer = courseL;
    studentSched = sched;
      currSem = new Semester(SemEnum.FALL, 16);
  }

  //Temporary predefined Constructor
  public ClassyHandler(int i){

      currSem = new Semester(SemEnum.FALL, 16);

      Course cis350 = new Course(1, 350, "Software Engineering",
                                                            "This is a class.");
    Course cis263 = new Course(1, 263, "Data Structures", "This is a class.");



    Section s1 = new Section(cis350, 1, 1000, 1050, "MWF", currSem);
    Section s2 = new Section(cis350, 2, 1100, 1150, "MWF", currSem);
    Section s3 = new Section(cis263, 1, 1000, 1050, "MWF", currSem);
    Section s4 = new Section(cis263, 2, 1100, 1150, "MWF", currSem);

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

  public ArrayList<Course> getSearchBuffer() { return searchBuffer;}
  public ArrayList<Section> getStudentSched() { return studentSched;}

  // Getter methods that return search buffer and student sched in the form of
  // arrays
  public Course[] getArraySearchBuf() {

    Course[] temp = new Course[searchBuffer.size()];

    for(int i = 0; i < searchBuffer.size(); i++) {
      temp[i] = searchBuffer.get(i);
    }
    return temp;
  }

  public Section[] getArrayStuSched() {

    Section[] temp = new Section[studentSched.size()];

    for(int i = 0; i < studentSched.size(); i++) {
      temp[i] = studentSched.get(i);
    }
    return temp;
  }

  // Set the current Semester
  public void setCurrSem(Semester s) {
      currSem = s;

      // TODO: load new search buffer/schedule based on new semester
  }
  // Adds given section to student schedule checking for time conflicts
  // Returns index of inserted element if successful, -1 if  unsuccessful.
  public int addSectionToSched(Section s) {

    if(!studentSched.contains(s)) {
        for(int i = 0; i < studentSched.size(); i++) {
            if(s.checkTimeConflict(studentSched.get(i)))
                return -1;
            else if(s.getCourse().compare(studentSched.get(i).getCourse()))
                return -1;
        }
        studentSched.add(s);
        return studentSched.size()-1;
    } else
        return -1;

  }

    // TODO Implement loadSearchedCourseBuffer
    public void loadSearchBuffer(String s) {

        APICommunicator comm = new APICommunicator();
        APIRequest req = new APIRequest(ServicesEnum.SEARCH_COURSES);

        JSONObject data;
        APIResponse resp;
        req.addRequestProperty("searchPhrase", s);
        req.addRequestProperty("semID",1);

        if(comm.sendRequest(req)) {
            resp = comm.getRequestResult();
            data = resp.getResponseList();

            parseJSONObject(data);
        }
    }

  public void loadDefaultSearchBuff() {

    APICommunicator comm = new APICommunicator();
    APIRequest req = new APIRequest(ServicesEnum.SEARCH_COURSES);

    JSONObject data;
    APIResponse resp;
    req.addRequestProperty("searchPhrase", "*");
    req.addRequestProperty("semesterID",1);

    if(comm.sendRequest(req)) {

        resp = comm.getRequestResult();
        data = resp.getResponseList();

        parseJSONObject(data);
    }
  }

  private void parseJSONObject(JSONObject obj) {
      try {
          String response = obj.getString("Response");
          if(response.equals("success")) {
              searchBuffer.clear();
              JSONArray cList = obj.getJSONArray("courseList");

              // Parse all the received courses
              for(int i = 0; i < cList.length(); i++) {
                  JSONObject cTemp = cList.getJSONObject(i);

                  String dep = cTemp.getString("department");
                  int dID;
                  if(dep.equals("CIS"))
                      dID = 1;
                  else if(dep.equals("CFV"))
                      dID = 2;
                  else
                      dID = 3;

                  int cID = Integer.parseInt(cTemp.getString("courseID"));
                  String title = cTemp.getString("title");
                  String desc = cTemp.getString("Description");
                  // TODO: implement credit hours

                  //Create course
                  Course tCourse = new Course(dID,cID,title,desc);
                  JSONArray sList = cTemp.getJSONArray("sections");

                  // parse all the the sections of the course
                  for(int j = 0; j < sList.length(); j++) {
                      JSONObject sTemp = sList.getJSONObject(j);

                      int sID = Integer.parseInt(sTemp.getString("sectionID"));

                      // TODO: implement semester check
                      Semester sem = new Semester(SemEnum.FALL, 16);
                      // TODO: implement professor
                      // TODO: convert string times to int
                      String st = sTemp.getString("startTime");
                      st = st.substring(0,2);
                      int stime = (int) Math.random()*(1400-900) + 900;
                      int etime = stime + 100;
                      //int stime = 1100;
                      //int etime = 1200;
                      String meet = sTemp.getString("meetDays");
                      // TODO: implement building + room
                      // TODO: implement seats and seats open

                      //Create Section and add it to course's section list
                      Section tSection = new Section(tCourse,sID,stime,etime,meet,sem);
                      tCourse.addSection(tSection);
                  }

                  searchBuffer.add(tCourse);
              }
          }
      } catch(Exception e) {
          System.out.println(e.getMessage());
      }
  }

}
