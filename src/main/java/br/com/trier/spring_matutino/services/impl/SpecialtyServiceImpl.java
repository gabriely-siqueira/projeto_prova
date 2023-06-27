package br.com.trier.spring_matutino.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.spring_matutino.domain.Specialty;
import br.com.trier.spring_matutino.repositories.SpecialtyRepository;
import br.com.trier.spring_matutino.services.SpecialtyService;
import br.com.trier.spring_matutino.services.exceptions.IntegrityViolationException;
import br.com.trier.spring_matutino.services.exceptions.ObjectNotFoundException;

@Service
public class SpecialtyServiceImpl implements SpecialtyService {

	@Autowired
	private SpecialtyRepository specialtyRepository;

	@Override
	public Specialty insert(Specialty specialty) {
		validateSpecialty(specialty);
		checkForExistingSpecialty(specialty);
		return specialtyRepository.save(specialty);
	}

	private void validateSpecialty(Specialty specialty) {
		if (specialty == null) {
			throw new IntegrityViolationException("The specialty is null");
		}
		String description = specialty.getDescription();
		if (description == null || description.isBlank()) {
			throw new IntegrityViolationException("Please provide the specialty description");
		}
		if (!isValidDescription(description)) {
			throw new IntegrityViolationException("Invalid specialty description");
		}
	}

	private boolean isValidDescription(String description) {
		String lowercaseDescription = description.toLowerCase();
		return lowercaseDescription.equals("orthology") || lowercaseDescription.equals("gynecology")
				|| lowercaseDescription.equals("cardiology") || lowercaseDescription.equals("dermatology");
	}

	private void checkForExistingSpecialty(Specialty specialty) {
		List<Specialty> existingSpecialties = specialtyRepository
				.findByDescriptionContainingIgnoreCase(specialty.getDescription());
		for (Specialty existingSpecialty : existingSpecialties) {
			if (!existingSpecialty.getId().equals(specialty.getId())) {
				throw new IntegrityViolationException("This specialty already exists");
			}
		}
	}

	@Override
	public List<Specialty> listAll() {
		List<Specialty> specialties = specialtyRepository.findAll();
		if (specialties.isEmpty()) {
			throw new ObjectNotFoundException("No specialties found");
		}
		return specialties;
	}

	@Override
	public Specialty findById(Integer id) {
		return specialtyRepository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Specialty %d not found".formatted(id)));
	}

	@Override
	public Specialty update(Specialty specialty) {
		validateSpecialty(specialty);
		return specialtyRepository.save(specialty);
	}

	@Override
	public void delete(Integer id) {
		if (!specialtyRepository.existsById(id)) {
			throw new ObjectNotFoundException("Specialty %d not found".formatted(id));
		}
		specialtyRepository.deleteById(id);
	}

	@Override
	public List<Specialty> findByDescriptionContainingIgnoreCase(String description) {
		List<Specialty> specialties = specialtyRepository.findByDescriptionContainingIgnoreCase(description);
		if (specialties.isEmpty()) {
			throw new ObjectNotFoundException("No specialty found with description: " + description);
		}
		return specialties;
	}
}