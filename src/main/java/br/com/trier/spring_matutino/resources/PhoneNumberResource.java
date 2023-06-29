package br.com.trier.spring_matutino.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.trier.spring_matutino.domain.Doctor;
import br.com.trier.spring_matutino.domain.Patient;
import br.com.trier.spring_matutino.domain.PhoneNumber;
import br.com.trier.spring_matutino.services.PhoneNumberService;

@RestController
@RequestMapping("/phone-numbers")
public class PhoneNumberResource {
    @Autowired
    private PhoneNumberService phoneNumberService;

    @PostMapping
    public ResponseEntity<PhoneNumber> insert(@RequestBody PhoneNumber phoneNumber) {
        PhoneNumber createdPhoneNumber = phoneNumberService.insert(phoneNumber);
        return ResponseEntity.ok(createdPhoneNumber);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PhoneNumber> update(@PathVariable Integer id, @RequestBody PhoneNumber phoneNumber) {
        phoneNumber.setId(id);
        PhoneNumber updatedPhoneNumber = phoneNumberService.update(phoneNumber);
        return ResponseEntity.ok(updatedPhoneNumber);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        phoneNumberService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<PhoneNumber>> listAll() {
        List<PhoneNumber> phoneNumbers = phoneNumberService.listAll();
        return ResponseEntity.ok(phoneNumbers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhoneNumber> findById(@PathVariable Integer id) {
        PhoneNumber phoneNumber = phoneNumberService.findById(id);
        return ResponseEntity.ok(phoneNumber);
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<PhoneNumber>> findByDoctorId(@PathVariable Doctor doctor) {
        List<PhoneNumber> phoneNumbers = phoneNumberService.findByDoctor(doctor);
        return ResponseEntity.ok(phoneNumbers);
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<PhoneNumber>> findByPatientId(@PathVariable Patient patient) {
        List<PhoneNumber> phoneNumbers = phoneNumberService.findByPatient(patient);
        return ResponseEntity.ok(phoneNumbers);
    }

    @GetMapping("/number/{number}")
    public ResponseEntity<List<PhoneNumber>> findByNumber(@PathVariable String number) {
        List<PhoneNumber> phoneNumbers = phoneNumberService.findByNumber(number);
        return ResponseEntity.ok(phoneNumbers);
    }
}
