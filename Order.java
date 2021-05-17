package backup;


/*
 * Represents a order that user requests.
 * 
 */

public class Order {

	protected long orderNumber;
	protected String date;
	protected int estimatedTime=4;
	protected String orderType; 
	protected boolean isNew; 
	
	protected String performerID, performerName, recipientName;

	//Default constructor for Order class
	public Order(){};
	
	//Order class constructor
	public Order(long orderNumber, String date, String performerName, String performerID, String recipientName, boolean isNew){
		this.orderNumber = orderNumber;
		this.date = date;
		this.performerName = performerName;
		this.performerID = performerID;
		this.recipientName = recipientName;
		this.isNew = isNew;
	}
	
	//Set and Get methods
	
	public long getOrderNumber(){
		return orderNumber;
	}
	
	public void setOrderNumber(long orderNumber){
		this.orderNumber = orderNumber;
	}
	
	public String getDate(){
		return date;
	}
	
	public void setDate(String date){
		this.date = date;
	}

	public String getPerformerName(){
		return performerName;
	}
	
	public void setPerformerName(String performerID){
		this.performerID = performerID;
	}
	
	public String getPerformerID(){
		return performerID;
	}
	
	public void setPerformerID(String performerName){
		this.performerName = performerName;
	}
		
	public String getRecipientName(){
		return recipientName;
	}
	
	public void setRecipientName(String recipientName){
		this.recipientName = recipientName;
	}
	
	public int getEstimatedTime(){
		return estimatedTime;
	}
	
	public boolean getIsNew(){
		return isNew;
	}
	
	public void setIsNew(boolean isNew){
		this.isNew = isNew;
	}
	
	//Convert all the parameters to String for printing out
	public String toString(){
		return "Order Number: " + orderNumber + ", Date: " + date + " Performer Name: " + performerName 
				+ " Performer ID: " + performerID + " Recipient Name: " + recipientName + " isNew: " + isNew;
	}
}
