package navPanels;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JScrollPane;

import util.ButtonManager;
import util.Manager;
import util.TableManager;

public class SalesPanel extends POSPanel{
	
	JButton viewProductsSoldButton, viewTransactionsButton;
	
	public SalesPanel(int frameWidth, int frameHeight) {
		super(frameWidth, frameHeight);
		
		// adjust according to how many buttons there are in this panel
		subMenuGridLayout.setColumns(3);
		subMenuGridLayout.setRows(1);
	}

	@Override
	public void initSubMenuButtons() {
		viewProductsSoldButton = ButtonManager.getButton("View Products Sold");
		viewTransactionsButton = ButtonManager.getButton("View Transactions");

	
		subMenuButtons.add(viewProductsSoldButton);
		subMenuButtons.add(viewTransactionsButton);

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
		PanelManager.addPanel("Sales", this);
	}
	

	public void switchTable(String request) {
		TableManager tableManager = new TableManager();
		String query = "";
		String context = "";
		if (request.equals("product_sold")) {
			query = "select * from product_sold";
			context = "product_sold";
		}
		else {
			query = "select * from transactions";
			context = "transactions";
		}
			
		Object[][] data = null;
		try {
			data = sqlAccess.getTable(query);
		} catch (Exception e) {
			e.printStackTrace();
		}

		table = tableManager.getTable(context, data);
		scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(tablePanel.getWidth(),tablePanel.getHeight()));
		tablePanel.add(scrollPane);

		contentPanel.add(tablePanel, "table");
		contentCardLayout.show(contentPanel, "table");
	}
		

	public void checkButtonPressed(String buttonName) {
		clearContents();
		
		switch(buttonName) {
		case "Show Table": 
			showTable("transactions");
			break;
		case "View Products Sold":
			switchTable("product_sold");
			break;
		case "View Transactions":
			switchTable("transactions");
			break;
		}
		
		toggleBorders(buttonName);
	}
}