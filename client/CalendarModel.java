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
		takenSlots = new ArrayList<Point>();
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
	* Selects cells and sets their availability
	************************************************/
	public void select(int row, int col){ 
		calendar[row][col] = Cell.TAKEN;
		saveCoor(row, col);
	}

	/*************************************
	* Saves coordinates of calendar slots
	******************************************/
	public void saveCoor(int row, int col){
		coordinates.setLocation(row, col);
		takenSlots.add(coordinates.getLocation());
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
	public void addClass(Course c){
		registeredCourses.add(c);
	}
	
	/*************************************
	* @return size of registered courses
	*************************************/
	public int registeredClassSize(){
		return registeredCourses.size();
	}
}
