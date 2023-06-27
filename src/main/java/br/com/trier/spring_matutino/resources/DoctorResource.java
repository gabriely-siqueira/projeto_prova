package br.com.trier.spring_matutino.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.trier.spring_matutino.domain.Doctor;
import br.com.trier.spring_matutino.services.DoctorService;
import br.com.trier.spring_matutino.services.SpecialtyService;


@RestController
@RequestMapping("/doctors")
public class DoctorResource {
	@Autowired
	private DoctorService doctorService;
	@Autowired
	private SpecialtyService specialtyService;

	@PostMapping
	public ResponseEntity<Doctor> insert(@RequestBody Doctor doctor) {
		Doctor createdDoctor = doctorService.insert(doctor);
		return ResponseEntity.ok(createdDoctor);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Doctor> update(@PathVariable Integer id, @RequestBody Doctor doctor) {
		doctor.setId(id);
		Doctor updatedDoctor = doctorService.update(doctor);
		return ResponseEntity.ok(updatedDoctor);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		doctorService.delete(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping
	public ResponseEntity<List<Doctor>> listAll() {
		List<Doctor> doctor = doctorService.listAll();
		return ResponseEntity.ok(doctor);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Doctor> findById(@PathVariable Integer id) {
		Doctor doctor = doctorService.findById(id);
		return ResponseEntity.ok(doctor);
	}

	@GetMapping("/city/{cityId}")
	public ResponseEntity<List<Doctor>> findBySpecialty(Integer specialtyId) {
		List<Doctor> doctor = doctorService.findBySpecialty(specialtyService.findById(specialtyId));
		return ResponseEntity.ok(doctor);
	}

	@GetMapping("/street/{street}")
	public ResponseEntity<List<Doctor>> findByNameStartsWithIgnoreCase(String name) {
		List<Doctor> doctors = doctorService.findByNameStartsWithIgnoreCase(name);
		return ResponseEntity.ok(doctors);
	}
}
