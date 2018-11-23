/*
 *  this class just creates all of the buttons that are used throughout the application and stores them in
 *  a hash map for easy access from other classes
 */

package util;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JButton;

import mvc.Controller;

public class ButtonManager {

	// title bar buttons
	static JButton signOutButton;

	// menu
	static JButton expensesButton, productButtons, ingredientsButton, staffButton, salesButton;

	// sign in panel
	static JButton signInButton;

	// expenses panel
	static JButton addExpenseButton, removeExpenseButton;
	
	// products panel
	static JButton addProductsToStockButton, removeProductsFromStockButton, addNewProductButton, removeExistingProductButton;
	
	// ingredients panel
	static JButton addIngredientsToStockButton, removeIngredientsFromStockButton, addNewIngredientButton, removeExistingIngredientButton;

	// staff panel
	static JButton viewStaffButton, addNewEmployeeButton, removeEmployeeButton, addDatabaseUserButton, removeDatabaseUserButton;

	// sales panel
	static JButton viewProductsSoldButton, viewTransactionsButton;

	// generic buttons
	static JButton submitButton;


	public ButtonManager() {
		// menu buttons
		expensesButton = new JButton("Expenses");
		productButtons = new JButton("Products");
		ingredientsButton = new JButton("Ingredients");
		staffButton = new JButton("Staff");
		salesButton = new JButton("Sales");

		//title bar buttons
		signOutButton = new JButton("Sign Out");

		// sign in buttons
		signInButton = new JButton("Sign In");
		
		// input
		submitButton = new JButton("Submit");

		// expenses buttons
		addExpenseButton = new JButton("Add Expense");
		removeExpenseButton = new JButton("Remove Expense");

		// product buttons
		addProductsToStockButton = new JButton("Add Products To Stock");
		removeProductsFromStockButton = new JButton("<html><center>"+"Remove Products"+"<br>"+"From Stock"+"</center></html>");
		addNewProductButton = new JButton("Add New Product");
		removeExistingProductButton = new JButton("Remove Existing Product");
		
		// ingredients buttons
		addIngredientsToStockButton = new JButton("Add Ingredients To Stock");
		removeIngredientsFromStockButton = new JButton("<html><center>"+"Remove Ingredients"+"<br>"+"From Stock"+"</center></html>");
		removeExistingIngredientButton = new JButton("<html><center>"+"Remove Existing"+"<br>"+"Ingredients"+"</center></html>");
		addNewIngredientButton = new JButton("Add New Ingredient");

		// staff buttons
		viewStaffButton = new JButton("View Staff");
		addNewEmployeeButton = new JButton("Add New Employee");
		removeEmployeeButton = new JButton("Remove Employee");
		addDatabaseUserButton = new JButton("Add Database User");
		removeDatabaseUserButton = new JButton("Remove Database User");


		// sales buttons
		viewProductsSoldButton = new JButton("View Products Sold");
		viewTransactionsButton = new JButton("View Transactions");
		
		// maps
		setUpButtonMap();
	}

	static HashMap<String, JButton> map = new HashMap<String, JButton>();

	private void setUpButtonMap(){
		// title bar
		addButton("Sign Out", signOutButton);

		// signin panel
		addButton("Sign In", signInButton);

		// menu panel
		addButton("Expenses", expensesButton);
		addButton("Products", productButtons);
		addButton("Ingredients", ingredientsButton);
		addButton("Staff", staffButton);
		addButton("Sales", salesButton);
		
		// input
		addButton("Submit", submitButton);

		// expenses buttons
		addButton("Add Expense", addExpenseButton);
		addButton("Remove Expense", removeExpenseButton);

		// product buttons
		addButton("Add Products To Stock", addProductsToStockButton);
		addButton("Remove Products From Stock", removeProductsFromStockButton);
		addButton("Add New Product", addNewProductButton);
		addButton("Remove Existing Product", removeExistingProductButton);
		
		// ingredient buttons
		addButton("Add Ingredients To Stock", addIngredientsToStockButton);
		addButton("Remove Ingredients From Stock", removeIngredientsFromStockButton);
		addButton("Add New Ingredient", addNewIngredientButton);
		addButton("Remove Existing Ingredient", removeExistingIngredientButton);

		// staff panel
		addButton("View Staff", viewStaffButton);
		addButton("Add New Employee", addNewEmployeeButton);
		addButton("Remove Employee", removeEmployeeButton);
		addButton("Add Database User", addDatabaseUserButton);
		addButton("Remove Database User", removeDatabaseUserButton);
		
		// sales panel
		addButton("View Products Sold", viewProductsSoldButton);
		addButton("View Transactions", viewTransactionsButton);
	}


	// sets the controller as the button listener for all buttons
	public static void setActionListeners(Controller controller) {
		Iterator entries = map.entrySet().iterator();
		while (entries.hasNext()) {
			Map.Entry entry = (Map.Entry) entries.next();
			String key = (String)entry.getKey();
			JButton value = (JButton)entry.getValue();
			//System.out.println("Key = " + key + ", Value = " + value);
			value.addActionListener(controller);
		}
	}

	// allows the controller to get whatever button they need by passing in the button that was pressed
	public static String getButtonName(JButton button) {
		Iterator entries = map.entrySet().iterator();
		String result = "";
		while (entries.hasNext()) {
			Map.Entry entry = (Map.Entry) entries.next();
			String key = (String)entry.getKey();
			JButton value = (JButton)entry.getValue();
			if (value == button) {
				result = key;
			}
			//System.out.println("Key = " + key + ", Value = " + value);
		}
		return result;
	}

	// adds a button to the hash map
	private void addButton(String name, JButton button) {
		map.put(name, button);
	}

	// allows any class to get a button based on its name
	public static JButton getButton(String name) {
		return map.get(name);
	}

	// toggles so that the sign out button is only shown when a user is signed in
	public static void setSignOutActive(boolean value) {
		signOutButton.setVisible(value);
		signOutButton.setEnabled(value);
	}




}
