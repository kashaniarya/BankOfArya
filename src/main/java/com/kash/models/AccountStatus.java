package main.java.com.kash.models;

import main.java.com.kash.exceptions.InvalidAccountStatusException;

public class AccountStatus {
	private int statusId; // primary key
	private String status; // not null, unique
	
	//private static int sID = 1;
	
	public AccountStatus(String status) throws InvalidAccountStatusException {
//		this.statusId = sID;
//		sID += 1;
		if (status.toLowerCase().equals("pending") || status.toLowerCase().equals("open") || status.toLowerCase().equals("closed") || status.toLowerCase().equals("denied")) {
			this.status = status;
			if(status.equals("pending")) {
				this.statusId = 1;
			}
			else if(status.equals("open")) {
				this.statusId = 2;
			}
			else if(status.equals("closed")) {
				this.statusId = 3;
			}
			else {
				this.statusId = 4;
			}
		}
		else {
			throw new InvalidAccountStatusException(status + "is an invalid account status");
		}
	}
	
	public AccountStatus(int id, String status) throws InvalidAccountStatusException {
		this.statusId = id;
		//sID += 1;
		if (status.toLowerCase().equals("pending") || status.toLowerCase().equals("open") || status.toLowerCase().equals("closed") || status.toLowerCase().equals("denied")) {
			this.status = status;
			this.statusId = id;
		}
		else {
			throw new InvalidAccountStatusException(status + "is an invalid account status");
		}
	}
	
	public int getID() {
		return this.statusId;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	@Override
	public String toString() {
		return this.statusId + ": " + this.status;
	}
}
