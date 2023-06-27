package br.com.trier.spring_matutino.services.impl;

import java.util.List;
import java.util.Optional;

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

        List<PhoneNumber> existingNumbers = phoneNumberRepository.findByNumber(phoneNumber.getNumber());
        for (PhoneNumber existingPhoneNumber : existingNumbers) {
            if (!existingPhoneNumber.getId().equals(phoneNumber.getId())) {
                throw new IntegrityViolationException("This phoneNumber already exists");
            }
        }

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
                .orElseThrow(() -> new ObjectNotFoundException("PhoneNumber %d not found".formatted(id)));
    }

    @Override
    public PhoneNumber update(PhoneNumber phoneNumber) {
        validatePhoneNumber(phoneNumber);
        return phoneNumberRepository.save(phoneNumber);
    }

    @Override
    public void delete(Integer id) {
        if (!phoneNumberRepository.existsById(id)) {
            throw new ObjectNotFoundException("PhoneNumber %d not found".formatted(id));
        }
        phoneNumberRepository.deleteById(id);
    }

    @Override
    public List<PhoneNumber> findByDoctorId(Integer doctorId) {
        return phoneNumberRepository.findByDoctorId(doctorId);
    }

    @Override
    public List<PhoneNumber> findByPatientId(Integer patientId) {
        return phoneNumberRepository.findByPatientId(patientId);
    }

    @Override
    public PhoneNumber findByNumber(String number) {
        return phoneNumberRepository.findByNumber(number);
    }
}
