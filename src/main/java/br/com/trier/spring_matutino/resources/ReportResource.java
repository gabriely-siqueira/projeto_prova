package br.com.trier.spring_matutino.resources;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.spring_matutino.domain.Appointment;
import br.com.trier.spring_matutino.services.AppointmentService;
import br.com.trier.spring_matutino.services.exceptions.ObjectNotFoundException;



@RestController
@RequestMapping("/reports")
public class ReportResource {

	 @Autowired
	    private AppointmentService appointmentService;
	 
	 @GetMapping("/appointments")
	 public ResponseEntity<List<Appointment>> getAllAppointmentsReport() {
	     List<Appointment> appointments = appointmentService.listAll();
	     return ResponseEntity.ok(appointments);
	 }
	 
	 @GetMapping("/appointments/{id}")
	 public ResponseEntity<Appointment> getAppointmentByIdReport(@PathVariable Integer id) {
	     Appointment appointment = appointmentService.findById(id);
	     return ResponseEntity.ok(appointment);
	 }
	 
	 @GetMapping("/appointments/doctor/{doctorId}")
	 public ResponseEntity<List<Appointment>> getAppointmentsByDoctorReport(@PathVariable Integer doctorId) {
	     List<Appointment> appointments = appointmentService.findByDoctorId(doctorId);
	     return ResponseEntity.ok(appointments);
	 }
	 @GetMapping("/appointments/date/{date}")
	 public ResponseEntity<List<Appointment>> getAppointmentsByDateReport(@PathVariable LocalDate date) {
	     List<Appointment> appointments = appointmentService.findByDate(date);
	     return ResponseEntity.ok(appointments);
	 }
	 @GetMapping("/appointments/doctor/{doctorId}/date/{date}/time/{time}")
	 public ResponseEntity<List<Appointment>> getAppointmentsByDoctorDateAndTimeReport(
	         @PathVariable Integer doctorId, @PathVariable LocalDate date, @PathVariable LocalTime time) {
	     List<Appointment> appointments = appointmentService.findByDoctorIdAndDateAndTime(doctorId, date, time);
	     return ResponseEntity.ok(appointments);
	 }
	 @GetMapping("/appointments/doctor/{doctorId}/patient/{patientId}")
	 public ResponseEntity<List<Appointment>> getAppointmentsByDoctorAndPatientReport(
	         @PathVariable Integer doctorId, @PathVariable Integer patientId) {

	     List<Appointment> appointments = appointmentService.listAll()
	             .stream()
	             .filter(appointment -> appointment.getDoctor().getId().equals(doctorId) &&
	                     appointment.getPatient().getId().equals(patientId))
	             .toList();

	     if (appointments.isEmpty()) {
	         throw new ObjectNotFoundException(String.format("Não há consultas marcadas com o médico %d e paciente %d",
	                 doctorId, patientId));
	     }

	     return ResponseEntity.ok(appointments);
	 }







	    
	
}