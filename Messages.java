package backup;

/**
 * Service class that is designed to be used by OrderManager class
 * Classes can use the static method of this class without instantiation
 */

public class Messages {

	public static String shipmentSuccessMsg(String sender, String receiver){
		return " Hello " + sender + "\n Your order to " + receiver + " \n was deliveried successfully!!";
	}
	
	public static String shipmentFailMsg(String sender, String receiver){
		return " Hello " + sender + "\n Your order to " + receiver + " \n has failed!!";
	}
}
