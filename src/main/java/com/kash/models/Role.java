package main.java.com.kash.models;

import main.java.com.kash.exceptions.InvalidRoleException;

public class Role {
	private int roleId; // primary key
	private String role; // not null, unique
	
	//private static int rID = 1;
	
	public Role(String role) throws InvalidRoleException {
		String r = role.toLowerCase();
		//this.roleId = rID;
		//rID += 1;
		if (r.equals("admin") || r.equals("employee") || r.equals("standard") || r.equals("premium")) {
			this.role = role;
			if (r.equals("admin")) {
				this.roleId = 1;
			}
			else if (r.equals("employee")) {
				this.roleId = 2;
			}
			else if(r.equals("premium")) {
				this.roleId = 3;
			}
			else {
				this.roleId = 4;
			}
		}
		else {
			throw new InvalidRoleException(role + "is an invalid role");
		}
	}
	
	public Role(int id, String role) throws InvalidRoleException {
		this.roleId = id;
		if (role.toLowerCase().equals("admin") || role.toLowerCase().equals("employee") || role.toLowerCase().equals("standard") || role.toLowerCase().equals("premium")) {
			this.role = role;
			this.roleId = id;
		}
		else {
			throw new InvalidRoleException(role + "is an invalid role");
		}
	}
	
	public int getID() {
		return this.roleId;
	}
	
	public String getRole() {
		return this.role;
	}
	
	@Override
	public String toString() {
		return this.roleId + ": " + this.role;
	}
}
