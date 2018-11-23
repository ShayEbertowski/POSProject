/*
 * 
 * 		This is the main view - it is an instance of JFrame, and several other panels are added to it to set up the GUI
 */


package mvc;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.BorderFactory;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;

import navPanels.ExpensesPanel;
import navPanels.IngredientsPanel;
import navPanels.POSPanel;
import navPanels.ProductsPanel;
import navPanels.SalesPanel;
import navPanels.SignInPanel;
import navPanels.StaffPanel;
import util.ButtonManager;
import util.Manager;

public class POSView extends JFrame{
	private static final long serialVersionUID = 1L;
	CardLayout mainCardLayout, subCardLayout;
	BorderLayout borderLayout = new BorderLayout();
	GridLayout menuGridLayout = new GridLayout(5,1);
	
	// colors
	Color titleBarColor = Manager.getColor("Violet");
	Color idleButtonColor = Manager.getColor("Orange");
	Color activeButtonColor = Manager.getColor("Green");

	// instances of awt tools used to determine the size of the frame
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	Dimension dimension = toolkit.getScreenSize();

	// setting the size of the frame to 3/4 the size of the device screen
	int frameWidth = (dimension.width/2) + (dimension.width/4);
	int frameHeight = (dimension.height/2) + (dimension.height/4);

	// JPanels
	JPanel titlePanel, mainCardPanel, contentContainerPanel, menuPanel, subCardPanel, signInPanel;

	// buttons
	JButton expensesButton, productsButton, ingredientsButton, staffButton, salesButton;
	JButton[] menuButtons = new JButton[5]; // update size if adding more menu buttons

	// POSPanels
	POSPanel expensesPanel, productsPanel, ingredientsPanel, staffPanel, salesPanel;

	// labels
	JLabel titleLabel;
	
	// setting up the controller for the view
	Controller controller;
	public void setController(Controller controller) {
		this.controller = controller;
	}

	// constructor - sets up the initial GUI
	public POSView() {
		this.setFocusable(true);
		initFrame();
		initPanels();
		initButtons();
		addPanelsToFrame();
		addPanelsToMainCardPanel();
		addPanelsToSubCardPanel();
		initVisibilities();

		// must be at the end!!!
		((SignInPanel) signInPanel).setDefaultButton(); // this must come AFTER adding the panel to the frame
		((SignInPanel) signInPanel).setFocusToInputField(); // this must come AFTER setting the visibilities
	}


	// init the main JFrame
	void initFrame() {
		this.setSize(frameWidth, frameHeight);

		// centering the frame in the middle of the screen based on screen size
		int xPosition = (dimension.width / 2) - (this.getWidth() / 2);
		int yPosition = (dimension.height / 2) - (this.getHeight() / 2);		
		this.setLocation(xPosition, yPosition);

		// prevent user from changing the frame size
		this.setResizable(false);

		// allow the x button to terminate the application
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// setting the title of the frame
		this.setTitle("POS");

		// layout for the frame
		this.setLayout(borderLayout);

		// init labels 
		titleLabel = new JLabel("POS", SwingConstants.CENTER);
		titleLabel.setForeground(Color.decode("#fefefe"));
		titleLabel.setFont(new Font("Serif", Font.BOLD, 22));
	}

	// init the JPanels
	void initPanels() {
		// main panel
		mainCardLayout = new CardLayout();
		mainCardPanel = new JPanel(mainCardLayout);

		// title panel
		titlePanel = new JPanel();
		titlePanel.setBackground(titleBarColor);
		titlePanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		titlePanel.setLayout(new BorderLayout());
		titlePanel.add(titleLabel, BorderLayout.CENTER);
		titlePanel.add(ButtonManager.getButton("Sign Out"), BorderLayout.EAST);

		// content container panel - holds the menu and sub card panels
		contentContainerPanel = new JPanel(new BorderLayout());

		// menu panel
		menuPanel = new JPanel(menuGridLayout);
		menuPanel.setPreferredSize(new Dimension(frameWidth/4, frameHeight));
		menuPanel.setBackground(Manager.getColor("Light Grey"));
		menuPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		menuGridLayout.setVgap(20);

		// sub card panel
		subCardLayout = new CardLayout();
		subCardPanel = new JPanel(subCardLayout);

		// sign in panel
		signInPanel = new SignInPanel();


		// sections panels --- which one is displayed depends on the state of the program
		expensesPanel= new ExpensesPanel(frameWidth, frameHeight);
		productsPanel= new ProductsPanel(frameWidth, frameHeight);
		ingredientsPanel = new IngredientsPanel(frameWidth, frameHeight);
		staffPanel= new StaffPanel(frameWidth, frameHeight);
		salesPanel = new SalesPanel(frameWidth, frameHeight);
	}

	void initButtons(){
		// these are all of the main navigation buttons
		expensesButton = ButtonManager.getButton("Expenses");
		productsButton = ButtonManager.getButton("Products");
		ingredientsButton = ButtonManager.getButton("Ingredients");
		staffButton = ButtonManager.getButton("Staff");
		salesButton = ButtonManager.getButton("Sales");
		
		// add to menu button array
		menuButtons[0] = expensesButton;
		menuButtons[1] = productsButton;
		menuButtons[2] = ingredientsButton;
		menuButtons[3] = staffButton;
		menuButtons[4] = salesButton;

		expensesButton.setBackground(idleButtonColor);
		expensesButton.setForeground(Color.white);
		expensesButton.setFont(new Font("Arial", Font.PLAIN, 16));
		expensesButton.setBorder(BorderFactory.createRaisedBevelBorder());

		productsButton.setBackground(idleButtonColor);
		productsButton.setForeground(Color.white);
		productsButton.setFont(new Font("Arial", Font.PLAIN, 16));
		productsButton.setBorder(BorderFactory.createRaisedBevelBorder());
		
		ingredientsButton.setBackground(idleButtonColor);
		ingredientsButton.setForeground(Color.white);
		ingredientsButton.setFont(new Font("Arial", Font.PLAIN, 16));
		ingredientsButton.setBorder(BorderFactory.createRaisedBevelBorder());

		staffButton.setBackground(idleButtonColor);
		staffButton.setForeground(Color.white);
		staffButton.setFont(new Font("Arial", Font.PLAIN, 16));
		staffButton.setBorder(BorderFactory.createRaisedBevelBorder());

		salesButton.setBackground(idleButtonColor);
		salesButton.setForeground(Color.white);
		salesButton.setFont(new Font("Arial", Font.PLAIN, 16));
		salesButton.setBorder(BorderFactory.createRaisedBevelBorder());

		menuPanel.add(expensesButton);
		menuPanel.add(productsButton);
		menuPanel.add(ingredientsButton);
		menuPanel.add(staffButton);
		menuPanel.add(salesButton);
	}

	private void addPanelsToFrame() {
		// this and the following 2 methods set up the panel hierarchy
		this.add(titlePanel, BorderLayout.NORTH);
		this.add(mainCardPanel);
	}

	void addPanelsToMainCardPanel() {
		mainCardPanel.add(signInPanel, "Sign In");
		mainCardPanel.add(contentContainerPanel, "Content Container");

		contentContainerPanel.add(menuPanel, BorderLayout.WEST);
		contentContainerPanel.add(subCardPanel, BorderLayout.CENTER);
	}

	void addPanelsToSubCardPanel() {
		subCardPanel.add(expensesPanel, "Expenses");
		subCardPanel.add(productsPanel, "Products");
		subCardPanel.add(ingredientsPanel, "Ingredients");
		subCardPanel.add(staffPanel, "Staff");
		subCardPanel.add(salesPanel, "Sales");
	}


	void initVisibilities() {
		// initializing panel visibilities
		this.setVisible(true);

		// button visibilities
		ButtonManager.setSignOutActive(false);
	}

	public void setActiveButton(String activeButton) {
		// this method changes the look of the buttons so that the active button is beveled down, bigger, and a different color than the idle buttons
		switch(activeButton) {
		case "Expenses":
			for (int i = 0; i < menuButtons.length; i++) {
				if (menuButtons[i] == expensesButton) {
					// full size and lowered bevel border
					menuButtons[i].setBackground(activeButtonColor);
					menuButtons[i].setBorder(BorderFactory.createLineBorder(Manager.getColor("Title Bar")));
					menuButtons[i].setBorder(BorderFactory.createLoweredBevelBorder());
				}
				else {
					// half size and raised bevel border
					menuButtons[i].setBackground(idleButtonColor);
					menuButtons[i].setBorder(new CompoundBorder(
							BorderFactory.createMatteBorder(0,0,0,100, Manager.getColor("Light Grey")), 
							BorderFactory.createRaisedBevelBorder()));
				}
			}
			break;

		case "Products":
			for (int i = 0; i < menuButtons.length; i++) {
				if (menuButtons[i] == productsButton) {
					// full size and lowered bevel border
					menuButtons[i].setBackground(activeButtonColor);
					menuButtons[i].setBorder(BorderFactory.createLineBorder(Manager.getColor("Title Bar")));
					menuButtons[i].setBorder(BorderFactory.createLoweredBevelBorder());
				}
				else {
					// half size and raised bevel border
					menuButtons[i].setBackground(idleButtonColor);
					menuButtons[i].setBorder(new CompoundBorder(
							BorderFactory.createMatteBorder(0,0,0,100, Manager.getColor("Light Grey")), 
							BorderFactory.createRaisedBevelBorder()));
				}
			}
			break;
			
		case "Ingredients":
			for (int i = 0; i < menuButtons.length; i++) {
				if (menuButtons[i] == ingredientsButton) {
					// full size and lowered bevel border
					menuButtons[i].setBackground(activeButtonColor);
					menuButtons[i].setBorder(BorderFactory.createLineBorder(Manager.getColor("Title Bar")));
					menuButtons[i].setBorder(BorderFactory.createLoweredBevelBorder());
				}
				else {
					// half size and raised bevel border
					menuButtons[i].setBackground(idleButtonColor);
					menuButtons[i].setBorder(new CompoundBorder(
							BorderFactory.createMatteBorder(0,0,0,100, Manager.getColor("Light Grey")), 
							BorderFactory.createRaisedBevelBorder()));
				}
			}
			break;

		case "Staff":
			for (int i = 0; i < menuButtons.length; i++) {
				if (menuButtons[i] == staffButton) {
					// full size and lowered bevel border
					menuButtons[i].setBackground(activeButtonColor);
					menuButtons[i].setBorder(BorderFactory.createLineBorder(Manager.getColor("Title Bar")));
					menuButtons[i].setBorder(BorderFactory.createLoweredBevelBorder());
				}
				else {
					// half size and raised bevel border
					menuButtons[i].setBackground(idleButtonColor);
					menuButtons[i].setBorder(new CompoundBorder(
							BorderFactory.createMatteBorder(0,0,0,100, Manager.getColor("Light Grey")), 
							BorderFactory.createRaisedBevelBorder()));
				}
			}
			break;
			
		case "Sales":
			for (int i = 0; i < menuButtons.length; i++) {
				if (menuButtons[i] == salesButton) {
					// full size and lowered bevel border
					menuButtons[i].setBackground(activeButtonColor);
					menuButtons[i].setBorder(BorderFactory.createLineBorder(Manager.getColor("Title Bar")));
					menuButtons[i].setBorder(BorderFactory.createLoweredBevelBorder());
				}
				else {
					// half size and raised bevel border
					menuButtons[i].setBackground(idleButtonColor);
					menuButtons[i].setBorder(new CompoundBorder(
							BorderFactory.createMatteBorder(0,0,0,100, Manager.getColor("Light Grey")), 
							BorderFactory.createRaisedBevelBorder()));
				}
			}
			break;
		}
	}

	// getters //////////////////////////////////////////////////////
	public CardLayout getMainCardLayout() {
		return mainCardLayout;
	}

	public CardLayout getSubCardLayout() {
		return subCardLayout;
	}

	// panels -----------------------------------------------------
	public JPanel getMainCardPanel() {
		return mainCardPanel;
	}

	public JPanel getSubCardPanel() {
		return subCardPanel;
	}

	public JPanel getSignInPanel() {
		return signInPanel;
	}


	// labels -----------------------------------------------------
	public JLabel getTitleLabel() {
		return this.titleLabel;
	}




}




