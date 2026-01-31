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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.hotel.eclipsehotel.model.Customer;
import br.com.hotel.eclipsehotel.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController 
@RequiredArgsConstructor
public class CustomerController {
	private static final Logger log = LoggerFactory.getLogger(CustomerController.class);
	private final CustomerService service;
	
	@GetMapping("/tolist")
	public ResponseEntity<List<Customer>> toListCustomers(){
		log.info("List all customers in the system.");
		List<Customer> customersList = service.toListCustomers();
		return ResponseEntity.ok(customersList);
	}
	
	@PostMapping("/savecustomer")
	public ResponseEntity<Customer> saveCustomer(@Valid @RequestBody Customer customer){
		log.info("Saves one costumer in the system.");
		Customer saved = service.saveCustomer(customer);
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}
	
	@GetMapping("/findcustomer/{id}")
	public ResponseEntity<Customer> findCustomerById(@PathVariable Long id){
		Customer foundCustomer = service.findCustomerById(id);
		return ResponseEntity.ok(foundCustomer);
	}
	
	@DeleteMapping("/deletecustomer/{id}")
	public ResponseEntity<Void> deleteCustomerById(@PathVariable Long id){
		service.deleteCustomerById(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("updatecustomer/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @Valid @RequestBody Customer customer){
		Customer updated = service.updateCustomer(id, customer);
		return ResponseEntity.ok(updated);
	}

}
