package backup;

/**
 * This class is designed to read orders details from orders DB.
 * If the order is new then it will be updated to false
 * and the user package in the DB will be decreased by one
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class OrderManager {
	
	static ArrayList <Order> orderList = new ArrayList<Order>();
	static Order order;
	
	public static int orderNumber;
	public static String date, recipientName, performerID, performerName;
	public static boolean isNew;
	public static int ordersInPackage;
	
    public static void readOrderDB() throws SQLException{
    	
    	Connection conn = null;
		try {
			conn = connClass.getConn();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		//Read from orders DB
    	String query1="select* from orders";
    	Statement stmt = conn.createStatement();
    	ResultSet rs = stmt.executeQuery(query1);
    	int orders_on_package = Integer.parseInt(orderDrone.packages.getText())-1;
		while(rs.next()) {
			orderNumber = rs.getInt("orderNumber");
			date = rs.getString("date");
			performerName = rs.getString("performerName");
			performerID = rs.getString("performerID");
			recipientName = rs.getString("recipientName");
			isNew = rs.getBoolean("isNew");
			
			//If order is new then assign a new drone for the order
			if(isNew) {
				order = new Order(orderNumber, date, performerName, performerID, recipientName, isNew);
				orderDrone(order);
				order.setIsNew(false);
				System.out.println(order.toString());
				
				//update package on the orderDrone form
				orderDrone.packages.setText(String.valueOf(orders_on_package));
				
				//update user regarding the shipment
				orderDrone.systemUpdates.setText(Messages.shipmentSuccessMsg(performerName, recipientName));
				
			//Update order status in orders DB
			try {
				Connection conn1 = connClass.getConn();
				String query2 = "UPDATE `orders` SET `isNew`= ? where performerID like'" + order.getPerformerID() + "'"; //handle all new orders?????????
				PreparedStatement preparedStmt=conn.prepareStatement(query2);

				preparedStmt.setBoolean(1, order.getIsNew());
	
				preparedStmt.execute();
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
			
			//Update number of orders in package on user DB
			try {
				Connection conn1 = connClass.getConn();
				String query2 = "UPDATE `users` SET `package`= ? where User_ID like'" + order.getPerformerID() + "'";
				PreparedStatement preparedStmt=conn.prepareStatement(query2);
				preparedStmt.setInt(1, orders_on_package);
	
				preparedStmt.execute();
				} catch (SQLException e1) {
					e1.printStackTrace();
					orderDrone.systemUpdates.setText(Messages.shipmentFailMsg(performerName, recipientName));
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
				}
		}
		conn.close();
		rs.close();
		}
		
    
    public static void orderDrone(Order order){
    	DroneStack.addDronestoStack();
    	DroneStack.assignDrone(order);
    }
}
