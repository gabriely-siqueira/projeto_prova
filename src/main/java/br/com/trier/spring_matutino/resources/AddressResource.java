package br.com.trier.spring_matutino.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.trier.spring_matutino.domain.Address;
import br.com.trier.spring_matutino.services.AddressService;
import br.com.trier.spring_matutino.services.CityService;

@RestController
@RequestMapping("/addresses")
public class AddressResource {
	@Autowired
	private AddressService addressService;
	@Autowired
	private CityService cityService;

	@PostMapping
	public ResponseEntity<Address> insert(@RequestBody Address address) {
		Address createdAddress = addressService.insert(address);
		return ResponseEntity.ok(createdAddress);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Address> update(@PathVariable Integer id, @RequestBody Address address) {
		address.setId(id);
		Address updatedAddress = addressService.update(address);
		return ResponseEntity.ok(updatedAddress);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		addressService.delete(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping
	public ResponseEntity<List<Address>> listAll() {
		List<Address> addresses = addressService.listAll();
		return ResponseEntity.ok(addresses);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Address> findById(@PathVariable Integer id) {
		Address address = addressService.findById(id);
		return ResponseEntity.ok(address);
	}

	@GetMapping("/city/{cityId}")
	public ResponseEntity<List<Address>> findByCity(@PathVariable("cityId") Integer cityId) {
		List<Address> addresses = addressService.findByCity(cityService.findById(cityId));
		return ResponseEntity.ok(addresses);
	}

	@GetMapping("/street/{street}")
	public ResponseEntity<List<Address>> findByStreetContainingIgnoreCase(@PathVariable String street) {
		List<Address> addresses = addressService.findByStreetContainingIgnoreCase(street);
		return ResponseEntity.ok(addresses);
	}
}
