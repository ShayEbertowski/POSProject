package inputPanels;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import util.Manager;

public class AddNewExpensePanel extends InputPanel {

	JLabel costIDLabel, costTypeLabel, dateLabel, amountLabel;
	JLabel[] labels = {costIDLabel, costTypeLabel, dateLabel, amountLabel};

	JTextField costIDField, costTypeField, dateField, amountField;
	JTextField[] textFields = {costIDField, costTypeField, dateField, amountField};

	String[] labelNames = {"Cost ID:", "Type:", "Date:", "Amount:"};

	
	public AddNewExpensePanel(int rows, int columns) {
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
		String costID = "\"" + textFields[0].getText() + "\",";
		String costType = "\"" + textFields[1].getText() + "\",";
		String date = "\"" + textFields[2].getText() + "\",";
		String amount = "\"" + textFields[3].getText() + "\"";
		
		String query = "INSERT INTO `expenses`(`CostId`, `CostType`, `CostDate`, `Amount`) "
				+ "VALUES (" + costID + costType + date + amount +  ")";
		
		
		
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
