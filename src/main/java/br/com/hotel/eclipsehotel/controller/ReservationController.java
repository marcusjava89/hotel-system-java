package br.com.hotel.eclipsehotel.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.hotel.eclipsehotel.model.Reservation;
import br.com.hotel.eclipsehotel.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController @RequiredArgsConstructor
public class ReservationController {
	private final ReservationService service;
	
	@PostMapping("/open")
	public ResponseEntity<Reservation> openReservation(@Valid @RequestBody Reservation reservation){
		Reservation open = service.openReservation(reservation);
		return ResponseEntity.status(HttpStatus.CREATED).body(open);
	}
	
	@PutMapping("/close/{id}")
	public ResponseEntity<Reservation> closeReservation(@PathVariable Long id){
		Reservation close = service.closeReservation(id);
		return ResponseEntity.ok(close);
	}
	
	@GetMapping("/time/{id}")
	public ResponseEntity<Long> reservationTime(@PathVariable Long id){
		long days = service.reservationTime(id);
		return ResponseEntity.ok(days);
	}
	
	@GetMapping("/occupied")
	public ResponseEntity<List<Long>> occupiedRooms(){
		List<Long> rooms = service.occupiedRooms();
		return ResponseEntity.ok(rooms);
	}
}





















