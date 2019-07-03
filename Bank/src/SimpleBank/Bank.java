package SimpleBank;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Bank {

	private ArrayList<BankAccount> accounts = new ArrayList<BankAccount>();
	private String names;
	
	public Bank(ArrayList<BankAccount> accounts, String names) {
		this.accounts = accounts;
		this.names = names;
	}
	
	public String getName() {
		return names;
	}
	
	public void setName(String names) {
		this.names = names;
	}
	
	public BankAccount getAccount(String userName, String hashOfPIN) {
		for(BankAccount bankAccount : accounts) {
			if(bankAccount.getUserName().trim().equalsIgnoreCase(userName) && bankAccount.getHashOfPIN().equals(hashOfPIN)) {
				return bankAccount;
			}
		}
		return null;
	}
	
	public boolean accountExist(String userName) {
		for(BankAccount bankAccount : accounts) {
			if(bankAccount != null && bankAccount.getUserName().trim().equalsIgnoreCase(userName)) {
				System.out.println("Username " + userName + " exists");
				return true;
			}
		}
		return false;
	}
	
	public boolean pinMatched(String hashOfPIN) {
		for(BankAccount bankAccount : accounts) {
			if(bankAccount != null && bankAccount.getHashOfPIN().trim().equals(hashOfPIN)) {
				System.out.println("PIN matched");
				return true;
			}
		}
		return false;
		
	}
	
	public void registerAccount(BankAccount account) {
		if( !accountExist(account.getUserName())) {
			accounts.add(account);
		}
	}
	
	public void terminateAccount(String userName, String hashOfPIN) {
		for(int i = 0; i < accounts.size(); i++) {
			if(accounts.get(i) != null && accounts.get(i).getUserName().trim().equalsIgnoreCase(userName)
						&& accounts.get(i).getHashOfPIN().equals(hashOfPIN)) {
				System.out.println("Removing username: " + userName);
				accounts.remove(i);
			}
		}	
	}
	
	public void writeToFile(PrintWriter out) {
		if(accounts == null || out == null) {
			return;
		}
		
		for(BankAccount bankAccount : accounts) {
			bankAccount.writeToFile(out);
		}
		out.close();
	}
	
	public void readFromFile(Scanner in) {
		
		BankAccount bankAccount = new BankAccount(in);
		
		if(accounts == null || in == null) {
			return;
		}
		
		bankAccount.readFromFile(in);
		accounts.add(bankAccount);
		
		in.close();
	}
	
}
