package br.com.hotel.eclipsehotel.model;

import br.com.hotel.eclipsehotel.enums.RoomType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity @Table(name = "rooms") @Getter @Setter @EqualsAndHashCode
@SequenceGenerator(name = "seq_room", sequenceName = "seq_room", initialValue = 1, allocationSize = 1)
public class Room {
	
	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_room") 
	@Column(name = "id", nullable = false)
	@Setter(AccessLevel.NONE)
	private Long id;
	
	@Column(name = "room_number", nullable = false, unique = true)
	private int roomNumber;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "type", nullable = false)
	private RoomType type;
	
	@Positive(message = "Prices cannot be negatives.")
	@Column(name = "price", nullable = false)
	private Double price;

}
