package br.com.trier.spring_matutino.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.trier.spring_matutino.domain.Patient;


@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
	List<Patient> findByNameStartsWithIgnoreCase(String name);
	Optional<Patient> findByCpf(String cpf);

}
