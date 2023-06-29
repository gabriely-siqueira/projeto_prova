package br.com.trier.spring_matutino.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.trier.spring_matutino.domain.Doctor;
import br.com.trier.spring_matutino.domain.Patient;
import br.com.trier.spring_matutino.domain.PhoneNumber;
import br.com.trier.spring_matutino.repositories.PhoneNumberRepository;
import br.com.trier.spring_matutino.services.PhoneNumberService;
import br.com.trier.spring_matutino.services.exceptions.IntegrityViolationException;
import br.com.trier.spring_matutino.services.exceptions.ObjectNotFoundException;


@Service
public class PhoneNumberServiceImpl implements PhoneNumberService {
	@Autowired
    private PhoneNumberRepository repository;

    @Override
    public PhoneNumber insert(PhoneNumber number) {
        validatePhoneNumber(number);
        return repository.save(number);
    }

    private void validatePhoneNumber(PhoneNumber number) {
        if (number == null) {
            throw new IntegrityViolationException("O telefone não pode ser nulo");
        }
        validatePhoneNumberExistence(number);
    }

    private void validatePhoneNumberExistence(PhoneNumber number) {
        List<PhoneNumber> numbersFound = repository.findByNumber(number.getNumber());
        if (!numbersFound.isEmpty()) {
            for (PhoneNumber t : numbersFound) {
                if (number.getPatient() != null && t.getPatient() != null
                        && (number.getId() == null || !t.getId().equals(number.getId()))) {
                    throw new IntegrityViolationException("Esse telefone já existe");
                } else if (number.getDoctor() != null && t.getDoctor() != null
                        && (number.getId() == null || !t.getId().equals(number.getId()))) {
                    throw new IntegrityViolationException("Esse telefone já existe");
                } else if (number.getPatient() == null && t.getDoctor() == null
                        && !t.getPatient().getCpf().equals(number.getDoctor().getCpf())) {
                    throw new IntegrityViolationException("Esse telefone já existe");
                } else if (t.getPatient() == null && number.getDoctor() == null
                        && !t.getDoctor().getCpf().equals(number.getPatient().getCpf())) {
                    throw new IntegrityViolationException("Esse telefone já existe");
                }
            }
        }
    }

    @Override
    public PhoneNumber update(PhoneNumber number) {
        if (!listAll().contains(number)) {
            throw new ObjectNotFoundException("Número de telefone inexistente");
        }
        return insert(number);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(findById(id));
    }

    @Override
    public PhoneNumber findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Número de telefone %s inexistente".formatted(id)));
    }

    @Override
    public List<PhoneNumber> listAll() {
        List<PhoneNumber> numbers = repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        if (numbers.isEmpty()) {
            throw new ObjectNotFoundException("Não há telefones cadastrados");
        }
        return numbers;
    }

    @Override
    public List<PhoneNumber> findByDoctor(Doctor doctor) {
        List<PhoneNumber> numbers = repository.findByDoctor(doctor);
        if (numbers.isEmpty()) {
            throw new ObjectNotFoundException(
                    "Médico %s não possui número de telefone cadastrado".formatted(doctor.getId()));
        }
        return numbers;
    }

    @Override
    public List<PhoneNumber> findByPatient(Patient patient) {
        List<PhoneNumber> numbers = repository.findByPatient(patient);
        if (numbers.isEmpty()) {
            throw new ObjectNotFoundException(
                    "Paciente %s não possui número de telefone cadastrado".formatted(patient.getId()));
        }
        return numbers;
    }

    @Override
    public List<PhoneNumber> findByNumber(String number) {
        List<PhoneNumber> numbers = repository.findByNumber(number);
        if (numbers.isEmpty()) {
            throw new ObjectNotFoundException("Número de telefone inexistente");
        }
        return numbers;
    }
}
