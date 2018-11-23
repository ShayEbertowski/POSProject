/*
 * 
 *  This class takes in a 2d array from the calling class and converts it into a table to be displayed
 */

package util;

import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.ResultSetMetaData;

public class TableManager extends JPanel{
	
	ZebraTable table;
	
	public TableManager() {
		super(new GridLayout(1,0));
	}

	
	public ZebraTable getTable(String callingEvent, Object[][]data) {
		String[] columnNames = null;
		
		String[] staffColumnNames = {"Staff ID", "First Name", "Last Name", "Job Title",
				"Date of Hire", "Wage Amount"};
		
		String[] productsColumnNames = {"Product ID", "Product Type ID", "Product Name", "Produced in Store?",
				"Unit Type", "Units in Stock", "Cost of Production", "Sale Price"};
		
		String[] ingredientsColumnNames = {"Ingredient ID", "Ingredient Name","Unit Type", "Units In Stock", "Cost"};
		
		String[] expensesColumnNames = {"Cost ID", "Type", "Date", "Amount"};
		
		String[] transactionsColumnNames = {"TransactionId", "TransactionDate", "Total"};
		
		String[] productsSoldColumnNames = {"Sale ID", "Transaction ID", "Product ID", "Units Sold"};
		
		
		// calling events must match their respective column names in the database
		switch (callingEvent) {
		case "staff": 
			columnNames = staffColumnNames;
			break;
		case "product_inventory": 
			columnNames = productsColumnNames;
			break;
		case "ingredient_inventory": 
			columnNames = ingredientsColumnNames;
			break;
		case "expenses":
			columnNames = expensesColumnNames;
			break;
		case "transactions":
			columnNames = transactionsColumnNames;
			break;
		case "product_sold":
			columnNames = productsSoldColumnNames;
		
		}
		table = new ZebraTable(data, columnNames);
		return table;
	}


}
