package br.com.trier.spring_matutino.domain.dto;

import java.util.ArrayList;
import java.util.List;

import br.com.trier.spring_matutino.domain.Address;
import br.com.trier.spring_matutino.domain.Patient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO {
    private Integer id;
    private String name;
    private String email;
    private String cpf;
    private Address address;
    
    public static List<PatientDTO> toDTOList(List<Patient> patients) {
        List<PatientDTO> patientDTOs = new ArrayList<>();
        for (Patient patient : patients) {
            PatientDTO patientDTO = new PatientDTO(patient.getId(), patient.getName(), patient.getEmail(),
                    patient.getCpf(), patient.getAddress());
            patientDTOs.add(patientDTO);
        }
        return patientDTOs;
    }
}
