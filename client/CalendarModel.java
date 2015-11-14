package packageCal;

import java.util.*;
import java.awt.*;

public class CalendarModel {

	/** Sets 2-dimensional array for calendar */
	private Cell[][] calendar;

	private int rows;
	private int columns;
	
	/** List of registered courses **/
	private ArrayList<Course> registeredCourses;

	/** Coordinates for calendar slots **/
	private Point coordinates;

	/** List of taken time slots **/
	private ArrayList<Point> takenSlots;

	/****************************************
	* Constructor for setting up new calendar
	****************************************/
	public CalendarModel(){
		this.rows = 15;
		this.columns = 8;
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
		takenSlots.clear();
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
	* Selects MWF cells and sets their availability
	************************************************/
	public void selectMWF(int row1, int col1, int row2, int col2, int row3, int col3){  
		calendar[row1][col1] = Cell.TAKEN;
		calendar[row2][col2] = Cell.TAKEN;
		calendar[row3][col3] = Cell.TAKEN;
		saveMWF(row1, col1, row2, col2, row3, col3);
	}

	/************************************************************
	* Selects Tuesday, Thursday cells and sets their availability
	*************************************************************/
	public void selectTR(int row1, int col1, int row2, int col2){  
		calendar[row1][col1] = Cell.TAKEN;
		calendar[row2][col2] = Cell.TAKEN;
		saveTR(row1, col1, row2, col2);
	}

	/*************************************
	* Saves coordinates of MWF slots
	******************************************/
	public void saveMWF(int row1, int col1, int row2, int col2, int row3, int col3){
		coordinates.setLocation(row1, col1);
		takenSlots.add(coordinates.getLocation());
		coordinates.setLocation(row2, col2);
		takenSlots.add(coordinates.getLocation());
		coordinates.setLocation(row3, col3);
		takenSlots.add(coordinates.getLocation());
	}

	/*************************************
	* Saves coordinates of Tuesday, Thursday slots
	******************************************/
	public void saveTR(int row1, int col1, int row2, int col2){
		coordinates.setLocation(row1, col1);
		takenSlots.add(coordinates.getLocation());
		coordinates.setLocation(row2, col2);
		takenSlots.add(coordinates.getLocation());
	}

	/*************************************
	* Displays current calendar
	*************************************/
	public Cell[][] getCalendar(){
		return calendar;
	}
	
	public void addClass(Course c){
		registeredCourses.add(c);
	}
	
	public int registeredClassSize(){
		return registeredCourses.size();
	}
	public ArrayList<Course> getRegisteredCourse(){
		return registeredCourses;
	}
}
