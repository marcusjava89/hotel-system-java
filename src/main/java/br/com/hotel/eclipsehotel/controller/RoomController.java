package br.com.hotel.eclipsehotel.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.hotel.eclipsehotel.model.Room;
import br.com.hotel.eclipsehotel.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController @RequiredArgsConstructor
public class RoomController {
	
	private final RoomService service;
	
	@GetMapping("/list")
	public ResponseEntity<List<Room>> roomList(){
		List<Room> list = service.roomList();
		return ResponseEntity.ok(list);
	}
	
	@PostMapping("/save")
	public ResponseEntity<Room> saveRoom(@Valid @RequestBody Room room){
		Room saved = service.saveRoom(room);
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}
	
	@GetMapping("/findid/{id}")
	public ResponseEntity<Room> findRoomById(@PathVariable Long id){
		Room found = service.findRoomById(id);
		return ResponseEntity.ok(found);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteRoom(@PathVariable Long id){
		service.deleteRoomById(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/updateroom/{id}")
	public ResponseEntity<Room> updateRoom(@PathVariable Long id,@Valid Room room){
		Room update = service.updateRoom(id, room);
		return ResponseEntity.ok(update);
	}
	

}

