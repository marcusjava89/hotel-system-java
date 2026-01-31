package br.com.hotel.eclipsehotel;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import br.com.hotel.eclipsehotel.model.Address;
import br.com.hotel.eclipsehotel.model.Customer;
import br.com.hotel.eclipsehotel.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import tools.jackson.databind.ObjectMapper;

@SpringBootTest(classes = HotelApplication.class)
@AutoConfigureMockMvc
public class CustomerIntegrationTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private CustomerRepository repository;

	@Test
	public void contextLoads() {
	}

	@Test
	@Transactional
	public void test_toListCustomers_returns200() throws Exception {
		Address address1 = new Address();
		address1.setZipCode("33658974");
		address1.setStreet("Rua do Catete");
		address1.setNeighborhood("Catete");
		address1.setState("Rio de Janeiro");

		Customer customer1 = new Customer();
		customer1.setPhone("(21)635268547");
		customer1.setEmail("marcus@email.com");
		customer1.setName("Marcus");
		customer1.setAddress(address1);

		repository.saveAndFlush(customer1);

		Address address2 = new Address();
		address2.setZipCode("41785697");
		address2.setStreet("Rua ");
		address2.setNeighborhood("Vila Gustavo");
		address2.setState("São Paulo");

		Customer customer2 = new Customer();
		customer2.setPhone("(21)523687412");
		customer2.setEmail("hanna@hanna.com");
		customer2.setName("Hanna");
		customer2.setAddress(address2);
		repository.saveAndFlush(customer2);

		mvc.perform(get("/tolist")).andExpect(status().isOk()).andExpect(jsonPath("$[0].name").value("Marcus"))
				.andExpect(jsonPath("$[1].name").value("Hanna")).andExpect(jsonPath("$.length()").value(2));

	}

	@Test
	public void test_toListCustomers_emptyList_returns200() throws Exception {
		mvc.perform(get("/tolist")).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(0));
	}

	@Test
	@Transactional
	public void test_saveCustomer_returns_201() throws Exception{
		Address address1 = new Address();
		address1.setZipCode("33658974");
		address1.setStreet("Rua do Catete");
		address1.setNeighborhood("Catete");
		address1.setState("Rio de Janeiro");

		Customer customer1 = new Customer();
		customer1.setPhone("(21)635268547");
		customer1.setEmail("marcus@email.com");
		customer1.setName("Marcus");
		customer1.setAddress(address1);
		
		mvc.perform(post("/savecustomer").content(mapper.writeValueAsString(customer1))
		.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
		.andExpect(jsonPath("$.name").value("Marcus"))
		.andExpect(jsonPath("$.email").value("marcus@email.com"));
	}
	
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = {" ", "ab", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz12345678901"})
	@Transactional
	@DisplayName("Tests a empty, less then 3 words, more then 600 digits, names and return 400.")
	public void test_saveCustomer_invalidName_returns400(String name) throws Exception{
		Address address1 = new Address();
		address1.setZipCode("33658974");
		address1.setStreet("Rua do Catete");
		address1.setNeighborhood("Catete");
		address1.setState("Rio de Janeiro");

		Customer customer1 = new Customer();
		customer1.setPhone("(21)635268547");
		customer1.setEmail("marcus@email.com");
		customer1.setName(name);
		customer1.setAddress(address1);
		
		mvc.perform(post("/savecustomer").content(mapper.writeValueAsString(customer1))
		.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
		
	}
	
	@ParameterizedTest 
	@NullAndEmptySource
	@ValueSource(strings = {"com", " ", "marcus@marcus@"})
	@Transactional
	@DisplayName("Returns 400 when trying to save a customer with an invalid email adress.")
	public void test_saveCustomer_invalidEmail_returns400(String email) throws Exception{
		Address address1 = new Address();
		address1.setZipCode("33658974");
		address1.setStreet("Rua do Catete");
		address1.setNeighborhood("Catete");
		address1.setState("Rio de Janeiro");

		Customer customer1 = new Customer();
		customer1.setPhone("(21)635268547");
		customer1.setEmail(email);
		customer1.setName("Marcus");
		customer1.setAddress(address1);
		
		mvc.perform(post("/savecustomer").content(mapper.writeValueAsString(customer1))
		.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
	}
	
	@ParameterizedTest 
	@NullAndEmptySource
	@ValueSource(strings = {" "})
	@DisplayName("Returns 400 when trying to save a customer with an invalid email adress.")
	public void test_saveCustomer_invalidPhoneNumber_returns400(String phone) throws Exception{
		Address address1 = new Address();
		address1.setZipCode("33658974");
		address1.setStreet("Rua do Catete");
		address1.setNeighborhood("Catete");
		address1.setState("Rio de Janeiro");

		Customer customer1 = new Customer();
		customer1.setPhone(phone);
		customer1.setEmail("marcus@marcus.com");
		customer1.setName("Marcus");
		customer1.setAddress(address1);
		
		mvc.perform(post("/savecustomer").content(mapper.writeValueAsString(customer1))
		.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());	
	}
	
	@Test
	@Transactional
	public void test_saveCustomer_invalidAddress_returns400() throws Exception{
		Customer customer1 = new Customer();
		customer1.setPhone("(21)635268547");
		customer1.setEmail("marcus@email.com");
		customer1.setName("Marcus");
		customer1.setAddress(null);
		
		mvc.perform(post("/savecustomer").content(mapper.writeValueAsString(customer1))
		.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());	
	}
	
	@Test
	@Transactional
	public void test_saveCustomer_duplicateEmailAddress_returns409() throws Exception{
		Address address1 = new Address();
		address1.setZipCode("33658974");
		address1.setStreet("Rua do Catete");
		address1.setNeighborhood("Catete");
		address1.setState("Rio de Janeiro");

		Customer customer1 = new Customer();
		customer1.setPhone("(21)635268547");
		customer1.setEmail("marcus@email.com");
		customer1.setName("Marcus");
		customer1.setAddress(address1);

		repository.saveAndFlush(customer1);

		Address address2 = new Address();
		address2.setZipCode("41785697");
		address2.setStreet("Avenida Paulista");
		address2.setNeighborhood("Pinheiros");
		address2.setState("São Paulo");

		Customer customer2 = new Customer();
		customer2.setPhone("(21)523687412");
		customer2.setEmail("marcus@email.com");
		customer2.setName("Hanna");
		customer2.setAddress(address2);
		
		mvc.perform(post("/savecustomer").content(mapper.writeValueAsString(customer2))
		.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isConflict());
		
	}
	
	@Test
	@Transactional
	public void test_findCustomerById_returns200() throws Exception{
		Address address1 = new Address();
		address1.setZipCode("33658974");
		address1.setStreet("Rua do Catete");
		address1.setNeighborhood("Catete");
		address1.setState("Rio de Janeiro");

		Customer customer1 = new Customer();
		customer1.setPhone("(21)635268547");
		customer1.setEmail("marcus@email.com");
		customer1.setName("Marcus");
		customer1.setAddress(address1);

		repository.saveAndFlush(customer1);
		
		mvc.perform(get("/findcustomer/"+customer1.getId())).andExpect(status().isOk()).
		andExpect(jsonPath("$.name").value("Marcus")).andExpect(jsonPath("$.email").value("marcus@email.com"))
		.andExpect(jsonPath("$.phone").value("(21)635268547"));
	}
	
	@Test
	@Transactional
	public void test_findCustomerById_customerNotFound_returns404() throws Exception{
		Address address1 = new Address();
		address1.setZipCode("33658974");
		address1.setStreet("Rua do Catete");
		address1.setNeighborhood("Catete");
		address1.setState("Rio de Janeiro");

		Customer customer1 = new Customer();
		customer1.setPhone("(21)635268547");
		customer1.setEmail("marcus@email.com");
		customer1.setName("Marcus");
		customer1.setAddress(address1);

		repository.saveAndFlush(customer1);
		
		mvc.perform(get("/findcustomer/2")).andExpect(status().isNotFound());
	}
	
	@Test
	@Transactional
	public void test_deleteCustomerById_returns200() throws Exception{
		Address address1 = new Address();
		address1.setZipCode("33658974");
		address1.setStreet("Rua do Catete");
		address1.setNeighborhood("Catete");
		address1.setState("Rio de Janeiro");

		Customer customer1 = new Customer();
		customer1.setPhone("(21)635268547");
		customer1.setEmail("marcus@email.com");
		customer1.setName("Marcus");
		customer1.setAddress(address1);

		repository.saveAndFlush(customer1);
		
		mvc.perform(delete("/deletecustomer/"+customer1.getId())).andExpect(status().isNoContent());
	}
	
	@Test
	@Transactional
	public void test_deleteCustomerById_customerNotFound_returns404() throws Exception{
		mvc.perform(delete("/deletecustomer/1")).andExpect(status().isNotFound());
	}
	
	@Test
	@Transactional
	public void test_updateCustomer_returns200() throws Exception{
		Address address1 = new Address();
		address1.setZipCode("33658974");
		address1.setStreet("Rua do Catete");
		address1.setNeighborhood("Catete");
		address1.setState("Rio de Janeiro");

		Customer customer1 = new Customer();
		customer1.setPhone("(21)635268547");
		customer1.setEmail("marcus@email.com");
		customer1.setName("Marcus");
		customer1.setAddress(address1);
		repository.saveAndFlush(customer1);
		
		Address address2 = new Address();
		address2.setZipCode("41785697");
		address2.setStreet("Rua ");
		address2.setNeighborhood("Vila Gustavo");
		address2.setState("São Paulo");

		Customer customer2 = new Customer();
		customer2.setPhone("(21)523687412");
		customer2.setEmail("hanna@email.com");
		customer2.setName("Hanna");
		customer2.setAddress(address2);
		
		mvc.perform(put("/updatecustomer/"+customer1.getId()).content(mapper.writeValueAsString(customer2))
		.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(jsonPath("$.name").value("Hanna")).andExpect(jsonPath("$.email").value("hanna@email.com"));
	}
	
}

















