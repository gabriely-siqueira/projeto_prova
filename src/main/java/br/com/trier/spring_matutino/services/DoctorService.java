package br.com.trier.spring_matutino.services;

import java.util.List;

import br.com.trier.spring_matutino.domain.Doctor;
import br.com.trier.spring_matutino.domain.Specialty;

public interface DoctorService {
    Doctor insert(Doctor doctor);

    List<Doctor> listAll();

    Doctor findById(Integer id);

    Doctor update(Doctor doctor);

    void delete(Integer id);

    List<Doctor> findByNameStartsWithIgnoreCase(String name);

    List<Doctor> findBySpecialty(Specialty specialty);
}
