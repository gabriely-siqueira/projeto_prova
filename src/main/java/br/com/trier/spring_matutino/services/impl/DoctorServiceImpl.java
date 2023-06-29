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
		validateDoctor(doctor);
		return doctorRepository.save(doctor);
	}

	private void validateDoctor(Doctor doctor) {
		if (doctor == null) {
			throw new IntegrityViolationException("O médico não pode ser nulo");
		} else if (doctor.getName() == null || doctor.getName().isBlank()) {
			throw new IntegrityViolationException("Preencha o nome do médico");
		} else if (doctor.getCpf() == null || doctor.getCpf().isBlank()) {
			throw new IntegrityViolationException("Preencha o CPF do médico");
		}
		validateCpf(doctor);
	}

	private void validateCpf(Doctor doctor) {
		Optional<Doctor> doctorFound = doctorRepository.findByCpf(doctor.getCpf());
		if (doctorFound.isPresent() && !doctorFound.get().getId().equals(doctor.getId())) {
			throw new IntegrityViolationException("O CPF desse médico já existe");
		}
	}

	@Override
	public List<Doctor> listAll() {
		List<Doctor> doctors = doctorRepository.findAll();
		if (doctors.isEmpty()) {
			throw new ObjectNotFoundException("Nenhum médico encontrado");
		}
		return doctors;
	}

	@Override
	public Doctor findById(Integer id) {
		return doctorRepository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Médico %d não foi encontrado".formatted(id)));
	}

	@Override
	public Doctor update(Doctor doctor) {
		if (!listAll().contains(doctor)) {
			throw new ObjectNotFoundException("Médico não existe");
		}
		return doctorRepository.save(doctor);
	}

	@Override
	public void delete(Integer id) {
		if (!doctorRepository.existsById(id)) {
			throw new ObjectNotFoundException("Médico %d não existe".formatted(id));
		}
		doctorRepository.deleteById(id);
	}

	@Override
	public List<Doctor> findBySpecialty(Specialty specialty) {
		List<Doctor> doctors = doctorRepository.findBySpecialty(specialty);
		if (doctors.isEmpty()) {
			throw new ObjectNotFoundException("Não há médicos com a especialidade: " + specialty.getDescription());
		}
		return doctors;
	}

	@Override
	public List<Doctor> findByNameStartsWithIgnoreCase(String name) {
		List<Doctor> doctors = doctorRepository.findByNameStartsWithIgnoreCase(name);
		if (doctors.isEmpty()) {
			throw new ObjectNotFoundException("Não há médicos com o nome " + name);
		}
		return doctors;
	}

	@Override
	public Doctor findByCpf(String cpf) {
		return doctorRepository.findByCpf(cpf)
				.orElseThrow(() -> new ObjectNotFoundException("Médico %s inexistente".formatted(cpf)));
	}

}
