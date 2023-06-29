package br.com.trier.spring_matutino.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

	@Autowired
	private DoctorService doctorService;

	@Autowired
	private PatientService patientService;

	@Test
	@DisplayName("Teste deletar número de telefone")
	@Sql({ "classpath:resources/sql/phone_number_db.sql" })
	@Sql({ "classpath:resources/sql/phone_number.sql" })
	void deleteTest() {
		service.delete(2);
		assertEquals(2, service.listAll().size());
	}

	@Test
	@DisplayName("Teste deletar número de telefone inexistente")
	@Sql({ "classpath:resources/sql/phone_number_db.sql" })
	void deleteNotFoundTest() {
		var exception = assertThrows(ObjectNotFoundException.class, () -> service.delete(1));
		assertEquals("Número de telefone 1 inexistente", exception.getMessage());
	}

	@Test
	@DisplayName("Teste buscar número de telefone pelo ID")
	@Sql({ "classpath:resources/sql/phone_number_db.sql" })
	@Sql({ "classpath:resources/sql/phone_number.sql" })
	void findByIdTest() {
		var number = service.findById(2);
		assertEquals("(48) 99222-1234", number.getNumber());
	}

	@Test
	@DisplayName("Teste buscar número de telefone por ID inexistente")
	@Sql({ "classpath:resources/sql/phone_number_db.sql" })
	@Sql({ "classpath:resources/sql/phone_number.sql" })
	void findByIdNotFoundTest() {
		var exception = assertThrows(ObjectNotFoundException.class, () -> service.findById(1));
		assertEquals("Número de telefone 1 inexistente", exception.getMessage());
	}

	@Test
	@DisplayName("Teste buscar todos os números de telefone")
	@Sql({ "classpath:resources/sql/phone_number_db.sql" })
	@Sql({ "classpath:resources/sql/phone_number.sql" })
	void listAllTest() {
		assertEquals(3, service.listAll().size());
	}

	@Test
	@DisplayName("Teste buscar todos os números de telefone com lista vazia")
	@Sql({ "classpath:resources/sql/phone_number_db.sql" })
	void listAllEmptyTest() {
		var exception = assertThrows(ObjectNotFoundException.class, () -> service.listAll());
		assertEquals("Não há telefones cadastrados", exception.getMessage());
	}

	@Test
	@DisplayName("Teste buscar números de telefone de um médico")
	@Sql({ "classpath:resources/sql/phone_number_db.sql" })
	@Sql({ "classpath:resources/sql/phone_number.sql" })
	void findByDoctorTest() {
		var numbers = service.findByDoctor(doctorService.findById(1));
		assertEquals(2, numbers.size());
		assertEquals("(48) 99111-1234", numbers.get(0).getNumber());
	}

	@Test
	@DisplayName("Teste buscar números de telefone de um médico que não possui telefone")
	@Sql({ "classpath:resources/sql/phone_number_db.sql" })
	void findByDoctorEmptyTest() {
		var numbers = service.findByDoctor(doctorService.findById(3));
		assertEquals(0, numbers.size());
	}

	@Test
	@DisplayName("Teste buscar números de telefone de um paciente")
	@Sql({ "classpath:resources/sql/phone_number_db.sql" })
	@Sql({ "classpath:resources/sql/phone_number.sql" })
	void findByPatientTest() {
		var numbers = service.findByPatient(patientService.findById(1));
		assertEquals(1, numbers.size());
		assertEquals("(48) 99333-1234", numbers.get(0).getNumber());
	}

	@Test
	@DisplayName("Teste buscar números de telefone de um paciente que não possui telefone")
	@Sql({ "classpath:resources/sql/phone_number_db.sql" })
	void findByPatientEmptyTest() {
		var numbers = service.findByPatient(patientService.findById(1));
		assertEquals(0, numbers.size());
	}
}
