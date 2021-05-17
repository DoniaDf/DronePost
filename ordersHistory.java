package backup;

/**
 * Represents a user orders history
 */

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.*;

public class ordersHistory extends JFrame implements ActionListener{
	
	//UI components
	public JTextField user_Name = new JTextField(20);
	public JTextField phone = new JTextField(20);
	protected JButton userPanelBtn = new JButton ("Back to Activity");
	protected JButton closeBtn = new JButton ("Close");
	
	User user_login = LoginPage.user_login;
	
	public ordersHistory(){
		
		//Adding panels to the form
		JPanel p1 = new JPanel(new GridLayout());
		p1.add(new Label("User Name"));
		p1.add(user_Name).setEnabled(false);
		p1.add(new Label("User Phone: "));
		p1.add(phone).setEnabled(false);
		p1.add(new Label(""));
		p1.add(new Label(""));
		
		JPanel p2 = new JPanel(new GridLayout(2,2));
		p2.add(userPanelBtn);
		p2.add(new Label(""));
		p2.add(closeBtn);
		p2.add(new Label(""));
		p2.add(new Label(""));
		p2.setAlignmentX(CENTER_ALIGNMENT);
		
		userPanelBtn.addActionListener(this);
		closeBtn.addActionListener(this);
		
		//Order table UI
		String data[][] = null;
		String column[]={"Order number", "performerID", "Performer Name", "Recipient Name", "Date", "Status"};
		String query, date, performerID, performer_name, recipient_name; //,pack;
		int orderNum, status, i=0,k=0,arrSize=0;
		Connection conn;
		Statement stmt;
		ResultSet rs;
		
		//Getting the user order history from Orders DB
		try{
			conn = connClass.getConn();
			query ="SELECT * from orders";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				arrSize++;
			}
			
			data = new String[arrSize][20];
			//rs.close();
			rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				k=0;
				orderNum = rs.getInt("orderNumber");
				performerID = rs.getString("performerID");
				performer_name = rs.getString("performerName");
				recipient_name = rs.getString("recipientName");
				date = rs.getString("date");
				status = rs.getInt("isNew");
				
				//User user_login = LoginPage.user_login;
				String userNameID = user_login.getUserID();
				
				if(status == 0 && (performerID.equals(userNameID))) {
					
					System.out.println(userNameID + " " + performer_name + " " + recipient_name + " " + date + " " +  status + " " + orderNum);
				
					data[i][k++] = "" + orderNum;
					data[i][k++] = "" + performerID;
					data[i][k++] = "" + performer_name;
					data[i][k++] = "" + recipient_name;
					data[i][k++] = date.toString();
					data[i][k++] = String.valueOf(status);
					
					i++;
				}
			}
			
			rs.close();
			conn.close();
		}catch(Exception e){
			System.out.println(e);
		}
		
		//Table setting
		JTable myTable=new JTable(data,column);
		myTable.setBounds(30,40,200,300);
		JScrollPane sp=new JScrollPane(myTable);
		
		setLayout(new BorderLayout());
		add(p1, BorderLayout.NORTH);
		add(sp, BorderLayout.CENTER);
		add(p2, BorderLayout.SOUTH);
		pack();
		setSize(550,600);
	}
	
	//Return to Activity and set the fields with the details of the current user
	@Override
	public void actionPerformed(ActionEvent av) {
		
		String arg = av.getActionCommand();
		if(arg.equals("Back to Activity")){
			this.setVisible(false);
			
			chooseActivity activity = new chooseActivity();
			String ordersInPackage = String.valueOf(activity.packageOrders);
			
			activity.userName.setText(this.user_Name.getText());
			activity.phone.setText(this.phone.getText());
			activity.userID.setText(user_login.getUserID());
			activity.ordersPackage.setText(ordersInPackage);
			activity.setVisible(true);
		}    
		
		if(arg.equals("Close")){
			this.setVisible(false);
		} 
	}
}