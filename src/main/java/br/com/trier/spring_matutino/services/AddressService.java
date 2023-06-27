package br.com.trier.spring_matutino.services;

import java.util.List;

import br.com.trier.spring_matutino.domain.Address;
import br.com.trier.spring_matutino.domain.City;

public interface AddressService {
	Address insert(Address address);

	List<Address> listAll();

	Address findById(Integer id);

	Address update(Address address);

	void delete(Integer id);

	List<Address> findByCity(City city);

	List<Address> findByStreetContainingIgnoreCase(String street);

}
