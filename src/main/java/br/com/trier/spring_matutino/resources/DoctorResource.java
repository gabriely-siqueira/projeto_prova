package br.com.trier.spring_matutino.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.trier.spring_matutino.domain.Doctor;
import br.com.trier.spring_matutino.domain.dto.DoctorDTO;
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
    public ResponseEntity<DoctorDTO> insert(@RequestBody DoctorDTO doctorDTO) {
        Doctor doctor = new Doctor(doctorDTO);
        Doctor createdDoctor = doctorService.insert(doctor);
        DoctorDTO createdDoctorDTO = createdDoctor.toDTO();
        return ResponseEntity.ok(createdDoctorDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorDTO> update(@PathVariable Integer id, @RequestBody DoctorDTO doctorDTO) {
        Doctor doctor = new Doctor(doctorDTO);
        doctor.setId(id);
        Doctor updatedDoctor = doctorService.update(doctor);
        DoctorDTO updatedDoctorDTO = updatedDoctor.toDTO();
        return ResponseEntity.ok(updatedDoctorDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        doctorService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<DoctorDTO>> listAll() {
        List<Doctor> doctors = doctorService.listAll();
        List<DoctorDTO> doctorDTOs = DoctorDTO.toDTOList(doctors);
        return ResponseEntity.ok(doctorDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> findById(@PathVariable Integer id) {
        Doctor doctor = doctorService.findById(id);
        DoctorDTO doctorDTO = doctor.toDTO();
        return ResponseEntity.ok(doctorDTO);
    }

    @GetMapping("/specialty/{specialtyId}")
    public ResponseEntity<List<DoctorDTO>> findBySpecialty(@PathVariable Integer specialtyId) {
        List<Doctor> doctors = doctorService.findBySpecialty(specialtyService.findById(specialtyId));
        List<DoctorDTO> doctorDTOs = DoctorDTO.toDTOList(doctors);
        return ResponseEntity.ok(doctorDTOs);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<DoctorDTO>> findByNameStartsWithIgnoreCase(@PathVariable String name) {
        List<Doctor> doctors = doctorService.findByNameStartsWithIgnoreCase(name);
        List<DoctorDTO> doctorDTOs = DoctorDTO.toDTOList(doctors);
        return ResponseEntity.ok(doctorDTOs);
    }
}
