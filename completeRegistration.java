package backup;

/**
 * Represents a a form that enables the user to put his payment details
 * and to choose an order package
 */


import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

public class completeRegistration extends JFrame implements ActionListener {
	
	//UI Components
	protected JTextField cardHolderName = new JTextField(20);
	protected Choice cardType = new Choice();
	protected JTextField cardNumber = new JTextField(16);
	protected JTextField expiryDate = new JTextField(5);
	protected JTextField cardCvv = new JTextField(3);
	protected Choice packages = new Choice();
	
	//Buttons
	protected JButton submitButton = new JButton ("Submit");
	protected JButton cancelButton = new JButton ("Cancel");
	
	//isNewPackage: flag that indicates if the user has a package before or not
	//if the user has a package before then isNewPackage = false, else isNewPackage = true;  
	public static boolean isNewPackage = true; 
		
	String query1, query2;
	public static User register_user;
	
	public completeRegistration() {
		super("Complete your registration");
		packages.add("50 orders - Up to 1 year - 99$");
		packages.add("100 orders - Up to 1 year - 179$");
		packages.add("Unlimited orders - 20$ monthly");
		
		cardType.add("Visa");
		cardType.add("Mastercard");
		cardType.add("American Express");
		
		//adding the components to the labels
		JPanel p1 = new JPanel(new GridLayout(0,2));
		p1.add(new Label("Card holder name"));
		p1.add(cardHolderName);
		p1.add(new Label("Card Type"));
		p1.add(cardType);
		p1.add(new Label ("Card number"));
		p1.add(cardNumber);
		p1.add(new Label ("Expiry date"));
		p1.add(expiryDate);
		p1.add(new Label ("CVV"));
		p1.add(cardCvv);
		
		JPanel p2 = new JPanel (new GridLayout(0,2));
		p2.add(new Label("Choose a package"));
		p2.add(packages);
		p2.add(new Label(""));
		
		JPanel p3 = new JPanel (new GridLayout(0,4));
		p3.add(new Label(""));
		p3.add(submitButton);
		p3.add(cancelButton);
		
		setLayout(new BorderLayout());
		add(p1, BorderLayout.NORTH);
		add(p2, BorderLayout.CENTER);
		add(p3, BorderLayout.SOUTH);
		pack();
		
		//Adding listeners to the buttons
		submitButton.addActionListener(this);
		cancelButton.addActionListener(this);
		
		//Adding Data validators to the different fields
		cardHolderName.setInputVerifier(inputValidation.CharVerifier);
		cardNumber.setInputVerifier(inputValidation.NumberVerifier);
		expiryDate.setInputVerifier(inputValidation.dateVerifier);
		cardCvv.setInputVerifier(inputValidation.NumberVerifier);
	}

    public void actionPerformed(ActionEvent av) {
        String arg = av.getActionCommand();
        
        if (arg.equals("Cancel")){
            this.setVisible(false);
        }
        
        //If submit button is chosen, then save the details in the user constructor that's been created in registration class
        //Update the User DB with the new user
        else if (arg.equals("Submit")){
            String holderName = cardHolderName.getText();
            String type = cardType.getSelectedItem();
            String cNumber = cardNumber.getText();
            String expiry = expiryDate.getText();
            String cvv = cardCvv.getText();
            String cc_details = "Holder name: " + holderName + ", Type: " + type + "\nCard number: " + cNumber + ", Expiry: " + expiry + ", CVV: " + cvv;
            int packageSelected = 0;

            switch (packages.getSelectedIndex()) {
              case 0:
            	  packageSelected = 50;
                break;
              case 1:
            	  packageSelected = 100;
                break;
              case 2:
            	  packageSelected = 1000; // 1000 orders package represents "Unlimited Package"
                break;
            }
            
            if (!holderName.isEmpty() && !type.isEmpty() && !cNumber.isEmpty() &&
                    !expiry.isEmpty() && !cvv.isEmpty() && packageSelected != 0){
                
            	//If this is the first time that the user buy package and he came from the registration form
            	if(isNewPackage){
		        	register_user = Registration.user;
		            register_user.setcCreditCard(cc_details);
		            register_user.setPackage(packageSelected);
		            
		          //Update the user DB with the user details then update all the user details in user DB
		            try{
		                Connection conn = connClass.getConn();
		                query1 = "INSERT INTO `users`(`Name`, `Family`, `Email`, `Password`, `Phone_Number`, `Adress`, `package`, `CC_details`) VALUES (?,?,?,?,?,?,?,?)";
	                    
	                    PreparedStatement preparedStmt = conn.prepareStatement(query1);
						preparedStmt.setString(1,register_user.getName());
						preparedStmt.setString(2,register_user.getLastName());
						preparedStmt.setString(3,register_user.getEmail());
						preparedStmt.setString(4,register_user.getPassword());
						preparedStmt.setString(5,register_user.getPhoneNum());
						preparedStmt.setString(6,register_user.getAddress());
						preparedStmt.setInt(7,register_user.getPackage());
						preparedStmt.setString(8,register_user.getCreditCard());
						
						preparedStmt.execute();
						conn.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					}
		            
		          //If the user buy a package before and he came from the chooseActivity form
		          //Then update only package and CC_details in user DB
            	} else{
            		register_user = chooseActivity.Activity_User;
            		System.out.println(register_user.toString());
            		register_user.setcCreditCard(cc_details);
		            register_user.setPackage(packageSelected);
		            System.out.println(register_user.toString());
		            
		            try{
		                Connection conn = connClass.getConn();
		                query1 = "UPDATE users SET package= ?, CC_details=? where User_ID ='" + register_user.getUserID() + "'";
		                
		                PreparedStatement preparedStmt=conn.prepareStatement(query1);
						preparedStmt.setInt(1, register_user.getPackage());
						preparedStmt.setString(2, register_user.getCreditCard());
			
						preparedStmt.execute();
						} catch (SQLException e1) {
							e1.printStackTrace();
						} catch (ClassNotFoundException e1) {
							e1.printStackTrace();
						}
            	}

				this.setVisible(false);
				LoginPage login = new LoginPage();
				login.setVisible(true);
				}
				else JOptionPane.showMessageDialog(null, "Fields is mandatory");
			}
		}
}
