package br.com.trier.spring_matutino.domain;

import lombok.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "phone_number")
public class PhoneNumber {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(AccessLevel.PRIVATE)
	@Column(name = "id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "doctor_id")
	private Doctor doctor;

	@ManyToOne
	@JoinColumn(name = "patient_id")
	private Patient patient;

	@Column(name = "number", unique = true)
	private String number;

	@Column(name = "entity_type")
	private String entityType;
	
	@Column(name = "entity_id")
	private Long entityId;

	public PhoneNumber(Doctor doctor, String number) {
		this.doctor = doctor;
		this.number = number;
		this.entityType = "DOCTOR";
	}

	public PhoneNumber(Patient patient, String number) {
		this.patient = patient;
		this.number = number;
		this.entityType = "PATIENT";
	}
}
