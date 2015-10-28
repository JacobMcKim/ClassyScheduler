/*****************************************************************************
* ClassyHandler
*
* A Handles actions of searching courses in search bar, and adding/removing
* sections to and from the users current schedule.
*
* Created by Zack Drescher
* Last edited on 10.28.15 ZD
******************************************************************************
* Todo:
* Add functionality
* Implement Communication with server
* implement loadDefaultSearchBuff
* Implement loadSearchedCourseBuffer
* error resolution in addSectionToSched
* implement checking time conflicts in student schedule in addSectionToSched
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
      // TODO check time conflicts
      studentSched.add(s);
    }
    // TODO error resolution
  }
}
