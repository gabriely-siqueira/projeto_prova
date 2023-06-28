package br.com.trier.spring_matutino.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import br.com.trier.spring_matutino.TestBase;
import br.com.trier.spring_matutino.domain.Doctor;
import br.com.trier.spring_matutino.domain.PhoneNumber;
import br.com.trier.spring_matutino.services.exceptions.ObjectNotFoundException;

@SpringBootTest
@Transactional
public class DoctorServiceTest extends TestBase {
    @Autowired
    private AddressService addressService;

    @Autowired
    private SpecialtyService specialtyService;

    @Autowired
    private DoctorService doctorService;

    @Test
    @DisplayName("Insert Doctor Test")
    @Sql({ "classpath:resources/sql/doctor_db.sql" })
    void insertTest() {
        Doctor doctor = new Doctor();
        doctor.setName("João Silva");
        doctor.setEmail("joaosilva@example.com");

        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setDoctor(doctor);
        phoneNumber.setNumber("1234567890");

        doctor.getPhoneNumbers().add(phoneNumber);

        doctor.setSpecialty(specialtyService.findById(1));
        doctor.setAddress(addressService.findById(1));

        doctorService.insert(doctor);

        assertEquals(1, doctorService.listAll().size());
        assertEquals("João Silva", doctor.getName());
    }

    @Test
    @DisplayName("Find Doctor by ID Test")
    @Sql({ "classpath:resources/sql/doctor_db.sql", "classpath:resources/sql/doctor.sql" })
    void findByIdTest() {
        Doctor doctor = doctorService.findById(1);
        assertThat(doctor).isNotNull();
        assertEquals(1, doctor.getId());
        assertEquals("João Silva", doctor.getName());
    }

    @Test
    @DisplayName("Update Doctor Test")
    @Sql({ "classpath:resources/sql/doctor_db.sql", "classpath:resources/sql/doctor.sql" })
    void updateTest() {
        Doctor doctor = doctorService.findById(1);
        doctor.setName("Cleiton Junior");
        doctor.setEmail("cleitonjunior@example.com");

        // Verifique se a lista de números de telefone não está vazia antes de acessá-la
        if (!doctor.getPhoneNumbers().isEmpty()) {
            PhoneNumber phoneNumber = doctor.getPhoneNumbers().get(0);
            phoneNumber.setNumber("1234567890");
        }

        doctorService.update(doctor);
        Doctor newDoctor = doctorService.findById(1);
        assertEquals("Cleiton Junior", newDoctor.getName());

        // Verifique se a lista de números de telefone não está vazia antes de acessá-la
        if (!newDoctor.getPhoneNumbers().isEmpty()) {
            assertEquals("1234567890", newDoctor.getPhoneNumbers().get(0).getNumber());
        }
    }

    @Test
    @DisplayName("Find Doctor by Invalid ID Test")
    @Sql({ "classpath:resources/sql/doctor_db.sql", "classpath:resources/sql/doctor.sql" })
    void findByIdNotFoundTest() {
        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class,
                () -> doctorService.findById(10));
        assertEquals("Doctor not found", exception.getMessage());
    }

    @Test
    @DisplayName("Delete Doctor Test")
    @Sql({ "classpath:resources/sql/doctor_db.sql", "classpath:resources/sql/doctor.sql" })
    void deleteTest() {
        doctorService.delete(1);
        assertEquals(3, doctorService.listAll().size());
    }

    @Test
    @DisplayName("Delete Invalid Doctor Test")
    @Sql({ "classpath:resources/sql/doctor_db.sql", "classpath:resources/sql/doctor.sql" })
    void deleteInvalidTest() {
        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class,
                () -> doctorService.delete(5));
        assertEquals("Doctor not found", exception.getMessage());
    }

    @Test
    @DisplayName("List All Doctors Test")
    @Sql({ "classpath:resources/sql/doctor_db.sql", "classpath:resources/sql/doctor.sql" })
    void listAllTest() {
        assertEquals(4, doctorService.listAll().size());
    }

    @Test
    @DisplayName("List All Doctors with Empty List Test")
    void listAllEmptyTest() {
        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> doctorService.listAll());
        assertEquals("No doctors found", exception.getMessage());
    }

    @Test
    @DisplayName("Find Doctor by Specialty Test")
    @Sql({ "classpath:resources/sql/doctor_db.sql", "classpath:resources/sql/doctor.sql" })
    void findBySpecialtyTest() {
        List<Doctor> list = doctorService.findBySpecialty(specialtyService.findById(1));
        assertEquals(1, list.size());
    }

    @Test
    @DisplayName("Find Doctor by Invalid Specialty ID Test")
    @Sql({ "classpath:resources/sql/doctor_db.sql", "classpath:resources/sql/doctor.sql" })
    void findBySpecialtyNotFoundTest() {
        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class,
                () -> doctorService.findBySpecialty(specialtyService.findById(10)));
        assertEquals("Specialty 10 not found", exception.getMessage());
    }
}
