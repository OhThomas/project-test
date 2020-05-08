package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class Log {
	
	@FXML
	Label balance;
	@FXML
	Label balLabel;
	int currentAccount;
	@FXML
	TextField userInputBalance = new TextField();
	String currentBalance;
	/*
	 * Method for getting balance of checking. 
	 */
	@FXML
	private void checkingBalance(){
		currentAccount = 5;
		String cbal = Main.database.retrieveInfo(UserAccount.userName, currentAccount);
		String Current = "Checking balance:";
		balLabel.setText(Current);
		balance.setText(cbal);
	}
	/*
	 * Method for getting balance of savings
	 */
	@FXML
	private void savingsBalance(){
		currentAccount = 6;
		String sbal = Main.database.retrieveInfo(UserAccount.userName, currentAccount);
		balLabel.setText("Savings Balance:");
		balance.setText(sbal);
	}
	/*
	 * Method for withdrawing balance
	 */
	@FXML
	private void withdrawl(){
		float money;
		if(currentBalance != null && (currentAccount == 5 || currentAccount == 6)){
			if(currentAccount == 5 || currentAccount == 6){
				money = Float.valueOf(Main.database.retrieveInfo(UserAccount.userName, currentAccount));
				//System.out.println(currentBalance);
				money -= Float.valueOf(currentBalance);
				System.out.println(money);
				Main.database.updateInfo(UserAccount.userName, currentAccount, Float.toString(money));
				balance.setText(Float.toString(money));
				currentBalance = "";
				userInputBalance.setText("");
			}
			else{
				balLabel.setText("Please select checking or savings first");
			}
		}
	}
	
	/*
	 * Method for depositing balance
	 */
	@FXML
	private void deposit(){
		float money;
		if(currentBalance != null)
		{
			if(currentAccount == 5 || currentAccount == 6){
				money = Float.valueOf(Main.database.retrieveInfo(UserAccount.userName, currentAccount));
			//System.out.println(currentBalance);
				money += Float.valueOf(currentBalance);
				System.out.println(money);
				Main.database.updateInfo(UserAccount.userName, currentAccount, Float.toString(money));
				balance.setText(Float.toString(money));
				currentBalance = "";
				userInputBalance.setText("");
			}
			else{
				balLabel.setText("Please select checking or savings first");
			}
		}
	}
	
	@FXML
	private void userInput(KeyEvent event){
		currentBalance = userInputBalance.getText();
		System.out.println(currentBalance);
	}
	/*
	 * Method to go back to main menu
	 */
	@FXML
	private void menuButton(){
		Main.controller.changeScene("UserAccount.fxml");
	}
	@FXML
	private void transHistory(){
		Main.controller.changeScene("TransactionHist.fxml");
	}

}
