package br.com.trier.spring_matutino.services;

import java.util.List;

import br.com.trier.spring_matutino.domain.PhoneNumber;

public interface PhoneNumberService {
	PhoneNumber insert(PhoneNumber phoneNumber);

	List<PhoneNumber> listAll();

	PhoneNumber findById(Integer id);

	PhoneNumber update(PhoneNumber phoneNumber);

	void delete(Integer id);

	List<PhoneNumber> findByDoctorId(Integer doctorId);

	List<PhoneNumber> findByPatientId(Integer patientId);

	List<PhoneNumber> findByNumber(String number);

}
