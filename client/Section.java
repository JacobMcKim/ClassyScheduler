/*****************************************************************************
* Section
*
* An entity used to reperesent a section of a course
* Created by Zack Drescher
* Last edited on 10.28.15 ZD
******************************************************************************
* Todo:
* Find a better data type for start time and end time
* integrate WeeklySchedule
* implement day checking within checkTimeConflict
******************************************************************************/

public class Section {

   private Course course;
   private int sID;
   private int sTime, eTime;
   private String meet;

   // Creates a Setion with all the given information.
   public Section(Course c, int s, int t1, int t2, String m) {

     course = c;
     sID = s;
     sTime = t1;
     eTime = t2;
     meet = m;
   }

   public Course getCourse() { return course;}
   public int getSID() { return sID;}
   public int getSTime() { return sTime;}
   public int getETime() { return eTime;}
   public String getMeetings() { return meet;}

   // retruns true if there is a time conflict between this section and section
   // s, returns false other wise.
   public boolean checkTimeConflict(Section s) {

     // If s either starts or finishes within this sections time period
     if((sTime <= s.getSTime() && eTime >= s.getSTime()) ||
                              (sTime <= s.getETime() && eTime >= s.getETime())){
        return true;
     }
     // If this section either starts or finished within s's time period
     else if((s.getSTime() <= sTime && s.getETime() >= sTime) ||
                              (s.getSTime() <= eTime && s.getETime() >= eTime)){
        return true;
     }
     //TODO if there are time conflicts check if they exist on the same day.
     return false;
   }
}
