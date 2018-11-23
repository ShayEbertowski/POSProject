/*
 * 
 * As panels are created they are added to this hash map for easy access from other classes
 */

package navPanels;

import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JPanel;

import util.Manager;

public class PanelManager {
	
	static HashMap<String, JPanel> map = new HashMap<String, JPanel>();
	
	static void addPanel(String name, POSPanel panel) {
		map.put(name, panel);
	}
	
	// Polymorphic version to accommodate sign in panel and any other panel that doesn't extend Panel
	public static void addPanel(String name, JPanel panel) {
		map.put(name,  panel);
	}
	
	public static POSPanel getPanel(String name) {
		return (POSPanel) map.get(name);
	}

	public static SignInPanel getSignInPanel() {
		return (SignInPanel) map.get("Sign In");
	}
}
