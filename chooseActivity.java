package backup;

/*
 * Represents a UI that enables user to choose activity
 * Two activities can be chosen by the user: Order drone and show order history of login user 
 */

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.*;
import java.util.ArrayList;    

class chooseActivity extends JFrame implements ActionListener{    
	
	//UI components
	public JRadioButton orderButton = new JRadioButton("New Order");
	public JRadioButton historyButton = new JRadioButton("History");
	public JTextField userID = new JTextField(20);
	public JTextField userName = new JTextField(20);
	public JTextField phone = new JTextField(20);
	public JTextField ordersPackage = new JTextField(5);
	protected JButton nextButton = new JButton ("Next");
	
	public static int packageOrders;
	public static User Activity_User;
	
	public chooseActivity(){  
		
		ButtonGroup bg=new ButtonGroup();    
		bg.add(orderButton);
		bg.add(historyButton);
		
		//Adding panels to the Activity form
		JPanel p1 = new JPanel(new GridLayout(0,2));
		p1.add(new Label("User ID"));
		p1.add(userID).setEnabled(false);
		p1.add(new Label("User Name"));
		p1.add(userName).setEnabled(false);
		p1.add(new Label("User Phone: "));
		p1.add(phone).setEnabled(false);
		p1.add(new Label("Remaining orders in your package: "));
		p1.add(ordersPackage).setEnabled(false);
		
		JPanel p2 = new JPanel(new GridLayout(0,2));
		p2.add(orderButton);
		p2.add(new Label(""));
		p2.add(historyButton);
		
		JPanel p3 = new JPanel(new GridLayout(0,2));
		p3.add(nextButton);
		p3.add(new Label(""));
		p3.add(new Label(""));
		p3.setAlignmentX(CENTER_ALIGNMENT);
		
		setLayout(new GridLayout(3,2));
		add(p1, BorderLayout.NORTH);
		add(p2, BorderLayout.CENTER);
		add(p3, BorderLayout.SOUTH);
		pack();
		
		//Adding listener to the Next button
		nextButton.addActionListener(this);

	}
		   
	/*
	 * If Order button is selected, the user will move to the OrderDrone form
	 * User package will be shown on the OrderDrone form 
	 * If Order History button is selected, the user will move to the Order History form
	 */
	public void actionPerformed(ActionEvent e){  
		
		User user_login = LoginPage.user_login;
		packageOrders = user_login.getPackage();
		String Activity_UserName = user_login.getName();
		String Activiy_User_ID = user_login.getUserID();
		
		//Show order drone form and load user package, phone, and name
		if(orderButton.isSelected()){
			//If user have at least one order in his package 
			if (packageOrders > 0) {
				this.setVisible(false);
				String numOfPackages = Integer.toString(packageOrders);
				this.setVisible(false);
	            orderDrone order_drone = new orderDrone();
	            order_drone.userName.setText(Activity_UserName);
	            order_drone.phone.setText(phone.getText());
	            orderDrone.packages.setText(numOfPackages);
	            order_drone.userID.setText(Activiy_User_ID);
	            order_drone.setVisible(true);
			}
			//Else if user package is empty, show payment details form to enable user to buy new package
            else {
          
            String name = user_login.getName();
            String user_ID = userID.getText();
			String familyName = user_login.getLastName();
			String mail = user_login.getEmail();
			String userPassword = user_login.getPassword();
			String phone = user_login.getPhoneNum();
			String cityName = user_login.getAddress();
			String zipCode = user_login.getAddress();
			String street = user_login.getAddress();
			String house = user_login.getAddress();
			String address = "City: " + cityName + ", ZIP: " + zipCode + ", Street: " + street + ", House: " + house;
			
			completeRegistration payment = new completeRegistration();
			
		    Activity_User = new User(user_ID, name, familyName, mail, userPassword, phone, address, 0, null);
			completeRegistration.isNewPackage = false;
			this.setVisible(false);

			payment.setVisible(true);
			
			} 
         }
		
		//Show order history
		if(historyButton.isSelected()){
			this.setVisible(false);
			ordersHistory order_history = new ordersHistory();
			order_history.user_Name.setText(Activity_UserName);
			order_history.phone.setText(phone.getText());
			order_history.setVisible(true);
		}    
	}
}   