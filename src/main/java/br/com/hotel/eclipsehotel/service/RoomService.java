package br.com.hotel.eclipsehotel.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.hotel.eclipsehotel.exception.DuplicatedRoomNumberException;
import br.com.hotel.eclipsehotel.exception.RoomNotFound;
import br.com.hotel.eclipsehotel.model.Room;
import br.com.hotel.eclipsehotel.repository.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomService {
	  
	private final RoomRepository repository;
	
	public List<Room> roomList() {
		List<Room> list = repository.findAll();
		return list;
	}
	
	@Transactional
	public Room saveRoom(Room room) {
		Room saved = repository.save(room);
		return saved;
	}
	
	public Room findRoomById(Long id) {
		Room found = repository.findById(id).orElseThrow(() -> new RoomNotFound(id));
		return found;
	}
	
	@Transactional
	public void deleteRoomById(Long id) {
		Room found = repository.findById(id).orElseThrow(() -> new RoomNotFound(id));
		repository.delete(found);
	}
	
	@Transactional
	public Room updateRoom(Long id, Room room) {
		Room found = repository.findById(id).orElseThrow(() -> new RoomNotFound(id));
		
		boolean r1 = repository.findByRoomNumber(room.getRoomNumber()).isPresent();
		
		if(found.getRoomNumber() != room.getRoomNumber() && r1) {
			throw new DuplicatedRoomNumberException(room.getRoomNumber());
		}
		
		found.setPrice(room.getPrice());
		found.setRoomNumber(room.getRoomNumber());
		found.setType(room.getType());
		
		Room savedRoom = repository.saveAndFlush(found);

		return savedRoom;
	}
	
}