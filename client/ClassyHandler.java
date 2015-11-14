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
import java.util.ArrayList;

public class ClassyHandler {

  private ArrayList<Course> searchBuffer;
  private ArrayList<Section> studentSched;

  //Creates an a handler with empty feilds
  public ClassyHandler () {

    searchBuffer = new ArrayList<Course>();
    studentSched = new ArrayList<Section>();
  }

  //Creates a hadler with the given searchBuffer and studentSched
  public ClassyHandler(ArrayList<Course> courseL, ArrayList<Section> sched) {

    searchBuffer = courseL;
    studentSched = sched;
  }

  //Temporary predefined Constructor
  public ClassyHandler(int i){

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

  public ArrayList<Course> getSearchBuffer() { return searchBuffer;}
  public ArrayList<Section> getStudentSched() { return studentSched;}

  // TODO implement loadDefaultSearchBuff
  public void loadDefaultSearchBuff() {

    // will load the default searchBuffer from server
  }

  // TODO Implement loadSearchedCourseBuffer
  public void loadSearchedCourseBuffer(int desiredDNum, int desiedCID) {

    // will will request desired course from server and load it into seach
    // buffer
  }

  public void addSectionToSched(Section s) {

    if(!studentSched.contains(s)) {
      studentSched.add(s);
    }
    // TODO error resolution
  }
/*
  public void loadDefaultSearchBuff() {

    APICommunicator comm = new APICommunicator;
    APIRequest req = new APIRequest(ServciesEnum.SEARCH_COURSES);

    JSONObject data;
    APIResponse resp;
    req.addRequestProperty("searchPhrase", "*");

    if(comm.sendRequest(req)) {

      resp = comm.getRequestResult();
      data = resp.getResponseList();

      // TODO: load results into searchbuffer
    }else {
      // TODO: server communication error handling
    }
  }
  */
}
