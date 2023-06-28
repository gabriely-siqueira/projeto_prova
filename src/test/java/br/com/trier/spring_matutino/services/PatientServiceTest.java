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
import br.com.trier.spring_matutino.domain.PhoneNumber;
import br.com.trier.spring_matutino.services.exceptions.ObjectNotFoundException;

@SpringBootTest
@Transactional
public class PatientServiceTest extends TestBase {
	
    @Autowired
    private AddressService addressService;
    @Autowired
    private PatientService patientService;

    
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
    @DisplayName("Insert Patient Test")
    @Sql({ "classpath:resources/sql/city.sql" })
    @Sql({ "classpath:resources/sql/address.sql" })
    void insertTest() {
        Patient patient = new Patient();
        patient.setId(1);
        patient.setName("Paulo Siqueira");
        patient.setEmail("paulosiqueira@example.com");
        
        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setPatient(patient);
        phoneNumber.setNumber("1233667880");
        
        patient.getPhoneNumbers().add(phoneNumber);
        
        patient.setAddress(addressService.findById(1));
        
        patientService.insert(patient);
        
        assertEquals(1, patientService.listAll().size());
        assertEquals("Paulo Siqueira", patient.getName());
    }

    @Test
    @DisplayName("Update Patient Test")
    @Sql({ "classpath:resources/sql/city.sql" })
    @Sql({ "classpath:resources/sql/address.sql" })
    @Sql({"classpath:resources/sql/patient.sql" })
    void updateTest() {
        Patient patient = patientService.findById(1);
        patient.setName("Rogério Braga");
        patient.setEmail("rojeriobraga@example.com");

        // Verifique se a lista de números de telefone não está vazia antes de acessá-la
        if (!patient.getPhoneNumbers().isEmpty()) {
            PhoneNumber phoneNumber = patient.getPhoneNumbers().get(0);
            phoneNumber.setNumber("1234123890");
        }

        patientService.update(patient);
        Patient newPatient = patientService.findById(1);
        assertEquals("Rogério Braga", newPatient.getName());

        // Verifique se a lista de números de telefone não está vazia antes de acessá-la
        if (!newPatient.getPhoneNumbers().isEmpty()) {
            assertEquals("1234123890", newPatient.getPhoneNumbers().get(0).getNumber());
        }
    }

    @Test
    @DisplayName("Find Patient by Invalid ID Test")
    @Sql({ "classpath:resources/sql/city.sql" })
    @Sql({ "classpath:resources/sql/address.sql" })
    @Sql({"classpath:resources/sql/patient.sql" })
    void findByIdNotFoundTest() {
        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class,
                () -> patientService.findById(10));
        assertEquals("Patient not found: 10", exception.getMessage());
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
