package main;
import mvc.Controller;
import mvc.POSView;
import util.ButtonManager;
import util.InputManager;
import util.Manager;

public class CandyShop {
	
	/*
	 * ------------------------Note that the order of the main method is important!!!------------------------
	 */

	public static void main(String[] args) throws Exception {
		// setting up helper classes
        InputManager inputMAnager = new InputManager();
        ButtonManager buttonManager = new ButtonManager();
        
        // set up and link view and controller
 		POSView view  = new POSView();
 		Controller controller = new Controller(); // must come before thread helper
 		
 		
 		// linking view and controller together
 		view.setController(controller);
 		Manager.setView(view);
 		controller.setView(view);
 		Manager.setController(controller);
 		
    }

}
