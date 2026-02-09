package br.com.hotel.eclipsehotel.model;

import java.time.LocalDateTime;

import br.com.hotel.eclipsehotel.enums.ReservationStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Entity 
@Table(name = "reservations") 
@Getter @Setter 
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@SequenceGenerator(name = "seq_reservation", sequenceName = "seq_reservation", initialValue = 1, allocationSize = 1)
public class Reservation {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_reservation") 
	@Column(name = "id", nullable = false)
	@EqualsAndHashCode.Include
	private Long id;
	
	/*EAGER because we need to bring the customer from a reservation.*/
	@ManyToOne(cascade = {}, fetch = FetchType.EAGER)
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;
	
	/*LAZY because we don't need bring the room from a reservation, only if we want.*/
	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "room_id", nullable = false)
	private Room room;
	
	@Column(name = "checkin", nullable = false)
	private LocalDateTime checkin;
	
	@Column(name = "checkout", nullable = true) 
	private LocalDateTime checkout; 
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private ReservationStatus status;

}
