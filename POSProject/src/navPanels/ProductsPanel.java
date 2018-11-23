package navPanels;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import inputPanels.AddNewProductPanel;
import util.ButtonManager;
import util.InputManager;
import util.Manager;
import util.TableManager;

public class ProductsPanel extends POSPanel{

	JButton addProductsToStockButton, removeProductsFromStockButton, addNewProductButton, removeExistingProductButton;

	JLabel productIDLabel, productTypeIDLabel, productNameLabel, madeInStoreLabel, unitTypeLabel, unitsInStockLabel, productionCostLabel, salePriceLabel;
	JTextField productIDfield, productTypeIDfield, productNamefield, madeInStorefield, unitTypefield, unitsInStockfield, productionCostfield, salePriceField;
	JLabel[] productLabels = {productIDLabel, productTypeIDLabel, productNameLabel, madeInStoreLabel, unitTypeLabel, unitsInStockLabel, productionCostLabel, salePriceLabel};
	JTextField[] productTextFields = {productIDfield, productTypeIDfield, productNamefield, madeInStorefield, unitTypefield, unitsInStockfield, productionCostfield, salePriceField};
	String[] productLabelNames = {"Product ID", "Product Type ID", "Product Name", "Made in Store?", "Unit Type", "Units in Stock", "Cost of Production", "Sales Price"};

	public ProductsPanel(int frameWidth, int frameHeight) {
		super(frameWidth, frameHeight);

		// adjust according to how many buttons there are in this panel
		subMenuGridLayout.setColumns(5);
		subMenuGridLayout.setRows(1);
	}

	@Override
	public void initSubMenuButtons() {
		addProductsToStockButton = ButtonManager.getButton("Add Products To Stock");
		removeProductsFromStockButton = ButtonManager.getButton("Remove Products From Stock");
		addNewProductButton = ButtonManager.getButton("Add New Product");
		removeExistingProductButton = ButtonManager.getButton("Remove Existing Product");

		subMenuButtons.add(addProductsToStockButton);
		subMenuButtons.add(removeProductsFromStockButton);
		subMenuButtons.add(addNewProductButton);
		subMenuButtons.add(removeExistingProductButton);

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
		PanelManager.addPanel("Products", this);
	}


	public void checkButtonPressed(String buttonName) {
		clearContents();

		switch(buttonName) {
		case "Show Table": 
			showTable("product_inventory");
			break;
			
		case "Add Products To Stock": 
			checkIfAddableToStock("product_inventory");
			addProductsToStockButton.setBorder(BorderFactory.createRaisedBevelBorder());
			break;
			
		case "Remove Products From Stock": 
			//TODO: for now just deleting regardless of what the radio button is, change this!
			checkIfRemovableFromStock("ingredient_inventory");
			removeProductsFromStockButton.setBorder(BorderFactory.createRaisedBevelBorder());
			break;
			
		case "Add New Product": 
			showAddRowPanel("Add New Product");
			break;
		case "Remove Existing Product": 
			boolean isSelected = false;
			for (int i = 0; i < table.getRowCount(); i++) {
				if(table.isRowSelected(i))
					isSelected = true;
			}
			if (!isSelected) {
				JOptionPane.showMessageDialog(Manager.getView(),"ERROR: \nPlease select a product to remove.");
			} else {
				JOptionPane.showMessageDialog(null, deletionWarningPanel);
				deleteRow(String.valueOf(table.getValueAt(table.getSelectedRow(), 0)), "product_inventory");
			}
			showTable("product_inventory");
			removeProductsFromStockButton.setBorder(BorderFactory.createRaisedBevelBorder());
			
			break;
			
		// input buttons ////////////////////////////////////////////////////////////////
		case "Submit":
			currentInputPanel.submit();
			showTable("product_inventory");
			break;
		}

		toggleBorders(buttonName);

	}

}
