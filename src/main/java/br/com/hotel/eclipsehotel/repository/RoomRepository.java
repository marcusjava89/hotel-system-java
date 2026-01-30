package br.com.hotel.eclipsehotel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.hotel.eclipsehotel.model.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long>{
	public Optional<Room> findByRoomNumber(int roomNumber);
}
