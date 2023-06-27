package br.com.trier.spring_matutino.services;

import java.util.List;

import br.com.trier.spring_matutino.domain.Specialty;

public interface SpecialtyService {
    Specialty insert(Specialty specialty);

    List<Specialty> listAll();

    Specialty findById(Integer id);

    Specialty update(Specialty specialty);

    void delete(Integer id);

    List<Specialty> findByDescriptionContainingIgnoreCase(String description);
}
