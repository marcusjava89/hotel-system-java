package br.com.hotel.eclipsehotel.exception;

public class RoomNotFound extends RuntimeException{
	public RoomNotFound(Long id) {
		super("The id ="+id+" is not connected with any room.");
	}
}
