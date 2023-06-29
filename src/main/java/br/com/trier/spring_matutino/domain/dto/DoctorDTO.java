package br.com.trier.spring_matutino.domain.dto;

import java.util.ArrayList;
import java.util.List;

import br.com.trier.spring_matutino.domain.Address;
import br.com.trier.spring_matutino.domain.Doctor;
import br.com.trier.spring_matutino.domain.Specialty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDTO {
	private Integer id;
	private String name;
	private String email;
	private String cpf;
	private Specialty specialty;
	private Address address;

	public static List<DoctorDTO> toDTOList(List<Doctor> doctors) {
		List<DoctorDTO> doctorDTOs = new ArrayList<>();
		for (Doctor doctor : doctors) {
			doctorDTOs.add(doctor.toDTO());
		}
		return doctorDTOs;
	}
}

