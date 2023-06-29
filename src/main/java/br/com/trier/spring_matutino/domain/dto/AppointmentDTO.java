package br.com.trier.spring_matutino.domain.dto;

import java.time.LocalDate;
import java.time.LocalTime;

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
public class AppointmentDTO {
    private Integer id;
    private DoctorDTO doctor;
    private PatientDTO patient;
    private LocalDate date;
    private LocalTime time;

   
}
