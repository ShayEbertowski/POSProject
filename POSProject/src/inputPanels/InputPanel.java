/*
 * 
 * 
 *  This class is a panel that represents any panel that serves the purpose of allowing the user to enter input
 *  add database user, add new employee, add new expense, add new ingredient, and add new product inherit this class
 *  so I wont comment much on them
 */

package inputPanels;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import util.ButtonManager;
import util.Manager;
import util.SQLAccess;

public class InputPanel extends JPanel{
	
	// an instance of the class that allows access to the database
	SQLAccess sqlAccess = new SQLAccess();
	
	
	// attributes that will be needed in any child class
	JPanel labelsPanel, fieldsPanel;
	JButton submitButton;

	JLabel[] labels;
	JTextField[] textFields;
	String[] labelNames;
	
	GridLayout gridLayout;
	
	// a generic constructor that sets up the attributes that all child classes share
	// note that each child class adds more to the constructor in order to set up their individual attributes
	public InputPanel(int rows, int columns) {
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createEmptyBorder(0, 100, 0, 100));
		
		gridLayout = new GridLayout(rows, columns);

		labelsPanel = new JPanel(gridLayout);
		fieldsPanel = new JPanel(gridLayout);
		labelsPanel.setBackground(Manager.getColor("Light Grey"));
		labelsPanel.setBorder(BorderFactory.createMatteBorder(20, 30, 20, 0, Manager.getColor("Light Grey")));
		fieldsPanel.setBackground(Manager.getColor("Light Grey"));
		fieldsPanel.setBorder(BorderFactory.createMatteBorder(20, 0, 20, 0, Manager.getColor("Light Grey")));
		
		this.setBackground(Manager.getColor("Light Grey"));
		this.add(labelsPanel, BorderLayout.WEST);
		this.add(fieldsPanel, BorderLayout.CENTER);
		
		
		submitButton = ButtonManager.getButton("Submit");
		submitButton.setBorder(BorderFactory.createMatteBorder(10, 500, 10, 30, Manager.getColor("Light Grey")));
	}
	
	public void submit() {
		// empty here, but will do different things depending on the child class that calls it
	}

}
