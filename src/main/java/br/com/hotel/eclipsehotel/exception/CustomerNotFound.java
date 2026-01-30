package br.com.hotel.eclipsehotel.exception;

public class CustomerNotFound extends RuntimeException{
	public CustomerNotFound(Long id) {
		super("Customer with id = "+id+" wasn't found.");
	}
}
