package br.com.trier.spring_matutino.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.spring_matutino.domain.Address;
import br.com.trier.spring_matutino.domain.City;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
	List<Address> findByCity(City city);

	List<Address> findByStreetContainingIgnoreCase(String street);

}
