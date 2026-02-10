package br.com.hotel.eclipsehotel.model;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity 
@Table(name = "customers") 
@Getter @Setter 
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@SequenceGenerator(name = "seq_customers", sequenceName = "seq_customers", initialValue = 1, allocationSize = 1)
public class Customer {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_customers") 
	@Column(name = "id_customer", nullable = false)
	@Setter(AccessLevel.NONE)
	@EqualsAndHashCode.Include
	private Long idCustomer;
	
	@NotBlank(message = "The name cannot be empty.") 
	@Size(min = 3, max = 60) 
	@Column(name = "name", nullable = false)
	private String name;
	
	@Email(message = "Invalid e-mail address format.") 
	@NotBlank(message = "E-mail cannot be empty.")
	@Column(name = "email", nullable = false, unique = true)
	private String email;
	
	@NotBlank(message = "We need a phone number to contact the customer.") 
	@Column(name = "phone", nullable = false)
	private String phone;
	
	@Column(name = "create_at", nullable = false, updatable = false)
	@Setter(AccessLevel.NONE)
	private LocalDateTime createAt;
	
	@PrePersist
	public void prePersist() {
		createAt = LocalDateTime.now();
	}
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "id_address", nullable = false)
	@NotNull(message = "Address can not be null.")
	private Address address;
	
}
