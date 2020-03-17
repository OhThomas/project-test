/*hi again hi*/

package application;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
/**
 * Controller for the Login.fxml file.
 * 
 * @author Thomas Rader (vil203)
 */
public class Login implements Initializable{
	@FXML
	Label incorrectLabel = new Label();
	@FXML
	TextField usernameTextField = new TextField();
	@FXML
	PasswordField passwordTextField = new PasswordField();
	
	/**
	 * Processes the username input everytime a key is released.
	 * 
	 * @param event			Key pressed
	 */
	@FXML
	public void processUsername(KeyEvent event){
		System.out.println(usernameTextField.getText());
	}
	
	/**
	 * Processes the password input everytime a key is released.
	 * 
	 * @param event			Key pressed
	 */
	@FXML
	public void processPassword(KeyEvent event){
		System.out.println(passwordTextField.getText());
	}
	
	/**
	 * Attempts to log the user in after they press the Log In button.
	 * 
	 * @param event			Log in button pressed
	 */
	@FXML
	public void processLogin(ActionEvent event){
		if(isAccount()){
			System.out.println("HERE");
//			Main.userAccount.changeScene("UserAccount.fxml");
			
//			Main.primaryStage.setScene(Main.userScene);
//			Main.userAccount.nameLabel.setText(usernameTextField.getText());
//			System.out.println(Main.userAccount.nameLabel.getText());
//			controller.changeScene2(event, "UserAccount.fxml");
			Main.controller.changeScene("UserAccount.fxml");
			incorrectLabel.setOpacity(0);
			//change main fxml to account
		}
		else{
			incorrectLabel.setOpacity(1);
			//print incorrect username/password
		}
		System.out.println(passwordTextField.getText());
	}
	
	/**
	 * Attempts to log the user in after they release the Enter key.
	 */
	public void processLogin(){
		if(isAccount()){
			Main.controller.changeScene("UserAccount.fxml");
			incorrectLabel.setOpacity(0);
		}
		else{
			incorrectLabel.setOpacity(1);
		}
		System.out.println(passwordTextField.getText());
	}
	
	/**
	 * Changes the scene to the CreateAccount.fxml file.
	 */
	@FXML
	public void processCreateAccount(){
		//change window
		incorrectLabel.setOpacity(0);
		Main.controller.changeScene("CreateAccount.fxml");
	}
	
	/**
	 * Uses username and password in the textfield to attempt to login.
	 * 
	 * @return		returns true if the account is found with the appropriate login information
	 */
	private boolean isAccount(){
		return Main.database.loginAttempt(usernameTextField.getText(), passwordTextField.getText());
	}
	
	/**
	 * Initializes variables and event handlers (Enter key pressed) before the scene is set up for this fxml file.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		usernameTextField.setOnKeyPressed(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent keyEvent) {
				if(keyEvent.getCode() == KeyCode.ENTER){
					processLogin();
				}
			}
		});
		passwordTextField.setOnKeyPressed(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent keyEvent) {
				if(keyEvent.getCode() == KeyCode.ENTER){
					processLogin();
				}
			}
		});
	}
}
