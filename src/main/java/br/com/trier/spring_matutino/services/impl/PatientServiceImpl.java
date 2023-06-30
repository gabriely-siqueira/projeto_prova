package br.com.trier.spring_matutino.services.impl;

import java.util.List;
import java.util.Optional;

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
    private PatientRepository patientRepository;

    @Override
    public Patient insert(Patient patient) {
    	validatePatient(patient);
        return patientRepository.save(patient);
    }
    
    private void validatePatient(Patient patient) {
		  if(patient == null) {
		   throw new IntegrityViolationException("O paciente não pode ser nulo");
		  } else if(patient.getName() == null || patient.getName().isBlank()) {
		   throw new IntegrityViolationException("Preencha o nome do paciente");
		  } else if(patient.getCpf() == null || patient.getCpf().isBlank()) {
		   throw new IntegrityViolationException("Preencha o CPF do paciente");
		  }
		  validateCpf(patient);
		 }
			
		 private void validateCpf(Patient patient) {
		  Optional<Patient> patientFound = patientRepository.findByCpf(patient.getCpf());
		  if(patientFound.isPresent()) {
		   if(patientFound.get().getId() != patient.getId()) {
		    throw new IntegrityViolationException("O CPF desse paciente já existe");
		   }
		  }
		 }

    @Override
    public List<Patient> listAll() {
        List<Patient> patients = patientRepository.findAll();
        if (patients.isEmpty()) {
            throw new ObjectNotFoundException("Nenhum paciente encontrado");
        }
        return patients;
    }

    @Override
    public Patient findById(Integer id) {
        return patientRepository.findById(id)
        		.orElseThrow(() -> new ObjectNotFoundException("Paciente %d não foi encontrado".formatted(id)));
    }

    @Override
    public Patient update(Patient patient) {
    	if(!listAll().contains(patient)) {
			throw new ObjectNotFoundException("Paciente não existe");
		}
        return patientRepository.save(patient);
    }

    @Override
    public void delete(Integer id) {
        if (!patientRepository.existsById(id)) {
            throw new ObjectNotFoundException("Paciente não existe");
        }
        patientRepository.deleteById(id);
    }

    @Override
    public List<Patient> findByNameStartsWithIgnoreCase(String name) {
        List<Patient> patients = patientRepository.findByNameStartsWithIgnoreCase(name);
        if (patients.isEmpty()) {
            throw new ObjectNotFoundException("Não há pacientes com o nome: " + name);
        }
        return patients;
    }
    @Override
	public Patient findByCpf(String cpf) {
		return patientRepository.findByCpf(cpf)
				.orElseThrow(() -> new ObjectNotFoundException("Paciente %s inexistente".formatted(cpf)));
	}

   
}
