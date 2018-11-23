package navPanels;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import util.ButtonManager;
import util.Manager;

public class ExpensesPanel extends POSPanel{

	JButton addExpenseButton, removeExpenseButton;

	public ExpensesPanel(int frameWidth, int frameHeight) {
		super(frameWidth, frameHeight);
		// adjust according to how many buttons there are in this panel
		subMenuGridLayout.setColumns(3);
		subMenuGridLayout.setRows(1);
	}

	@Override
	public void initSubMenuButtons() {
		addExpenseButton = ButtonManager.getButton("Add Expense");
		removeExpenseButton = ButtonManager.getButton("Remove Expense");

		subMenuButtons.add(addExpenseButton);
		subMenuButtons.add(removeExpenseButton);

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
		PanelManager.addPanel("Expenses", this);
	}

	public void checkButtonPressed(String buttonName) {
		clearContents();

		switch(buttonName) {
		case "Show Table": 
			showTable("expenses");
			break;
		case "Add Expense":
			showAddRowPanel(buttonName);
			break;
		case "Remove Expense":
			boolean isSelected = false;
			for (int i = 0; i < table.getRowCount(); i++) {
				if(table.isRowSelected(i))
					isSelected = true;
			}
			if (!isSelected) {
				JOptionPane.showMessageDialog(Manager.getView(),"ERROR: \nPlease select an expense to remove.");
			} else {
				JOptionPane.showMessageDialog(null, deletionWarningPanel);
				deleteRow(String.valueOf(table.getValueAt(table.getSelectedRow(), 0)), "expenses");
			}
			showTable("expenses");
			removeExpenseButton.setBorder(BorderFactory.createRaisedBevelBorder());
			break;

		case "Submit":
			currentInputPanel.submit();
			showTable("expenses");
			break;
		}
		
		toggleBorders(buttonName);
	}


}