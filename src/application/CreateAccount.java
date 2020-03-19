package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
/**
 * Controller for the CreateAccount.fxml file.
 * 
 * @author Thomas Rader (vil203)
 */
public class CreateAccount implements Initializable{
	
	InputValidation inputValidation = new InputValidation();
	@FXML
	Label incorrectLabel = new Label();
	@FXML
	TextField usernameTextField = new TextField();
	@FXML
	PasswordField passwordTextField = new PasswordField();
	@FXML
	TextField emailTextField = new TextField();
	@FXML
	TextField firstNameTextField = new TextField();
	@FXML
	TextField lastNameTextField = new TextField();
	@FXML
	Button createAccountButton = new Button();
	
	/**
	 * Processes username input everytime they release a key.
	 * 
	 * @param event		Key pressed
	 */
	@FXML
	public void processUsername(KeyEvent event){
		System.out.println(usernameTextField.getText());
	}
	
	/**
	 * Processes password input everytime they release a key.
	 * 
	 * @param event		Key pressed
	 */
	@FXML
	public void processPassword(KeyEvent event){
		System.out.println(passwordTextField.getText());
	}
	
	/**
	 * Processes email input everytime they release a key.
	 * 
	 * @param event		Key pressed
	 */
	@FXML
	public void processEmail(KeyEvent event){
		System.out.println(emailTextField.getText());
	}
	
	/**
	 * Processes first name input everytime they release a key.
	 * 
	 * @param event		Key pressed
	 */
	@FXML
	public void processFirstName(KeyEvent event){
		System.out.println(firstNameTextField.getText());
	}

	/**
	 * Processes last name input everytime they release a key.
	 * 
	 * @param event		Key pressed
	 */
	@FXML
	public void processLastName(KeyEvent event){
		System.out.println(lastNameTextField.getText());
	}

	/**
	 * Creates an account whenever they press the text or press enter (determined by other methods/handlers).
	 * 
	 * @param event		Text clicked
	 */
	public void processCreateAccount(){
		if(usernameTextField.getText().isEmpty()){
			incorrectLabel.setText("Blank Username");
			incorrectLabel.setOpacity(1);
		}
		else if(inputValidation.commaCheck(usernameTextField.getText(),"Username", incorrectLabel)){
			
		}
		else if(inputValidation.lengthCheck(usernameTextField.getText(),"Username", incorrectLabel)){
			
		}
		else if(inputValidation.specialCharacterCheck(usernameTextField.getText(),"Username", incorrectLabel)){
			
		}
		else if(passwordTextField.getText().isEmpty()){
			incorrectLabel.setText("Blank Password");
			incorrectLabel.setOpacity(1);
		}
		else if(inputValidation.passwordCheck(passwordTextField.getText(),incorrectLabel)){
			
		}
		else if(inputValidation.commaCheck(passwordTextField.getText(),"Password", incorrectLabel)){
			
		}
		else if(inputValidation.lengthCheck(passwordTextField.getText(),"Password", incorrectLabel)){
			
		}
		else if(inputValidation.specialCharacterCheck(passwordTextField.getText(),"Password", incorrectLabel)){
			
		}
		else if(emailTextField.getText().isEmpty()){
			incorrectLabel.setText("Blank Email");
			incorrectLabel.setOpacity(1);
		}
		else if(inputValidation.emailCheck(emailTextField.getText(),incorrectLabel)){
			
		}
		else if(inputValidation.commaCheck(emailTextField.getText(),"Email", incorrectLabel)){
			
		}
		else if(inputValidation.lengthCheck(emailTextField.getText(),"Email", incorrectLabel)){
			
		}
		else if(inputValidation.specialCharacterCheck(emailTextField.getText(),"Email", incorrectLabel)){
			
		}
		else if(firstNameTextField.getText().isEmpty()){
			incorrectLabel.setText("Blank First Name");
			incorrectLabel.setOpacity(1);
		}
		else if(inputValidation.commaCheck(firstNameTextField.getText(),"First Name", incorrectLabel)){
			
		}
		else if(inputValidation.lengthCheck(firstNameTextField.getText(),"First Name", incorrectLabel)){
			
		}
		else if(inputValidation.specialCharacterCheck(firstNameTextField.getText(),"First Name", incorrectLabel)){
			
		}
		else if(lastNameTextField.getText().isEmpty()){
			incorrectLabel.setText("Blank Last Name");
			incorrectLabel.setOpacity(1);
		}
		else if(inputValidation.commaCheck(lastNameTextField.getText(),"Last Name", incorrectLabel)){
			
		}
		else if(inputValidation.lengthCheck(lastNameTextField.getText(),"Last Name", incorrectLabel)){
			
		}
		else if(inputValidation.specialCharacterCheck(lastNameTextField.getText(),"Last Name", incorrectLabel)){
			
		}
		else if(Main.database.accountExists(usernameTextField.getText(),0)){
			incorrectLabel.setText("Username Already Exists");
			incorrectLabel.setOpacity(1);
		}
		else if(Main.database.accountExists(emailTextField.getText(),2)){
			incorrectLabel.setText("Email Already In Use");
			incorrectLabel.setOpacity(1);
		}
		//CHECK FOR ALL ERRORS [check if username/email already exist in database]
		else{
			incorrectLabel.setOpacity(0);
			Main.database.createAccount(usernameTextField.getText(), passwordTextField.getText(), emailTextField.getText(), firstNameTextField.getText(), lastNameTextField.getText());
			Main.controller.changeScene("Login.fxml");
		}
	}


	/**
	 * Creates an account whenever they press the text.
	 * 
	 * @param event		Text clicked
	 */
	@FXML
	public void processCreateAccount(ActionEvent event){
		processCreateAccount();
	}
	
	/**
	 * Goes back to the Login.fxml screen.
	 */
	@FXML
	public void processBack(){
		incorrectLabel.setOpacity(0);
		Main.controller.changeScene("Login.fxml");
	}
	/**
	 * Initializes variables before the changed scene takes place.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		usernameTextField.setOnKeyPressed(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent keyEvent) {
				if(keyEvent.getCode() == KeyCode.ENTER){
					processCreateAccount();
				}
			}
		});
		passwordTextField.setOnKeyPressed(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent keyEvent) {
				if(keyEvent.getCode() == KeyCode.ENTER){
					processCreateAccount();
				}
			}
		});
		emailTextField.setOnKeyPressed(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent keyEvent) {
				if(keyEvent.getCode() == KeyCode.ENTER){
					processCreateAccount();
				}
			}
		});
		firstNameTextField.setOnKeyPressed(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent keyEvent) {
				if(keyEvent.getCode() == KeyCode.ENTER){
					processCreateAccount();
				}
			}
		});
		lastNameTextField.setOnKeyPressed(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent keyEvent) {
				if(keyEvent.getCode() == KeyCode.ENTER){
					processCreateAccount();
				}
			}
		});
		createAccountButton.setOnKeyPressed(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent keyEvent) {
				if(keyEvent.getCode() == KeyCode.ENTER){
					processCreateAccount();
				}
			}
		});
	}
}
