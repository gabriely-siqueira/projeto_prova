package br.com.trier.spring_matutino.services.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.spring_matutino.domain.Appointment;
import br.com.trier.spring_matutino.repositories.AppointmentRepository;
import br.com.trier.spring_matutino.services.AppointmentService;
import br.com.trier.spring_matutino.services.exceptions.ObjectNotFoundException;

@Service
public class AppointmentServiceImpl implements AppointmentService {

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Override
	public Appointment insert(Appointment appointment) {
		return appointmentRepository.save(appointment);
	}

	@Override
	public List<Appointment> listAll() {
		List<Appointment> appointments = appointmentRepository.findAll();
		if (appointments.isEmpty()) {
			throw new ObjectNotFoundException("Nenhuma consulta encontrada");
		}
		return appointments;
	}

	@Override
	public Appointment findById(Integer id) {

		return appointmentRepository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Consulta %d não existe".formatted(id)));
	}

	@Override
	public Appointment update(Appointment appointment) {
		validateAppointmentId(appointment.getId());
		return appointmentRepository.save(appointment);
	}

	@Override
	public void delete(Integer id) {
		if (!appointmentRepository.existsById(id)) {
			throw new ObjectNotFoundException("Consulta %d não existe".formatted(id));
		}
		appointmentRepository.deleteById(id);
	}

	@Override
	public List<Appointment> findByDoctorId(Integer doctorId) {
		List<Appointment> appointments = appointmentRepository.findByDoctorId(doctorId);
		if (appointments.isEmpty()) {
			throw new ObjectNotFoundException("Não há consultas com médico %d".formatted(doctorId));
		}
		return appointments;
	}

	@Override
	public List<Appointment> findByPatientId(Integer patientId) {
		List<Appointment> appointments = appointmentRepository.findByPatientId(patientId);
		if (appointments.isEmpty()) {
			throw new ObjectNotFoundException("Não há consultas marcadas com paciente %d".formatted(patientId));
		}
		return appointments;
	}

	@Override
	public List<Appointment> findByDate(LocalDate date) {
		List<Appointment> appointments = appointmentRepository.findByDate(date);
		if (appointments.isEmpty()) {
			throw new ObjectNotFoundException("Não há consultas marcadas no dia " + date);
		}
		return appointments;
	}

	@Override
	public List<Appointment> findByDateAndTime(LocalDate date, LocalTime time) {
		List<Appointment> appointments = appointmentRepository.findByDateAndTime(date, time);
		if (appointments.isEmpty()) {
			throw new ObjectNotFoundException("Não há consultas marcadas no dia: " + date + " as " + time);
		}
		return appointments;
	}

	@Override
	public List<Appointment> findByDateBetween(LocalDate startDate, LocalDate endDate) {
		List<Appointment> appointments = appointmentRepository.findByDateBetween(startDate, endDate);
		if (appointments.isEmpty()) {
			throw new ObjectNotFoundException("Não há consultas marcadas entre " + startDate + " e " + endDate);
		}
		return appointments;
	}

	private void validateAppointmentId(Integer id) {
		if (id == null || !appointmentRepository.existsById(id)) {
			throw new ObjectNotFoundException("Invalid appointment ID: " + id);
		}
	}
}