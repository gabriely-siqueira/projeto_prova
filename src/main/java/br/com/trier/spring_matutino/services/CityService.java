package br.com.trier.spring_matutino.services;

import java.util.List;

import br.com.trier.spring_matutino.domain.City;

public interface CityService {
    City insert(City city);

    List<City> listAll();

    City findById(Integer id);

    City update(City city);

    void delete(Integer id);

    List<City> findByNameContainingIgnoreCase(String name);
}
