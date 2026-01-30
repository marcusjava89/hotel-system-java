package br.com.hotel.eclipsehotel.exception;

public class ExistentEmailException extends RuntimeException{
	public ExistentEmailException() {
		super("Another customer is using this e-mail adress.");
	}
}
