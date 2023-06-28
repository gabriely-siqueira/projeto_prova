package br.com.trier.spring_matutino.repositories;


import br.com.trier.spring_matutino.domain.PhoneNumber;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Integer> {
	List<PhoneNumber> findByDoctorId(Integer doctorId);
    List<PhoneNumber> findByPatientId(Integer patientId);
    List<PhoneNumber>  findByNumber(String number);
}

