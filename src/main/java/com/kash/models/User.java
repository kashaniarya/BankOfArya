package com.kash.models;

import java.util.ArrayList;
import java.util.List;

import com.kash.exceptions.InvalidRoleException;

public class User {
	private int userId; // primary key
	private String username; // not null, unique
	private String password; // not null
	private String firstName; // not null
	private String lastName; // not null
	private String email; // not null
	private Role role;
	private List<Account> accounts;
	
	//private static int uID = 1;
	
	
	public User(String username, String password, String firstName, String lastName, String email, String role) throws InvalidRoleException {
		this.userId = -1;
		//User.uID += 1;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.role = new Role(role);
		this.accounts = new ArrayList<Account>();
	}
	
	public User(int id, String username, String password, String firstName, String lastName, String email, String role) throws InvalidRoleException {
		this.userId = id;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.role = new Role(role);
		this.accounts = new ArrayList<Account>();
	}
	
	public User(int id, String username, String password, String firstName, String lastName, String email, String role, List<Account> la) throws InvalidRoleException {
		this.userId = id;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.role = new Role(role);
		this.accounts = la;
	}
	
	public int getID() {
		return this.userId;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public String getFirstname() {
		return this.firstName;
	}
	
	public String getLastname() {
		return this.lastName;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public Role getRole() {
		return this.role;
	}
	
	public List<Account> getAccounts() {
		return this.accounts;
	}
	
	@Override
	public String toString() {
		return this.userId + ": " + this.username + ", " + this.firstName + " " + this.lastName + ", " + this.email + ", " + this.role + ", " + this.accounts;
	}
}
