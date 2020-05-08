package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
/**
 * Controls changing the scene, opening windows, & calculator controls (maybe change).
 * 
 * @author Thomas Rader (vil203)
 */
public class Controller {
	@FXML
	private Label result;
	private Model model = new Model();
	private long operand1 = 0;
	private long operand2 = 0;
	private String operator = "";
	private boolean start = true;
	/**
	 * Changes the fxml file to the one provided in the parameter.
	 * 
	 * @param url		String of the fxml file you want to change the scene to (INCLUDE .fxml)
	 */
	public void changeScene(String url){
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/application/"+url));
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Main.primaryStage.setScene(scene);
			Main.primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Method for main menu button
	 */
	@FXML
	private void main_menu(){
		Main.controller.changeScene("UserAccount.fxml");
	}
	/**
	 * Changes the scene and changes the window size accordingly. (method above does not change window size)
	 * 
	 * @param event		Event used to get the window
	 * @param url		Name of the fxml file you want to change the scene to (INCLUDE .fxml)
	 */
	public void changeScene2(ActionEvent event, String url){
		Parent tableViewParent;
		try {
			tableViewParent = FXMLLoader.load(getClass().getResource("/application/"+url));
	        Scene tableViewScene = new Scene(tableViewParent);
	        
	        //This line gets the Stage information
	        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
	        
	        window.setScene(tableViewScene);
	        window.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Processes operand buttons.
	 * @param event		Button pressed
	 */
	@FXML
	public void processOperands(ActionEvent event){
		if (start){
			result.setText("");
			start = false;
		}
		String value = ((Button)event.getSource()).getText();
		result.setText(result.getText()+value);
	}
	
	/**
	 * Processes operator buttons.
	 * @param event		Button pressed
	 */
	@FXML
	public void processOperators(ActionEvent event){
		String value = ((Button)event.getSource()).getText();
		if(!value.contentEquals("=")){
			if(!operator.isEmpty())
				return;
			operator = value;
			operand1 = Long.parseLong(result.getText());
			result.setText("");
		}
		else{
			if(operator.isEmpty())
				return;
			operand2 = Long.parseLong(result.getText());
			float output = model.calculate(operand1, operand2, operator);
			result.setText(String.valueOf(output));
			if(!result.getText().isEmpty())
				operand1 = (long)output;
			operator = "";
			start = true;
		}
	}
}
