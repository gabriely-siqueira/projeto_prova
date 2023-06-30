package br.com.trier.spring_matutino.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import br.com.trier.spring_matutino.TestBase;
import br.com.trier.spring_matutino.domain.Appointment;

import br.com.trier.spring_matutino.services.exceptions.IntegrityViolationException;
import br.com.trier.spring_matutino.services.exceptions.ObjectNotFoundException;

@Transactional
public class AppointmentServiceTest extends TestBase {
	@Autowired
	private AppointmentService appointmentService;
	@Autowired
	private DoctorService doctorService;
	@Autowired
	private PatientService patientService;

	@Test
	@DisplayName("Insert Appointment Test")
	@Sql({ "classpath:/resources/sql/appointment_db.sql" })
	void insertTest() {
		Appointment appointment = new Appointment(null, doctorService.findById(1), patientService.findById(1),
				LocalDate.now(), LocalTime.now());
		appointmentService.insert(appointment);
		assertEquals(1, appointmentService.listAll().size());
		assertEquals(1, appointment.getId());
	}

	@Test
	@DisplayName("Find Appointment by ID Test")
	@Sql({ "classpath:/resources/sql/appointment_db.sql", "classpath:/resources/sql/appointment.sql" })
	void findByIdTest() {
		Appointment appointment = appointmentService.findById(1);
		assertThat(appointment).isNotNull();
		assertEquals(1, appointment.getId());
	}
	@Test
	@DisplayName("Insert a duplicate appointment")
	@Sql({ "classpath:/resources/sql/appointment_db.sql", "classpath:/resources/sql/appointment.sql" })
	void insertDuplicatedTest() {
	    Appointment appointment = new Appointment(null, doctorService.findById(2), patientService.findById(2),
	            LocalDate.of(2023, 7, 5), LocalTime.of(10, 0));

	    var exception = assertThrows(IntegrityViolationException.class, () -> appointmentService.insert(appointment));
	    assertEquals("Não é possível marcar uma consulta com um médico que já possui outra consulta agendada na mesma data/hora",
	            exception.getMessage());
	}

	@Test
	@DisplayName("Update Appointment Test")
	@Sql({ "classpath:/resources/sql/appointment_db.sql", "classpath:/resources/sql/appointment.sql" })
	void updateTest() {
		Appointment appointment = new Appointment(1, doctorService.findById(1), patientService.findById(2),
				LocalDate.of(2023, 7, 1), LocalTime.of(9, 0));
		appointmentService.update(appointment);
		assertEquals(1, appointment.getId());
	}

	@Test
	@DisplayName("Find Appointment by Invalid ID Test")
	@Sql({ "classpath:/resources/sql/appointment_db.sql", "classpath:/resources/sql/appointment.sql" })
	void findByIdNotFoundTest() {
		ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class,
				() -> appointmentService.findById(10));
		assertEquals("Consulta 10 não existe", exception.getMessage());
	}

	@Test
	@DisplayName("Delete Appointment Test")
	@Sql({ "classpath:/resources/sql/appointment_db.sql", "classpath:/resources/sql/appointment.sql" })
	void deleteTest() {
		appointmentService.delete(1);
		assertEquals(2, appointmentService.listAll().size());
	}

	@Test
	@DisplayName("Delete Invalid Appointment Test")
	@Sql({ "classpath:/resources/sql/appointment_db.sql", "classpath:/resources/sql/appointment.sql" })
	void deleteInvalidTest() {
		ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class,
				() -> appointmentService.delete(10));
		assertEquals("Consulta 10 não existe", exception.getMessage());
	}

	@Test
	@DisplayName("List All Appointments Test")
	@Sql({ "classpath:/resources/sql/appointment_db.sql", "classpath:/resources/sql/appointment.sql" })
	void listAllTest() {
		assertEquals(3, appointmentService.listAll().size());
	}

	@Test
	@DisplayName("List All Appointments with Empty List Test")
	void listAllEmptyTest() {
		ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class,
				() -> appointmentService.listAll());
		assertEquals("Nenhuma consulta encontrada", exception.getMessage());
	}

	@Test
	@DisplayName("Find Appointments by Doctor ID Test")
	@Sql({ "classpath:/resources/sql/appointment_db.sql", "classpath:/resources/sql/appointment.sql" })
	void findByDoctorIdTest() {
		List<Appointment> appointments = appointmentService.findByDoctorId(1);
		assertThat(appointments).isNotEmpty();

	}

	@Test
	@DisplayName("Find Appointments by Patient ID Test")
	@Sql({ "classpath:/resources/sql/appointment_db.sql", "classpath:/resources/sql/appointment.sql" })
	void findByPatientIdTest() {
		List<Appointment> appointments = appointmentService.findByPatientId(1);
		assertThat(appointments).isNotEmpty();

	}

	@Test
	@DisplayName("Find Appointments by Date Test")
	@Sql({ "classpath:/resources/sql/appointment_db.sql", "classpath:/resources/sql/appointment.sql" })
	void findByDateTest() {
		LocalDate date = LocalDate.of(2023, 6, 30);
		List<Appointment> appointments = appointmentService.findByDate(date);
		assertThat(appointments).isNotEmpty();

	}

	@Test
	@DisplayName("Find Appointments by Date and Time Test")
	@Sql({ "classpath:/resources/sql/appointment_db.sql", "classpath:/resources/sql/appointment.sql" })
	void findByDateAndTimeTest() {
		LocalDate date = LocalDate.of(2023, 6, 30);
		LocalTime time = LocalTime.of(9, 0);
		List<Appointment> appointments = appointmentService.findByDateAndTime(date, time);
		assertThat(appointments).isNotEmpty();

	}

	@Test
	@DisplayName("Find Appointments by Date Range Test")
	@Sql({ "classpath:/resources/sql/appointment_db.sql", "classpath:/resources/sql/appointment.sql" })
	void findByDateBetweenTest() {
		LocalDate startDate = LocalDate.of(2023, 6, 1);
		LocalDate endDate = LocalDate.of(2023, 6, 30);
		List<Appointment> appointments = appointmentService.findByDateBetween(startDate, endDate);
		assertThat(appointments).isNotEmpty();
	}
}
