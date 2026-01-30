package br.com.hotel.eclipsehotel.exception;

public class DuplicatedRoomNumberException extends RuntimeException{
	public DuplicatedRoomNumberException(int roomNumber) {
		super("The roomNumber "+roomNumber+" alredy exist and cannot be duplicated.");
	}
}
