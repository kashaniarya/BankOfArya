package com.kash.dao;

import java.util.List;

import com.kash.models.Account;
import com.kash.models.User;

public interface BankDao {
	
	public boolean insertUser(User u);
	
	public String readPasswordFromBankDB(String username);
	
	public void addAccount(String username, Account a );
	
	public List<Account> readAllUsersAccounts(String username);
	
	public Account getAccountById(int id);
	
	//public int getCountForAllAccountsDB();
	
	public boolean updateAccountBalance(Double new_balance, int id);
	
	public boolean updateAccountToClosed(int id);
	
	public User getUserByUsername(String username);
	
	public User getUserById(int id);
	
	public List<User> getAllUsers();
	
	public boolean updateUser(int id, String username, String fname, String lname, String email, String r);
	
	public boolean updateAccount(int id, Double balance, String acc_status, String acc_type);
	
	public List<Account> getAccountByType(String type);
	
	public List<Account> getAccountByStatus(String status);
}
