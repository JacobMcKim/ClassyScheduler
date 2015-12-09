package packageCal;
/*****************************************************************************
* CalendarModel
*
* Model for Calendar 
*
* Created by Kristopher Trevino
* Last edited on 11.15.15 ZD
******************************************************************************
*
******************************************************************************/
import java.util.*;
import java.awt.*;

import javax.swing.DefaultListModel;

public class CalendarModel {

	/** Sets 2-dimensional array for calendar */
	private Cell[][] calendar;

	private int rows;
	private int columns;
	
	/** List of registered courses **/
	private ArrayList<Course> registeredCourses;
	
	/** List of registered courses **/
	private ArrayList<Section> registeredSections;

	/** Coordinates for calendar slots **/
	private Point coordinates;

	/** List of taken time slots **/
	private ArrayList<Point> takenSlots;

	DefaultListModel<Course> model = new DefaultListModel<Course>();
	
	ClassyHandler classy;
	
	/****************************************
	* Constructor for setting up new calendar
	****************************************/
	public CalendarModel(ClassyHandler c){
		this.classy = c;
		this.rows = 15;
		this.columns = 8;
		takenSlots = new ArrayList<Point>();
		registeredSections = new ArrayList<Section>();
		registeredCourses = new ArrayList<Course>();
		coordinates = new Point();
		calendar = new Cell[rows][columns];
		//sets all new cells to empty
		for(int row = 0; row < this.rows; row++)
			for(int col = 0; col < this.columns; col++){
				calendar[row][col] = Cell.EMPTY;
			//sets time and date cells to taken
				if (row == 0 || col == 0)
					calendar[row][col] = Cell.TAKEN;
		}
	}

	/**************************************
	* Resets calendar
	**************************************/
	public void reset(){
		registeredCourses.clear();
		registeredSections.clear();
		takenSlots.clear();
		model.clear();
		//sets all new cells to empty
		for(int row = 0; row < this.rows; row++)
			for(int col = 0; col < this.columns; col++){
				calendar[row][col] = Cell.EMPTY;
			//sets time and date cells to taken
			if (row == 0 || col == 0)
				calendar[row][col] = Cell.TAKEN;
			}
	}

	/***********************************************
	* Selects cells and sets their availability to taken
	************************************************/
	public void select(int row, int col){ 
		calendar[row][col] = Cell.TAKEN;
		saveCoor(row, col);
	}
	
	/***********************************************
	* Selects cells and sets their availability to available
	************************************************/
	public void unSelect(int row, int col){ 
		calendar[row][col] = Cell.EMPTY;
		deleteCoor(row, col);
	}

	/*************************************
	* Saves coordinates of calendar slots
	******************************************/
	public void saveCoor(int row, int col){
		coordinates.setLocation(row, col);
		takenSlots.add(coordinates.getLocation());
	}
	
	/*************************************
	* Deletes coordinates of calendar slots
	******************************************/
	public void deleteCoor(int row, int col){
		coordinates.setLocation(row, col);
		takenSlots.remove(coordinates.getLocation());
	}
	
	//TESTING
	public Point retrieveCoor(int row, int col){
		coordinates.setLocation(row, col);
		return coordinates.getLocation();
	}

	/*************************************
	* Displays current calendar
	*************************************/
	public Cell[][] getCalendar(){
		return calendar;
	}
	
	/*************************************
	* Adds course
	*************************************/
	public void addClass(Section s){
		classy.addSectionToSched(s);                                 //----------------CLASSY
		registeredCourses.add(s.getCourse());
		registeredSections.add(s);
		model.addElement(s.getCourse());
	}
	
	/*************************************
	* Removes course
	*************************************/
	public void removeClass(int i){
		//TODO try catch block
		if (registeredCourses.isEmpty() == false){
			classy.removeSectionFromSched(registeredSections.get(i)); //---------------CLASSY
			registeredCourses.remove(i);
			registeredSections.remove(i);
			model.removeElementAt(i);
		}
	}
	
	/*************************************
	* @return size of registered courses
	*************************************/
	public int registeredCoursesSize(){
		return registeredCourses.size();
	}
	
	/*************************************
	* @return size of registered sections
	*************************************/
	public int registeredSectionsSize(){
		return registeredSections.size();
	}
	
	/*************************************
	* 
	*************************************/
	public Section getSection(int i){
		return registeredSections.get(i);
	}
	
	/*************************************
	* 
	*************************************/
	public Course getCourse(int i){
		return registeredCourses.get(i);
	}
	
	/*************************************
	 * 
	 * @return list of registered courses
	 ************************************/
	public  DefaultListModel<Course> getModelList(){
		return model;
	}
	 
}
