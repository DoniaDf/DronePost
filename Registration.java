package backup;

/**
 * Represents a registration form that enables the user to input his details
 */

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.event.WindowEvent;
import java.nio.charset.CharacterCodingException;
import java.sql.*;
import java.util.ArrayList;

public class Registration extends JFrame implements ActionListener{
	
	//Text fields
	protected JTextField firstName = new JTextField(20);
	protected JTextField lastName = new JTextField(20);
	protected JTextField email = new JTextField(30);
	protected JTextField password = new JTextField(10);
	protected JTextField phoneNumber = new JTextField(10);
	protected JTextField city = new JTextField(20);
	protected JTextField ZIP = new JTextField(20);
	protected JTextField streetName = new JTextField(20);
	protected JTextField houseNumber = new JTextField(20);
	
	//Buttons
	protected JButton nextButton = new JButton ("Next");
	protected JButton clearButton = new JButton ("Clear");
	protected JButton cancelButton = new JButton ("Cancel");
	
	public static Label l1;
	
	public static User user;
	
	String query;
	
	public Registration() {
		super ("New user registration");
		
		//adding the components to the labels
		JPanel p1 = new JPanel(new GridLayout(0,2));
		p1.add(new Label("First name"));
		p1.add(firstName);
		p1.add(new Label("Last name"));
		p1.add(lastName);
		p1.add(new Label("Email address"));
		p1.add(email);
		p1.add(new Label ("Password"));
		p1.add(password);
		p1.add(new Label("Phone number"));
		p1.add(phoneNumber);
		
		p1.add(new Label("Address:"));
		p1.add(new Label(""));
		
		p1.add(new Label("City"));
		p1.add(city);
		p1.add(new Label("ZIP"));
		p1.add(ZIP);
		p1.add(new Label("Street Name"));
		p1.add(streetName);
		p1.add(new Label("House Number"));
		p1.add(houseNumber);
		
		
		JPanel p2 = new JPanel(new FlowLayout());
		p2.add(nextButton);
		p2.add(clearButton);
		p2.add(cancelButton);
		
		setLayout(new BorderLayout());
		add(p1, BorderLayout.NORTH);
		add(p2, BorderLayout.CENTER);
		pack();
		
		//Adding listeners to the buttons
		nextButton.addActionListener(this);
		clearButton.addActionListener(this);
		cancelButton.addActionListener(this);
		
		//Adding data validators to the text fields
		firstName.setInputVerifier(inputValidation.CharVerifier);
		phoneNumber.setInputVerifier(inputValidation.phoneNumberVerifier);
		lastName.setInputVerifier(inputValidation.CharVerifier);
		city.setInputVerifier(inputValidation.CharVerifier);
		ZIP.setInputVerifier(inputValidation.NumberVerifier);
		streetName.setInputVerifier(inputValidation.CharVerifier);
		houseNumber.setInputVerifier(inputValidation.NumberVerifier);
		email.setInputVerifier(inputValidation.emailVerifier);
	}
	
	public void actionPerformed(ActionEvent av) {
		String arg = av.getActionCommand();
		if (arg.equals("Clear")) {
			firstName.setText("");
			lastName.setText("");
			email.setText("");
			password.setText("");
			phoneNumber.setText("");
			city.setText("");
			ZIP.setText("");
			streetName.setText("");
			houseNumber.setText("");
		}
		
		else if (arg.equals("Next")) {
			String name = firstName.getText();
			String familyName = lastName.getText();
			String mail = email.getText();
			String userPassword = password.getText();
			String phone = phoneNumber.getText();
			String cityName = city.getText();
			String zipCode = ZIP.getText();
			String street = streetName.getText();
			String house = houseNumber.getText();
			String address = "City: " + cityName + ", ZIP: " + zipCode + ", Street: " + street + ", House: " + house;
			
			completeRegistration payment = new completeRegistration();
			
			if(!name.isEmpty() && !familyName.isEmpty() && !zipCode.isEmpty() && !street.isEmpty() && !cityName.isEmpty() && 
					!phone.isEmpty() && !mail.isEmpty() && !house.isEmpty() && !userPassword.isEmpty()) {

				
				user = new User(null, name, familyName, mail, userPassword, phone, address, 0, null);
				this.setVisible(false);

				payment.setVisible(true);
				
			} else JOptionPane.showMessageDialog(null, "Fields is mandatory"); // show a dialog message
		}
		
		else if (arg.contentEquals("Cancel")){
			this.setVisible(false);
		}
	}
}
