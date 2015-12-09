
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class control {
	//--------------------------------------
	// Creates the main program frame
	//--------------------------------------
	public static void main (String[] args){
		JFrame frame = new JFrame ("Classy Scheduler");
		frame.setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().add(new CalendarPanel());
		frame.setExtendedState(frame.MAXIMIZED_BOTH);
		frame.pack(); 
		frame.setVisible(true);

	}
}

