package application;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class TransactionHist {
	@FXML
	TextArea History = new TextArea();

	@FXML
	private void mainMenu(){
		Main.controller.changeScene("UserAccount.fxml");
	}
	@FXML
	private void back(){
		Main.controller.changeScene("Log.fxml");
	}
	@FXML
	private void check(){
		String currCheck = Main.database.accountHistory(UserAccount.userName, "CHECKINGS");
		if(currCheck != null)
			History.setText(currCheck);
		else
			History.setText("No History to Display for Checking");
	}
	@FXML
	private void savings(){
		String currSav = Main.database.accountHistory(UserAccount.userName, "SAVINGS");
		System.out.println("This is the value of currSav \"" + currSav + "\"");
		if(currSav != null)
			History.setText(currSav);
		else
			History.setText("No History to Display for Savings");
	}
}
