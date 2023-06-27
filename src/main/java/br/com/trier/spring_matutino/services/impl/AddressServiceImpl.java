package br.com.trier.spring_matutino.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.spring_matutino.domain.Address;
import br.com.trier.spring_matutino.domain.City;

import br.com.trier.spring_matutino.repositories.AddressRepository;
import br.com.trier.spring_matutino.services.AddressService;
import br.com.trier.spring_matutino.services.exceptions.IntegrityViolationException;
import br.com.trier.spring_matutino.services.exceptions.ObjectNotFoundException;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Address insert(Address address) {
        validateAddress(address);
        return addressRepository.save(address);
    }

    private void validateAddress(Address address) {
        if (address == null) {
            throw new IntegrityViolationException("The address is null");
        } else if (address.getStreet() == null || address.getStreet().isBlank()) {
            throw new IntegrityViolationException("Please provide the street name");
        }
    }

    @Override
    public List<Address> listAll() {
        List<Address> addresses = addressRepository.findAll();
        if (addresses.isEmpty()) {
            throw new ObjectNotFoundException("No addresses found");
        }
        return addresses;
    }

    @Override
    public Address findById(Integer id) {
    	return addressRepository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Doctor not found".formatted(id)));
    }

    @Override
    public Address update(Address address) {
        validateAddress(address);
        return addressRepository.save(address);
    }

    @Override
    public void delete(Integer id) {
        if (!addressRepository.existsById(id)) {
            throw new ObjectNotFoundException("Address not found");
        }
        addressRepository.deleteById(id);
    }

    @Override
    public List<Address> findByCity(City city) {
        List<Address> addresses = addressRepository.findByCity(city);
        if (addresses.isEmpty()) {
            throw new ObjectNotFoundException("No addresses found for city: " + city.getName());
        }
        return addresses;
    }

    @Override
    public List<Address> findByStreetContainingIgnoreCase(String street) {
        List<Address> addresses = addressRepository.findByStreetContainingIgnoreCase(street);
        if (addresses.isEmpty()) {
            throw new ObjectNotFoundException("No addresses found with street: " + street);
        }
        return addresses;
    }
}
