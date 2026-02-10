package br.com.hotel.eclipsehotel.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity @Table(name = "address") 
@Getter @Setter @EqualsAndHashCode
@SequenceGenerator(name = "seq_address", sequenceName = "seq_address", initialValue = 1, allocationSize = 1)
public class Address {

	@Id
	@GeneratedValue(generator = "seq_address", strategy = GenerationType.SEQUENCE)
	@Setter(AccessLevel.NONE)
	@Column(name = "id_address", nullable = false)
	private Long idAddress;
	
	@NotBlank
	@Pattern(regexp = "\\d{8}", message = "In Brazil a zip code has 8 digits.")
	@Column(name = "zip_code", nullable = false)
	private String zipCode;
	
	@NotBlank(message = "A street name can not be empty.")
	@Size(max = 60)
	private String street;
	
	@Column(name = "address_details")
	private String addressDetails;
	
	@Column(name = "neighborhood", nullable = false)
	private String neighborhood;
	
	@Column(name = "state", nullable = false)
	private String state;
	
}






