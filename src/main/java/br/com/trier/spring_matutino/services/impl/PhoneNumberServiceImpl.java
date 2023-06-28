package br.com.trier.spring_matutino.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.spring_matutino.domain.PhoneNumber;
import br.com.trier.spring_matutino.repositories.PhoneNumberRepository;
import br.com.trier.spring_matutino.services.PhoneNumberService;
import br.com.trier.spring_matutino.services.exceptions.IntegrityViolationException;
import br.com.trier.spring_matutino.services.exceptions.ObjectNotFoundException;

@Service
public class PhoneNumberServiceImpl implements PhoneNumberService {
    @Autowired
    private PhoneNumberRepository phoneNumberRepository;

    @Override
    public PhoneNumber insert(PhoneNumber phoneNumber) {
        validatePhoneNumber(phoneNumber);
        checkIfPhoneNumberExists(phoneNumber);
        return phoneNumberRepository.save(phoneNumber);
    }

    private void validatePhoneNumber(PhoneNumber phoneNumber) {
        if (phoneNumber == null) {
            throw new IntegrityViolationException("The phoneNumber is null");
        }
        if (phoneNumber.getNumber() == null || phoneNumber.getNumber().isBlank()) {
            throw new IntegrityViolationException("Please provide the phone number");
        }
    }

    private void checkIfPhoneNumberExists(PhoneNumber phoneNumber) {
        List<PhoneNumber> existingNumbers = phoneNumberRepository.findByNumber(phoneNumber.getNumber());
        for (PhoneNumber existingPhoneNumber : existingNumbers) {
            if (!existingPhoneNumber.getId().equals(phoneNumber.getId())) {
                throw new IntegrityViolationException("This phoneNumber already exists");
            }
        }
    }

    @Override
    public List<PhoneNumber> listAll() {
        List<PhoneNumber> numbers = phoneNumberRepository.findAll();
        if (numbers.isEmpty()) {
            throw new ObjectNotFoundException("No numbers found");
        }
        return numbers;
    }

    @Override
    public PhoneNumber findById(Integer id) {
        return phoneNumberRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("PhoneNumber " + id + " not found"));
    }

    @Override
    public PhoneNumber update(PhoneNumber phoneNumber) {
        validatePhoneNumber(phoneNumber);
        return phoneNumberRepository.save(phoneNumber);
    }

    @Override
    public void delete(Integer id) {
        if (!phoneNumberRepository.existsById(id)) {
            throw new ObjectNotFoundException("PhoneNumber " + id + " not found");
        }
        phoneNumberRepository.deleteById(id);
    }

    @Override
    public List<PhoneNumber> findByDoctorId(Integer doctorId) {
        List<PhoneNumber> doctorNumbers = phoneNumberRepository.findByDoctorId(doctorId);
        if (doctorNumbers.isEmpty()) {
            throw new ObjectNotFoundException("No phone numbers found for this doctor");
        }
        return doctorNumbers;
    }

    @Override
    public List<PhoneNumber> findByPatientId(Integer patientId) {
        List<PhoneNumber> patientNumbers = phoneNumberRepository.findByPatientId(patientId);
        if (patientNumbers.isEmpty()) {
            throw new ObjectNotFoundException("No phone numbers found for this patient");
        }
        return patientNumbers;
    }

    @Override
    public List<PhoneNumber> findByNumber(String number) {
        List<PhoneNumber> numbers = phoneNumberRepository.findByNumber(number);
        if (numbers.isEmpty()) {
            throw new ObjectNotFoundException("No phone found with this number: " + number);
        }
        return numbers;
    }
}
