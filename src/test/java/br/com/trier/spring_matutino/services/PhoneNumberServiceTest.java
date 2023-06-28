package br.com.trier.spring_matutino.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import br.com.trier.spring_matutino.TestBase;
import br.com.trier.spring_matutino.domain.PhoneNumber;
import br.com.trier.spring_matutino.services.exceptions.IntegrityViolationException;
import br.com.trier.spring_matutino.services.exceptions.ObjectNotFoundException;

@Transactional
public class PhoneNumberServiceTest extends TestBase {
	@Autowired
	private PhoneNumberService service;

	private PhoneNumber number;


	@Test
	@DisplayName("Test inserting a number")
	void insertTest() {
		service.insert(number);
		List<PhoneNumber> numbers = service.listAll();
		assertEquals(4, numbers.size());
		assertThat(numbers).contains(number);
	}

	@Test
	@DisplayName("Test inserting a duplicated number")
	@Sql({ "classpath:/resources/sql/phone_number.sql" })
	void insertDuplicatedTest() {
		IntegrityViolationException exception = assertThrows(IntegrityViolationException.class,
				() -> service.insert(number));
		assertEquals("This number already exists", exception.getMessage());
	}

	@Test
	@DisplayName("Test inserting an invalid number")
	@Sql({ "classpath:/resources/sql/phone_number.sql" })
	void insertInvalidPhoneNumberNameTest() {
		number.setNumber("643646464643");
		IntegrityViolationException exception = assertThrows(IntegrityViolationException.class,
				() -> service.insert(number));
		assertEquals("Please provide the phone number", exception.getMessage());
	}

	@Test
	@DisplayName("Test inserting an invalid state")
	@Sql({ "classpath:/resources/sql/phone_number.sql" })
	void insertInvalidStateTest() {
		number.setNumber("");
		IntegrityViolationException exception = assertThrows(IntegrityViolationException.class,
				() -> service.insert(number));
		assertEquals("Please provide the number", exception.getMessage());
	}

	@Test
	@DisplayName("Test updating a number")
	@Sql({ "classpath:/resources/sql/phone_number.sql" })
	void updateTest() {
		number.setNumber("Updated Number");
		service.update(number);
		List<PhoneNumber> numbers = service.listAll();
		assertEquals("Updated Number", numbers.get(0).getNumber());
	}

	@Test
	@DisplayName("Test deleting a number")
	@Sql({ "classpath:/resources/sql/phone_number.sql" })
	void deleteTest() {
		service.delete(1);
		List<PhoneNumber> numbers = service.listAll();
		assertEquals(2, numbers.size());
	}

	@Test
	@DisplayName("Test deleting a non-existent number ID")
	@Sql({ "classpath:/resources/sql/phone_number.sql" })
	void deleteInexistentTest() {
		ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> service.delete(10));
		assertEquals("PhoneNumber 10 not found", exception.getMessage());
	}

	@Test
	@DisplayName("Test listing phone numbers")
	@Sql({ "classpath:/resources/sql/phone_number.sql" })
	void listAllTest() {
		List<PhoneNumber> numbers = service.listAll();
		assertEquals(2, numbers.size());
	}

	@Test
	@DisplayName("Test listing empty phone numbers")
	void listAllEmptyTest() {
		ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> service.listAll());
		assertEquals("No numbers found", exception.getMessage());
	}

}