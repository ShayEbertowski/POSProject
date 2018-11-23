package inputPanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import expections.CannotPerformOperationException;
import navPanels.POSPanel;
import util.ButtonManager;
import util.Manager;
import util.PasswordManager;

public class AddDatabaseUserPanel extends InputPanel {

	JLabel credentialsIDLabel, usernameLabel, hashLabel;
	JLabel[] labels = {credentialsIDLabel, usernameLabel, hashLabel};

	JTextField credentialsIDField, usernameField, hashField;
	JTextField[] textFields = {credentialsIDField, usernameField, hashField};

	String[] labelNames = {"Credentials ID:", "Username:", "Password:"};

	
	public AddDatabaseUserPanel(int rows, int columns) {
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
		String credentialsID = "\"" + textFields[0].getText() + "\",";
		String username = "\"" + textFields[1].getText() + "\",";
		String password = textFields[2].getText();
		
		String temp = "";
		try {
			temp = PasswordManager.createHash(password);
		} catch (CannotPerformOperationException e1) {
			e1.printStackTrace();
		}
		
		String hash = "\"" + temp + "\"";
		
		String query = "INSERT INTO `Credentials`(`credentialsId`, `Username`, `Hash`) "
				+ "VALUES (" + credentialsID + username + hash  +  ")";
		
		
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
