/*****************************************************************************
* Section
*
* An entity used to reperesent a section of a course
* Created by Zack Drescher
* Last edited on 11.8.15 ZD
******************************************************************************
* Todo:
*
******************************************************************************/

public class Section {

   private Course course;
   private int sID;
   private int sTime, eTime;
   private WeeklySchedule meet;
   private Semester sem;

   // Creates a Setion with all the given information.
   public Section(Course c, int s, int t1, int t2, String m, Semester s2) {

     course = c;
     sID = s;
     sTime = t1;
     eTime = t2;
     meet = new WeeklySchedule(m);
     sem = s2;
   }

   public Course getCourse() { return course;}
   public int getSID() { return sID;}
   public int getSTime() { return sTime;}
   public int getETime() { return eTime;}
   public WeeklySchedule getMeetings() { return meet;}
   public Semester getSem() {return sem;}

   // retruns true if there is a time conflict between this section and section
   // s, returns false other wise.
   public boolean checkTimeConflict(Section s) {

    //If the sections share a day
    if(meet.checkConflict(s.getMeetings())) {
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
    }
    return false;
   }

    public String toString() {
        String s = course.toString();
        s += "- " + sID;
        return s;
    }
}
