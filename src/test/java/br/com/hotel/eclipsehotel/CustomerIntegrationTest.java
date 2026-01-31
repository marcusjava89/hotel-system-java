package br.com.hotel.eclipsehotel;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
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
	public void toListCustomers_returns_200() throws Exception {
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
	public void test_toListCustomers_emptyList_returns_200() throws Exception {
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
}

















