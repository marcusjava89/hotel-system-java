package br.com.hotel.eclipsehotel.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.hotel.eclipsehotel.enums.ReservationStatus;
import br.com.hotel.eclipsehotel.exception.ReservationNotFound;
import br.com.hotel.eclipsehotel.model.Reservation;
import br.com.hotel.eclipsehotel.model.Room;
import br.com.hotel.eclipsehotel.repository.ReservationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {

	private final ReservationRepository repository;

	@Transactional
	public Reservation openReservation(Reservation reservation) {
		reservation.setStatus(ReservationStatus.SCHEDULED);
		/* checkout must be null, there is no checkout yet. */
		reservation.setCheckout(null);
		Reservation opened = repository.saveAndFlush(reservation);
		return opened;
	}

	@Transactional
	public Reservation closeReservation(Long id) {
		Reservation reservation = repository.findById(id).orElseThrow(() -> new ReservationNotFound(id));

		if (!reservation.getStatus().equals(ReservationStatus.IN_USE)) {
			throw new IllegalStateException("Reservation must be IN_USE to be closed");
		}

		reservation.setCheckout(LocalDateTime.now());
		reservation.setStatus(ReservationStatus.FINISHED);
		Reservation closed = repository.saveAndFlush(reservation);

		return closed;

	}

	@Transactional
	public Reservation canceledReservation(Long id) {

		Reservation reservation = repository.findById(id).orElseThrow(() -> new ReservationNotFound(id));
		
		if(!reservation.getStatus().equals(ReservationStatus.SCHEDULED)) {
			throw new IllegalStateException("Reservation must be SCHEDULED to be canceled.");
		}
		
		reservation.setCheckout(LocalDateTime.now());
		reservation.setStatus(ReservationStatus.CANCELED);
		Reservation canceled = repository.saveAndFlush(reservation);

		return canceled;

	}

	public Long reservationTime(Long id) {
		Reservation reservation = repository.findById(id).orElseThrow(() -> new ReservationNotFound(id));

		LocalDateTime end;

		if (reservation.getStatus() == ReservationStatus.FINISHED) {
			end = reservation.getCheckout();
		} else {
			end = LocalDateTime.now(); /* Reservation still open. */
		}

		long days = ChronoUnit.DAYS.between(reservation.getCheckin(), end);
		return days;
	}

	/*
	 * This method do not return the rooms (the object) only the rooms number's to
	 * make easy to the client read.
	 */
	public List<Long> occupiedRooms() {
		List<Room> rooms = new ArrayList<>();

		List<Reservation> reservations = repository.findAll();
		for (Reservation r : reservations) {
			if (r.getStatus() == ReservationStatus.IN_USE) {
				rooms.add(r.getRoom());
			}
		}

		List<Long> roomNumbers = new ArrayList<>();
		for (Room ro : rooms) {
			roomNumbers.add((long) ro.getRoomNumber());
		}

		return roomNumbers;
	}

}
