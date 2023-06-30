package br.com.trier.spring_matutino.resources;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.spring_matutino.SpringMatutinoApplication;

import br.com.trier.spring_matutino.config.jwt.LoginDTO;
import br.com.trier.spring_matutino.domain.Specialty;


@ActiveProfiles("teste")
@SpringBootTest(classes = SpringMatutinoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpecialtyResourceTest {

	@Autowired
	protected TestRestTemplate rest;
	
	private HttpHeaders getHeaders(String email, String password){
		LoginDTO loginDTO = new LoginDTO(email, password);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
		ResponseEntity<String> responseEntity = rest.exchange(
				"/auth/token", 
				HttpMethod.POST,  
				requestEntity,    
				String.class   
				);
		String token = responseEntity.getBody();
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		return headers;
	}
	
	private ResponseEntity<Specialty> getSpecialty(String url) {
		return rest.exchange(url, 
							 HttpMethod.GET, 
							 new HttpEntity<>(getHeaders("usuario1@teste.com.br", "321")), 
							 Specialty.class);
				
	}
	
	@SuppressWarnings("unused")
	private ResponseEntity<List<Specialty>> getSpecialties(String url) {
		return rest.exchange(url, 
							 HttpMethod.GET, 
							 new HttpEntity<>(getHeaders("usuario1@teste.com.br", "321")), 
							 new ParameterizedTypeReference<List<Specialty>>() {
		});
	}
	
	@Test
	@DisplayName("Teste inserir especialidade com permissão de ADMIN")
	@Sql({"classpath:/resources/sql/clean.sql"})
	@Sql({"classpath:/resources/sql/user.sql"})
	void insertAdminTest() {
		Specialty specialty = new Specialty(null, "orthology");
		HttpHeaders headers = getHeaders("usuario1@teste.com.br", "321");
		HttpEntity<Specialty> requestEntity = new HttpEntity<>(specialty, headers);
		ResponseEntity<Specialty> responseEntity = rest.exchange("/specialties", HttpMethod.POST, requestEntity, Specialty.class);
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals("orthology", responseEntity.getBody().getDescription());
	}
	
	@Test
	@DisplayName("Teste inserir especialidade com descrição duplicada")
	@Sql({"classpath:/resources/sql/clean.sql"})
	@Sql({"classpath:/resources/sql/user.sql"})
	@Sql({"classpath:/resources/sql/specialty.sql"})
	void insertDuplicatedTest() {
		Specialty specialty = new Specialty(null, "cardiology");
		HttpHeaders headers = getHeaders("usuario1@teste.com.br", "321");
		HttpEntity<Specialty> requestEntity = new HttpEntity<>(specialty, headers);
		ResponseEntity<Specialty> responseEntity = rest.exchange("/specialties", HttpMethod.POST, requestEntity, Specialty.class);
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	@DisplayName("Teste inserir especialidade inválido")
	@Sql({"classpath:/resources/sql/clean.sql"})
	@Sql({"classpath:/resources/sql/user.sql"})
	@Sql({"classpath:/resources/sql/specialty.sql"})
	void insertInvalidTest() {
		Specialty specialty = new Specialty(null, "");
		HttpHeaders headers = getHeaders("usuario1@teste.com.br", "321");
		HttpEntity<Specialty> requestEntity = new HttpEntity<>(specialty, headers);
		ResponseEntity<Specialty> responseEntity = rest.exchange("/specialties", HttpMethod.POST, requestEntity, Specialty.class);
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	
	@Test
	@DisplayName("Teste alterar especialidade com permissão de ADMIN")
	@Sql({"classpath:/resources/sql/clean.sql"})
	@Sql({"classpath:/resources/sql/user.sql"})
	@Sql({"classpath:/resources/sql/specialty.sql"})
	void updateAdminTest() {
		Specialty specialty = new Specialty(2, "dermatology");
		HttpHeaders headers = getHeaders("usuario1@teste.com.br", "321");
		HttpEntity<Specialty> requestEntity = new HttpEntity<>(specialty, headers);
		ResponseEntity<Specialty> responseEntity = rest.exchange("/specialties/2", HttpMethod.PUT, requestEntity, Specialty.class);
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals("dermatology", responseEntity.getBody().getDescription());
	}
}