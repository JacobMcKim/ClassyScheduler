/*****************************************************************************
* Tester
*
* A class used to test various components of the client until we figure out a
* unit testing tool.
* Created by Zack Drescher
* Last edited on 10.28.15 ZD
******************************************************************************
* Todo:

* Course
*  constuctors
*  compare
* Section
*  constrution
* Handler
*  construction
*  addSectionToSched
******************************************************************************/

public class Tester {

  public static void main(String args[]) {

    Course cis350 = new Course(1, 350, "Software Engineering",
                                                            "This is a class.");

    WeeklySchedule mtwhf = new WeeklySchedule("MTWHF");
    WeeklySchedule mwf = new WeeklySchedule("MWF");
    WeeklySchedule th = new WeeklySchedule("TH");

    if(mtwhf.checkConflict(mwf))
      System.out.println("Conflict MTWHF v MWF detected");
    else
      System.out.println("MTWHF v MWF went wrong");

    if(mtwhf.checkConflict(th))
      System.out.println("Conflict MTWHF v th detected");
    else
      System.out.println("MTWHF v th went wrong");

    if(!mwf.checkConflict(th))
      System.out.println("No conflict mwf v th detected");
    else
      System.out.println("mwf v th went wrong");


    Section s1 = new Section(cis350, 1, 1000, 1050, "MWF");
    Section s2 = new Section(cis350, 2, 1100, 1150, "MWF");
    Section tc1 = new Section(cis350, 3, 1000, 1050, "MWF");

    if(s1.checkTimeConflict(tc1))
      System.out.println("Conflict detected!!");
    else
      System.out.println("failed to detect conflict");

    if(cis350.addSection(s1) == 0)
      System.out.println("s1 added!!!");
    else
      System.out.println("s1 failed to add");

    if(cis350.addSection(s2) == 1)
      System.out.println("s2 added!!!");
    else
      System.out.println("s2 failed to add");
  }

}
