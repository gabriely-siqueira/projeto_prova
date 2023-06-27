package br.com.trier.spring_matutino.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.trier.spring_matutino.domain.City;
import br.com.trier.spring_matutino.services.CityService;

@RestController
@RequestMapping("/cities")
public class CityResource {
	
	private final CityService cityService;
	
	@Autowired
	public CityResource(CityService cityService) {
		this.cityService = cityService;
	}
	
	@PostMapping
	public ResponseEntity<City> insert(@RequestBody City city) {
		City createdCity = cityService.insert(city);
		return ResponseEntity.ok(createdCity);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<City> update(@PathVariable Integer id, @RequestBody City city) {
		city.setId(id);
		City updatedCity = cityService.update(city);
		return ResponseEntity.ok(updatedCity);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		cityService.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping
	public ResponseEntity<List<City>> listAll() {
		List<City> cities = cityService.listAll();
		return ResponseEntity.ok(cities);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<City> findById(@PathVariable Integer id) {
		City city = cityService.findById(id);
		return ResponseEntity.ok(city);
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<List<City>> findByNameContainingIgnoreCase(@PathVariable String name) {
		List<City> cities = cityService.findByNameContainingIgnoreCase(name);
		return ResponseEntity.ok(cities);
	}
}
