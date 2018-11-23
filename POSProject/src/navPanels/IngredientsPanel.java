package navPanels;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import inputPanels.AddNewIngredientPanel;
import util.ButtonManager;
import util.InputManager;
import util.Manager;
import util.TableManager;

public class IngredientsPanel extends POSPanel{

	JButton addIngredientsToStockButton, removeIngredientsFromStockButton, addNewIngredientButton, removeExistingIngredientButton;

	JLabel ingredientIDLabel, ingredientNameLabel, recipeIDLabel;
	JTextField ingredientIDField, ingredientNameField, recipeIDField;
	JLabel[] ingredientLabels = {ingredientIDLabel, ingredientNameLabel, recipeIDLabel};
	JTextField[] ingredientTextFields = {ingredientIDField, ingredientNameField, recipeIDField};
	String[] ingredientLabelNames = {"Ingredient ID", "Ingredient Name", "Recipe ID"};

	public IngredientsPanel(int frameWidth, int frameHeight) {
		super(frameWidth, frameHeight);

		// adjust according to how many buttons there are in this panel
		subMenuGridLayout.setColumns(5);
		subMenuGridLayout.setRows(1);
	}

	@Override
	public void initSubMenuButtons() {
		addIngredientsToStockButton = ButtonManager.getButton("Add Ingredients To Stock");
		removeIngredientsFromStockButton = ButtonManager.getButton("Remove Ingredients From Stock");
		addNewIngredientButton = ButtonManager.getButton("Add New Ingredient");
		removeExistingIngredientButton = ButtonManager.getButton("Remove Existing Ingredient");

		subMenuButtons.add(addIngredientsToStockButton);
		subMenuButtons.add(removeIngredientsFromStockButton);
		subMenuButtons.add(addNewIngredientButton);
		subMenuButtons.add(removeExistingIngredientButton);

		for (int i = 0; i < subMenuButtons.size(); i++) {
			subMenuPanel.add(subMenuButtons.get(i));
			subMenuButtons.get(i).setBackground(Manager.getColor("Green"));
			subMenuButtons.get(i).setForeground(Color.white);
			subMenuButtons.get(i).setBorder(BorderFactory.createRaisedBevelBorder());
			subMenuButtons.get(i).setPreferredSize(new Dimension(150,100));
		}
	}

	@Override
	public void addToPanelManager() {
		PanelManager.addPanel("Ingredients", this);
	}

	public void checkButtonPressed(String buttonName) {
		clearContents();

		switch(buttonName) {
		case "Show Table": 
			showTable("ingredient_inventory");
			break;
		case "Add Ingredients To Stock": 
			checkIfAddableToStock("ingredient_inventory");
			addIngredientsToStockButton.setBorder(BorderFactory.createRaisedBevelBorder());
			break;
		case "Remove Ingredients From Stock":
			checkIfRemovableFromStock("ingredient_inventory");
			removeIngredientsFromStockButton.setBorder(BorderFactory.createRaisedBevelBorder());
			break;
		case "Add New Ingredient": 
			showAddRowPanel(buttonName);
			break;
		case "Remove Existing Ingredient": 
			boolean isSelected = false;
			for (int i = 0; i < table.getRowCount(); i++) {
				if(table.isRowSelected(i))
					isSelected = true;
			}
			if (!isSelected) {
				JOptionPane.showMessageDialog(Manager.getView(),"ERROR: \nPlease select an ingredient to remove.");
			} else {
				JOptionPane.showMessageDialog(null, deletionWarningPanel);
				deleteRow(String.valueOf(table.getValueAt(table.getSelectedRow(), 0)), "ingredient_inventory");
			}
			showTable("ingredient_inventory");
			removeIngredientsFromStockButton.setBorder(BorderFactory.createRaisedBevelBorder());
			break;

		// input buttons ////////////////////////////////////////////////////////////////
		case "Submit":
			currentInputPanel.submit();
			showTable("ingredient_inventory");
			break;

		}
		toggleBorders(buttonName);
	}

}
