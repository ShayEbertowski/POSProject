package navPanels;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import inputPanels.AddDatabaseUserPanel;
import inputPanels.AddEmployeePanel;
import inputPanels.InputPanel;
import util.ButtonManager;
import util.Manager;
import util.TableManager;

public class StaffPanel extends POSPanel{
	
	JButton addNewEmployeeButton, removeEmployeeButton, addDatabaseUserButton;
	
	public StaffPanel(int frameWidth, int frameHeight) {
		super(frameWidth, frameHeight);
		// adjust according to how many buttons there are in this panel
		subMenuGridLayout.setColumns(4);
		subMenuGridLayout.setRows(1);
	}


	@Override
	public void initSubMenuButtons() {
		addNewEmployeeButton = ButtonManager.getButton("Add New Employee");
		removeEmployeeButton = ButtonManager.getButton("Remove Employee");
		addDatabaseUserButton = ButtonManager.getButton("Add Database User");
	
		subMenuButtons.add(addNewEmployeeButton);
		subMenuButtons.add(removeEmployeeButton);
		subMenuButtons.add(addDatabaseUserButton);

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
		PanelManager.addPanel("Staff", this);
	}


	public void checkButtonPressed(String buttonName) {
		clearContents();
		
		switch(buttonName) {
		// sub-menu buttons ////////////////////////////////////////////////////////////////
		case "Show Table": 
			showTable("staff");
			break;
		case "Add New Employee": 
			showAddRowPanel(buttonName);
			break;
		case "Remove Employee":
			//TODO: for now just deleting regardless of what the radio button is, change this!
			boolean isSelected = false;
			for (int i = 0; i < table.getRowCount(); i++) {
				if(table.isRowSelected(i))
					isSelected = true;
			}
			if (!isSelected) {
				JOptionPane.showMessageDialog(Manager.getView(),"ERROR: \nPlease select an employee to remove.");
			} else {
				JOptionPane.showMessageDialog(null, deletionWarningPanel);
				deleteRow(String.valueOf(table.getValueAt(table.getSelectedRow(), 0)), "staff");
			}
			showTable("staff");
			removeEmployeeButton.setBorder(BorderFactory.createRaisedBevelBorder());
			
			break;
		case "Add Database User":
			showAddRowPanel(buttonName);
			break;
			
		case "Remove Database User":
			//do stuff
			break;
			
			
		// input buttons ////////////////////////////////////////////////////////////////
		case "Submit":
			currentInputPanel.submit();
			showTable("staff");
			break;
		}
		
		toggleBorders(buttonName);
	}
	
	
	
}
