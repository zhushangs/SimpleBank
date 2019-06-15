package SimpleBank;

import java.util.ArrayList;

public class Main {
	
	public static void main(String[] args) throws Exception {
		
		ArrayList<BankAccount> accounts = new ArrayList<BankAccount>();
		
		Bank bank = new Bank(accounts, "Simple Bank");
		
		BankMenu.landingMenu(bank);
	}

}
