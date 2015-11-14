/*****************************************************************************
* ClassyHandler
*
* A Handles actions of searching courses in search bar, and adding/removing
* sections to and from the users current schedule.
*
* Created by Zack Drescher
* Last edited on 11.11.15 ZD
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

  public ArrayList<Course> getSearchBuffer() { return searchBuffer;}
  public ArrayList<String> getStudentSched() { return studentSched;}

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
}
