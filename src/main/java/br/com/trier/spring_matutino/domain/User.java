package br.com.trier.spring_matutino.domain;

import br.com.trier.spring_matutino.domain.dto.UserDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "email", unique = true)
	private String email;

	@Column(name = "password")
	private String password;

	@Column(name = "roles")
	private String roles;

	public User(UserDTO dto) {
		this(dto.getId(), dto.getName(), dto.getEmail(), dto.getPassword(), dto.getRoles());
	}

	public UserDTO toDTO() {
		return new UserDTO(this.id, this.name, this.email, this.password, this.roles);
	}
}
