package br.com.trier.spring_matutino.repositories;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import br.com.trier.spring_matutino.domain.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
	List<Appointment> findByDoctorId(Integer doctorId);

	List<Appointment> findByPatientId(Integer patientId);

	List<Appointment> findByDate(LocalDate date);

	List<Appointment> findByDateAndTime(LocalDate date, LocalTime time);

	List<Appointment> findByDateBetween(LocalDate startDate, LocalDate endDate);

	List<Appointment> findByDoctorIdAndDateAndTime(Integer doctorId, LocalDate date, LocalTime time);
;
}
