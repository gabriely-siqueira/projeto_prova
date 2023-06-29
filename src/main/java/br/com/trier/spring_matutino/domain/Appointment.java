package br.com.trier.spring_matutino.domain;

import java.time.LocalDate;
import java.time.LocalTime;

import br.com.trier.spring_matutino.domain.dto.AppointmentDTO;
import br.com.trier.spring_matutino.domain.dto.DoctorDTO;
import br.com.trier.spring_matutino.domain.dto.PatientDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "time")
    private LocalTime time;

    public Appointment(AppointmentDTO appointmentDTO) {
        this.id = appointmentDTO.getId();
        this.doctor = appointmentDTO.getDoctor() != null ? new Doctor(appointmentDTO.getDoctor()) : null;
        this.patient = appointmentDTO.getPatient() != null ? new Patient(appointmentDTO.getPatient()) : null;
        this.date = appointmentDTO.getDate();
        this.time = appointmentDTO.getTime();
    }

    public AppointmentDTO toDTO() {
        DoctorDTO doctorDTO = doctor != null ? doctor.toDTO() : null;
        PatientDTO patientDTO = patient != null ? patient.toDTO() : null;
        return new AppointmentDTO(id, doctorDTO, patientDTO, date, time);
    }
}
