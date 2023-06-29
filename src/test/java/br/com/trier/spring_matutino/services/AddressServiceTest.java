package br.com.trier.spring_matutino.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import br.com.trier.spring_matutino.TestBase;
import br.com.trier.spring_matutino.domain.Address;
import br.com.trier.spring_matutino.services.exceptions.ObjectNotFoundException;

@Transactional
public class AddressServiceTest extends TestBase {
	@Autowired
	private AddressService addressService;
	@Autowired
	private CityService cityService;

	@Test
	@DisplayName("Insert Address Test")
	@Sql({ "classpath:/resources/sql/city.sql" })
	void insertTest() {
		Address address = new Address(null, "Example Street 1", "Example Neighborhood 1", "12345-678", "complement 1",
				"1", cityService.findById(1));
		addressService.insert(address);
		assertEquals(1, addressService.listAll().size());
		assertEquals("Example Street 1", address.getStreet());
	}

	@Test
	@DisplayName("Find Address by ID Test")
	@Sql({ "classpath:/resources/sql/city.sql" })
	@Sql({ "classpath:/resources/sql/address.sql"})
	void findByIdTest() {
		Address address = addressService.findById(1);
		assertThat(address).isNotNull();
		assertEquals(1, address.getId());
		assertEquals("Example Street 1", address.getStreet());
	}

	@Test
	@DisplayName("Update Address Test")
	@Sql({ "classpath:/resources/sql/city.sql" })
	@Sql({ "classpath:/resources/sql/address.sql"})
	void updateTest() {
		Address address = new Address(1, "New Street", null, null, null, null, cityService.findById(1));
		addressService.update(address);
		Address newAddress = addressService.findById(1);
		assertEquals("New Street", newAddress.getStreet());
	}

	@Test
	@DisplayName("Find Address by Invalid ID Test")
	@Sql({ "classpath:/resources/sql/city.sql" })
	@Sql({ "classpath:/resources/sql/address.sql"})
	void findByIdNotFoundTest() {
		ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class,
				() -> addressService.findById(10));
		assertEquals("Endereço 10 não foi encontrado", exception.getMessage());
	}

	@Test
	@DisplayName("Delete Address Test")
	@Sql({ "classpath:/resources/sql/city.sql" })
	@Sql({ "classpath:/resources/sql/address.sql" })
	void deleteTest() {
		addressService.delete(1);
		assertEquals(2, addressService.listAll().size());
	}

	@Test
	@DisplayName("Delete Invalid Address Test")
	@Sql({ "classpath:/resources/sql/city.sql" })
	@Sql({ "classpath:/resources/sql/address.sql" })
	void deleteInvalidTest() {
		ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> addressService.delete(5));
		assertEquals("Esse endereço não existe", exception.getMessage());
	}

	@Test
	@DisplayName("List All Addresses Test")
	@Sql({ "classpath:/resources/sql/city.sql" })
	@Sql({ "classpath:/resources/sql/address.sql" })
	void listAllTest() {
		assertEquals(3, addressService.listAll().size());
	}

	@Test
	@DisplayName("List All Addresses with Empty List Test")
	void listAllEmptyTest() {
		ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> addressService.listAll());
		assertEquals("Nenhum endereço encontrado", exception.getMessage());
	}

	@Test
	@DisplayName("Find Address by City Test")
	@Sql({ "classpath:/resources/sql/city.sql" })
	@Sql({ "classpath:/resources/sql/address.sql" })
	void findByCityTest() {
		List<Address> list = addressService.findByCity(cityService.findById(1));
		assertEquals(1, list.size());
	}

	@Test
	@DisplayName("Find Address by Invalid City ID Test")
	@Sql({ "classpath:/resources/sql/city.sql" })
	@Sql({ "classpath:/resources/sql/address.sql"})
	void findByCityNotFoundTest() {
		ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> cityService.findById(10));
		assertEquals("City 10 not found", exception.getMessage());
	}

	@Test
	@DisplayName("Find Address by Street Name Test")
	@Sql({ "classpath:/resources/sql/city.sql" })
	@Sql({ "classpath:/resources/sql/address.sql"})
	void findByStreetContainingIgnoreCaseTest() {
		List<Address> list = addressService.findByStreetContainingIgnoreCase("P");
		assertEquals(3, list.size());
	}

	@Test
	@DisplayName("Find Address by Invalid Street Name Test")
	@Sql({ "classpath:/resources/sql/city.sql" })
	@Sql({ "classpath:/resources/sql/address.sql"})
	void findByStreetContainingIgnoreCaseNotFoundTest() {
		ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class,
				() -> addressService.findByStreetContainingIgnoreCase("J"));
		assertEquals("Nenhuma rua começa com J", exception.getMessage());
	}
}