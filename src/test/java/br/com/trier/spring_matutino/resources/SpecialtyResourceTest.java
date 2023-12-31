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
	
    @Test
    @DisplayName("Teste alterar especialidade com descrição duplicada")
    @Sql({"classpath:/resources/sql/clean.sql"})
    @Sql({"classpath:/resources/sql/user.sql"})
    @Sql({"classpath:/resources/sql/specialty.sql"})
    void updateDuplicatedTest() {
        Specialty specialty = new Specialty(2, "Ginecology");
        HttpHeaders headers = getHeaders("usuario1@teste.com.br", "321");
        HttpEntity<Specialty> requestEntity = new HttpEntity<>(specialty, headers);
        ResponseEntity<Specialty> responseEntity = rest.exchange("/specialties/2", HttpMethod.PUT, requestEntity, Specialty.class);
        
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }
    
    
    @Test
    @DisplayName("Teste deletar especialidade com permissão de ADMIN")
    @Sql({"classpath:/resources/sql/clean.sql"})
    @Sql({"classpath:/resources/sql/user.sql"})
    @Sql({"classpath:/resources/sql/specialty.sql"})
    void deleteAdminTest() {
        HttpHeaders headers = getHeaders("usuario1@teste.com.br", "321");
        HttpEntity<Void> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<Void> response = rest.exchange("/specialties/2", HttpMethod.DELETE, requestEntity, Void.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }
    
    @Test
    @DisplayName("Teste deletar especialidade inexistente")
    @Sql({"classpath:/resources/sql/clean.sql"})
    @Sql({"classpath:/resources/sql/user.sql"})
    @Sql({"classpath:/resources/sql/specialty.sql"})
    void deleteNotFoundTest() {
        HttpHeaders headers = getHeaders("usuario1@teste.com.br", "321");
        HttpEntity<Void> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<Void> response = rest.exchange("/specialties/10", HttpMethod.DELETE, requestEntity, Void.class);
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }
    
    @Test
    @DisplayName("Teste deletar especialidade com permissão de ADMIN")
    @Sql({"classpath:/resources/sql/clean.sql"})
    @Sql({"classpath:/resources/sql/user.sql"})
    @Sql({"classpath:/resources/sql/specialty.sql"})
    void deleteUserTest() {
        HttpHeaders headers = getHeaders("test2@teste.com.br", "321");
        HttpEntity<Void> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<Void> response = rest.exchange("/specialties/2", HttpMethod.DELETE, requestEntity, Void.class);
        assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
    }
    
    @Test
    @DisplayName("Teste listar todos os especialidades com permissão de ADMIN")
    @Sql({"classpath:/resources/sql/clean.sql"})
    @Sql({"classpath:/resources/sql/user.sql"})
    @Sql({"classpath:/resources/sql/specialty.sql"})
    void listAllAdminTest() {
        ResponseEntity<List<Specialty>> response = rest.exchange(
                "/specialties", 
                HttpMethod.GET, 
                new HttpEntity<>(getHeaders("usuario1@teste.com.br", "321")),
                new ParameterizedTypeReference<List<Specialty>>() {} 
        );
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(4, response.getBody().size());
    }
    
   
    
    @Test
    @DisplayName("Teste buscar especialidades pelo id")
    @Sql({"classpath:/resources/sql/clean.sql"})
    @Sql({"classpath:/resources/sql/user.sql"})
    @Sql({"classpath:/resources/sql/specialty.sql"})
    void findByIdTest() {
        ResponseEntity<Specialty> response = getSpecialty("/specialties/3");
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        
        Specialty specialty = response.getBody();
        assertEquals("cardiology", specialty.getDescription());
    }
    
    @Test
    @DisplayName("Teste buscar especialidades por id inexistente")
    @Sql({"classpath:/resources/sql/clean.sql"})
    @Sql({"classpath:/resources/sql/user.sql"})
    @Sql({"classpath:/resources/sql/specialty.sql"})
    void findByIdNotFoundTest() {
        ResponseEntity<Specialty> response = getSpecialty("/specialties/10");
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }
    
    @Test
    @DisplayName("Teste buscar especialidade pela descrição ignorando o case")
    @Sql({"classpath:/resources/sql/clean.sql"})
    @Sql({"classpath:/resources/sql/user.sql"})
    @Sql({"classpath:/resources/sql/specialty.sql"})
    void findByNameEqualsIgnoreCaseTest() {
        ResponseEntity<List<Specialty>> response = getSpecialties("/specialties/description/orthology");
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(1, response.getBody().size());
    }
}