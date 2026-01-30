package br.com.hotel.eclipsehotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.hotel.eclipsehotel.model.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>{
	
}
