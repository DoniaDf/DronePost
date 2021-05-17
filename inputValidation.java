package backup;

/**
 * This Class is designed to validate the user inputs in the different text fields
 * All the methods are static to easy call them from the different classes with no need for instantiation
 */

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class inputValidation {
	
	//Validate date with specific date format by using DateValidatorUsingDateFormat method
	//If the input date is not appropriate, message will be shown to the user accordingly
	static DateValidator validator = new DateValidatorUsingDateFormat("dd-mm-yyyy");
	
	public static InputVerifier dateVerifier = new InputVerifier() {
		
		@Override
		public boolean verify(JComponent input) {
			JTextField temp = (JTextField)input;
			String str = String.valueOf(temp.getText());
			if (!validator.isValid(str)){
				JOptionPane.showMessageDialog(null, "You MUST input only date with format DD-MM-YYYY!");
				return false;
			}
			return true;
		}
	};
	
	//Validate fields that should contain only characters in the input
	//If the input is not characters, message will be shown to the user accordingly
	public static InputVerifier CharVerifier = new InputVerifier() {
		
		@Override
		public boolean verify(JComponent input) {
			JTextField temp = (JTextField)input;
			String str = String.valueOf(temp.getText());
			if (!str.equals("") && (str != null) && !str.matches("^[a-zA-Z]*$")){
				JOptionPane.showMessageDialog(null, "You MUST input only characters!");
				return false;
			}
			return true;
           
        }
    };
    
    //Validate fields that should contain only numbers in the input
  	//If the input is not numbers, message will be shown to the user accordingly
    public static InputVerifier NumberVerifier = new InputVerifier() {
		
		@Override
		public boolean verify(JComponent input) {
			JTextField temp = (JTextField)input;
			try {
                int number = Integer.parseInt(temp.getText());
                return true;
            }
            catch(NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "You MUST input only numbers!");
            }
            return false;
        }
    };
    
    //Validate fields that should contain only 10 numbers in the input
  	//If the input is not characters and the length isn't equal to 10, message will be shown to the user accordingly
	public static InputVerifier phoneNumberVerifier = new InputVerifier() {
		
		@Override
		public boolean verify(JComponent input) {
			JTextField temp = (JTextField)input;
			String number = temp.getText();
			if (number.matches("[0-9]+") && number.length()==10){
				return true;
			} else {
				JOptionPane.showMessageDialog(null, "You MUST input number of 10 digits!");
				return false;
			}
        }
    };
    
  //Validate fields that should contain only 10 numbers in the input
  	//If the input is not characters and the length isn't equal to 10, message will be shown to the user accordingly
	public static InputVerifier passwordVerifier = new InputVerifier() {
			
		@Override
		public boolean verify(JComponent input) {
			JTextField temp = (JTextField)input;
			String password = temp.getText();
			if (password.length()==8){
				return true;
			} else {
				JOptionPane.showMessageDialog(null, "You MUST type a password that contains 8 characters & numbers!");
				return false;
			}
        }
    };

    //Validate fields that should contain only specific email suffices in the input
  	//If the input is not containing one of these suffices, message will be shown to the user accordingly
	public static InputVerifier emailVerifier = new InputVerifier() {
		
		@Override
		public boolean verify(JComponent input) {
			JTextField temp = (JTextField)input;
			String number = temp.getText();
			if (number.contains("@gmail.com") || number.contains("@hotmail.com") || number.contains("@walla.co.il")  ){
				return true;
			} else {
				JOptionPane.showMessageDialog(null, "Only Gmail, Hotmail and Walla are accepted!");
				return false;
			}
        }
    };
}
