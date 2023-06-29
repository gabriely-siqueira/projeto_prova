package br.com.trier.spring_matutino.domain;

import br.com.trier.spring_matutino.domain.dto.PatientDTO;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "patient")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "cpf", unique = true)
    private String cpf;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    public Patient(PatientDTO patientDTO) {
        this.id = patientDTO.getId();
        this.name = patientDTO.getName();
        this.email = patientDTO.getEmail();
        this.cpf = patientDTO.getCpf();
        this.address = patientDTO.getAddress();
    }

    public PatientDTO toDTO() {
        return new PatientDTO(id, name, email, cpf, address);
    }
}
