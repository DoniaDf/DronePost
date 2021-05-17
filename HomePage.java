package backup;

/*
 * Represents a Drone Post home page
 * Enables registration for a new user or log in for an existent user
 */

import java.awt.*;
import javax.swing.*;
import java.awt.event.*; 

public class HomePage extends JFrame implements ActionListener{
	
	//UI components
	protected JButton loginButton = new JButton ("Login");
	protected JButton signUpButton = new JButton ("Sign Up");
	
	public HomePage() {
		super("Drone Post");
		
		//Add panels to the UI form
		JPanel p1 = new JPanel(new GridLayout(0,1));
		p1.add(new Label("Welcome to our Drone Post App"));
		p1.add(new Label(""));
		
		JPanel p2 = new JPanel(new GridLayout(0,4));
		p2.add(new Label(""));
		p2.add(loginButton);
		p2.add(signUpButton);
		
		setLayout(new BorderLayout());
		add(p1, BorderLayout.NORTH);
		add(p2, BorderLayout.SOUTH);
		pack();
		
		//Adding listener to the buttons
		loginButton.addActionListener(this);
		signUpButton.addActionListener(this);

	}

	/**
	 * If the user choose Sign-up then he will move to the Registration form
	 * If the user choose Login then he will move to the LoginPage form
	 */
	public void actionPerformed(ActionEvent av) {
		String arg = av.getActionCommand();
		if (arg.equals("Sign Up")) {
			this.setVisible(false);
			new Registration().setVisible(true);
		}
		else if (arg.contentEquals("Login")) {
			this.setVisible(false);
			new LoginPage().setVisible(true);
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HomePage home = new HomePage();
		home.show();
	}
}
