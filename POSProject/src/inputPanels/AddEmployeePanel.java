package inputPanels;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import navPanels.POSPanel;
import util.ButtonManager;
import util.Manager;

public class AddEmployeePanel extends InputPanel {

	JLabel employeeIDLabel, firstNameLabel, lastNameLabel, jobTitleLabel, dateOfHireLabel, wageAmountLabel;
	JLabel[] labels = {employeeIDLabel, firstNameLabel, lastNameLabel, jobTitleLabel, dateOfHireLabel, wageAmountLabel};

	JTextField employeeIDfield, firstNamefield, lastNamefield, jobTitlefield, dateOfHirefield, wageAmountfield;
	JTextField[] textFields = {employeeIDfield, firstNamefield, lastNamefield, jobTitlefield, dateOfHirefield, wageAmountfield};

	String[] labelNames = {"Employee ID:", "First Name:", "Last Name:", "Job Title:", "Date of Hire:", "Wage Amount:"};

	
	public AddEmployeePanel(int rows, int columns) {
		super(rows, columns);
		for (int i = 0; i < labels.length; i++) {
			labels[i] = new JLabel(labelNames[i] + "   ", SwingConstants.RIGHT);
			labelsPanel.add(labels[i]);
			
			textFields[i] = new JTextField();
			textFields[i].setBorder(BorderFactory.createMatteBorder(10,10,10,30, Manager.getColor("Light Grey")));
			fieldsPanel.add(textFields[i]);
		}
		
		labelsPanel.add(new JLabel()/*place holder"*/);
		fieldsPanel.add(submitButton);
	}

	
	@Override
	public void submit() {
		// TODO: make it so that cannot be submitted without all fields filled in
		String staffID = "\"" + textFields[0].getText() + "\",";
		String firstName = "\"" + textFields[1].getText() + "\",";
		String lastName = "\"" + textFields[2].getText() + "\",";
		String jobTitle = "\"" + textFields[3].getText() + "\",";
		String dateOfHire = "\"" + textFields[4].getText() + "\",";
		String wageAmount = "\"" + textFields[5].getText() + "\"";
		
		String query = "INSERT INTO `staff`(`StaffId`, `FirstName`, `LastName`, `JobTitle`, `DateOfHire`, `WageAmount`) "
				+ "VALUES (" + staffID + firstName + lastName + jobTitle + dateOfHire + wageAmount +  ")";
		
		
		boolean didSucceed = false;
		try {
			sqlAccess.runInsertOrDeleteQuery(query);
			didSucceed = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		if (didSucceed) {
			JOptionPane.showMessageDialog(Manager.getView(),"Addition Successful.");	
		}
		else {
			JOptionPane.showMessageDialog(Manager.getView(),"ERROR: \nSomething went wrong: "
					+ "\nPlease try again or contact your tech support team for help.");	
		}
	}
}
