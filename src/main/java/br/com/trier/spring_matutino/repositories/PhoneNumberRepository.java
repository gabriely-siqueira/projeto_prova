package br.com.trier.spring_matutino.repositories;


import br.com.trier.spring_matutino.domain.Doctor;
import br.com.trier.spring_matutino.domain.Patient;
import br.com.trier.spring_matutino.domain.PhoneNumber;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Integer> {
	List<PhoneNumber> findByDoctor(Doctor doctor);
    List<PhoneNumber> findByPatient(Patient patient);
    List<PhoneNumber>  findByNumber(String number);
}

