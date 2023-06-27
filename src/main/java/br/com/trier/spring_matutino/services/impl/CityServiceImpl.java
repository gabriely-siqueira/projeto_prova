package br.com.trier.spring_matutino.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.spring_matutino.domain.City;

import br.com.trier.spring_matutino.repositories.CityRepository;
import br.com.trier.spring_matutino.services.CityService;
import br.com.trier.spring_matutino.services.exceptions.IntegrityViolationException;
import br.com.trier.spring_matutino.services.exceptions.ObjectNotFoundException;

@Service
public class CityServiceImpl implements CityService {

	@Autowired
	private CityRepository cityRepository;

	@Override
	public City insert(City city) {
	    validateCity(city);

	    List<City> existingCities = cityRepository.findByNameContainingIgnoreCase(city.getName());
	    for (City existingCity : existingCities) {
	        if (!existingCity.getId().equals(city.getId())) {
	            throw new IntegrityViolationException("This city already exists");
	        }
	    }

	    return cityRepository.save(city);
	}

	private void validateCity(City city) {
		if (city == null) {
			throw new IntegrityViolationException("The city is null");
		}
		if (city.getName() == null || city.getName().isBlank()) {
			throw new IntegrityViolationException("Please provide the city name");
		}
		if (city.getState() == null || city.getState().isBlank()) {
			throw new IntegrityViolationException("Please provide the state");
		}
	}

	@Override
	public List<City> listAll() {
		List<City> cities = cityRepository.findAll();
		if (cities.isEmpty()) {
			throw new ObjectNotFoundException("No cities found");
		}
		return cities;
	}

	@Override
	public City findById(Integer id) {
		return cityRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("City %d not found".formatted(id)));
	}

	@Override
	public City update(City city) {
		validateCity(city);
		return cityRepository.save(city);
	}

	@Override
	public void delete(Integer id) {
		if (!cityRepository.existsById(id)) {
			throw new ObjectNotFoundException("City %d not found".formatted(id));
		}
		cityRepository.deleteById(id);
	}

	@Override
	public List<City> findByNameContainingIgnoreCase(String name) {
		List<City> cities = cityRepository.findByNameContainingIgnoreCase(name);
		if (cities.isEmpty()) {
			throw new ObjectNotFoundException("No cities found with name: " + name);
		}
		return cities;
	}
}
