package inputPanels;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import util.Manager;

public class AddNewProductPanel extends InputPanel {

	JLabel productIDLabel, productTypeIDLabel, productNameLabel, producedInStoreLabel, unitTypeLabel, unitsInStockLabel, costOfProductionLabel, salePriceLabel;
	JLabel[] labels = {productIDLabel, productTypeIDLabel, productNameLabel, producedInStoreLabel, unitTypeLabel, unitsInStockLabel, costOfProductionLabel, salePriceLabel};

	JTextField productIDField, productTypeIDField, productNameField, producedInStoreField, unitTypeField, unitsInStockField, costOfProductionField, salePriceField;
	JTextField[] textFields = {productIDField, productTypeIDField, productNameField, producedInStoreField, unitTypeField, unitsInStockField, costOfProductionField, salePriceField};

	String[] labelNames = {"Product ID:", "Product Type ID:", "Product Name:", "Produced in Store?:",
							"Unit Type:", "Units in Stock:", "Cost of Production:", "Sale Price:"};

	
	public AddNewProductPanel(int rows, int columns) {
		super(rows, columns);
		this.setPreferredSize(new Dimension(800, 800));
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
		String productID = "\"" + textFields[0].getText() + "\",";
		String productTypeIDName = "\"" + textFields[1].getText() + "\",";
		String productName = "\"" + textFields[2].getText() + "\",";
		String producedInStore = "\"" + textFields[3].getText() + "\",";
		String unitType = "\"" + textFields[4].getText() + "\",";
		String unitsInStock = "\"" + textFields[5].getText() + "\",";
		String costOfProduction = "\"" + textFields[6].getText() + "\",";
		String salePrice = "\"" + textFields[7].getText() + "\"";
		
		String query = "INSERT INTO `product_inventory`(`ProductId`, `ProductTypeId`, `ProductName`, `MadeInStore`, `UnitType`, `UnitsInStock`, `ProductionCost`, `SalePrice`) "
				+ "VALUES (" + productID + productTypeIDName + productName + producedInStore + unitType + unitsInStock + costOfProduction + salePrice +  ")";
		
		
		
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
