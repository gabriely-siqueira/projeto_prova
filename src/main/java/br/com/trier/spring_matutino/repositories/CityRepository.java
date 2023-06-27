package br.com.trier.spring_matutino.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.spring_matutino.domain.City;


@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
	 List<City> findByNameContainingIgnoreCase(String name);
}
