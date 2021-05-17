package backup;

/*
 * Represents a user enrolled to the DronePost service.
 * Contains user details
 */

public class User {

	protected String userID;
	protected String userName;
	protected String lastName;
	protected String email;
	protected String password;
	protected String address;
	protected String phoneNumber;
	protected int orderPackage;
	protected String creditCard;

	//User constructor
	public User(String userID, String userName, String lastName, String email, String password, String phoneNumber, String address, int orderPackage, String creditCard){
		this.userID = userID;
		this.userName = userName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.orderPackage = orderPackage;
		this.creditCard = creditCard;
	}
	
	//Set and get methods
	
	public String getUserID(){
		return userID;
	}
	
	public void setUserID(String userID){
		this.userID = userID;
	}
	
	public String getName(){
		return userName;
	}
	
	public void setName(String userName){
		this.userName = userName;
	}
	
	public String getLastName(){
		return lastName;
	}
	
	public void setLastName(String lastName){
		this.lastName = lastName;
	}
	
	public String getEmail(){
		return email;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public String getAddress(){
		return address;
	}
	
	public void setAddress(String address){
		this.address = address;
	}
	
	public String getPhoneNum(){
		return phoneNumber;
	}
	
	public void setPhoneNum(String phoneNumber){
		this.phoneNumber = phoneNumber;
	}
	
	public int getPackage(){
		return orderPackage;
	}
	
	public void setPackage(int orderPackage){
		this.orderPackage = orderPackage;
	}
	
	public String getCreditCard(){
		return creditCard;
	}
	
	public void setcCreditCard(String creditCard){
		this.creditCard = creditCard;
	}
	
	//Convert all the parameters to String for printing out
	public String toString(){
		return "User ID: " + userID + "User Name: " + userName + ", Last Name: " + lastName + ", email: " + email + ", Address: " + address + 
				", Phone Number: " + phoneNumber + ", Order Package: " + orderPackage + ", CC details: " + creditCard;
	}
}
