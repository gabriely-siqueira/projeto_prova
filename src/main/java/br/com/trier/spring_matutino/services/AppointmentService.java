package br.com.trier.spring_matutino.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import br.com.trier.spring_matutino.domain.Appointment;

public interface AppointmentService {
	Appointment insert(Appointment appointment);

	List<Appointment> listAll();

	Appointment findById(Integer id);

	Appointment update(Appointment appointment);

	void delete(Integer id);

	List<Appointment> findByDoctorId(Integer doctorId);

	List<Appointment> findByPatientId(Integer patientId);

	List<Appointment> findByDate(LocalDate date);

	List<Appointment> findByDateAndTime(LocalDate date, LocalTime time);

	List<Appointment> findByDateBetween(LocalDate startDate, LocalDate endDate);

	List<Appointment> findByDoctorIdAndDateAndTime(Integer doctorId, LocalDate date, LocalTime time);
;

}
