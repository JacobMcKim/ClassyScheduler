/*****************************************************************************
* Course
*
* An entity used to reperesent a Course
* Created by Zack Drescher
* Last edited on 11.8.15 ZD
******************************************************************************
* Todo:
*
******************************************************************************/
import java.util.ArrayList;

public class Course {

   private int dNum, cID, cred;
   String title;
   String description;
   ArrayList<Section> sectionList;

   // Creates a Course with an empty section list
   public Course(int d, int c, String t, String desc) {

     dNum = d;
     cID = c;
     title = t;
     description = desc;
     sectionList = new ArrayList<Section>();
   }

    public Course(int d, int c, String t, int cr, String desc) {

        dNum = d;
        cID = c;
        title = t;
        cred = cr;
        description = desc;
        sectionList = new ArrayList<Section>();
    }

   // Creates a Course with the given Section list
   public Course(int d, int c, String t, String desc, ArrayList<Section> sl) {

     dNum = d;
     cID = c;
     title = t;
     description = desc;
     sectionList = sl;
   }

   public int getDNum() { return dNum;}
   public int getCID() { return cID;}
   public String getTitle() { return title;}
   public String getDescription() { return description;}
   public ArrayList<Section> getSectionList() { return sectionList;}


   public Section getSection(int i) {
      return sectionList.get(i);
   }

   // Adds the given section to this courses Section list.
   // returns the index of the secion if successful or -1 if unsuccessful
   public int addSection(Section s) {

     if(compare(s.getCourse())) {
      sectionList.add(s);
      return sectionList.size() - 1;
    }
    return -1;
   }

   // Compares this course to another course.
   // returns true if they have the same dNum and CID, false otherwise.
   public boolean compare(Course c) {

     if(c.getDNum() == dNum)
      if(c.getCID() == cID)
        return true;
     return false;
   }

    public String toString() {
        String s = "";

        if(dNum == 1)
            s += "CIS ";
        else if(dNum == 2)
            s += "CFV ";
        else
            s += "XXX ";

        s += cID;
        return s;
    }

}
