package br.com.trier.spring_matutino.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;



import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import br.com.trier.spring_matutino.TestBase;
import br.com.trier.spring_matutino.domain.Patient;
import br.com.trier.spring_matutino.services.exceptions.ObjectNotFoundException;

@SpringBootTest
@Transactional
public class PatientServiceTest extends TestBase {
	
    @Autowired
    private AddressService addressService;
    @Autowired
    private PatientService patientService;

    @Test
    @DisplayName("Insert Patient Test")
    @Sql({ "classpath:resources/sql/city.sql" })
    @Sql({ "classpath:resources/sql/address.sql" })
    void insertTest() {
        Patient patient = new Patient(1, "Paulo Siqueira", "paulosiqueira@example.com","1233667880",
                addressService.findById(1));
        patientService.insert(patient);
        assertEquals(1, patientService.listAll().size());
        assertEquals("Paulo Siqueira", patient.getName());
    }

    @Test
    @DisplayName("Find Patient by ID Test")
    @Sql({ "classpath:resources/sql/city.sql" })
    @Sql({ "classpath:resources/sql/address.sql" })
    @Sql({"classpath:resources/sql/patient.sql" })
    void findByIdTest() {
        Patient patient = patientService.findById(1);
        assertThat(patient).isNotNull();
        assertEquals(1, patient.getId());
        assertEquals("Paulo Siqueira", patient.getName());
    }

    @Test
    @DisplayName("Update Patient Test")
    @Sql({ "classpath:resources/sql/city.sql" })
    @Sql({ "classpath:resources/sql/address.sql" })
    @Sql({"classpath:resources/sql/patient.sql" })
    void updateTest() {
        Patient patient = new Patient(1, "Rogério Braga", "rojeriobraga@example.com","1234123890", 
                addressService.findById(1));
        patientService.update(patient);
        Patient newPatient = patientService.findById(1);
        assertEquals("Rogério Braga", newPatient.getName());
    }

    @Test
    @DisplayName("Find Patient by Invalid ID Test")
    @Sql({ "classpath:resources/sql/city.sql" })
    @Sql({ "classpath:resources/sql/address.sql" })
    @Sql({"classpath:resources/sql/patient.sql" })
    void findByIdNotFoundTest() {
        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class,
                () -> patientService.findById(10));
        assertEquals("Patient not found", exception.getMessage());
    }

    @Test
    @DisplayName("Delete Patient Test")
    @Sql({ "classpath:resources/sql/city.sql" })
    @Sql({ "classpath:resources/sql/address.sql" })
    @Sql({"classpath:resources/sql/patient.sql" })
    void deleteTest() {
        patientService.delete(1);
        assertEquals(2, patientService.listAll().size());
    }

    @Test
    @DisplayName("Delete Invalid Patient Test")
    @Sql({ "classpath:resources/sql/city.sql" })
    @Sql({ "classpath:resources/sql/address.sql" })
    @Sql({"classpath:resources/sql/patient.sql" })
    void deleteInvalidTest() {
        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class,
                () -> patientService.delete(5));
        assertEquals("Patient not found", exception.getMessage());
    }

    @Test
    @DisplayName("List All Patients Test")
    @Sql({ "classpath:resources/sql/city.sql" })
    @Sql({ "classpath:resources/sql/address.sql" })
    @Sql({"classpath:resources/sql/patient.sql" })
    void listAllTest() {
        assertEquals(3, patientService.listAll().size());
    }

    @Test
    @DisplayName("List All Patients with Empty List Test")
    void listAllEmptyTest() {
        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> patientService.listAll());
        assertEquals("No patients found", exception.getMessage());
    }

   
}
