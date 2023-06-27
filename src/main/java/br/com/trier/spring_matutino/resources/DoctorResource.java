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
    private final DoctorService doctorService;
    private final SpecialtyService specialtyService;

    @Autowired
    public DoctorResource(DoctorService doctorService, SpecialtyService specialtyService) {
        this.doctorService = doctorService;
        this.specialtyService = specialtyService;
    }

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
        List<Doctor> doctors = doctorService.listAll();
        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> findById(@PathVariable Integer id) {
        Doctor doctor = doctorService.findById(id);
        return ResponseEntity.ok(doctor);
    }

    @GetMapping("/specialty/{specialtyId}")
    public ResponseEntity<List<Doctor>> findBySpecialty(@PathVariable Integer specialtyId) {
        List<Doctor> doctors = doctorService.findBySpecialty(specialtyService.findById(specialtyId));
        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Doctor>> findByNameStartsWithIgnoreCase(@PathVariable String name) {
        List<Doctor> doctors = doctorService.findByNameStartsWithIgnoreCase(name);
        return ResponseEntity.ok(doctors);
    }
}
