package br.com.hotel.eclipsehotel.exception;

public class ReservationNotFound extends RuntimeException{

	public ReservationNotFound(Long id) {
		super("Reservation with id = "+id+" not found.");
	}
	
}
