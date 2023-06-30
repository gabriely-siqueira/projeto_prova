package br.com.trier.spring_matutino.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.spring_matutino.TestBase;
import br.com.trier.spring_matutino.domain.User;
import br.com.trier.spring_matutino.services.exceptions.IntegrityViolationException;
import br.com.trier.spring_matutino.services.exceptions.ObjectNotFoundException;
import jakarta.transaction.Transactional;

@Transactional
public class UserServiceTest extends TestBase{
	
	@Autowired
	UserService service;
	
	@Test
	@DisplayName("Teste buscar usuário por id")
	@Sql({"classpath:/resources/sql/user.sql"})
	void findByIdTest() {
		var user = service.findById(3);
		assertThat(user).isNotNull();
		assertEquals(3, user.getId());
		assertEquals("Usuario1", user.getName());
		assertEquals("usuario1@teste.com.br", user.getEmail());
		assertEquals("321", user.getPassword());
	}
	
	@Test
	@DisplayName("Teste buscar usuário por id inexistente")
	@Sql({"classpath:/resources/sql/user.sql"})
	void findByIdNonExistentTest() {
		var exception = assertThrows(ObjectNotFoundException.class, () -> service.findById(10));
		assertEquals("Usuário 10 não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste listar usuarios")
	@Sql({"classpath:/resources/sql/user.sql"})
	void listAllTest() {
		List<User> users = service.listAll();
		assertEquals(2, users.size());
		assertEquals("Usuario1", users.get(0).getName());	
	}
	
	@Test
	@DisplayName("Teste listar usuarios com lista vazia")
	void listAllEmptyTest() {
		var exception = assertThrows(ObjectNotFoundException.class, () -> service.listAll());
		assertEquals("Não há usuários cadastrados", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Teste salvar usuario")
	void insertTest() {
		User user = new User(null, "Usuario Test 3", "test3@teste.com.br", "123", "ADMIN");
		service.insert(user);
		List<User> users = service.listAll();
		assertEquals(1, users.size());
	}
	
	@Test
	@DisplayName("Teste salvar usuario com email duplicado")
	@Sql({"classpath:/resources/sql/user.sql"})
	void insertDuplicatedEmailTest() {
		User user = new User(null, "Usuario Test 3", "usuario2@teste.com.br", "123", "ADMIN");
		var exception = assertThrows(IntegrityViolationException.class, () -> service.insert(user));
		assertEquals("Esse email já existe", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste salvar usuario com dados vazios")
	void insertEmptyUserTest() {
		User user = new User(null, "", "", "", "");
		var exception = assertThrows(IntegrityViolationException.class, () -> service.insert(user));
		assertEquals("O nome está vazio", exception.getMessage());
		
		User user2 = new User(null, "teste", "", "", "");
		var exception2 = assertThrows(IntegrityViolationException.class, () -> service.insert(user2));
		assertEquals("O email está vazio", exception2.getMessage());
	}
	
	@Test
	@DisplayName("Teste deletar usuario")
	@Sql({"classpath:/resources/sql/user.sql"})
	void deleteTest() {
		service.delete(3);
		assertEquals(1, service.listAll().size());
	}
	
	@Test
	@DisplayName("Teste deletar usuario inexistente")
	@Sql({"classpath:/resources/sql/user.sql"})
	void deleteInexistentTest() {
		var exception = assertThrows(ObjectNotFoundException.class, () -> service.delete(5));
		assertEquals("Usuário 5 não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste alterar usuario")
	@Sql({"classpath:/resources/sql/user.sql"})
	void updateTest() {
		User user = new User(3, "Usuario Test", "test@teste.com.br", "123", "ADMIN");
		service.update(user);
		assertEquals("Usuario Test", service.listAll().get(0).getName());
	}
	
	@Test
	@DisplayName("Teste alterar usuario com email duplicado")
	@Sql({"classpath:/resources/sql/user.sql"})
	void updateDuplicatedEmailTest() {
		User user = new User(4, "Usuario2", "usuario1@teste.com.br", "123", "ADMIN");
		var exception = assertThrows(IntegrityViolationException.class, () -> service.update(user));
		assertEquals("Esse email já existe", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste alterar usuario inexistente")
	@Sql({"classpath:/resources/sql/user.sql"})
	void updateInvalidTest() {
		User user = new User(5, "Usuario3", "test@teste.com.br", "123", "ADMIN");
		var exception = assertThrows(ObjectNotFoundException.class, () -> service.update(user));
		assertEquals("Esse usuário não existe", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar usuario por nome que contenha x letras")
	@Sql({"classpath:/resources/sql/user.sql"})
	void findByNomeContainsIgnoreCaseTest() {
		List<User> users = service.findByName("Usuario");
		assertEquals(2, users.size());
	}
	
	@Test
	@DisplayName("Teste buscar usuario por nome inexistente")
	@Sql({"classpath:/resources/sql/user.sql"})
	void findByNomeContainsIgnoreCaseNotFoundTest() {
		var exception = assertThrows(ObjectNotFoundException.class, () -> service.findByName("w"));
		assertEquals("Não há nenhum usuário com o nome w", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar usuário pelo email")
	@Sql({"classpath:/resources/sql/user.sql"})
	void findByEmailTest() {
		var user = service.findByEmail("usuario1@teste.com.br");
		assertThat(user).isNotNull();
		assertEquals(3, user.getId());
		assertEquals("Usuario1", user.getName());
		assertEquals("321", user.getPassword());
	}
	
	@Test
	@DisplayName("Teste buscar usuário pelo email inválido")
	@Sql({"classpath:/resources/sql/user.sql"})
	void findByEmailNotFoundTest() {
		var exception = assertThrows(ObjectNotFoundException.class, () -> service.findByEmail("teste"));
		assertEquals("Usuário com email teste não encontrado", exception.getMessage());
	}
}