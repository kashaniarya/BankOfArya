package main.java.com.kash.models;

public class Account {
	private int accountId; // primary key
	private double balance;  // not null
	private AccountStatus status;
	private AccountType type;
	
	//private static int aID = 1;
	
	public Account(double balance, AccountStatus status, AccountType type) {
		this.accountId = -1;
		//aID += 1;
		this.balance = balance;
		this.status = status;
		this.type = type;
	}
	
	public Account(int id, double balance, AccountStatus status, AccountType type) {
		this.accountId = id;
		this.balance = balance;
		this.status = status;
		this.type = type;
	}
	
	public void setID(int id) {
		this.accountId = id;
	}
	
	public int getID() {
		return this.accountId;
	}
	
	public double getBalance() {
		return this.balance;
	}
	
	public AccountStatus getStatus() {
		return this.status;
	}
	
	public AccountType getType() {
		return this.type;
	}
	
	@Override
	public String toString() {
		return this.accountId + ": " + this.balance + ", " + this.status + ", " + this.type;
	}
}
