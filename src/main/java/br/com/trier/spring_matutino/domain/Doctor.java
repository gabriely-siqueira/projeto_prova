package br.com.trier.spring_matutino.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import br.com.trier.spring_matutino.domain.dto.DoctorDTO;
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
@Entity(name = "doctor")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "email", unique = true)
    private String email;
    
    @Column(name = "cpf", unique = true)
    private String cpf;

    @ManyToOne
    @JoinColumn(name = "specialty_id")
    private Specialty specialty;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    public Doctor(DoctorDTO doctorDTO) {
        this.name = doctorDTO.getName();
        this.email = doctorDTO.getEmail();
        this.cpf = doctorDTO.getCpf();
        this.specialty = doctorDTO.getSpecialty();
        this.address = doctorDTO.getAddress();
    }

    public DoctorDTO toDTO() {
        return new DoctorDTO(id, name, email, cpf, specialty, address);
    }
}

