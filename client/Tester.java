/*****************************************************************************
* Tester
*
* A class used to test various components of the client until we figure out a
* unit testing tool.
* Created by Zack Drescher
* Last edited on 11.14.15 ZD
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

import java.util.ArrayList;

public class Tester {

  public static void main(String args[]) {

    Course cis350 = new Course(1, 350, "Software Engineering",
                                                            "This is a class.");

    WeeklySchedule mtwhf = new WeeklySchedule("MTWHF");
    WeeklySchedule mwf = new WeeklySchedule("MWF");
    WeeklySchedule th = new WeeklySchedule("TH");

    System.out.println("MTWHF v MWF");
    if(mtwhf.checkConflict(mwf))
      System.out.println("passed");
    else
      System.out.println("failed");
    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~");

    System.out.println("MTWHF v TH");
    if(mtwhf.checkConflict(th))
      System.out.println("passed");
    else
      System.out.println("failed");
    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~");

    System.out.println("MWF v TH no conflict");
    if(!mwf.checkConflict(th))
      System.out.println("passed");
    else
      System.out.println("failed");

    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~");
    System.out.println("Section testing");
    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~");

    Section s1 = new Section(cis350, 1, 1000, 1050, "MWF");
    Section s2 = new Section(cis350, 2, 1100, 1150, "MWF");
    Section tc1 = new Section(cis350, 3, 1000, 1050, "MWF");

    System.out.println("check time and day conflict");
    if(s1.checkTimeConflict(tc1))
      System.out.println("passed");
    else
      System.out.println("failed");


    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~");
    System.out.println("Course testing");
    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~");
    System.out.println("Add section to course");
    if(cis350.addSection(s1) == 0)
      System.out.println("passed");
    else
      System.out.println("failed");
    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~");

    System.out.println("Add second section to course");
    if(cis350.addSection(s2) == 1)
      System.out.println("passed");
    else
      System.out.println("failed");
    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~");
    System.out.println("Add section with time conflict");
    if(cis350.addSection(tc1) == -1)
      System.out.println("passed");
    else
      System.out.println("failed");
    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~");

    System.out.println("Make sure section wasn't added");
    if(cis350.getSectionList().size() == 2)
      System.out.println("passed");
    else
      System.out.println("failed");


    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~");
    System.out.println("Handler testing");
    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~");

    System.out.println("Constructor Test");
    Course cis263 = new Course(1, 263, "Data Structures", "This is a class.");
    Section s3 = new Section(cis263, 1, 1000, 1050, "MWF");
    Section s4 = new Section(cis263, 2, 1100, 1150, "MWF");

    cis263.addSection(s3);
    cis263.addSection(s4);

    ArrayList<Course> cList = new ArrayList<Course>();
    cList.add(cis350);
    cList.add(cis263);

    ArrayList<Section> sList = new ArrayList<Section>();
    sList.add(s1);

    ClassyHandler classy = new ClassyHandler(cList, sList);

    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~");
    System.out.println("Search Buffer properly instantiated");
    if(classy.getSearchBuffer().size() == cList.size())
      System.out.println("passed");
    else
      System.out.println("failed");


    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~");
    System.out.println("studentSched properly instantiated");
    if(classy.getStudentSched().size() == sList.size())
      System.out.println("passed");
    else
      System.out.println("failed");

  }
}
