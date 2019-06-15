package SimpleBank;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class BankMenu {

	static Scanner in = new Scanner(System.in);
	
	public static void landingMenu(Bank bank) throws Exception{
		
		boolean exit = false;
		
		System.out.println("-----------------------------------");
		System.out.println("1. Log in ");
		System.out.println("2. Sign up ");
		System.out.println("3. Terminate account");
		System.out.println("4. Save Bank account list to a file ");
		System.out.println("5. Load Bank account list from a file ");
		System.out.println("6. Exit");
		System.out.println("-----------------------------------");
		
		String input = in.nextLine();
		
		while(!exit) {
			switch(input.trim()) {
			
			case "1":
				loginMenu(bank);
				break;
				
			case "2":
				signupMenu(bank);
				break;
				
			case "3":
				terminateMenu(bank);
				break;
				
			case "4":
				saveToFileMenu(bank);
				break;
				
			case "5":
				loadFromFileMenu(bank);
				break;
				
			case "6":
				exit = true;
				break;
				
			default:
				System.out.println("Invalid input");
				break;
			}									
		}		
	}
	
	public static void loginMenu(Bank bank) throws Exception {
		
		System.out.println("Enter you username: ");
		String userName = in.next();
		
		System.out.println("Enter you PIN:");
		String pin = in.next();
		
		String hashOfPIN = BankAccount.hashOf(pin);
		
		if(bank.accountExist(userName) && bank.pinMatched(hashOfPIN)) {
			System.out.println("Login successfully");
			System.out.println("Going to the main Menu");
			mainMenu(bank.getAccount(userName, hashOfPIN));
		}else {
			System.out.println("Login failed, please check user name and pin again");
			landingMenu(bank);
		}
	}
	
	public static void signupMenu(Bank bank) throws Exception {
		
		System.out.println("Enter you first name:");
		String firstName = in.nextLine();
		
		System.out.println("Enter you last name:");
		String lastName = in.nextLine();
		
		System.out.println("Enter you Address:");
		String address = in.nextLine();
		
		System.out.println("Enter you username(letter only):");
		String userName = in.nextLine();
		
		if(bank.accountExist(userName)) {
			System.out.print("User name is token, try to use a different one ");
			return;
		}
		
		if(!BankAccount.isValidUserName(userName)) {
			System.out.println("Invalid user name ");
			return;
		}
		
		System.out.println("Enter you PIN(4 digits):");
		String pin = in.nextLine();
		if(!BankAccount.isValidPIN(pin)) {
			System.out.println("Invalid PIN, 4 digits only!");
			return;
		}
		
		String hashOfPIN = BankAccount.hashOf(pin);
		
		if(BankAccount.isValidUserName(userName)&& BankAccount.isValidPIN(pin)) {
			System.out.println(firstName + '|' + lastName + '|' + address + '|' + userName
					+ '|' + hashOfPIN);
			bank.registerAccount(new BankAccount(firstName, lastName, address, userName, hashOfPIN));
		}else {
			System.out.println("Account not added, please check information agian!");
		}
		landingMenu(bank);
	}
	
	public static void terminateMenu(Bank bank) throws Exception {
		
		System.out.println("Enter you username: ");
		String userName = in.nextLine();
		
		System.out.println("Enter you PIN:");
		String pin = in.nextLine();
		
		String hashOfPIN = BankAccount.hashOf(pin);
		
		if(bank.accountExist(userName) && bank.pinMatched(hashOfPIN)) {
			System.out.println("Are you sure you want to terminate your account? [Y/N]");
			String answer = in.nextLine().toUpperCase();
			
			switch(answer) {
			case "Y":
				bank.terminateAccount(userName, hashOfPIN);
				break;
				
			case "N":
				break;
			}
		}else {
			System.out.println("Username or PIN not correct!");
		}
		
		landingMenu(bank);
	}
	
	public static void saveToFileMenu(Bank bank) throws Exception {
		
		System.out.println("Enter the file name: ");
		String fileName = in.nextLine();
		File file = new File(fileName);
		
		System.out.println("Saving file.......");
		PrintWriter out = new PrintWriter(file);
		bank.writeToFile(out);
		
		landingMenu(bank);
		
	}
	
	public static void loadFromFileMenu(Bank bank) throws Exception {
		
		System.out.println("Enter file name: ");
		String fileName = in.nextLine();
		File file = new File(fileName);
		Scanner input = new Scanner(file);
		
		if(file.exists()) {			
			System.out.println("Loading file......");
			while(input.hasNext()) {
				bank.readFromFile(input);
			}
		}else {
			System.out.println("File not found!");
		}
		
		landingMenu(bank);
	}

	public static void mainMenu(BankAccount ba) {
		
		boolean exit = false;
		
		while(!exit) {
			System.out.println("-----------------------------------");
			System.out.println(ba.getFirstName() + '|' + ba.getLastName() + '|' + ba.getAddress() + '|' +
					ba.getUserName() + '|' + ba.getHashOfPIN() + '|' + ba.getBalance());
			System.out.println("-----------------------------------");
			System.out.println("1. Deposit Moeny");
			System.out.println("2. Withdraw Moeny");
			System.out.println("3. Account Details");
			System.out.println("4. Exit");
			System.out.println("-----------------------------------");
			
			String input = in.nextLine();
			
			switch(input.trim()) {
			case "1":
				depositMenu(ba);
				break;
				
			case "2":
				withdrawMenu(ba);
				break;
				
			case "3":
				accountMenu(ba);
				break;
				
			case "4":
				exit = true;
				break;
			}
		}
	}
	
	public static void depositMenu(BankAccount ba) {
		
		System.out.println("Enter amount to deposit: ");
		double amount = in.nextDouble();
		if(amount >= 0) {
			ba.deposit(amount);
		}else {
			System.out.println("Need to be positive number!");
		}
	}
	
	public static void withdrawMenu(BankAccount ba) {
		
		System.out.println("Enter amount to withdraw: ");
		double amount = in.nextDouble();
		if(amount >= 0 && ba.getBalance() >= amount) {
			ba.withdraw(amount);
		}else {
			System.out.println("Insufficient funds!");
		}
	}
	
	public static void accountMenu(BankAccount ba) {
		
		boolean exit = false;
		
		while(!exit) {
			System.out.println("-----------------------------------");
			System.out.println("1. Change First Name ");
			System.out.println("2. Change Last name ");
			System.out.println("3. Change Address ");
			System.out.println("4. Change username ");
			System.out.println("5. Change PIN ");
			System.out.println("6. Exit");
			System.out.println("-----------------------------------");
		
			String input = in.nextLine();
			
			switch(input) {
			case "1":
				changeFirstNameMenu(ba);
				break;
			
			case "2":
				changeLastNameMenu(ba);
				break;
				
			case "3":
				changeAddressMenu(ba);
				break;
				
			case "4":
				changeUserNameMenu(ba);
				break;
				
			case "5":
				changePINMenu(ba);
				break;
				
			case "6":
				exit = true;
				break;
			}
		}
	}

	private static void changePINMenu(BankAccount ba) {
		
		String newPIN;
		System.out.println("Please enter your new PIN(4 digits only): ");
		newPIN = in.nextLine();
		while( !BankAccount.isValidPIN(newPIN)) {
			System.out.println("Invalid PIN! Please enter again");
			newPIN = in.nextLine();
		}
		String hashOfPIN= BankAccount.hashOf(newPIN);
		ba.setHashOfPIN(hashOfPIN);
	}

	private static void changeUserNameMenu(BankAccount ba) {
		
		System.out.println("Your current user name is " + ba.getUserName());
		
		System.out.println("Ener your new user name: ");
		String newUserName = in.nextLine();
		
		while( !BankAccount.isValidUserName(newUserName)) {
			System.out.println("Invalid username! Please enter again");
			newUserName = in.nextLine();	
		}
		
		ba.setUserName(newUserName);
	}

	private static void changeAddressMenu(BankAccount ba) {
		System.out.println("Your current address is " + ba.getUserName());
		
		System.out.println("Ener your new address: ");
		String newAddress = in.nextLine();
		
		ba.setUserName(newAddress);		
	}

	private static void changeLastNameMenu(BankAccount ba) {
		
		System.out.println("Your current last name is " + ba.getUserName());
		
		System.out.println("Ener your new last name: ");
		String newLastName = in.nextLine();
		
		ba.setUserName(newLastName);
		
	}

	private static void changeFirstNameMenu(BankAccount ba) {
		
		System.out.println("Your current first name is " + ba.getUserName());
		
		System.out.println("Ener your new first name: ");
		String newFirstName = in.nextLine();
		
		ba.setUserName(newFirstName);
	}
}
