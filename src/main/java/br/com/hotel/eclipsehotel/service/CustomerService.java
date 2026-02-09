package br.com.hotel.eclipsehotel.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.hotel.eclipsehotel.exception.CustomerNotFound;
import br.com.hotel.eclipsehotel.exception.ExistentEmailException;
import br.com.hotel.eclipsehotel.model.Customer;
import br.com.hotel.eclipsehotel.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service @RequiredArgsConstructor
public class CustomerService {
	private final CustomerRepository repository;
	
	public List<Customer> toListCustomers(){
		List<Customer> list = repository.findAll();
		return list;
	}
	
	@Transactional
	public Customer saveCustomer(Customer customer) {
		if(repository.findByEmail(customer.getEmail()).isPresent()) {
			throw new ExistentEmailException();
		}
		
		Customer saved = repository.save(customer);
		return saved;
	}
	
	public Customer findCustomerById(Long id) {
		Customer found = repository.findById(id).orElseThrow(() -> new CustomerNotFound(id));
		return found;
	}
	
	@Transactional
	public void deleteCustomerById(Long id) {
		Customer found = repository.findById(id).orElseThrow(() -> new CustomerNotFound(id));
		repository.delete(found);
	}
	
	@Transactional
	public Customer updateCustomer(Long id, Customer customerUpdate) {
		/*id belongs to the customer that will be update. customerUpdate is the new information to customer.*/
		Customer found = repository.findById(id).orElseThrow(() -> new CustomerNotFound(id));
		
		boolean e1 = repository.findByEmail(customerUpdate.getEmail()).isPresent(); 
		
		if(!customerUpdate.getEmail().equals(found.getEmail()) && e1) {
			throw new ExistentEmailException();
		}

		found.setName(customerUpdate.getName());
		found.setPhone(customerUpdate.getPhone());
		found.setEmail(customerUpdate.getEmail());
		
		Customer updatedCustomer = repository.saveAndFlush(found);
		return updatedCustomer;
		
	}
	
}
