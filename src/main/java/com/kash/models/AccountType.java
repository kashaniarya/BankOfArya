package main.java.com.kash.models;

import main.java.com.kash.exceptions.InvalidAccountTypeException;

public class AccountType {
	private int typeId; // primary key
	private String type; // not null, unique
	
	//private static int tID = 1;
	
	public AccountType(String type) throws InvalidAccountTypeException {
//		this.typeId = tID;
//		tID += 1;
		if(type.toLowerCase().equals("checking") || type.toLowerCase().equals("savings")) {
			this.type = type;
			if(type.equals("checking")) {
				this.typeId = 1;
			}
			else {
				this.typeId = 2;
			}
		}
		else {
			throw new InvalidAccountTypeException(type + "is an invalid account type");
		}
	}
	
	public AccountType(int id, String type) throws InvalidAccountTypeException {
		this.typeId = id;
		//tID += 1;
		if(type.toLowerCase().equals("checking") || type.toLowerCase().equals("savings")) {
			this.type = type;
			this.typeId = id;
		}
		else {
			throw new InvalidAccountTypeException(type + "is an invalid account type");
		}
	}
	
	public int getID() {
		return this.typeId;
	}
	
	public String getType() {
		return this.type;
	}
	
	@Override
	public String toString() {
		return this.typeId + ": " + this.type;
	}
}
