package com.kash.exceptions;

public class InvalidAccountStatusException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidAccountStatusException(String message)
	  {
	    super(message);
	  }
}
