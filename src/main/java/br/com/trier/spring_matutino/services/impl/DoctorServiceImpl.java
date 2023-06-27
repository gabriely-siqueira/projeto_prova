package br.com.trier.spring_matutino.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.spring_matutino.domain.Doctor;
import br.com.trier.spring_matutino.domain.Specialty;
import br.com.trier.spring_matutino.repositories.DoctorRepository;
import br.com.trier.spring_matutino.services.DoctorService;
import br.com.trier.spring_matutino.services.exceptions.IntegrityViolationException;
import br.com.trier.spring_matutino.services.exceptions.ObjectNotFoundException;

@Service
public class DoctorServiceImpl implements DoctorService {

	@Autowired
	DoctorRepository doctorRepository;

	@Override
	public Doctor insert(Doctor doctor) {
		return doctorRepository.save(doctor);
	}

	@Override
	public List<Doctor> listAll() {
		List<Doctor> doctors = doctorRepository.findAll();
		if (doctors.isEmpty()) {
			throw new ObjectNotFoundException("No doctors found");
		}
		return doctors;
	}

	@Override
	public Doctor findById(Integer id) {
		return doctorRepository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Doctor not found".formatted(id)));
	}

	@Override
	public Doctor update(Doctor doctor) {
		validateDoctorId(doctor.getId());
		return doctorRepository.save(doctor);
	}

	@Override
	public void delete(Integer id) {
		if (!doctorRepository.existsById(id)) {
			throw new ObjectNotFoundException("Doctor not found");
		}
		doctorRepository.deleteById(id);
	}

	@Override
	public List<Doctor> findBySpecialty(Specialty specialty) {
		List<Doctor> doctors = doctorRepository.findBySpecialty(specialty);
		if (doctors.isEmpty()) {
			throw new ObjectNotFoundException("No doctors found for specialty: " + specialty.getDescription());
		}
		return doctors;
	}

	@Override
	public List<Doctor> findByNameStartsWithIgnoreCase(String name) {
		List<Doctor> doctors = doctorRepository.findByNameStartsWithIgnoreCase(name);
		if (doctors.size() == 0) {
			throw new ObjectNotFoundException("No doctors found with name " + name);
		}
		return doctors;
	}

	private void validateDoctorId(Integer id) {
		if (id == null) {
			throw new IntegrityViolationException("Doctor ID cannot be null");
		}
	}
}
