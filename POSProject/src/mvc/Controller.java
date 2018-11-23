/*
 * 
 * 
 * 		The controller listens for events (buttons pressed) and responds to them accordingly by interacting with GUI objects
 */


package mvc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;

import javax.swing.JButton;
import javax.swing.JPanel;

import navPanels.PanelManager;
import navPanels.SignInPanel;
import util.ButtonManager;
import util.InputManager;
import util.Manager;
import util.PasswordManager;
import util.SQLAccess;
import util.TableManager;

public class Controller implements ActionListener{
	
	SQLAccess sqlAccess = new SQLAccess();
	PasswordManager passwordManager;
	TableManager tableManager;
	SignInPanel signInPanel;

	boolean loggedIn = false;
	
	String currentCategory = "";

 	public Controller() throws NoSuchAlgorithmException {
		passwordManager = new PasswordManager();
		tableManager = new TableManager();

		// set action listeners for all buttons in the hash map
		ButtonManager.setActionListeners(this);
		InputManager.getUsernameField().addActionListener(this);
		InputManager.getPasswordField().addActionListener(this);
	}
	
	
	
 	// setting up the view for which the controller updates as events occur
	POSView view;
	public void setView(POSView view) {
		this.view = view;
		signInPanel = (SignInPanel) view.getSignInPanel();
	}

	
	// ACTION LISTENER ////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void actionPerformed(ActionEvent event) {

		if (event.getSource() instanceof JButton) {
			JButton button = (JButton) event.getSource();
			executeJButtonEvent(button.getText());
		}
		else {
			// enter key was pressed and is signaling the default button
			if (signInPanel.checkCredentials()) {
				view.getMainCardLayout().show(view.getMainCardPanel(), "Content Container");
				loggedIn = true;
				view.getTitleLabel().setText("Main Menu");
				ButtonManager.setSignOutActive(true);
			} 
			else {
				InputManager.getUsernameField().setText("");
				InputManager.getPasswordField().setText("");
			}
		}
	}
	
	

	// a button was pressed
	private void executeJButtonEvent(String buttonText) {
		switch(buttonText) {
		case "Sign Out":
			// go back to the initial sign in state
			view.getMainCardLayout().show(view.getMainCardPanel(), "Sign In");
			loggedIn = false;
			view.getTitleLabel().setText("The Best POS System Ever Made");
			InputManager.getUsernameField().setText("");
			InputManager.getPasswordField().setText("");
			ButtonManager.setSignOutActive(false);
			break;
		case "Sign In":
			// only allow if the user name and password are accepted
			if (signInPanel.checkCredentials()) {
				view.getMainCardLayout().show(view.getMainCardPanel(), "Content Container");
				loggedIn = true;
				view.getTitleLabel().setText("Main Menu");
				ButtonManager.setSignOutActive(true);
			} 
			else {
				InputManager.getUsernameField().setText("");
				InputManager.getPasswordField().setText("");
			}
			break;

			// the following cases set up sub-menus within the view -------------------------------
		case "Expenses":
			currentCategory = "Expenses";
			view.getSubCardLayout().show(view.getSubCardPanel(), "Expenses");
			view.getTitleLabel().setText("Expenses Menu");
			PanelManager.getPanel("Expenses").setSubMenuVisible();
			view.setActiveButton("Expenses");
			PanelManager.getPanel("Expenses").checkButtonPressed("Show Table");
			break;

		case "Products":
			currentCategory = "Products";
			view.getSubCardLayout().show(view.getSubCardPanel(), "Products");
			view.getTitleLabel().setText("Products Menu");
			PanelManager.getPanel("Products").setSubMenuVisible();
			view.setActiveButton("Products");
			PanelManager.getPanel("Products").checkButtonPressed("Show Table");
			break;
			
		case "Ingredients":
			currentCategory = "Ingredients";
			view.getSubCardLayout().show(view.getSubCardPanel(), "Ingredients");
			view.getTitleLabel().setText("Ingredients Menu");
			PanelManager.getPanel("Ingredients").setSubMenuVisible();
			view.setActiveButton("Ingredients");
			PanelManager.getPanel("Ingredients").checkButtonPressed("Show Table");
			break;

		case "Staff":
			currentCategory = "Staff";
			view.getSubCardLayout().show(view.getSubCardPanel(), "Staff");
			view.getTitleLabel().setText("Staff Menu");
			PanelManager.getPanel("Staff").setSubMenuVisible();
			view.setActiveButton("Staff");
			// TODO: this should be set within the panel class and not here
			PanelManager.getPanel("Staff").checkButtonPressed("Show Table");
			break;

		case "Sales":
			currentCategory = "Sales";
			view.getSubCardLayout().show(view.getSubCardPanel(), "Sales");
			view.getTitleLabel().setText("Sales Menu");
			PanelManager.getPanel("Sales").setSubMenuVisible();
			view.setActiveButton("Sales");
			PanelManager.getPanel("Sales").checkButtonPressed("Show Table");
			break;
			
			
			// The following cases send context specific messages to what ever sub menu is active ------------------
		// staff 
		case "Add New Employee":
		case "Remove Employee":
		case "Add Database User":
		case "Remove Database User":
			PanelManager.getPanel("Staff").checkButtonPressed(buttonText);
			break;
			
		// expenses
		case "Add Expense":
		case "Remove Expense":
			PanelManager.getPanel("Expenses").checkButtonPressed(buttonText);
			break;

		// products 
		case "Add Products To Stock":
		case "Remove Products From Stock":
		case "Add New Product":
		case "Remove Existing Product":
			PanelManager.getPanel("Products").checkButtonPressed(buttonText);
			break;
			
		// ingredients
		case "Add Ingredients To Stock":
		case "Remove Ingredients From Stock":
		case "Add New Ingredient":
		case "Remove Existing Ingredient":
			PanelManager.getPanel("Ingredients").checkButtonPressed(buttonText);
			break;
			
		// sales
		case "View Products Sold":
		case "View Transactions":
			PanelManager.getPanel("Sales").checkButtonPressed(buttonText);

			
		// shared (polymorphic) input buttons -------------------------------------------------
			// submit button does different things depending on the context
		case "Submit":
			if (currentCategory.equals("Expenses")) {
				PanelManager.getPanel("Expenses").checkButtonPressed(buttonText);
			}
			else if (currentCategory.equals("Products")) {
				PanelManager.getPanel("Products").checkButtonPressed(buttonText);
			}
			else if (currentCategory.equals("Ingredients")) {
				PanelManager.getPanel("Ingredients").checkButtonPressed(buttonText);
			}
			else if (currentCategory.equals("Staff")) {
				PanelManager.getPanel("Staff").checkButtonPressed(buttonText);
			}
			else if (currentCategory.equals("Sales")) {
				PanelManager.getPanel("Sales").checkButtonPressed(buttonText);
			}
		}
	}

}











