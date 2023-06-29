package br.com.trier.spring_matutino.resources;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.trier.spring_matutino.domain.Appointment;
import br.com.trier.spring_matutino.services.AppointmentService;

@RestController
@RequestMapping("/appointments")
public class AppointmentResource {
    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<Appointment> insert(@RequestBody Appointment appointment) {
        Appointment createdAppointment = appointmentService.insert(appointment);
        return ResponseEntity.ok(createdAppointment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Appointment> update(@PathVariable Integer id, @RequestBody Appointment appointment) {
        appointment.setId(id);
        Appointment updatedAppointment = appointmentService.update(appointment);
        return ResponseEntity.ok(updatedAppointment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        appointmentService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Appointment>> listAll() {
        List<Appointment> appointments = appointmentService.listAll();
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> findById(@PathVariable Integer id) {
        Appointment appointment = appointmentService.findById(id);
        return ResponseEntity.ok(appointment);
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<Appointment>> findByDoctorId(@PathVariable Integer doctorId) {
        List<Appointment> appointments = appointmentService.findByDoctorId(doctorId);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Appointment>> findByPatientId(@PathVariable Integer patientId) {
        List<Appointment> appointments = appointmentService.findByPatientId(patientId);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<Appointment>> findByDate(@PathVariable LocalDate date) {
        List<Appointment> appointments = appointmentService.findByDate(date);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/date/{date}/time/{time}")
    public ResponseEntity<List<Appointment>> findByDateAndTime(
            @PathVariable LocalDate date, @PathVariable LocalTime time) {
        List<Appointment> appointments = appointmentService.findByDateAndTime(date, time);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/date/between")
    public ResponseEntity<List<Appointment>> findByDateBetween(
            @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        List<Appointment> appointments = appointmentService.findByDateBetween(startDate, endDate);
        return ResponseEntity.ok(appointments);
    }
}
