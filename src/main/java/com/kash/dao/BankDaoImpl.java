package main.java.com.kash.dao;

import main.java.com.kash.models.User;
import main.java.com.kash.models.Role;
import main.java.com.kash.models.Account;
import main.java.com.kash.models.AccountStatus;
import main.java.com.kash.models.AccountType;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



public class BankDaoImpl implements BankDao {

	@Override
	public boolean insertUser(User u) {
		boolean b = false;
		try {
			Connection conn = ConnectionFactory.getConnection();
			String sql = "INSERT INTO bank_user (username, pass, firstName, lastName, email, role_id, role_str, accounts_list) VALUES (?, ?, ?, ?, ?, ?, ?, '{}')";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			
			ps.setString(1,u.getUsername().toLowerCase());
			ps.setString(2,u.getPassword());
			ps.setString(3, u.getFirstname().toLowerCase());
			ps.setString(4, u.getLastname().toLowerCase());
			ps.setString(5, u.getEmail().toLowerCase());
			Role r = u.getRole();
			ps.setInt(6, r.getID());
			ps.setString(7, r.getRole().toLowerCase());
			
			
			ps.execute();
			
			
			conn.close();
			//return b;
			b = true;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	@Override
	public String readPasswordFromBankDB(String username) {
		String p = null;
		try {
			Connection conn = ConnectionFactory.getConnection();
			//System.out.println("conn: " + conn);
			String u = username.toLowerCase();
			String sql = "SELECT * FROM bank_user WHERE username = '" + u + "'";
			
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			if (rs.next()) {
				//System.out.println("in rs.next");
				//System.out.println();
				p = rs.getString("pass");
				//System.out.println("p:"+p);
				//return p;
			}
			
			conn.close();
			
		}
		catch (Exception e) {
			//System.out.println("falls in here");
			e.printStackTrace();
		}
		return p;
	}
	
	private int getCountForAllAccountsDB() {
		int count = -1;
		try {
			
			Connection conn = ConnectionFactory.getConnection();
				
			String sql = "SELECT count(*) from bank_accounts";
			
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			if(rs.next()) {
				count = rs.getInt(1);
			}
			
			
			conn.close();	
				
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	private int addAccountToAccountDB(Account a) {
		int id = -1;
		try {
			Connection conn = ConnectionFactory.getConnection();
			
			String sql = "INSERT INTO bank_accounts (account_id, balance, account_status_id, account_status_str, account_type_id, account_type_str) VALUES (?,?,?,?,?,?)";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			id = getCountForAllAccountsDB() + 1;
			
			ps.setInt(1, id);
			
			ps.setDouble(2, a.getBalance());
			AccountStatus as = a.getStatus();
			ps.setInt(3, as.getID());
			ps.setString(4, as.getStatus());
			AccountType at = a.getType();
			ps.setInt(5, at.getID());
			ps.setString(6, at.getType());
			
			ps.execute();
			
			conn.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}
	
	// list of accounts => int[] ids => object [] => sql array
	private Object[] accountsToObjArray(List<Account> la ) {
		int[] ia = new int[la.size()];
		int index = 0;
		for(Account ac : la) {
			ia[index] = ac.getID(); 
			index += 1;
		}
		Object[] o = new Object[ia.length];
		index = 0;
		for(int ii : ia) {
			o[index] = (Object) ii;
			index += 1;
		}
		return o;
	}
	
//	private int[] accountsToIntArray(List<Account> la ) {
//		int[] ia = new int[la.size()];
//		int index = 0;
//		for(Account ac : la) {
//			ia[index] = ac.getID(); 
//			index += 1;
//		}
//		
//		return ia;
//	}

	@Override
	public void addAccount(String username, Account a) {
		
		// add account to bank_account db
		int id = addAccountToAccountDB(a);
		a.setID(id);
		// add account_id to User's account_list
		try {
			Connection conn = ConnectionFactory.getConnection();
			
			List<Account> la = readAllUsersAccounts(username);//u.getAccounts();
			
			la.add(a);
			
			Object[] o = accountsToObjArray(la);
			
			Array sql_array = conn.createArrayOf("int", o);
			
			
			String sql = "UPDATE bank_user SET accounts_list = '" + sql_array + "' WHERE username = '" + username + "'";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.execute();
			
			
			conn.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private int[] readAllUsersAccountHelper(String username) {
		int[] retval_bad = new int[0];
			try {
				Connection conn = ConnectionFactory.getConnection();
				
				String sql = "SELECT * FROM bank_user WHERE username = '" + username + "'";
				
				Statement s = conn.createStatement();
				ResultSet rs = s.executeQuery(sql);
				
				if (rs.next()) {
					Array a = rs.getArray("accounts_list");
					Object[] o = (Object[]) a.getArray();
					
					int[] retval = new int[o.length];
					int index = 0;
					for(Object o_ : o) {
						retval[index] = (int) o_;
						index += 1;
					}
					conn.close();
					return retval;
				}
				
				conn.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		return retval_bad;
	}

	@Override
	public List<Account> readAllUsersAccounts(String username) {
		List<Account> retval = new ArrayList<Account>();
		// TODO Auto-generated method stub
		//List<Account> la = u.getAccounts();
		// READ FROM DATABASE
		int[] shit = readAllUsersAccountHelper(username);
		/*
		 * read from database sql array
		 *  turn it into obj[] array => int array [] ids
		 *  
		 *  interate thru int[] ids to get each Account by id
		 */
		for(int i : shit) {
			Account a = getAccountById(i);
			retval.add(a);
		}
		// TURN FROM INT TO LITERAL
		
		
		
		
		return retval;
	}

	@Override
	public Account getAccountById(int id) {
		// TODO Auto-generated method stub
		Account a = null;
		try {
			Connection conn = ConnectionFactory.getConnection();
			
			String sql = "SELECT * FROM bank_accounts WHERE account_id = " + id;
			
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			if (rs.next()) {
				a = new Account(
						id,
						rs.getDouble("balance"),
						new AccountStatus(rs.getString("account_status_str")),
						new AccountType(rs.getString("account_type_str"))
						);
						
			}
			
			
			conn.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return a;
	}
	
//	public static void main(String[] args) throws InvalidRoleException    {
//		
//		BankDao b = new BankDaoImpl();
//		User u = new User(1, "kashaniarya", "password", "arya", "kashani", "gmail", "standard");
//		//Account a = new Account(100.0, new AccountStatus("pending"), new AccountType("checking"));
//		System.out.println(b.readAllUsersAccounts(u));
//	}
	
//	public static void main(String[] args) {
//		
//		BankDao b = new BankDaoImpl();
//		//System.out.println(b.readAllUsersAccounts("kashaniarya"));
//		List<User> users = b.getAllUsers();
//		for (int i=0; i < users.size(); i++ ) {
//			System.out.println(users.get(i));
//		}
//	}

	@Override
	public boolean updateAccountBalance(Double new_balance, int id) {
		boolean retval = false;
		try {
			Connection conn = ConnectionFactory.getConnection();
			
			String sql = "UPDATE bank_accounts SET balance = " + new_balance + " WHERE account_id = " + id;
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.execute();
			
			conn.close();
			retval = true;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return retval;
	}

	@Override
	public boolean updateAccountToClosed(int id) {
		boolean retval = false;
		try {
			Connection conn = ConnectionFactory.getConnection();
			
			String sql = "UPDATE bank_accounts SET (account_status_id, account_status_str, balance) = (3,'closed',0) WHERE account_id = "+id;
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.execute();
			
			conn.close();
			retval = true;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return retval;
	}

	@Override
	public User getUserByUsername(String username) {
		User u = null;
		try {
			Connection conn = ConnectionFactory.getConnection();
			
			String sql = "SELECT * FROM bank_user WHERE username = '" + username + "'";
			
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			if (rs.next()) {
				int id = rs.getInt("user_id");
				//String username_ = rs.getString("username");
				String pass = rs.getString("pass");
				String fname = rs.getString("firstName");
				String lname = rs.getString("lastName");
				String email = rs.getString("email");
				String role_str = rs.getString("role_str");
				
				List<Account> la = readAllUsersAccounts(username);
				
				u = new User(id, username, pass, fname, lname, email, role_str, la);
			}
			
			conn.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return u;
	}

	@Override
	public User getUserById(int id) {
		User u = null;
		try {
			Connection conn = ConnectionFactory.getConnection();
			
			String sql = "SELECT * FROM bank_user WHERE user_id = " + id;
			
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			if (rs.next()) {
				//int id = rs.getInt("user_id");
				String username = rs.getString("username");
				String pass = rs.getString("pass");
				String fname = rs.getString("firstName");
				String lname = rs.getString("lastName");
				String email = rs.getString("email");
				String role_str = rs.getString("role_str");
				
				List<Account> la = readAllUsersAccounts(username);
				
				u = new User(id, username, pass, fname, lname, email, role_str, la);
			}
			
			conn.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return u;
	}

	@Override
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<User>();
		try {
			Connection conn = ConnectionFactory.getConnection();
			
			String sql = "SELECT * FROM bank_user";
			
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			while (rs.next()) {
				String username = rs.getString("username");
				//System.out.println("u: "+username);
				List<Account> la = readAllUsersAccounts(username);
				
				User u = new User(
						rs.getInt("user_id"),
						username,
						rs.getString("pass"),
						rs.getString("firstName"),
						rs.getString("lastName"),
						rs.getString("email"),
						rs.getString("role_str"),
						la
						);
				
				users.add(u);
			}
			
			conn.close();
			//return users;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}

	@Override
	public boolean updateUser(int id, String username, String fname, String lname, String email, String r) {
		boolean retval = false;
		try {
			Connection conn = ConnectionFactory.getConnection();
			
			String sql = "UPDATE bank_user SET (username, firstName, lastName, email, role_id, role_str) = (?,?,?,?,?,?) WHERE user_id = " + id;
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, fname);
			ps.setString(3, lname);
			ps.setString(4, email);
			
			int i=0;
			
			if (r.equals("admin")) {
				i = 1;
			}
			else if (r.equals("employee")) {
				i = 2;
			}
			else if(r.equals("premium")) {
				i = 3;
			}
			else {
				i = 4;
			}
			
			ps.setInt(5, i);
			ps.setString(6, r);
			
			ps.execute();
			
			
			conn.close();
			
			retval = true;
		}
		catch (Exception e) {
			retval = false;
		}
		return retval;
	}

	@Override
	public boolean updateAccount(int id, Double balance, String status, String type) {
		boolean retval = false;
		try {
			Connection conn = ConnectionFactory.getConnection();
			
			String sql = "UPDATE bank_accounts SET (balance, account_status_id, account_status_str, account_type_id, account_type_str) = (?,?,?,?,?) WHERE account_id = " + id;
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setDouble(1, balance);
			
			int statusId = 0;
			
			if(status.equals("pending")) {
				statusId = 1;
			}
			else if(status.equals("open")) {
				statusId = 2;
			}
			else if(status.equals("closed")) {
				statusId = 3;
			}
			else {
				statusId = 4;
			}
			
			ps.setInt(2, statusId);
			ps.setString(3, status);
			
			int typeId = 0;
			
			if(type.equals("checking")) {
				typeId = 1;
			}
			else {
				typeId = 2;
			}
			
			ps.setInt(4, typeId);
			ps.setString(5, type);
			
			ps.execute();
			conn.close();
			retval = true;
		}
		catch (Exception e) {
			retval = false;
		}
		return retval;
	}

	@Override
	public List<Account> getAccountByType(String type) {
		List<Account> la = new ArrayList<Account>();
		try {
			Connection conn = ConnectionFactory.getConnection();
			
			String sql = "SELECT * FROM bank_accounts WHERE account_type_str = '" + type + "'";
			
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			while (rs.next()) {
				Account a = new Account(
						rs.getInt("account_id"),
						rs.getDouble("balance"),
						new AccountStatus(rs.getString("account_status_str")),
						new AccountType(rs.getString("account_type_str"))
						);
				la.add(a);
			}
			
			
			conn.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return la;
	}

	@Override
	public List<Account> getAccountByStatus(String status) {
		List<Account> la = new ArrayList<Account>();
		try {
			Connection conn = ConnectionFactory.getConnection();
			
			String sql = "SELECT * FROM bank_accounts WHERE account_status_str = '" + status + "'";
			
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			while (rs.next()) {
				Account a = new Account(
						rs.getInt("account_id"),
						rs.getDouble("balance"),
						new AccountStatus(rs.getString("account_status_str")),
						new AccountType(rs.getString("account_type_str"))
						);
				la.add(a);
			}
			
			
			conn.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return la;
	}

}












