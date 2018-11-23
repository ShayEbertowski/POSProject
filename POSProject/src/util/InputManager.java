/*
 * 
 *   this class keeps all of the input fields in one spot, they are added to this class as they are created and can be accessed from
 *   any other class as needed
 */

package util;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class InputManager {

	static JTextField usernameField, amountField;;
	static JPasswordField passwordField;
	
	
	public InputManager() {
		usernameField = new JTextField();
		passwordField = new JPasswordField();
		amountField = new JTextField("Enter Amount Here");
	}
	
	
	// TODO: if there ends up being a lot of these then do it like button manager - create a hash map and iterate through it
	
	// getters
	public static JTextField getUsernameField(){
		return usernameField;
	}
	
	public static JPasswordField getPasswordField(){
		return passwordField;
	}
	
	public static JTextField getAmountField() {
		return amountField;
	}
}
