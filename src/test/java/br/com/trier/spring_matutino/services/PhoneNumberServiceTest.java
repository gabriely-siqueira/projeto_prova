package br.com.trier.spring_matutino.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import br.com.trier.spring_matutino.TestBase;
import br.com.trier.spring_matutino.services.exceptions.ObjectNotFoundException;

@Transactional
public class PhoneNumberServiceTest extends TestBase {

	@Autowired
	private PhoneNumberService service;

	@Autowired
	private DoctorService doctorService;

	@Autowired
	private PatientService patientService;

	@Test
	@DisplayName("Test delete phone number")
	@Sql({ "classpath:resources/sql/phone_number_db.sql" })
	@Sql({ "classpath:resources/sql/phone_number.sql" })
	void deleteTest() {
		service.delete(2);
		assertEquals(2, service.listAll().size());
	}

	@Test
	@DisplayName("Test delete non existent phone number")
	@Sql({ "classpath:resources/sql/phone_number_db.sql" })
	void deleteNotFoundTest() {
		var exception = assertThrows(ObjectNotFoundException.class, () -> service.delete(1));
		assertEquals("Número de telefone 1 inexistente", exception.getMessage());
	}

	@Test
	@DisplayName("Test find by phone number by invalid id")
	@Sql({ "classpath:resources/sql/phone_number_db.sql" })
	@Sql({ "classpath:resources/sql/phone_number.sql" })
	void findByIdTest() {
		var number = service.findById(2);
		assertEquals("(48) 99222-1234", number.getNumber());
	}

	@Test
	@DisplayName("Test find by phone number by id")
	@Sql({ "classpath:resources/sql/phone_number_db.sql" })
	@Sql({ "classpath:resources/sql/phone_number.sql" })
	void findByIdNotFoundTest() {
		var exception = assertThrows(ObjectNotFoundException.class, () -> service.findById(10));
		assertEquals("Número de telefone 10 inexistente", exception.getMessage());
	}

	@Test
	@DisplayName("Test list all phone numbers")
	@Sql({ "classpath:resources/sql/phone_number_db.sql" })
	@Sql({ "classpath:resources/sql/phone_number.sql" })
	void listAllTest() {
		assertEquals(3, service.listAll().size());
	}

	@Test
	@DisplayName("Test list all phone numbers with empty list")
	@Sql({ "classpath:resources/sql/phone_number_db.sql" })
	void listAllEmptyTest() {
		var exception = assertThrows(ObjectNotFoundException.class, () -> service.listAll());
		assertEquals("Não há telefones cadastrados", exception.getMessage());
	}

	@Test
	@DisplayName("Test find phone number by doctor")
	@Sql({ "classpath:resources/sql/phone_number_db.sql" })
	@Sql({ "classpath:resources/sql/phone_number.sql" })
	void findByDoctorTest() {
		var numbers = service.findByDoctor(doctorService.findById(1));
		assertEquals(2, numbers.size());
		assertEquals("(48) 99111-1234", numbers.get(0).getNumber());
	}

	@Test
	@DisplayName("Test Find Phone Numbers by a Doctor Without a Phone")
	@Sql({ "classpath:resources/sql/phone_number_db.sql" })
	@Sql({ "classpath:resources/sql/phone_number.sql" })
	void findByDoctorEmptyTest() {
		var exception = assertThrows(ObjectNotFoundException.class, () -> service.findByDoctor(doctorService.findById(3)));
		assertEquals("Médico 3 não possui número de telefone cadastrado", exception.getMessage());
		
	}

	@Test
	@DisplayName("Test find phone number by patient")
	@Sql({ "classpath:resources/sql/phone_number_db.sql" })
	@Sql({ "classpath:resources/sql/phone_number.sql" })
	void findByPatientTest() {
		var numbers = service.findByPatient(patientService.findById(1));
		assertEquals(1, numbers.size());
		assertEquals("(48) 99333-1234", numbers.get(0).getNumber());
	}

	@Test
	@DisplayName("Test Find Phone Numbers by a patient without a Phone")
	@Sql({ "classpath:resources/sql/phone_number_db.sql" })
	@Sql({ "classpath:resources/sql/phone_number.sql" })
	void findByPatientEmptyTest() {
		var exception = assertThrows(ObjectNotFoundException.class, () -> service.findByPatient(patientService.findById(3)));
		assertEquals("Paciente 3 não possui número de telefone cadastrado", exception.getMessage());
	}
	
	
}
