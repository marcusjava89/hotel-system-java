package br.com.hotel.eclipsehotel.model;

import java.time.LocalDateTime;

import br.com.hotel.eclipsehotel.enums.ReservationStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Entity @Table(name = "reservations") @Getter @Setter @EqualsAndHashCode
@SequenceGenerator(name = "seq_reservation", sequenceName = "seq_reservation", initialValue = 1, allocationSize = 1)
public class Reservation {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_reservation") 
	@Column(name = "id", nullable = false)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name = "room_id", nullable = false)
	private Room room;
	
	@Column(name = "checkin", nullable = false)
	@Setter(AccessLevel.NONE)
	private LocalDateTime checkin;
	
	@Column(name = "checkout", nullable = true)
	private LocalDateTime checkout; /*We deal with this part in ReservationService*/
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private ReservationStatus status;
	
	@PrePersist
	public void prePersist() {
		checkin = LocalDateTime.now();
	}

}
