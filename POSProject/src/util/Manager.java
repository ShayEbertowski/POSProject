package util;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.text.View;

import main.CandyShop;
import mvc.Controller;
import mvc.POSView;
import navPanels.POSPanel;

public class Manager {

	static POSView view;
	static Controller controller;
	static POSPanel expensesPanel, inventoryPanel, laborAndWagesPanel, orderingPanel, revenuePanel, staffPanel;

	public static Color red = Color.decode("#6A1B9A"); // violet
	public static Color green = Color.decode("#388E3C"); // green
	public static Color orange = Color.decode("#D84315"); // orange
	public static Color darkGrey = Color.decode("#424242");// dark grey
	public static Color lightGrey = Color.decode("#E0E0E0"); // light grey


	// allows any class to get a color so that its easy and the colors are consistent throughout the application
	public static Color getColor(String section) {
		Color color = null;
		switch(section) {
		case "Violet":
			color = red;
			break;
		case "Green": 
			color = green;
			break;
		case "Orange": 
			color = orange;
			break;
		case "Dark Grey":
			color = darkGrey;
			break;
		case "Light Grey":
			color = lightGrey;
		}
		return color;
	}

	// keeping a instances of a few of the main classes here so that the same instance can be accessed from other classes as needed
	public static POSView getView() {
		return view;
	}

	public static void setView(POSView v) {
		view = v;
	}

	public static Controller getController() {
		return controller;
	}

	public static void setController(Controller c) {
		controller = c;
	}

	
	// this is just a toString method that prints a message along with the class that calls it to help with debugging
	public static void log(Object o, String string) {
		String result = "";

		result = "--------------------------------------\nSOURCE:    " 
				+ o.getClass().getName() + "\nLOG:       " + string + "\n--------------------------------------";
		System.out.println(result + "\n");

	}


}
