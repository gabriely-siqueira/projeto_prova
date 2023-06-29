package br.com.trier.spring_matutino.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.trier.spring_matutino.domain.Patient;
import br.com.trier.spring_matutino.domain.dto.PatientDTO;
import br.com.trier.spring_matutino.services.PatientService;

@RestController
@RequestMapping("/patients")
public class PatientResource {
    @Autowired
    private PatientService patientService;

    @PostMapping
    public ResponseEntity<Patient> insert(@RequestBody PatientDTO patientDTO) {
        Patient patient = new Patient(patientDTO);
        Patient createdPatient = patientService.insert(patient);
        return ResponseEntity.ok(createdPatient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> update(@PathVariable Integer id, @RequestBody PatientDTO patientDTO) {
        Patient patient = new Patient(patientDTO);
        patient.setId(id);
        Patient updatedPatient = patientService.update(patient);
        return ResponseEntity.ok(updatedPatient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        patientService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<PatientDTO>> listAll() {
        List<Patient> patients = patientService.listAll();
        List<PatientDTO> patientDTOs = PatientDTO.toDTOList(patients);
        return ResponseEntity.ok(patientDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> findById(@PathVariable Integer id) {
        Patient patient = patientService.findById(id);
        PatientDTO patientDTO = patient.toDTO();
        return ResponseEntity.ok(patientDTO);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<PatientDTO>> findByNameStartsWithIgnoreCase(@PathVariable String name) {
        List<Patient> patients = patientService.findByNameStartsWithIgnoreCase(name);
        List<PatientDTO> patientDTOs = PatientDTO.toDTOList(patients);
        return ResponseEntity.ok(patientDTOs);
    }
}
