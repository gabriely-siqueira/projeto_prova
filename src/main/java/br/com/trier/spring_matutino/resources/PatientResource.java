package br.com.trier.spring_matutino.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.trier.spring_matutino.domain.Patient;
import br.com.trier.spring_matutino.services.PatientService;

@RestController
@RequestMapping("/patients")
public class PatientResource {
	@Autowired
	private PatientService patientService;

	@PostMapping
	public ResponseEntity<Patient> insert(@RequestBody Patient patient) {
		Patient createdPatient = patientService.insert(patient);
		return ResponseEntity.ok(createdPatient);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Patient> update(@PathVariable Integer id, @RequestBody Patient patient) {
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
	public ResponseEntity<List<Patient>> listAll() {
		List<Patient> patients = patientService.listAll();
		return ResponseEntity.ok(patients);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Patient> findById(@PathVariable Integer id) {
		Patient patient = patientService.findById(id);
		return ResponseEntity.ok(patient);
	}

	@GetMapping("/name/{name}")
	public ResponseEntity<List<Patient>> findByNameStartsWithIgnoreCase(@PathVariable String name) {
		List<Patient> patients = patientService.findByNameStartsWithIgnoreCase(name);
		return ResponseEntity.ok(patients);
	}
}
