package main.java.com.kash.exceptions;

public class InvalidAccountTypeException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidAccountTypeException(String message)
	  {
	    super(message);
	  }
}
