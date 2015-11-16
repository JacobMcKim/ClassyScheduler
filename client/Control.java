
import javax.swing.JFrame;

public class Control {
	//--------------------------------------
	// Creates the main program frame
	//--------------------------------------
	public static void main (String[] args){
		JFrame frame = new JFrame ("Classy Scheduler");
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new CalendarPanel());
		frame.setExtendedState(frame.MAXIMIZED_BOTH);
		frame.pack();
		frame.setVisible(true);
	}
}
