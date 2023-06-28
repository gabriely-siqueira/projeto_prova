package br.com.trier.spring_matutino.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalTime;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import br.com.trier.spring_matutino.TestBase;

import br.com.trier.spring_matutino.domain.Appointment;

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
    @Sql({  "classpath:/resources/sql/appointment_db.sql", "classpath:/resources/sql/appointment.sql" })
    void findByIdTest() {
        Appointment appointment = appointmentService.findById(1);
        assertThat(appointment).isNotNull();
        assertEquals(1, appointment.getId());
        // Verifique outros atributos do objeto Appointment conforme necessário
    }

    @Test
    @DisplayName("Update Appointment Test")
    @Sql({  "classpath:/resources/sql/appointment_db.sql", "classpath:/resources/sql/appointment.sql" })
    void updateTest() {
        Appointment appointment = new Appointment(1, null, null, LocalDate.now(), LocalTime.now());
        appointmentService.update(appointment);

        Appointment updatedAppointment = appointmentService.findById(1);
        // Verifique se o objeto Appointment foi atualizado corretamente
    }

    @Test
    @DisplayName("Find Appointment by Invalid ID Test")
    @Sql({ "classpath:/resources/sql/appointment_db.sql", "classpath:/resources/sql/appointment.sql" })
    void findByIdNotFoundTest() {
        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class,
                () -> appointmentService.findById(10));
        assertEquals("Appointment not found: 10", exception.getMessage());
    }

    @Test
    @DisplayName("Delete Appointment Test")
    @Sql({  "classpath:/resources/sql/appointment_db.sql", "classpath:/resources/sql/appointment.sql" })
    void deleteTest() {
        appointmentService.delete(1);
        assertEquals(2, appointmentService.listAll().size());
    }

    @Test
    @DisplayName("Delete Invalid Appointment Test")
    @Sql({  "classpath:/resources/sql/appointment_db.sql", "classpath:/resources/sql/appointment.sql" })
    void deleteInvalidTest() {
        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class,
                () -> appointmentService.delete(10));
        assertEquals("Appointment not found: 10", exception.getMessage());
    }

    @Test
    @DisplayName("List All Appointments Test")
    @Sql({  "classpath:/resources/sql/appointment_db.sql", "classpath:/resources/sql/appointment.sql" })
    void listAllTest() {
        assertEquals(3, appointmentService.listAll().size());
    }

    @Test
    @DisplayName("List All Appointments with Empty List Test")
    void listAllEmptyTest() {
        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class,
                () -> appointmentService.listAll());
        assertEquals("No appointments found", exception.getMessage());
    }

    // Outros testes necessários podem ser adicionados aqui
}
