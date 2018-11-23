/*
 * 
 * This is the main sub panel - it contains generic attributes and behaviors and all of the other non-input panels inherit them
 * 
 *  Expenses, ingredients, products, sales, and staff panels all inherit this so I wont comment them much
 */

package navPanels;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import inputPanels.AddDatabaseUserPanel;
import inputPanels.AddEmployeePanel;
import inputPanels.AddNewExpensePanel;
import inputPanels.AddNewIngredientPanel;
import inputPanels.AddNewProductPanel;
import inputPanels.InputPanel;
import mvc.Controller;
import mvc.POSView;
import util.InputManager;
import util.Manager;
import util.SQLAccess;
import util.TableManager;
import util.ZebraTable;


public abstract class POSPanel extends JPanel implements PanelMethods{
	private static final long serialVersionUID = 1L;
	SQLAccess sqlAccess = new SQLAccess();
	POSView view = Manager.getView();
	Controller controller = Manager.getController();
	
	// sub panels
	JPanel mainPanel, subMenuPanel, contentPanel, tablePanel;
	
	// panels specific to data input 
	JPanel inputPanel, labelsPanel, fieldsPanel, deletionWarningPanel;
	
	// tracks the current panel so that we know what to do with the submit button
	InputPanel currentInputPanel;
	
	// attributes to warn the user before they delete something
	JLabel deletionWarning;
	JRadioButton yesButton, noButton;
	ButtonGroup buttonGroup;

	// a scrollable panel in case any of the tables exceed the size of the window
	JScrollPane scrollPane;
	
	// setting up the layouts for the panels
	CardLayout mainCardLayout = new CardLayout(), contentCardLayout = new CardLayout();
	GridLayout subMenuGridLayout = new GridLayout();
	
	// a table that alternates white and grey rows
	ZebraTable table;
	
	// an array of buttons that can change size at run time since each sub mneu has different anmount of buttons
	ArrayList<JButton> subMenuButtons;
	
	// the size of the main frame
	int frameWidth, frameHeight;
	
	// constructor
	public POSPanel(int frameWidth, int frameHeight) {
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
		this.setLayout(mainCardLayout);
		setUpPanels();
	}
	
	// sets up the panels, their attributes, and their hierarchy
	void setUpPanels() {
		this.setLayout(mainCardLayout);
		this.setFocusable(true);
		
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBackground(Manager.getColor("Light Grey"));
		
		// init buttons list
		subMenuButtons = new ArrayList<JButton>();
		
		// init panels
		subMenuPanel = new JPanel();
		subMenuPanel.setBackground(Manager.getColor("Light Grey"));
		subMenuPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 25, 0));
		subMenuGridLayout.setHgap(20);
		
		contentPanel = new JPanel(contentCardLayout);
		tablePanel = new JPanel();
		tablePanel.setBackground(Manager.getColor("Light Grey"));
		
		inputPanel = new JPanel();
		inputPanel.setBackground(Manager.getColor("Light Grey"));
		
		//deletion warning
		deletionWarningPanel = new JPanel(new GridLayout(3,1));
		deletionWarning = new JLabel("Are you sure you want to delete?");
		yesButton = new JRadioButton("Delete");
		noButton = new JRadioButton("Cancel");
		noButton.setSelected(true);
		buttonGroup = new ButtonGroup();
		buttonGroup.add(yesButton);
		buttonGroup.add(noButton);
		deletionWarningPanel.add(deletionWarning);
		deletionWarningPanel.add(yesButton);
		deletionWarningPanel.add(noButton);
		
		//add the main panel 
		this.add(mainPanel, "Main Panel");
		
        mainPanel.add(subMenuPanel, BorderLayout.SOUTH);
		mainPanel.add(contentPanel, BorderLayout.CENTER);
		
		// colors
		contentPanel.setBackground(Manager.getColor("Light Grey"));
		
		// table
		table = new ZebraTable();
		
		// add elements to panels
		// note that: table must be added to content panel again in the subclass after it is created,
		// but it also must be added here or it wont be visible at all
		contentPanel.add(tablePanel, "table");
		contentPanel.add(inputPanel, "input");
		contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 75, 20, 75));
		
		// set default visibilities
		this.setVisible(true);
		contentPanel.setVisible(true);
		subMenuPanel.setVisible(false);
		
		initSubMenuButtons();
		addToPanelManager();
	}

	// empty method here, but each child of this class has specific cases for this method
	public void checkButtonPressed(String string) {
	}
	

	// a generic method that displays a table of information that is dependent on the child class that calls it
	// the information in the table comes from the database
	public void showTable(String requestedTable) {
		TableManager tableManager = new TableManager();

		Object[][] data = null;
		try {
			data = sqlAccess.getTable("select * from " + requestedTable);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		table = tableManager.getTable(requestedTable, data);
		scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(tablePanel.getWidth(),tablePanel.getHeight()));
		tablePanel.add(scrollPane);
		
		contentPanel.add(tablePanel, "table");
		contentCardLayout.show(contentPanel, "table");
		contentPanel.setBackground(Manager.getColor("Light Grey"));
	}
	
	// initially the sub mneus are not visible, but we make them visible once a button is pressed
	public void setSubMenuVisible() {
		this.subMenuPanel.setVisible(true);
	}
	
	// shows an input panel to add a new row to the child class that calls the method
	public void showAddRowPanel(String p) {
		InputPanel panel = null;
		
		if (p.equals("Add New Employee")) {
			panel = new AddEmployeePanel(7,1);
		}
		else if(p.equals("Add Database User")) {
			panel = new AddDatabaseUserPanel(4,1);
		}
		else if(p.equals("Add Expense")) {
			panel = new AddNewExpensePanel(9,1);
		}
		else if(p.equals("Add New Ingredient")) {
			panel = new AddNewIngredientPanel(6,1);
		}
		else if(p.equals("Add New Product")) {
			panel = new AddNewProductPanel(9,1);
		}
		
		inputPanel = panel;
		contentPanel.add(inputPanel, "input");
		contentPanel.revalidate();
		contentCardLayout.show(contentPanel, "input");
		currentInputPanel = panel;
	}
	
	
	// deletes a row from the table - the row deleted is the one that is currently highilighted
	public void deleteRow(String id, String targetedTable) {
		int idAsInteger = Integer.parseInt(id);
		String query = "";
		if (targetedTable.equals("staff")) {
			query = "DELETE FROM `" + targetedTable + "` WHERE staffId = " + idAsInteger;
		}
		else if (targetedTable.equals("expenses")) {
			query = "DELETE FROM `" + targetedTable + "` WHERE costId = " + idAsInteger;
		}
		else if (targetedTable.equals("ingredient_inventory")) {
			query = "DELETE FROM `" + targetedTable + "` WHERE IngredientId = " + idAsInteger;
		}
		else if (targetedTable.equals("product_inventory")) {
			query = "DELETE FROM `" + targetedTable + "` WHERE ProductId = " + idAsInteger;
		}
		
		boolean didSucceed = false;
		try {
			sqlAccess.runInsertOrDeleteQuery(query);
			didSucceed = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (didSucceed) {
			JOptionPane.showMessageDialog(Manager.getView(),"Deletion Successful.");	
		}
		else {
			JOptionPane.showMessageDialog(Manager.getView(),"ERROR: \nSomething went wrong: "
					+ "\nPlease try again or contact your tech support team for help.");	
		}
	}

	// either adds or substracts to product or inventory stock based on the parameters passed in 
	public void updateStock(String id, String targetedTable, char operator) {
		int idAsInteger = Integer.parseInt(id);
		String inputAmount = InputManager.getAmountField().getText();
		
		String currentAmount = "0";
		String selectedID = "";
		if (targetedTable.equals("staff")) {
			selectedID = "StaffId";
		}
		else if (targetedTable.equals("expenses")) {
			selectedID = "CostId";
		}
		else if (targetedTable.equals("ingredient_inventory")) {
			selectedID = "IngredientId";
		}
		else if (targetedTable.equals("product_inventory")) {
			selectedID = "ProductId";
		}
		try {
			currentAmount = sqlAccess.runFetchQuery("SELECT UnitsInStock FROM " + targetedTable + " WHERE " + selectedID +  
					" = " + idAsInteger);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		String amountToAdjustBy = "";
		if (operator == '+') {
			amountToAdjustBy = "\"" + (Integer.valueOf(currentAmount) + Integer.valueOf(inputAmount)) + "\"";
		}
		else {
			amountToAdjustBy = "\"" + (Integer.valueOf(currentAmount) - Integer.valueOf(inputAmount)) + "\"";
		}
		
		String query = "";
		if (targetedTable.equals("staff")) {
			query = "UPDATE " + targetedTable + " SET UnitsInStock = " + amountToAdjustBy + " WHERE StaffId = " + idAsInteger;
		}
		else if (targetedTable.equals("expenses")) {
			query = "UPDATE " + targetedTable + " SET UnitsInStock = " + amountToAdjustBy + " WHERE CostId = " + idAsInteger;
		}
		else if (targetedTable.equals("ingredient_inventory")) {
			query = "UPDATE " + targetedTable + " SET UnitsInStock = " + amountToAdjustBy + " WHERE IngredientId = " + idAsInteger;
		}
		else if (targetedTable.equals("product_inventory")) {
			query = "UPDATE " + targetedTable + " SET UnitsInStock = " + amountToAdjustBy + " WHERE ProductId = " + idAsInteger;
		}
		
		boolean didSucceed = false;
		try {
			sqlAccess.runUpdateQuery(query);
			didSucceed = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (didSucceed) {
			JOptionPane.showMessageDialog(Manager.getView(),"Operation Successful.");	
		}
		else {
			JOptionPane.showMessageDialog(Manager.getView(),"ERROR: \nSomething went wrong: "
					+ "\nPlease try again or contact your tech support team for help.");	
		}
	}
	
	// toggling sub menu buttons so that it is clear which one is active and which ones are idle
	public void toggleBorders(String buttonName) {
		JButton selectedButton = null;
		for (JButton b : subMenuButtons) {
			if (b.getText().equals(buttonName)){
				selectedButton = b;
				selectedButton.setBorder(BorderFactory.createLoweredBevelBorder());
			}
		}
		// TODO: without the following null check there is an error - might be a better way to do this...
		if (selectedButton != null) {
			for(JButton b : subMenuButtons) {
				if (!b.getText().equals(selectedButton.getText())) {
					b.setBorder(BorderFactory.createRaisedBevelBorder());
				}
			}
		}
	}
	
	// checking if a row is selected within the table, and if so, calling the udpate stock method
	public void checkIfAddableToStock(String callingClass) {
		boolean isSelected = false;
		for (int i = 0; i < table.getRowCount(); i++) {
			if(table.isRowSelected(i))
				isSelected = true;
		}
		if (!isSelected) {
			JOptionPane.showMessageDialog(Manager.getView(),"ERROR: \nPlease make a selection to add to stock.");
		} else {
			JOptionPane.showMessageDialog(null, InputManager.getAmountField());
			if (callingClass.equals("product_inventory")) {
				updateStock(String.valueOf(table.getValueAt(table.getSelectedRow(), 0)), "product_inventory", '+');
				showTable("product_inventory");
			}
			else {
				updateStock(String.valueOf(table.getValueAt(table.getSelectedRow(), 0)), "ingredient_inventory", '+');
				showTable("ingredient_inventory");
			}		
		}
	}
	
	// checking if a row is selected within the table, and if so, calling the update stock method
	// I just realized that this and the above method could be combined but I dont feel like doing that right now
	public void checkIfRemovableFromStock(String callingClass) {
		boolean isSelected = false;
		for (int i = 0; i < table.getRowCount(); i++) {
			if(table.isRowSelected(i))
				isSelected = true;
		}
		if (!isSelected) {
			JOptionPane.showMessageDialog(Manager.getView(),"ERROR: \nPlease make a selection to remove from stock.");
		} else {
			JOptionPane.showMessageDialog(null, InputManager.getAmountField());
			if (callingClass.equals("product_inventory")) {
				updateStock(String.valueOf(table.getValueAt(table.getSelectedRow(), 0)), "product_inventory", '-');
				showTable("product_inventory");
			}
			else {
				updateStock(String.valueOf(table.getValueAt(table.getSelectedRow(), 0)), "ingredient_inventory", '-');
				showTable("ingredient_inventory");
			}
		}
	}
	
	
	// clears the contents so that the next GUI item can be shown
	public void clearContents() {
		if (labelsPanel != null) {
			labelsPanel.removeAll();
			labelsPanel.revalidate();
		}
		if (fieldsPanel != null) {
			fieldsPanel.removeAll();
			fieldsPanel.revalidate();
		}
		tablePanel.removeAll();
		tablePanel.revalidate();
		inputPanel.removeAll();
		inputPanel.revalidate();
	}
}


















