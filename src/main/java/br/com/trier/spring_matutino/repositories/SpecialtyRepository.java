package br.com.trier.spring_matutino.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.spring_matutino.domain.Specialty;

@Repository
public interface SpecialtyRepository extends JpaRepository<Specialty, Integer> {
	List<Specialty> findByDescriptionContainingIgnoreCase(String description);
}
