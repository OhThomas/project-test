package application;

import javafx.scene.control.Label;
/**
 * Validates user input
 * 
 * @author Thomas Rader (vil203)
 */
public class InputValidation {
	/**
	 * Checks to see if password meets basic security measurements. 
	 * 
	 * @param s						String of the password
	 * @param incorrectLabel		Label passed in to change to certain text depending on unmet password qualification
	 * @return						returns true if they fail to meet the requirement
	 */
	public boolean passwordCheck(String s, Label incorrectLabel){
		if(s.length() < 6){
			incorrectLabel.setText("Password Needs At Least 6 Characters");
			incorrectLabel.setOpacity(1);
			return true;
		}
		else{
			boolean b = false;
			for(int i = 0; i < s.length(); i++){
				if(Character.isUpperCase(s.charAt(i))){
					b = true;
				}
				if(i == s.length() -1 && b == false){
					incorrectLabel.setText("Password Needs At Least 1 Uppercase Character");
					incorrectLabel.setOpacity(1);
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * Checks to see if their email meets basic email functionality.
	 * 
	 * @param s						String of the email
	 * @param incorrectLabel		Label passed in to match parameter above and make it look pretty (should delete)
	 * @return						returns true if they fail to meet the email regex *@*
	 */
	public boolean emailCheck(String s, Label incorrectLabel){
		for(int i = 0; i < s.length(); i++){
			if(s.charAt(i) == '@' && i > 0 && i != s.length()-1)
				return false;
		}
		incorrectLabel.setText("Email needs *@*");
		incorrectLabel.setOpacity(1);
		return true;
	}
	/**
	 * Checks to see if they have any commas in their input when creating an account.
	 * 
	 * @param s						String to check commas for
	 * @param parameter				Parameter to match text for label but can be done in CreateAccount(should delete)
	 * @param incorrectLabel		Label passed in to match parameter above and make it look pretty  (should delete)
	 * @return						returns true if they have commas in their account information
	 */
	public boolean commaCheck(String s, String parameter, Label incorrectLabel){
		if(s.contains(",")){
			incorrectLabel.setText(parameter + " Can't Contain Commas");
			incorrectLabel.setOpacity(1);
			return true;
		}
		return false;
	}
	/**
	 * Checks to see if the string is too long.
	 * 
	 * @param s						String to check length for
	 * @param parameter				Parameter to match text for label but can be done in CreateAccount(should delete)
	 * @param incorrectLabel		Label passed in to match parameter above and make it look pretty  (should delete)
	 * @return						returns true if the string is longer than 16 characters
	 */
	public boolean lengthCheck(String s, String parameter, Label incorrectLabel){
		if(parameter.equals("Password") || parameter.equals("Email")){
			if(64 < s.length()){
				incorrectLabel.setText(parameter + " Can't Be Greater Than 64 Characters");
				incorrectLabel.setOpacity(1);
				return true;
			}
		}
		else{
			if(16 < s.length()){
				incorrectLabel.setText(parameter + " Can't Be Greater Than 16 Characters");
				incorrectLabel.setOpacity(1);
				return true;
			}
		}
		return false;
	}
	/**
	 * Checks to see if the string is using special characters.
	 * 
	 * @param s						String to check special characters for
	 * @param parameter				Parameter to check which special characters to look for (Username, Password, Email, etc)
	 * @param incorrectLabel		Label passed in to match parameter above and make it look pretty  (should delete)
	 * @return						returns true if String s contains a special character forbidden by its parameter status
	 */
	public boolean specialCharacterCheck(String s, String parameter, Label incorrectLabel){
		switch(parameter){
		case "Password": case "Email": //Maybe change for email
			String passwordRegex = "[!@#\\$%\\^&\\*\\-_.]";
			for(int i = 0; i < s.length(); i++){
				if(!Character.isLetterOrDigit(s.charAt(i)) && !passwordRegex.contains(String.valueOf(s.charAt(i)))){
						incorrectLabel.setText(parameter + " Can Only Contain Letters, Digits, !, @, #, $, %, ^, &, *, -, or _");
						incorrectLabel.setOpacity(1);
						return true;
				}
			}
			break;
		default:
			for(int i = 0; i < s.length(); i++){
				if(!Character.isLetterOrDigit(s.charAt(i)) && s.charAt(i) != '_' && s.charAt(i) != '-'){
					incorrectLabel.setText(parameter + " Can Only Contain Letters, Digits, Hyphens, or Underscores");
					incorrectLabel.setOpacity(1);
					return true;
				}
			}
			break;
		}
		return false;
	}
}
