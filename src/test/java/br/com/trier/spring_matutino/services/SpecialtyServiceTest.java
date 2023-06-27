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
import br.com.trier.spring_matutino.domain.Specialty;
import br.com.trier.spring_matutino.services.exceptions.IntegrityViolationException;
import br.com.trier.spring_matutino.services.exceptions.ObjectNotFoundException;

@Transactional
public class SpecialtyServiceTest extends TestBase {

	@Autowired
	private SpecialtyService service;

	@Test
	@DisplayName("Test inserting a specialty")
	void insertTest() {
		Specialty specialty = new Specialty(null, "orthology");
		service.insert(specialty);
		List<Specialty> specialties = service.listAll();
		assertEquals(1, specialties.size());
		assertEquals("orthology", specialties.get(0).getDescription());
	}

	@Test
	@DisplayName("Test inserting a duplicated specialty")
	@Sql({ "classpath:/resources/sql/specialty.sql" })
	void insertDuplicatedTest() {
		Specialty specialty = new Specialty(null, "orthology");
		IntegrityViolationException exception = assertThrows(IntegrityViolationException.class,
				() -> service.insert(specialty));
		assertEquals("This specialty already exists", exception.getMessage());
	}

	@Test
	@DisplayName("Test inserting an invalid specialty")
	@Sql({ "classpath:/resources/sql/specialty.sql" })
	void insertInvalidTest() {
		Specialty specialty = new Specialty(null, "");
		IntegrityViolationException exception = assertThrows(IntegrityViolationException.class,
				() -> service.insert(specialty));
		assertEquals("Please provide the specialty description", exception.getMessage());
	}

	@Test
	@DisplayName("Test updating a specialty")
	@Sql({ "classpath:/resources/sql/specialty.sql" })
	void updateTest() {
	    Specialty specialty = new Specialty(1, "gynecology");
	    service.update(specialty);
	    List<Specialty> specialties = service.listAll();
	    assertEquals("gynecology", specialties.get(1).getDescription());
	}

	@Test
	@DisplayName("Test deleting a specialty")
	@Sql({ "classpath:/resources/sql/specialty.sql" })
	void deleteTest() {
		service.delete(1);
		List<Specialty> specialties = service.listAll();
		assertEquals(3, specialties.size());
	}

	@Test
	@DisplayName("Test deleting a non-existent specialty ID")
	@Sql({ "classpath:/resources/sql/specialty.sql" })
	void deleteInexistentTest() {
		ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> service.delete(10));
		assertEquals("Specialty 10 not found", exception.getMessage());
	}

	@Test
	@DisplayName("Test listing specialties")
	@Sql({ "classpath:/resources/sql/specialty.sql" })
	void listAllTest() {
		List<Specialty> specialties = service.listAll();
		assertEquals(4, specialties.size());
	}

	@Test
	@DisplayName("Test listing empty specialties")
	void listAllEmptyTest() {
		ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> service.listAll());
		assertEquals("No specialties found", exception.getMessage());
	}

	@Test
	@DisplayName("Test finding specialty by ID")
	@Sql({ "classpath:/resources/sql/specialty.sql" })
	void findByIdTest() {
		Specialty specialty = service.findById(1);
		assertThat(specialty).isNotNull();
		assertEquals("orthology", specialty.getDescription());
	}

	@Test
	@DisplayName("Test finding non-existent specialty by ID")
	@Sql({ "classpath:/resources/sql/specialty.sql" })
	void findByIdNotFoundTest() {
		ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> service.findById(10));
		assertEquals("Specialty 10 not found", exception.getMessage());
	}

	@Test
	@DisplayName("Test finding specialty by name ignoring case (not found)")
	@Sql({ "classpath:/resources/sql/specialty.sql" })
	void findByDescriptionContainingIgnoreCaseNotFoundTest() {
		ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class,
				() -> service.findByDescriptionContainingIgnoreCase("pediatry"));
		assertEquals("No specialty found with description: pediatry", exception.getMessage());
	}

	@Test
	@DisplayName("Test finding specialties by name")
	@Sql({ "classpath:/resources/sql/specialty.sql" })
	void findByDescriptionContainsIgnoreCaseTest() {
		List<Specialty> specialties = service.findByDescriptionContainingIgnoreCase("car");
		assertThat(specialties).isNotNull();
		assertEquals(1, specialties.size());
	}

	@Test
	@DisplayName("Test finding specialties by invalid name")
	@Sql({ "classpath:/resources/sql/specialty.sql" })
	void findByDescriptionContainsIgnoreCaseNotFoundTest() {
		ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class,
				() -> service.findByDescriptionContainingIgnoreCase("w"));
		assertEquals("No specialty found with description: w", exception.getMessage());
	}
}
