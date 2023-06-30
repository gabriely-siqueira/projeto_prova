package br.com.trier.spring_matutino.services;

import java.util.List;
import br.com.trier.spring_matutino.domain.Patient;

public interface PatientService {
    Patient insert(Patient patient);

    List<Patient> listAll();

    Patient findById(Integer id);

    Patient update(Patient patient);

    void delete(Integer id);

    List<Patient> findByNameStartsWithIgnoreCase(String name);

    Patient findByCpf(String cpf);

}
