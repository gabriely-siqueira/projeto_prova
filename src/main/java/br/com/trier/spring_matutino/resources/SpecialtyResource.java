package br.com.trier.spring_matutino.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.trier.spring_matutino.domain.Specialty;
import br.com.trier.spring_matutino.services.SpecialtyService;

@RestController
@RequestMapping("/specialties")
public class SpecialtyResource {
	
	@Autowired
	private SpecialtyService specialtyService;
	
	@PostMapping
	public ResponseEntity<Specialty> insert(@RequestBody Specialty specialty){
		Specialty savedSpecialty = specialtyService.insert(specialty);
		return ResponseEntity.ok(savedSpecialty);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Specialty> update(@PathVariable Integer id, @RequestBody Specialty specialty){
		specialty.setId(id);
		Specialty updatedSpecialty = specialtyService.update(specialty);
		return ResponseEntity.ok(updatedSpecialty);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		specialtyService.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping
	public ResponseEntity<List<Specialty>> listAll(){
		List<Specialty> specialtyList = specialtyService.listAll();
		return ResponseEntity.ok(specialtyList);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Specialty> findById(@PathVariable Integer id){
		Specialty specialty = specialtyService.findById(id);
		return ResponseEntity.ok(specialty);
	}
	
	@GetMapping("/description/{description}")
	public ResponseEntity<List<Specialty>> findByDescriptionContainingIgnoreCase(@PathVariable String description) {
		List<Specialty> specialtyList = specialtyService.findByDescriptionContainingIgnoreCase(description);
		return ResponseEntity.ok(specialtyList);
	}
}
