package SimpleBank;

import java.io.PrintWriter;
import java.util.Scanner;

public class BankAccount {

	//Fields
	private String firstName;
	private String lastName;
	private String address;
	private double balance;
	private String userName;
	private String hashOfPIN;
	
	//Constructor
	public BankAccount(String firstName, String lastName, String address, String userName, String hashOfPIN) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.userName = userName;
		this.hashOfPIN = hashOfPIN;
		
	}
	
	//getter
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getAddress() {
		return address;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public String getHashOfPIN() {
		return hashOfPIN;
	}
	
	//setter
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public void setHashOfPIN(String hashOfPIN) {
		this.hashOfPIN = hashOfPIN;
	}	
	
	public void setBalance(double amount) {
		balance += amount;
	}
	
	public void withdraw(double amount) {
		balance -= amount;
	}
	
	public void deposit(double amount) {
		balance += amount;
	}
	
	
	public BankAccount(Scanner in) {
		System.out.println( getFirstName() + '|' + getLastName() + '|' + getAddress() + '|' + getUserName() + '|' + getHashOfPIN() + '|' + getBalance());
	}
	
	public static boolean isValidUserName(String userName) {
		if(userName == null || userName.trim().length() == 0) {
			return false;
		}
		
		for(int i = 0; i < userName.length(); i++) {
			char c = userName.charAt(i);
			if( !Character.isLetter(c) ) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isValidPIN(String pin) {
		if(pin.length() != 4) {
			System.out.println("It has to be 4 digits!");
			return false;
		}
		
		for(int i = 0; i < pin.length(); i++) {
			char c = pin.charAt(i);
			if( !Character.isDigit(c)) {
				System.out.println("Digits only! ");
				return false;
			}
		}
			
		return true;
	}
	
	public static String hashOf(String pin) {
		if(pin.length() !=  4) {
			return null;
		}
		
		String hash = "";
		for(int i = 0; i < pin.length(); i++) {
			int digit = (int)pin.charAt(i);
			hash += "" + (digit | digit % 3);
			hash += "" + (((digit + i) % 5) * digit) + i;
			hash += "" + digit % 2;
		}
		
		return hash;
	}
	
	public void writeToFile(PrintWriter out) {
		out.println(getFirstName() + '|' + getLastName() + '|' + getAddress() + '|' + getUserName() + '|' + getHashOfPIN() + '|' + getBalance());
	}
	
	public void readFromFile(Scanner in) {
		
		while(in.hasNextLine()) {
			String line = in.next();
			String[] tokens = line.trim().split("|");
			
			setFirstName(tokens[0]);
			setLastName(tokens[1]);
			setAddress(tokens[2]);
			setUserName(tokens[3]);
			setHashOfPIN(tokens[4]);
			setBalance(Double.parseDouble(tokens[5]));
			
			System.out.println(tokens);

		}
	}
}
