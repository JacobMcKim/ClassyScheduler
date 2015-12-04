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
   private int sTime, eTime, seats, openSeats;
   private String prof, room;
   private WeeklySchedule meet;

   // Creates a Setion with all the given information.
   public Section(Course c, int s, int t1, int t2, String m) {

     course = c;
     sID = s;
     sTime = t1;
     eTime = t2;
     meet = new WeeklySchedule(m);
   }

   public Course getCourse() { return course;}
   public int getSID() { return sID;}
   public int getSTime() { return sTime;}
   public int getETime() { return eTime;}
   public String getProf() { return prof;}
   public WeeklySchedule getMeetings() { return meet;}
   public String getRoom() { return room;}
   public int getSeats() { return seats;}
   public int getOpenSeats() {return  openSeats;}

   public void setProf(String n) {prof = n;}
   public void setRoom(String r) {room = r;}
   public void setSeats(int s) {seats = s;}
   public void setOpenSeats(int s) {openSeats = s;}


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
