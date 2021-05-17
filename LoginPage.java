package backup;

/**
 * Represents a form that enables the user to log in by email and password that existent in the DB
 */

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
//import java.util.Scaneer;
import java.util.ArrayList;

public class LoginPage extends JFrame implements ActionListener{
	
	//UI Components
	protected JTextField email = new JTextField(20);
	protected JTextField password = new JTextField(20);
	protected JButton nextButton = new JButton ("Next");
	protected JButton cancelButton = new JButton ("Cancel");
	protected JButton backButton = new JButton ("Back"); 
	
	public Connection conn;
	public Statement stmt;
	public ResultSet rs;
	public String query, userIdDB,userNameDB, lastNameDB, emailDB, passwordDB, phoneNumberDB, addressDB, CC_DB;
	public int packageDB;
	
	static ArrayList <User> userLoginList = new ArrayList<User>();
	static User users;
	static User user_login;
	
	public LoginPage() {
		super ("Login");
		JPanel p1 = new JPanel(new GridLayout(0,2));
		p1.add(new Label("Email"));
		p1.add(email);
		p1.add(new Label("Password"));
		p1.add(password);
		
		JPanel p2 = new JPanel(new FlowLayout());
		p2.add(backButton);
		p2.add(nextButton);
		p2.add(cancelButton);
		
		setLayout(new BorderLayout());
		add(p1, BorderLayout.NORTH);
		add(p2, BorderLayout.CENTER);
		pack();
		
		//Adding listeners to buttons
		backButton.addActionListener(this);
		nextButton.addActionListener(this);
		cancelButton.addActionListener(this);
	}	
	
	public void actionPerformed(ActionEvent av) {
		String arg = av.getActionCommand();
		
		//Exit if cancel
		if (arg.equals("Cancel")) 
			this.setVisible(false);
		
		//move to chooseActivity form
		else if (arg.equals("Next")) {
			
			String emailString = email.getText();
			String passwordString = password.getText();
			
			if(!emailString.isEmpty() && !passwordString.isEmpty()) {
				
				//Get the users' details from the users DB
				try {
					conn = connClass.getConn();
					query ="SELECT `User_ID`, `Name`, `Family`, `Email`, `Password`, `Phone_Number`, `Adress`, `package`, `CC_details` FROM `users`";
					stmt = conn.createStatement();
					rs = stmt.executeQuery(query);
					while(rs.next()){
						userIdDB = rs.getString("User_ID");
						userNameDB = rs.getString("Name");
						lastNameDB = rs.getString("Family");
						emailDB = rs.getString("Email");
						passwordDB = rs.getString("Password");
						phoneNumberDB = rs.getString("Phone_Number");
						addressDB = rs.getString("Adress");
						packageDB= rs.getInt("package");
						CC_DB = rs.getString("CC_details");
						
						//Add the user details to a user constructor
						users = new User(userIdDB, userNameDB, lastNameDB, emailDB, passwordDB, phoneNumberDB, addressDB, packageDB, CC_DB);
						
						//Add users to an ArrayList
						userLoginList.add(users);
					}
					
					boolean isUserExist=true;
					chooseActivity activity = new chooseActivity();
					
					//Going through the users ArrayList and check the given email and password if it exists in the DB
					for (int i=0; i<userLoginList.size(); i++){
						
						//if the details exists then move to the activity form else return false
						if(userLoginList.get(i).getEmail().equals(emailString) && userLoginList.get(i).getPassword().equals(passwordString)){
							
							user_login = userLoginList.get(i);
							activity.userName.setText(user_login.getName());
							activity.phone.setText(user_login.getPhoneNum());
							activity.userID.setText(user_login.getUserID());
							activity.ordersPackage.setText(Integer.toString(user_login.getPackage()));
							
							this.setVisible(false);
							activity.setVisible(true);
							isUserExist = true;
							i=userLoginList.size();
							
						} else {
							isUserExist = false;
						}	
					}
					
					//if false is returned then show an appropriate message
					if(!isUserExist){
						JOptionPane.showMessageDialog(null, "Login Details are not correct", "ERROR", JOptionPane.ERROR_MESSAGE);
					}
					
					rs.close();
					conn.close();
					
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				
			} else JOptionPane.showMessageDialog(null, "Field is mandatory"); // show a dialog message
		} 
		else if (arg.equals("Back")) {
			this.setVisible(false);
			new HomePage().setVisible(true);
		}
	}
	
/*	public static void main(String[] args) {
		LoginPage user = new LoginPage();
		user.show();
	}*/
}
	
