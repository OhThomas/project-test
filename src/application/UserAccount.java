package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
/**
 * Controller for the UserAccount fxml file.
 * 
 * @author [blank]
 */
public class UserAccount  implements Initializable{

	@FXML
	Label nameLabel;
	public static String userName = "";
	
	/**
	 * Method to test checking/savings on database.
	 */
	@FXML
	private void databaseTest(){
		Main.database.updateInfo(userName, 5, "100.00");
		System.out.println(Main.database.accountHistory(userName, "CHECKINGS"));
	}
	
	/**
	 * Method to react to the user clicking the nameLabel at the top of the account.
	 */
	@FXML
	private void changeName(){
		System.out.println(userName);
		//nameLabel.setText("G");
	}
	
	/**
	 * Method to logout the user and return to the Login.fxml
	 */
	@FXML
	private void logout(){
		userName = "";
		//Reset variables
		Main.controller.changeScene("Login.fxml");
	}
	
	/**
	 * Initializes the variables before the scene is to this fxml file.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		nameLabel.setText(userName);
		
	}
	
	/**
	 * Getter for nameLabel.
	 * @return	nameLabel
	 */
	public Label getNameLabel() {
		return nameLabel;
	}
	
	/**
	 * Setter for name Label.
	 * 
	 * @param nameLabel
	 */
	public void setNameLabel(Label nameLabel) {
		this.nameLabel = nameLabel;
	}
	
	/**
	 * Getter for name.
	 * 
	 * @return	name
	 */
	public String getUserName() {
		return userName;
	}
	
	/**
	 * Setter for name.
	 * 
	 * @param name
	 */
	public void setUserName(String userName) {
		UserAccount.userName = userName;
	}
}
