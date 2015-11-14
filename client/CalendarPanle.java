package packageCal;
import java.awt.*;

import packageCal.Course;

import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import packageCal.Cell;

public class CalendarPanel extends JPanel{

	
//	private String[] courses = {"CIS 353", "CIS 343", "CIS 350", "MTH 325", "CIS 337", "CIS 290"};


	private int count;
	private JButton addClass, startOver;
	private JLabel leftLabel, centerLabel, rightLabel, courseLabel, sec1Label, sec2Label, searchLabel, registered; //registered-web registered
	private JPanel leftPanel, centerPanel, rightPanel, buttonPanel, coursePanel, searchPanel, secOnePanel, secTwoPanel, registeredList;

	private JPanel[][] calendar;
	private Cell[][] iCalendar;
	private int rows;
	private int columns;
	private CalendarModel cal;
	private JPanel calendarPanel;
	private TitledBorder tb;
	//label for calendar label
	JLabel label;
	//for list.getSelectedValue()
	String cour;
	JLabel registeredLabel;
	String[] regClass;
	private JList<String> webRegistered;
//	JTextField textBox;
//	String result = "";
	
	//course objects for testing, will have once jake's stuff communicates with it
	private Course course1 = new Course(5, 350, "CIS",
			"This class will discuss ways in which developers make projects");
	private Course course2 = new Course(5, 353, "Database Management",
			"This class will discuss ways in which databases are managed");
	private Section section1 = new Section(01, 0, 50, "MWF");
	private Section section2 = new Section(02, 60, 110, "MWF");
//	private String[] courses = {(c1.getCourseTitle() + " " + c1.getCourseID()), (c2.getCourseTitle() + " " + c2.getCourseID()) };
	private Course[] courses = {course1, course2};
//	private JList<String> list = new JList (courses);
	private JList<Course> list = new JList<Course>(courses);
//	private String[] section1 = {"MWF 1:00-1:50"};
	private Section[] sections = {section1, section2};
//	private JList listSec1 = new JList (section1);
	private JList<Section> listSec1;
//	private String[] section2 = {"TR 2:00-3:50"};
//	private JList listSec2 = new JList (section2);
	private JList<Section> listSec2 = new JList<Section>();
	
	
	
	//------------------------------------------
	// Constructor: Sets up the GUI
	//------------------------------------------
	public CalendarPanel(){
		//begintesting
		course1.addSection(section1);
		course1.addSection(section2);
		System.out.println("Number of Questions in course1: " + course1.getSectionList().size());
		//end testing
		rows = 15;
		columns = 8;
		cal = new CalendarModel();
		//sets up overall control panel
		setLayout (new BorderLayout());
		setBackground(Color.gray);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		count = 0;
		
		//LEFT PANEL
		leftLabel = new JLabel ("Courses");
		leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		leftPanel.setPreferredSize(new Dimension((screenSize.width / 5), screenSize.height));
		leftPanel.setBackground(Color.gray);
		leftPanel.add(leftLabel);
		leftPanel.add(Box.createVerticalGlue());
		
		//LEFT PANEL-->Search Panel
		searchPanel = new JPanel();
		searchPanel.setPreferredSize(new Dimension((screenSize.width / 5), (screenSize.height /8)));
		searchPanel.setBackground(Color.gray);
		searchLabel = new JLabel("Search Box goes here");
		searchPanel.add(searchLabel);
		leftPanel.add(searchPanel);

		//LEFT PANEL-->Course Panel
		coursePanel = new JPanel();
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
		leftPanel.add(coursePanel);				

		//LEFT PANEL-->Section1 Panel
		secOnePanel = new JPanel();
		secOnePanel.setPreferredSize(new Dimension((screenSize.width / 5), (screenSize.height /8)));
		secOnePanel.setBackground(Color.gray);
		sec1Label = new JLabel("Section 1");
		secOnePanel.add(sec1Label);
		JScrollPane listScroller1 = new JScrollPane(listSec1);
		listScroller1.setPreferredSize(new Dimension(220, 40));
		listScroller1.setAlignmentX(LEFT_ALIGNMENT);
//		listSec1.addListSelectionListener(new SectionListener());
//		listSec1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		secOnePanel.add(listScroller1);
		leftPanel.add(secOnePanel);	
		secOnePanel.setVisible(false);

		//LEFT PANEL-->Section2 Panel
		secTwoPanel = new JPanel();
		secTwoPanel.setPreferredSize(new Dimension((screenSize.width / 5), (screenSize.height /8)));
		secTwoPanel.setBackground(Color.gray);
		sec2Label = new JLabel("Section 2");
		secTwoPanel.add(sec2Label);
		JScrollPane listScroller2 = new JScrollPane(listSec2);
		listScroller2.setPreferredSize(new Dimension(220, 40));
		listScroller2.setAlignmentX(LEFT_ALIGNMENT);
		secTwoPanel.add(listScroller2);
		leftPanel.add(secTwoPanel);		
		secTwoPanel.setVisible(false);
		
		//LEFT PANEL-->Web Registered Panel
		registeredList = new JPanel();
		registeredList.setPreferredSize(new Dimension((screenSize.width / 5), (screenSize.height /4)));
		registeredList.setBackground(Color.blue);
		registeredLabel = new JLabel("Web Registered");
		registeredList.add(registeredLabel);
		JScrollPane listScroller3 = new JScrollPane(webRegistered);
		listScroller3.setPreferredSize(new Dimension(220, 60));
		listScroller3.setAlignmentX(LEFT_ALIGNMENT);
		registeredList.add(listScroller3);
		leftPanel.add(registeredList);	
		
		
		//LEFT PANEL-->Button Panel
		//------creating buttons for left panel
		addClass = new JButton ("Add Class");
		addClass.addActionListener(new ButtonListener());
		startOver = new JButton("Start Over");
		startOver.addActionListener(new ButtonListener());
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.setPreferredSize(new Dimension((screenSize.width / 5), (screenSize.height /8)));
		buttonPanel.setBackground(Color.gray);
		buttonPanel.add(Box.createRigidArea(new Dimension(5,0)));
		buttonPanel.add(addClass);
		buttonPanel.add(Box.createRigidArea(new Dimension(5,0)));
		buttonPanel.add(startOver);
		leftPanel.add(buttonPanel);
		
		//CENTER PANEL
		centerLabel = new JLabel("WINTER SEMESTER");
		centerPanel = new JPanel();
		centerPanel.setPreferredSize(new Dimension((screenSize.width / 2), screenSize.height));
		centerPanel.setBackground(Color.gray);
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		centerPanel.add(centerLabel);
		
		//CENTER PANEL-->Calendar Panel
		calendarPanel = new JPanel();
		calendarPanel.setLayout(new GridLayout(rows,columns));
		calendar = new JPanel[rows][columns];
//		JLabel calendarPanelLabel = new JLabel("Empty");
		String days[] = {"Monday", "Tuesday","Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
		String time[] = {"8am", "9am", "10am","11am","12pm","1pm","2pm","3pm","4pm","5pm","6pm","7pm","8pm","9pm"}; 
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
				//set labels for calendar panels ****COULD DO FOR Icalendar
//				if(row >0 && col >0){
//					label = new JLabel("empty");
//					calendar[row][col].add(label);
//				}
				
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
		rightPanel.add(rightLabel);		
 
//		setPreferredSize(new Dimension(200,80));
		setPreferredSize(new Dimension(screenSize.width, screenSize.height));
		setBackground(Color.gray);
		
		//CONTROL PANEL Layout
		add(leftPanel, BorderLayout.WEST);
		add(centerPanel, BorderLayout.CENTER);
		add(rightPanel, BorderLayout.EAST);
	}
	
	//----------------------------------------------
	// Checks if calendar panels are empty
	//----------------------------------------------
	private void displayCalendar(){
		iCalendar = cal.getCalendar();
		for (int row = 0; row < this.rows; row++)
			for (int col = 0; col < this.columns; col++) {
				if (iCalendar[row][col] == Cell.EMPTY){ 	
					calendar[row][col].setBorder(BorderFactory.createLoweredBevelBorder());
				}
				if (iCalendar[row][col] == Cell.TAKEN){
					calendar[row][col].setBorder(BorderFactory.createRaisedBevelBorder());
//					calendar[row][col].add(new JLabel("course info"));
				}
			}
	}
	

	//------------------------------------------------------
	// Represents a listener for button push (action) events.
	//-------------------------------------------------------
	private class ButtonListener implements ActionListener{
		//-------------------------------------------------------
		//Updates the counter and label when the button is pushed
		//-------------------------------------------------------
		public void actionPerformed (ActionEvent event){
			Object e = event.getSource();
			if(addClass == e){
			cal.addClass(list.getSelectedValue());
			System.out.println(cal.registeredClassSize());
			updateRegistered(list.getSelectedValue());
			}
			
			if(startOver == e){
				
//				label.setText("Pushes: " + count);
			}
		}
	}
	
	//------------------------------------------------------
		// Represents a listener for list of courses
		//-------------------------------------------------------
		private class CourseListener implements ListSelectionListener{
			//-------------------------------------------------------
			//Updates the counter and label when the button is pushed
			//-------------------------------------------------------
			public void valueChanged (ListSelectionEvent event){
//				//checks how many sections in course
//				checkSection(list);
//					createSectionPanel();
				
//				sets number of sections to visible
//				cour = list.getSelectedValue();
				//save course
//				cal.(list.getSelectedValue().toString(courses[0]));
				//checks how many sections
				for(int i=0; i<list.getSelectedValue().getSectionList().size(); i++){
					if(list.getSelectedValue().getSectionList().size() == 1){
//						((ListSelectionModel) list.getSelectedValue().getSectionList().get(0)).addListSelectionListener(new SectionListener());
						secOnePanel.setVisible(true);
					}
					if(list.getSelectedValue().getSectionList().size() == 2){
//						listSec1 = new JList<Section>(course1.getSectionList().get(0));
						secOnePanel.setVisible(true);
//						((ListSelectionModel) list.getSelectedValue().getSectionList().get(1)).addListSelectionListener(new SectionListener());
						secTwoPanel.setVisible(true);
					}
				}
			}
		}
		
		//------------------------------------------------------
		// Represents a listener for list of courses
		//-------------------------------------------------------
		private class SectionListener implements ListSelectionListener{
			//-------------------------------------------------------
			//Updates the counter and label when the button is pushed
			//-------------------------------------------------------
			public void valueChanged (ListSelectionEvent event){
				
				
//				if cell is available

				//test for displaying information on calendar panel
				calendar[6][1].setBackground(Color.green);
				calendar[6][3].setBackground(Color.green);
				calendar[6][5].setBackground(Color.green);
//				calendar[6][1].add(new JLabel(tempList.getSelectedValue()));
				calendar[6][3].add(new JLabel(cour));
				calendar[6][5].add(new JLabel(cour));
					
				
			}
			
			
		}
		

	
	private void updateRegistered(Course c){
		regClass = new String[10]; //limit of courses
		for(int i=0; i<cal.registeredClassSize(); i++)
			regClass[i] = (cal.getRegisteredCourse().get(i).getTitle() + cal.getRegisteredCourse().get(i).getCID());
		
		 webRegistered = new JList<String>(regClass);

	}
	
	boolean setSectionVisible(int n){
	if(n == 1)
		return true;
	else if(n == 2)
		return true;
	return false;
	}
}
