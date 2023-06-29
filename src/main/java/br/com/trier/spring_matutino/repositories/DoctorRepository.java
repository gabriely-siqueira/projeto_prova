package br.com.trier.spring_matutino.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.spring_matutino.domain.Doctor;
import br.com.trier.spring_matutino.domain.Specialty;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
	List<Doctor> findByNameStartsWithIgnoreCase(String name);

	List<Doctor> findBySpecialty(Specialty specialty);
	
	Optional<Doctor> findByCpf(String cpf);
}
