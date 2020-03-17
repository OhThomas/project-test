package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
/**
 * Goes through the database.properties file to read/write data (create server to store this information to 1 database instead of locally)
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
				String[] splitLine = line.split(",");
				String usernameLowercase = splitLine[0].toLowerCase();
				if(username.toLowerCase().equals(usernameLowercase.toString()) && password.equals(splitLine[1].toString())){
					UserAccount.name = splitLine[0];
					fileReader.close();
					return true;
				}
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
	 * @param i		Parameter in the data file you want to find (0=username,1=password,2=email,3=firstname,4=lastname)
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
			if(!file.exists())
				file.createNewFile();
			//encrypt data
			//fileWriter.append(username+","+password+","+email+","+firstName+","+lastName);
			fileWriter.append(EncryptionAES.encrypt(username+","+password+","+email+","+firstName+","+lastName));
			fileWriter.newLine();
			//end
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e1) {
			System.err.print("Could not write to database file.");
			e1.printStackTrace();
		}
	}
}
