package br.com.trier.spring_matutino.services;

import java.util.List;

import br.com.trier.spring_matutino.domain.Doctor;
import br.com.trier.spring_matutino.domain.Patient;
import br.com.trier.spring_matutino.domain.PhoneNumber;

public interface PhoneNumberService {
	PhoneNumber insert(PhoneNumber phoneNumber);

	List<PhoneNumber> listAll();

	PhoneNumber findById(Integer id);

	PhoneNumber update(PhoneNumber phoneNumber);

	void delete(Integer id);

	List<PhoneNumber> findByDoctor(Doctor doctor);

	List<PhoneNumber> findByPatient(Patient patient);

	List<PhoneNumber> findByNumber(String number);
}
