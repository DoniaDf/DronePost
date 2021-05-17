package backup;

/**
 * Represents orderDrone form that enables user to order drone
 * and to receive/send order to another user from the user DB
 */

import java.awt.*;
import javax.swing.*;

import com.mysql.jdbc.Util;

import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
import java.sql.PreparedStatement; 

public class orderDrone extends JFrame implements ActionListener, ItemListener{
	
	//UI Component
	public static JTextField packages = new JTextField(20);
	public JTextField userID = new JTextField(20);
	public Choice order = new Choice();
	public static Choice usersPhoneList = new Choice();
	public JTextField userName = new JTextField(20);
	public JTextField phone = new JTextField(20);
	public JButton orderButton = new JButton ("Order");
	public JButton cancelButton = new JButton ("Close");
	public JButton activityButton = new JButton ("Back to activity");
	public JButton newPackage = new JButton ("Take new package");
	public static JTextArea systemUpdates = new JTextArea();
	public static Label phoneLabel;
	public int packagesOrders;
	
	Connection conn;
	Statement stmt;
	ResultSet rs;
	String query, userPhone, performerID, performerName, recipientPhone, packageOrders, recipientName;
	int orderIndexSelected, numOfPackages;
	
	long millis=System.currentTimeMillis();
	Date date = new Date(millis);
	
	//ArrrayList that contains users from the DB
	ArrayList<User> usersDBList = LoginPage.userLoginList; 
	
	public orderDrone() {
		super("Order drone");
		
		order.add("Send order");
		order.add("Receive order");
		
		for(int i=0; i<usersDBList.size(); i++){
			userPhone = usersDBList.get(i).getPhoneNum();
			usersPhoneList.add(userPhone);
		}
				
		//Add UI components to labels
		JPanel p1 = new JPanel(new GridLayout(0,2));
		p1.add(new Label("User ID"));
		p1.add(userID).setEnabled(false);
		p1.add(new Label("User Name"));
		p1.add(userName).setEnabled(false); 
		p1.add(new Label("User Phone: "));
		p1.add(phone).setEnabled(false); 
		userName.setMaximumSize( userName.getPreferredSize() );
		phone.setMaximumSize( phone.getPreferredSize() );
		phoneLabel = new Label("Choose recipient phone number: ");
		p1.add(phoneLabel);
		p1.add(usersPhoneList);
		p1.add(new Label("Choose your order type: "));
		p1.add(order);
		p1.add(new Label(""));
		
		JPanel p2 = new JPanel(new GridLayout(0,2));
		//p2.setSize(this.WIDTH, 100);
		p2.add(new Label("Remaining orders in package: "));
		p2.add(packages).setEnabled(false);
		packages.setSize(userID.WIDTH, userID.HEIGHT);
		//p2.add(new Label(""));
		//p2.add(new Label(""));
		p2.add(new Label("Drone Post updates: "));
		p2.add(systemUpdates).setEnabled(false);;
		systemUpdates.setBackground(this.getBackground());
		systemUpdates.setFont(new Font("Serif", Font.ITALIC, 16));
		systemUpdates.setBackground(Color.GRAY);
		
		JPanel p3 = new JPanel(new FlowLayout());
		p3.add(activityButton);
		p3.add(orderButton);
		p3.add(cancelButton);
		
		setLayout(new BorderLayout());
		add(p1, BorderLayout.NORTH);
		add(p2, BorderLayout.CENTER);
		add(p3, BorderLayout.SOUTH);
		pack();
		
		setSize(450,350);
		
		//Add listeners to buttons
		activityButton.addActionListener(this);
		orderButton.addActionListener(this);
		cancelButton.addActionListener(this);
		order.addItemListener(this);
		newPackage.addItemListener(this);
	}
		
	public void actionPerformed(ActionEvent av) {
		String arg = av.getActionCommand();
		
		if (arg.equals("Close")) {
			this.setVisible(false);
		}
		
		else if (arg.equals("Order")) {
			performerName = userName.getText();
			performerID = userID.getText();
			recipientPhone = usersPhoneList.getSelectedItem();
			packageOrders = null;
			orderIndexSelected = order.getSelectedIndex();
			recipientName = null;
			
			for(int i=0; i<usersDBList.size(); i++){
				if(recipientPhone == usersDBList.get(i).getPhoneNum()){
					recipientName = usersDBList.get(i).getName();
				}
			}
			
			//If Send order is selected
			if(orderIndexSelected==0){
				usersPhoneList.setEnabled(false);
				order.setEnabled(false);
				//Update order DB with the order details
				try {
					Connection conn = connClass.getConn();
					query = "INSERT INTO `orders`(`performerName`, `performerID`, `recipientName`, `date`) VALUES (?,?,?,?)";
					PreparedStatement preparedStmt=conn.prepareStatement(query);
					preparedStmt.setString(1,performerName);
					preparedStmt.setString(2,performerID);
					preparedStmt.setString(3,recipientName);
					preparedStmt.setString(4,date.toString());

					preparedStmt.execute();
					conn.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					}
			} 
			
			//If Receive order is selected
			else if(orderIndexSelected==1){
				usersPhoneList.setEnabled(false);
				order.setEnabled(false);
				//Update order DB with the order details
				try {
					Connection conn = connClass.getConn();
					query = "INSERT INTO `orders`(`performerName`, `performerID`, `recipientName`, `date`) VALUES (?,?,?,?)";
					PreparedStatement preparedStmt=conn.prepareStatement(query);
					preparedStmt.setString(1,performerName);
					preparedStmt.setString(2,performerID);
					preparedStmt.setString(3,performerName);
					preparedStmt.setString(4,date.toString());

					preparedStmt.execute();
					conn.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					}	
			}
			
			usersPhoneList.setEnabled(false);
			order.setEnabled(false);
			orderButton.setEnabled(false);
			
			try {
				OrderManager.readOrderDB();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//If Back to Activity is chosen then back to activity form
		else if (arg.contentEquals("Back to activity")) {
			int ordersInPackage = Integer.parseInt(this.packages.getText());
			this.setVisible(false);
			chooseActivity activity = new chooseActivity();
			User user_login = LoginPage.user_login;
			user_login.setPackage(ordersInPackage);
			activity.userName.setText(this.userName.getText());
			activity.phone.setText(this.phone.getText());
			activity.userID.setText(this.userID.getText());
			activity.ordersPackage.setText(this.packages.getText());
			activity.setVisible(true);
		}
	}
	
	@Override
	public void itemStateChanged(ItemEvent il) {
		
		int i = order.getSelectedIndex();
		
		//if Receive order is selected then disappear usersPhoneList 
		if (i == 1 && phoneLabel.isVisible() && usersPhoneList.isVisible()){
			phoneLabel.setVisible(false);
			usersPhoneList.setVisible(false);
		}  
		//if Send order is selected then keep usersPhoneList visible
		else if (i == 0 && !phoneLabel.isVisible() && !usersPhoneList.isVisible()){
			phoneLabel.setVisible(true);
			usersPhoneList.setVisible(true);
		}
	}
}