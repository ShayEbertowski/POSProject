/*
 * 
 * Forcing panels that implement this interface to call these methods
 */

package navPanels;

import javax.swing.JTextArea;

public interface PanelMethods {
	
	void initSubMenuButtons();
	void addToPanelManager();
	void clearContents();
}
