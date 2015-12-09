package packageCal;
/*****************************************************************************
* CalendarPanel
* 
* Student User Interface
*
* Created by Kristopher Trevino
* Last edited on 11.15.15 ZD
******************************************************************************
* Todo:
* Add registered courses to a JList
* Add JList of registered courses to Web Registered Panel
* Make JTextField for course search tool
* Add Description of course to right panel
* Make action event for StartOver button 
******************************************************************************/
import java.awt.*;

import packageCal.Course;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import packageCal.Cell;

public class CalendarPanel extends JPanel{

	/** Buttons for GUI **/
	private JButton addClass, startOver, removeClass, save, searchButton, clearSearchButton;
	
	/** Size of screen **/
	Dimension screenSize;
	
	/** All panels for GUI **/
	private JPanel leftPanel, centerPanel, rightPanel, semesterPanel, buttonPanel, coursePanel, searchPanel, sectionPanel, calendarPanel, registeredList;
	
	/** Labels for all of the panels **/
	private JLabel leftLabel, centerLabel, rightLabel, semesterLabel, searchCoursesLabel, courseLabel, sectionsLabel, searchLabel, registered, registeredLabel;
	
	/** Panels for calendar **/
	private JPanel[][] calendar;
	
	/** Cells that determine if calendar slots are taken or empty **/
	private Cell[][] iCalendar;
	
	private int rows;
	private int columns;
	
	/** Object of Calendar model **/
	private CalendarModel cal;
	
	/** Weekday labels for Calendar panel **/
	private String[] days = {"Monday", "Tuesday","Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
	
	/** Class times label for Calendar panel **/
	private String[] time = {"8am", "9am", "10am","11am","12pm","1pm","2pm","3pm","4pm","5pm","6pm","7pm","8pm","9pm"};
	
	/** Border for class times **/
	private TitledBorder tb;

	/** List of Courses displayed **/
	private JList<Course> list;

	/** List of Sections **/
	private JList<Section> listSec;
	
	/** Description of Course **/
	JTextArea description;
	
	/** List of Registered Courses **/
	private JList<Course> webRegistered;
	
	/** Y-Coordinate of calendar cell **/
	private ArrayList<Integer> y;
	
	/** X-Coordinate of calendar cell **/
	int x;
	
	/** List of sections within a course **/
	DefaultListModel<Section> secList;
	
	DefaultListModel<Course> searchCourse;
	
	/** Searches classes **/
	JTextField search;
	
	Course c;
	
	/** Default panel background color **/
	Color defaultColor;

	/** Creates object for handler **/
	private ClassyHandler classy;
	
	private String courseText;
	
	JComboBox<SemEnum> j;
	
	
	DefaultListModel<Course> listmod;
	
	JButton logoutButton;
	LoginPanel loginP;
	boolean loggedIn;
	
	JScrollPane listScroller;
	JFrame f;
	
	
	/********************************************************************
	* Constructor: Sets up the GUI	
	*******************************************************************/
	public CalendarPanel(){
		loggedIn = false;
		int count = 0;
//		classy = new ClassyHandler(1);
		classy = new ClassyHandler();
		loginP = new LoginPanel(classy);
		displayLogin();
		while(count >= 0)
		if (loginP.getLoginStatus() == true){
			f.setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);
			f.removeAll();
			System.out.println("You have logged in!");
			count = -1;
		cal = new CalendarModel(classy);
		secList = new DefaultListModel<Section>();
		list = new JList<Course>(classy.getArraySearchBuf());
		listSec = new JList<Section>(secList);
		webRegistered = new JList<Course>(cal.getModelList());
		 
		//size for calendar panel
		rows = 15;
		columns = 8;
		
		//sets up overall CalendarPanel
		setLayout (new BorderLayout());
		setBackground(Color.gray);
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		//LEFT PANEL
		//TODO name of student that is logged in
		leftLabel = new JLabel ("Name of Student");
		leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		leftPanel.setPreferredSize(new Dimension((screenSize.width / 5), screenSize.height));
		leftPanel.setBackground(Color.gray);
		leftPanel.add(leftLabel);
		leftPanel.add(Box.createRigidArea(new Dimension(0,25)));
		
		//LEFT PANEL --->Semester Drop down
		semesterPanel = new JPanel();
		semesterPanel.setPreferredSize(new Dimension((screenSize.width / 5), (screenSize.height /8)));
		semesterPanel.setBackground(Color.gray);
		semesterLabel = new JLabel("Current Semester: ");
		j = new JComboBox<SemEnum>(SemEnum.values()); 
		j.addActionListener(new SemesterListener()); 
		semesterPanel.add(semesterLabel);
		semesterPanel.add(j);
		semesterPanel.add(Box.createRigidArea(new Dimension (0,10)));
		leftPanel.add(semesterPanel);
//		textArea.setLineWrap(true);  
//		textArea.setWrapStyleWord(true); 
		
		//LEFT PANEL-->Search Panel //TODO
		searchPanel = new JPanel();
//		searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
		searchPanel.setPreferredSize(new Dimension((screenSize.width / 5), (screenSize.height /8)));
		searchPanel.setBackground(Color.gray);
		searchCoursesLabel = new JLabel("Search Course");
		searchPanel.add(searchCoursesLabel);
		search = new JTextField("Search Here");
		search.setPreferredSize(new Dimension((screenSize.width/ 6), 20));
		searchPanel.add(search);
		search.addActionListener(new SearchListener());
		searchButton = new JButton("Search");
		searchButton.addActionListener(new ButtonListener());
		clearSearchButton = new JButton("Clear");
		clearSearchButton.addActionListener(new ButtonListener());
		searchPanel.add(searchButton);
		searchPanel.add(clearSearchButton);
		leftPanel.add(searchPanel);

		//LEFT PANEL-->Course Panel
		coursePanel = new JPanel();
		coursePanel.setPreferredSize(new Dimension((screenSize.width / 5), (screenSize.height /8)));
		coursePanel.setBackground(Color.gray);
		courseLabel = new JLabel("Courses");
		coursePanel.add(courseLabel);
		listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(220, 60));
		listScroller.setAlignmentX(LEFT_ALIGNMENT);
		list.addListSelectionListener(new CourseListener());
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		coursePanel.add(listScroller);
		leftPanel.add(coursePanel);				

		//LEFT PANEL-->Section1 Panel
		sectionPanel = new JPanel();
		sectionPanel.setPreferredSize(new Dimension((screenSize.width / 5), (screenSize.height /8)));
		sectionPanel.setBackground(Color.gray);
		sectionsLabel = new JLabel("Sections");
		sectionPanel.add(sectionsLabel);
		JScrollPane listScroller1 = new JScrollPane(listSec);
		listScroller1.setPreferredSize(new Dimension(220, 40));
		listScroller1.setAlignmentX(LEFT_ALIGNMENT);
		listSec.addListSelectionListener(new SectionListener());
		listSec.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		sectionPanel.add(listScroller1);
		leftPanel.add(sectionPanel);	
		sectionPanel.setVisible(false);

		
		//LEFT PANEL-->Web Registered Panel
		registeredList = new JPanel();
		registeredList.setPreferredSize(new Dimension((screenSize.width / 5), (screenSize.height /4)));
		registeredList.setBackground(Color.gray);
		registeredLabel = new JLabel("Web Registered");
		registeredList.add(registeredLabel);
		JScrollPane listScroller3 = new JScrollPane(webRegistered);
		listScroller3.setPreferredSize(new Dimension(220, 60));
		listScroller3.setAlignmentX(LEFT_ALIGNMENT);
		webRegistered.addListSelectionListener(new RegisteredListener());
		webRegistered.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		registeredList.add(listScroller3);
		//buttons 
		addClass = new JButton ("Add Class");
		addClass.addActionListener(new ButtonListener());
		removeClass = new JButton ("Remove Class");
		removeClass.addActionListener(new ButtonListener());
		registeredList.add(addClass);
		registeredList.add(removeClass);
		leftPanel.add(registeredList);	
		
		
		//LEFT PANEL-->Button Panel
//		save = new JButton ("Save Schedule");
//		save.addActionListener(new ButtonListener());
		startOver = new JButton("Start Over");
		startOver.addActionListener(new ButtonListener());
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.setPreferredSize(new Dimension((screenSize.width / 5), (screenSize.height /8)));
		buttonPanel.setBackground(Color.gray);
		buttonPanel.add(Box.createRigidArea(new Dimension(5,0)));
//		buttonPanel.add(save);
//		buttonPanel.add(Box.createRigidArea(new Dimension(5,0)));
		buttonPanel.add(startOver);
		leftPanel.add(buttonPanel);
		
		//CENTER PANEL
		centerLabel = new JLabel("FALL SEMESTER"); 
		centerPanel = new JPanel();
		centerPanel.setPreferredSize(new Dimension((screenSize.width / 2), screenSize.height));
		centerPanel.setBackground(Color.gray);
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		centerPanel.add(centerLabel);
		
		//CENTER PANEL-->Calendar Panel
		calendarPanel = new JPanel();
		defaultColor = calendarPanel.getBackground();
		calendarPanel.setLayout(new GridLayout(rows,columns));
		calendar = new JPanel[rows][columns];
		
		
		//creates calendar panels
		for (int row = 0; row < rows; row++) 
			for (int col = 0; col < columns; col++) {
				calendar[row][col] = new JPanel();
				calendar[row][col].setBorder(BorderFactory.createEtchedBorder());
				//sets labels for days of the week
				if (row == 0 && col > 0 ){
					calendar[row][col].add(new JLabel(days[col-1]));
					calendar[row][col].setBackground(Color.LIGHT_GRAY);
			    }
				//sets labels and formats panels for time of day
				if(row > 0 && col == 0){
					tb = BorderFactory.createTitledBorder(time[row -1]);
					calendar[row][col].setBorder(tb);
					calendar[row][col].setBackground(Color.LIGHT_GRAY);
				}
				
				//colors empty box in left corner of calendar
				calendar[0][0].setBackground(Color.LIGHT_GRAY);
				
				calendarPanel.add(calendar[row][col]);					
			}
		centerPanel.add(calendarPanel);
		displayCalendar();
				
		//RIGHT PANEL 
		rightLabel = new JLabel ("Description of Course");
		rightPanel = new JPanel();
		rightPanel.setPreferredSize(new Dimension((screenSize.width / 5), screenSize.height));
		rightPanel.setBackground(Color.gray);		
		rightPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		description = new JTextArea("Section is not currently selected");
		description.setPreferredSize(new Dimension((screenSize.width / 6),(screenSize.height / 5)));
		rightPanel.add(Box.createRigidArea(new Dimension(65,0)));
		logoutButton = new JButton("Logout");
		logoutButton.addActionListener(new ButtonListener());
		rightPanel.add(logoutButton);
		rightPanel.add(Box.createRigidArea(new Dimension(0,120)));
		rightPanel.add(rightLabel);
		rightPanel.add(description);
		
		
		//Overall CalendarPanel Layout
		setPreferredSize(new Dimension(screenSize.width, screenSize.height));
		setBackground(Color.gray);
		add(leftPanel, BorderLayout.WEST);
		add(centerPanel, BorderLayout.CENTER);
		add(rightPanel, BorderLayout.EAST);
		}
		else{
			System.out.println("");
			count++;
		}
		
	}
	
	private void displayLogin(){
		f = new JFrame();
		f.setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);
		f.getContentPane().add(loginP);
		f.setExtendedState(f.MAXIMIZED_BOTH);
		f.pack(); 
		f.setVisible(true);
		
	}
	
	/*************************************************
	* Displays GUI
	************************************************/
	private void displayCalendar(){
		iCalendar = cal.getCalendar();
		for (int row = 0; row < this.rows; row++)
			for (int col = 0; col < this.columns; col++) {
				if (iCalendar[row][col] == Cell.EMPTY){ 	
					calendar[row][col].setBorder(BorderFactory.createLoweredBevelBorder());
				}
				if (iCalendar[row][col] == Cell.TAKEN){
//					calendar[row][col].setBorder(BorderFactory.createRaisedBevelBorder());
//					calendar[x][y.get(i)].setBackground(Color.lightGray);
//					calendar[x][y.get(i)].add(new JLabel("Registered"));
				}
			}
	}
	
	private void resetCalendar(){
		for (int row = 0; row < rows; row++) 
			for (int col = 0; col < columns; col++) 
				if(row != 0 && col != 0){
					calendar[row][col].setBackground(defaultColor);
					calendar[row][col].removeAll();
				}
	}
	private boolean checkIfRegistered(Course c, Section s){
		boolean checkSection = false;
		boolean checkCourse = false;
		for(int i=0; i<cal.registeredCoursesSize(); i++)
			if(cal.getCourse(i) == c)
				checkCourse = true; 
		for(int i=0; i<cal.registeredSectionsSize(); i++)
			if(cal.getSection(i) == s)
				checkSection = true; 
		if(checkSection == true && checkCourse == true)
			return true;
		return false;
	}
	
	private boolean checkIfRegistered(Course c){
		for(int i=0; i<cal.registeredCoursesSize(); i++)
			if(cal.getCourse(i) == c)
				return true;
		return false;
	}
	
	private boolean timeConflict(Section s){
		for (int i=0; i<cal.registeredCoursesSize(); i++){
			if(cal.getSection(i).checkTimeConflict(s) == true || 
					s.checkTimeConflict(cal.getSection(i)) == true)
				return true;
		}
		return false;
	}
	
	/*********************************************************** 
	 * Represents a listener for button push events.
	 * 
	 **********************************************************/
	private class ButtonListener implements ActionListener{
		private Component frame;

		//-------------------------------------------------------
		//Adds selected Courses
		//-------------------------------------------------------
		public void actionPerformed (ActionEvent event){
			int start; //start time for class
			Object e = event.getSource();
		    //adds course
			if(addClass == e){
			//Check if did not select section
			if(listSec.getSelectedValue() == null)
				JOptionPane.showMessageDialog(frame, "Must Select a Section.");
			//Check if class is already registered
			else if (checkIfRegistered(list.getSelectedValue()) == true)
				JOptionPane.showMessageDialog(frame, "A Section for this Course is already registered.");
			else if (checkIfRegistered(list.getSelectedValue(), listSec.getSelectedValue()) == false
					&& timeConflict(listSec.getSelectedValue()) == false){
//				System.out.println("Class is being added");
				cal.addClass(listSec.getSelectedValue());
				for (int i = 0; i< y.size(); i++){
					cal.select(x, y.get(i));
					calendar[x][y.get(i)].removeAll();
					calendar[x][y.get(i)].setBackground(Color.lightGray);
					calendar[x][y.get(i)].add(new JLabel(courseText));
					calendar[x][y.get(i)].validate();
				}
			}
			else
				JOptionPane.showMessageDialog(frame, "Course and Section already registered.");
			//displays added courses on GUI Calendar
			
//			displayCalendar();
			}
			if(removeClass == e){
				try{
//					System.out.println(webRegistered.getSelectedIndex());
//				int xCoor = 0;
//				//section start time
//				Section sec = new Section();
				start = cal.getSection(webRegistered.getSelectedIndex()).getSTime();
//				System.out.println("Start: " + start);
				WeeklySchedule meetD = new WeeklySchedule();
				meetD = cal.getSection(webRegistered.getSelectedIndex()).getMeetings();
				//y-coordinates of cells
				y = new ArrayList<Integer>();
				//finds weekday meeting times and adds to y-coordinates
				for (int i=0; i<5; i++){
					if (meetD.day(i))
						y.add(i+1);
				}
//				//finds x-coordinate for calendar cells
				for (int i=800; i<=2000; i+=100)
					if(start == i)
						x = (i-700) /100;
					for (int i = 0; i< y.size(); i++){
						cal.unSelect(x, y.get(i));
						calendar[x][y.get(i)].removeAll();
						calendar[x][y.get(i)].setBackground(defaultColor);
						calendar[x][y.get(i)].validate();
//						System.out.println("Current Coordinates" + cal.retrieveCoor(x, y.get(i)));
					}
				cal.removeClass(webRegistered.getSelectedIndex());
				} catch (IndexOutOfBoundsException x){
				}
			}
			
			if(searchButton == e){
				String input = search.getText();
//				System.out.println(input);
				for (int i = 0; i < classy.getSearchBuffer().size(); i++){
					if(input.equals(classy.getSearchBuffer().get(i).toString())){
//						System.out.println("Yes");
						listmod = new DefaultListModel<Course>();
						listmod.addElement(classy.getSearchBuffer().get(i));
						coursePanel.removeAll();
//						list.removeAll();
						list = new JList<Course>(listmod);
						coursePanel.setPreferredSize(new Dimension((screenSize.width / 5), (screenSize.height /8)));
						coursePanel.setBackground(Color.gray);
						courseLabel = new JLabel("Courses");
						coursePanel.add(courseLabel);
						JScrollPane listScroller = new JScrollPane(list);
						listScroller.setPreferredSize(new Dimension(220, 60));
						listScroller.setAlignmentX(LEFT_ALIGNMENT);
						list.addListSelectionListener(new CourseListener());
						list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						coursePanel.add(listScroller);
						coursePanel.revalidate();
						coursePanel.repaint();
					}
				}
			}
			
			if(clearSearchButton == e){
				clearSearch();
			}
			
			if(logoutButton == e){
				classy.logout();
				System.out.println("You have logged out");
				loginP.setLoginStatus(false);
				displayLogin();
			}
			
			//resets GUI and CalendarModel
			if(startOver == e){
				int reply = JOptionPane.showConfirmDialog(null, "Calendar will Reset. \nAre you sure you want to continue?", "", JOptionPane.YES_NO_OPTION);
		        if (reply == JOptionPane.YES_OPTION) {
		        	classy.clearScehdule();
		            cal.reset();
					resetCalendar();
					search.setText("Search Here");
					clearSearch();
					sectionPanel.setVisible(false);
		        }

//				JOptionPane.showMessageDialog(frame, "The Calendar will Reset. \nAre you sure you want to continue?");
	
			}
		}
	}
	
	private void clearSearch(){
		search.setText("Search Here");
		coursePanel.removeAll();
//		list.removeAll();
		list = new JList<Course>(classy.getArraySearchBuf());
		coursePanel.setPreferredSize(new Dimension((screenSize.width / 5), (screenSize.height /8)));
		coursePanel.setBackground(Color.gray);
		courseLabel = new JLabel("Courses");
		coursePanel.add(courseLabel);
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(220, 60));
		listScroller.setAlignmentX(LEFT_ALIGNMENT);
		list.addListSelectionListener(new CourseListener());
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		coursePanel.add(listScroller);
		coursePanel.revalidate();
		coursePanel.repaint();
	}
	
	//for jcombobox
	private class SemesterListener implements ActionListener{
		public void actionPerformed(ActionEvent e)
		{
			centerLabel.setText(j.getSelectedItem().toString() + " SEMESTER");
			Semester semester = new Semester((SemEnum) j.getSelectedItem());
			classy.setCurrSem(semester);   //------------CLASSY
			classy.clearScehdule();
            cal.reset();
			resetCalendar();
			search.setText("Search Here");
			clearSearch();
			sectionPanel.setVisible(false);
		}
	}
	
	private class SearchListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			//TODO
			String input = search.getText();
//			System.out.println(input);
			for (int i = 0; i < classy.getSearchBuffer().size(); i++){
				if(input.equals(classy.getSearchBuffer().get(i).toString())){
					listmod = new DefaultListModel<Course>();
					listmod.addElement(classy.getSearchBuffer().get(i));
					coursePanel.removeAll();
//					list.removeAll();
					list = new JList<Course>(listmod);
					coursePanel.setPreferredSize(new Dimension((screenSize.width / 5), (screenSize.height /8)));
					coursePanel.setBackground(Color.gray);
					courseLabel = new JLabel("Courses");
					coursePanel.add(courseLabel);
					JScrollPane listScroller = new JScrollPane(list);
					listScroller.setPreferredSize(new Dimension(220, 60));
					listScroller.setAlignmentX(LEFT_ALIGNMENT);
					list.addListSelectionListener(new CourseListener());
					list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					coursePanel.add(listScroller);
					coursePanel.revalidate();
					coursePanel.repaint();
				}
			}
//				searchCourse = new DefaultListModel<Course>();
//				searchCourse.addElement(c);
//				list = new JList<Course>(searchCourse);
//			}
		}
	}
	/*******************************************************
	 *Represents a listener for list of courses 
	 * 
	 ******************************************************/
	private class CourseListener implements ListSelectionListener{
		//-------------------------------------------------------
		// 
		//-------------------------------------------------------
		public void valueChanged (ListSelectionEvent event){
			sectionPanel.setVisible(true);
			secList.clear();
			for (int i=0; i<list.getSelectedValue().getSectionList().size(); i++)
				secList.addElement(list.getSelectedValue().getSection(i));
		}
	}

	private void cellAvailability(){
		for (int i = 0; i< y.size(); i++){
//			System.out.println("Size of y: " + y.size());
			if(iCalendar[x][y.get(i)] == Cell.EMPTY){
//				calendar[x][y.get(i)].add(new JLabel("AVAILABLE"));
				calendar[x][y.get(i)].setBackground(Color.green);
				calendar[x][y.get(i)].validate();
			}
			else{
				calendar[x][y.get(i)].setBackground(Color.red);
//				calendar[x][y.get(i)].add(new JLabel("ALREADY TAKEN"));
				calendar[x][y.get(i)].validate();
			}
		} 
	}
	
	/*******************************************************
	 *Represents a listener for web registered courses
	 * 
	 ******************************************************/
	private class RegisteredListener implements ListSelectionListener{
		//-------------------------------------------------------
		// 
		//-------------------------------------------------------
		public void valueChanged (ListSelectionEvent event){
		}
	}
	
		/******************************************************
		 * Represents a listener for list of sections
		 * 
		 *****************************************************/
		private class SectionListener implements ListSelectionListener{
			int sec1 = 0;
			int sec2 = 0;
		//-------------------------------------------------------
		// Displays available and taken cells on calendar
		//-------------------------------------------------------
		public void valueChanged (ListSelectionEvent event){
			//to allow available cells colors to toggle on and off
			if (secList.isEmpty() == false && listSec.isSelectionEmpty() == false){
				description.setText(listSec.getSelectedValue().getDescription());
				WeeklySchedule meetD = new WeeklySchedule();
				meetD = listSec.getSelectedValue().getMeetings();
				//y-coordinates of cells
				y = new ArrayList<Integer>();
				//finds weekday meeting times and adds to y-coordinates
				for (int i=0; i<5; i++){
					if (meetD.day(i))
						y.add(i+1);
				}
				x = 0;
				//section start time
				int start = listSec.getSelectedValue().getSTime();
				//finds x-coordinate for calendar cells
				for (int i=800; i<=2000; i+=100)
					if(start == i)
						x = (i-700) /100;
				//checks if cell with coordinates is available and changes background color
			  courseText = listSec.getSelectedValue().toString();
			  if(!event.getValueIsAdjusting()){
				if(sec1 == sec2){
					cellAvailability();
//					System.out.println("Count is: "+count);
//					if (count == 2){
						sec1 = listSec.getSelectedValue().getSID();
//						count = 0;
//					}
				}else{
					for (int row = 0; row < rows; row++) 
						for (int col = 0; col < columns; col++) 
							if(calendar[row][col].getBackground() == Color.green){
								calendar[row][col].setBackground(defaultColor);
								calendar[row][col].removeAll();
							}
							else if(calendar[row][col].getBackground() == Color.red){
//								calendar[row][col].removeAll();
								calendar[row][col].setBackground(Color.lightGray);
//								calendar[row][col].add(new JLabel(courseText));
								calendar[row][col].validate();
								}
						
					cellAvailability();
				}
			  }
			}
		}
	}
}
