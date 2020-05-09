package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Scanner;
/**
 * Goes through the database.properties file to read/write data. (create server to store this information to 1 database instead of locally)
 * 
 * @author Thomas Rader (vil203)
 */
public class Database {
	
	/**
	 * Tries to match the username and password parameters given with those in the database.properties file.
	 * 
	 * @param username	String of the username
	 * @param password 	String of the password
	 * @return			Returns true if the username and password match the information stored in the database
	 */
	public boolean loginAttempt(String username, String password){
		String fileName = "src/"+this.getClass().getPackage().getName()+"/database.properties";
		File file = new File(fileName);
		try {
			if(!file.exists()){
				file.createNewFile();
				return false;
			}
			BufferedReader fileReader = new BufferedReader(new FileReader(file));
			//decrypt
			String line = fileReader.readLine();
			while(line != null){
				line = EncryptionAES.decrypt(line);
				if(line.equals("USERNAME, ")){
					line = EncryptionAES.decrypt(fileReader.readLine());
					String[] splitLine = line.split(",");
					String usernameLowercase = splitLine[0].toLowerCase();
					if(username.toLowerCase().equals(usernameLowercase.toString()) && password.equals(splitLine[1].toString())){
						UserAccount.userName = splitLine[0];
						fileReader.close();
						return true;
					}
					line = fileReader.readLine();
				}
				else
					line = fileReader.readLine();
			}
			fileReader.close();
		} catch (FileNotFoundException e) {
			System.err.print("Could not find database file");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.print("Could not read from database file");
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Checks to see if the account parameter already exists.
	 * 
	 * @param s		String you want to compare the parameter to
	 * @param i		Parameter in the data file you want to find (0=username,1=password,2=email,3=firstname,4=lastname,5=checkings,6=savings)
	 * @return		Returns true if the parameter matches the string (if username already exists)
	 */
	public boolean accountExists(String s, int i){
		String fileName = "src/"+this.getClass().getPackage().getName()+"/database.properties";
		File file = new File(fileName);
		try {
			if(!file.exists()){
				file.createNewFile();
				return false;
			}
			BufferedReader fileReader = new BufferedReader(new FileReader(file));
			//decrypt
			String line = fileReader.readLine();
			while(line != null){
				line = EncryptionAES.decrypt(line);
				if(line.equals("USERNAME, ")){
					line = EncryptionAES.decrypt(fileReader.readLine());
					String[] splitLine = line.split(",");
					//if i == 0 to not let the user get a username/email that is the same but different case sensitivity
					if(i == 0 || i == 2){
						s = s.toLowerCase();
						splitLine[i] = splitLine[i].toLowerCase();
					}
					if(s.equals(splitLine[i].toString())){
						fileReader.close();
						return true;
					}
					line = fileReader.readLine();
				}
				else
					line = fileReader.readLine();
			}
			fileReader.close();
		} catch (FileNotFoundException e) {
			System.err.print("Could not find database file");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.print("Could not read from database file");
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Writes all the information needed for a new account to the database.properties file.
	 * 
	 * @param username		username of the account
	 * @param password		password of the account
	 * @param email			email of the account holder
	 * @param firstName		first name of the account holder
	 * @param lastName		last name of the account holder
	 */
	public void createAccount(String username, String password, String email, String firstName, String lastName){
		String fileName = "src/"+this.getClass().getPackage().getName()+"/database.properties";
		File file = new File(fileName);
		try {
			BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file,true));
			if(!file.exists()){
				file.createNewFile();
			}
			//encrypt data
			//fileWriter.append(username+","+password+","+email+","+firstName+","+lastName);
			fileWriter.append(EncryptionAES.encrypt("USERNAME, "));
			fileWriter.newLine();
			fileWriter.append(EncryptionAES.encrypt(username+","+password+","+email+","+firstName+","+lastName+",0,0"));
			fileWriter.newLine();
			fileWriter.append(EncryptionAES.encrypt("CHECKINGSHISTORY, "));
			fileWriter.newLine();
			fileWriter.append(EncryptionAES.encrypt("SAVINGSHISTORY, "));
			fileWriter.newLine();
			//end
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e1) {
			System.err.print("Could not write to database file.");
			e1.printStackTrace();
		}
	}
	
	/**
	 * Retrieves information from an account using their username in form of String. (CONVERT TO DOUBLE IF RETREIVING CHECKING/SHARING ONCE DONE)
	 * 
	 * @param username		Username of the account to lookup(you can use UserAccount.userName)
	 * @param param			Parameter you want to receive (0=username,1=password,2=email,3=firstname,4=lastname,5=checkings,6=savings)
	 * @return				returns a String of the parameter you want to receive
	 */
	public String retrieveInfo(String username, int param){
		String fileName = "src/"+this.getClass().getPackage().getName()+"/database.properties";
		File file = new File(fileName);
		try {
			if(!file.exists()){
				file.createNewFile();
				return "";
			}
			BufferedReader fileReader = new BufferedReader(new FileReader(file));
			//decrypt
			String line = fileReader.readLine();
			while(line != null){
				line = EncryptionAES.decrypt(line);
				if(line.equals("USERNAME, ")){
					line = EncryptionAES.decrypt(fileReader.readLine());
					String[] splitLine = line.split(",");
					String splitLineUserName = splitLine[0].toLowerCase();
					if(username.toLowerCase().equals(splitLineUserName.toString())){
						fileReader.close();
						return splitLine[param];
					}
					line = fileReader.readLine();
				}
				else
					line = fileReader.readLine();
			}
			fileReader.close();
		} catch (FileNotFoundException e) {
			System.err.print("Could not find database file");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.print("Could not read from database file");
			e.printStackTrace();
		}
		return "";
	}
	/**
	 * Updates history on an accounts checking/savings timestamping old and new values to the database.properties file.
	 * 
	 * @param historyType		The history type you want to use
	 * @param oldValue			The old value of the checking/savings balance
	 * @param newValue			The new value of the checking/savings balance
	 * @param input				Scanner passed in to continue writing to file without having to close and search again
	 * @param fileWriter		BufferedWriter passed in to continue writing to file without having to close and search again
	 */
	private void updateHistory(String historyType, String oldValue, String newValue, Scanner input, BufferedWriter fileWriter){
		//checks to make sure method input is correct
		if(historyType.toUpperCase().trim().equals("CHECKING"))
			historyType = "CHECKINGS";
		else if(historyType.toUpperCase().trim().equals("SAVING"))
			historyType = "SAVINGS";
		else if(!(historyType.toUpperCase().trim().equals("CHECKINGS") || historyType.toUpperCase().trim().equals("SAVINGS")))
			return;
		String line = "";
		String decryptedLine = "";
		try {
			while(input.hasNextLine()){
				line = input.nextLine();
				decryptedLine = EncryptionAES.decrypt(line);
				//System.out.println("FAKEhere = "+decryptedLine + " actual = "+historyType);
				if(decryptedLine.equals(historyType.toUpperCase().trim()+ "HISTORY, ")){
					fileWriter.write(EncryptionAES.encrypt(historyType.toUpperCase().trim()+"HISTORY, "));
					fileWriter.newLine();
					Date date = new Date();
					if(Double.valueOf(oldValue) < Double.valueOf(newValue)){
						String output = "$"+oldValue+ "+$"+String.valueOf(Math.abs(Double.valueOf(oldValue)-Double.valueOf(newValue)))+"=$"+newValue;
						fileWriter.write(EncryptionAES.encrypt(String.format("%-44s %s", output,new Timestamp(date.getTime()))));
					}
					else{
						String output = "$"+oldValue+ "-$"+String.valueOf(Math.abs(Double.valueOf(oldValue)-Double.valueOf(newValue)))+"=$"+newValue;
						fileWriter.write(EncryptionAES.encrypt(String.format("%-44s %s", output,new Timestamp(date.getTime()))));
					}
					fileWriter.newLine();
					//while(input.hasNextLine()){
						//line = input.nextLine();
						//fileWriter.write(line);
						//fileWriter.newLine();
					//}
					//return;
				}
				else if(line.equals("USERNAME, ")){
					//end
//					fileWriter.write(line);//maybe delete
//					fileWriter.newLine();
					return;
				}
				else{
					fileWriter.write(line);
					fileWriter.newLine();
				}
				//System.out.println("line = "+line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Updates information from an account using their username and the updated parameter. (STORE WHAT YOU WANT THE END RESULT TO BE AS STRING)
	 * 
	 * @param username		Username of the account to lookup(you can use UserAccount.userName)
	 * @param param			Parameter you want to update (0=username,1=password,2=email,3=firstname,4=lastname,5=checkings,6=savings)
	 * @param update		The updated value you want to store
	 */
	public void updateInfo(String username, int param, String update){
		String fileName = "src/"+this.getClass().getPackage().getName()+"/database.properties";
		String fileNamee = "src/"+this.getClass().getPackage().getName()+"/databasee.properties";
		File file = new File(fileName);
		File filee = new File(fileNamee);
		try {
			if(!file.exists()){
				file.createNewFile();
				return;
			}
			if(filee.exists())
				filee.delete();
			Scanner input = new Scanner(file);
			BufferedWriter fileWriter = new BufferedWriter(new FileWriter(filee,true));
			//decrypt
			String line = input.nextLine();
			String decryptedLine = "";
			while(input.hasNextLine()){
				decryptedLine = EncryptionAES.decrypt(line);
				if(decryptedLine.equals("USERNAME, ")){
					fileWriter.write(EncryptionAES.encrypt("USERNAME, "));
					fileWriter.newLine();
					decryptedLine = EncryptionAES.decrypt(input.nextLine());
					String[] splitLine = decryptedLine.split(",");
					String usernameLowercase = splitLine[0].toLowerCase();
					if(username.toLowerCase().equals(usernameLowercase.toString())){
						switch(param){
						case 0:
							fileWriter.write(EncryptionAES.encrypt(update+","+splitLine[1]+","+splitLine[2]+","+splitLine[3]+","+splitLine[4]+","+splitLine[5]+","+splitLine[6]));
							fileWriter.newLine();
							break;
						case 1:
							fileWriter.write(EncryptionAES.encrypt(splitLine[0]+","+update+","+splitLine[2]+","+splitLine[3]+","+splitLine[4]+","+splitLine[5]+","+splitLine[6]));
							fileWriter.newLine();
							break;
						case 2:
							fileWriter.write(EncryptionAES.encrypt(splitLine[0]+","+splitLine[1]+","+update+","+splitLine[3]+","+splitLine[4]+","+splitLine[5]+","+splitLine[6]));
							fileWriter.newLine();
							break;
						case 3:
							fileWriter.write(EncryptionAES.encrypt(splitLine[0]+","+splitLine[1]+","+splitLine[2]+","+update+","+splitLine[4]+","+splitLine[5]+","+splitLine[6]));
							fileWriter.newLine();
							break;
						case 4:
							fileWriter.write(EncryptionAES.encrypt(splitLine[0]+","+splitLine[1]+","+splitLine[2]+","+splitLine[3]+","+update+","+splitLine[5]+","+splitLine[6]));
							fileWriter.newLine();
							break;
						case 6:
							fileWriter.write(EncryptionAES.encrypt(splitLine[0]+","+splitLine[1]+","+splitLine[2]+","+splitLine[3]+","+splitLine[4]+","+splitLine[5]+","+update));
							fileWriter.newLine();
							updateHistory("SAVINGS",splitLine[6],update,input,fileWriter);
							break;
						default://(case 5)
							fileWriter.write(EncryptionAES.encrypt(splitLine[0]+","+splitLine[1]+","+splitLine[2]+","+splitLine[3]+","+splitLine[4]+","+update+","+splitLine[6]));
							fileWriter.newLine();
							updateHistory("CHECKINGS",splitLine[5],update,input,fileWriter);
							break;
						}
					}
					else{
						fileWriter.write(line);
						fileWriter.newLine();
					}
					if(input.hasNextLine())
						line = input.nextLine();
				}
				else{
					fileWriter.write(line);
					fileWriter.newLine();
					line = input.nextLine();
				}
			}
			input.close();
			fileWriter.close();
			file.delete();
			filee.renameTo(new File("src/"+this.getClass().getPackage().getName()+"/database.properties"));
		} catch (FileNotFoundException e) {
			System.err.print("Could not find database file");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.print("Could not read from database file");
			e.printStackTrace();
		}
		return;
	}
	
	/**
	 * Method to return checking or savings history on an account.
	 * 
	 * @param username			Username of the checking/savings info you want to look up
	 * @param historyType		checking or savings (enter String:"CHECKINGS" for checking account history)
	 * @return returnString		returns String of checking/savings history
	 */
	public String accountHistory(String username, String historyType){
		String fileName = "src/"+this.getClass().getPackage().getName()+"/database.properties";
		File file = new File(fileName);
		String returnString = "";
		//checks to make sure method input is correct
		if(historyType.toUpperCase().trim().equals("CHECKING"))
			historyType = "CHECKINGS";
		else if(historyType.toUpperCase().trim().equals("SAVING"))
			historyType = "SAVINGS";
		else if(!(historyType.toUpperCase().trim().equals("CHECKINGS") || historyType.toUpperCase().trim().equals("SAVINGS")))
			return returnString;
		try {
			if(!file.exists()){
				file.createNewFile();
				return returnString;
			}
			BufferedReader fileReader = new BufferedReader(new FileReader(file));
			//decrypt
			String line = fileReader.readLine();
			while(line != null){
				line = EncryptionAES.decrypt(line);
				if(line.equals("USERNAME, ")){
					line = EncryptionAES.decrypt(fileReader.readLine());
					String[] splitLine = line.split(",");
					String splitLineUserName = splitLine[0].toLowerCase();
					if(username.toLowerCase().equals(splitLineUserName.toString())){
						//do stuff here
						line = fileReader.readLine();
						while(line != null){//In username
							line = EncryptionAES.decrypt(line);
							if(line.equals(historyType.toUpperCase().trim()+ "HISTORY, ")){
								line = fileReader.readLine();
								while(line != null){//In history
									line = EncryptionAES.decrypt(line);
									//at next info we don't need
									if(line.equals("USERNAME, ") || line.equals("CHECKINGSHISTORY, ") || line.equals("SAVINGSHISTORY, ")){
										fileReader.close();
										return returnString;
									}
									returnString = returnString + "\n" + line;
									line = fileReader.readLine();
								}
								fileReader.close();
							}
							line = fileReader.readLine();
						}
						fileReader.close();
					}
				}
				line = fileReader.readLine();
			}
			fileReader.close();
		}catch (FileNotFoundException e) {
			System.err.print("Could not find database file");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.print("Could not read from database file");
			e.printStackTrace();
		}
		return returnString;
	}
}
