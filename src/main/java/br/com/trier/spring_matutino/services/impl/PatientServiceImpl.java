package br.com.trier.spring_matutino.services.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import br.com.trier.spring_matutino.domain.Patient;

import br.com.trier.spring_matutino.repositories.PatientRepository;

import br.com.trier.spring_matutino.services.PatientService;

import br.com.trier.spring_matutino.services.exceptions.IntegrityViolationException;
import br.com.trier.spring_matutino.services.exceptions.ObjectNotFoundException;

@Service
public class PatientServiceImpl implements PatientService {

	@Autowired
	PatientRepository patientRepository;

	@Override
	public Patient insert(Patient patient) {
		return patientRepository.save(patient);
	}

	@Override
	public List<Patient> listAll() {
		List<Patient> patients = patientRepository.findAll();
		if (patients.isEmpty()) {
			throw new ObjectNotFoundException("No patients found");
		}
		return patients;
	}

	@Override
	public Patient findById(Integer id) {
		return patientRepository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Patient not found".formatted(id)));
	}

	@Override
	public Patient update(Patient patient) {
		validatepatientId(patient.getId());
		return patientRepository.save(patient);
	}

	@Override
	public void delete(Integer id) {
		if (!patientRepository.existsById(id)) {
			throw new ObjectNotFoundException("Patient not found");
		}
		patientRepository.deleteById(id);
	}



	@Override
	public List<Patient> findByNameStartsWithIgnoreCase(String name) {
		List<Patient> patients = patientRepository.findByNameStartsWithIgnoreCase(name);
		if (patients.size() == 0) {
			throw new ObjectNotFoundException("No patients found with name " + name);
		}
		return patients;
	}

	private void validatepatientId(Integer id) {
		if (id == null) {
			throw new IntegrityViolationException("Patient ID cannot be null");
		}
	}

}
