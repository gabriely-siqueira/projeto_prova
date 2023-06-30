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
import br.com.trier.spring_matutino.domain.City;


@ActiveProfiles("teste")
@SpringBootTest(classes = SpringMatutinoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CityResourceTest {

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
	
	private ResponseEntity<City> getCity(String url) {
		return rest.exchange(url, 
							 HttpMethod.GET, 
							 new HttpEntity<>(getHeaders("usuario1@teste.com.br", "321")), 
							 City.class);
				
	} 

	@SuppressWarnings("unused")
	private ResponseEntity<List<City>> getSpecialties(String url) {
		return rest.exchange(url, 
							 HttpMethod.GET, 
							 new HttpEntity<>(getHeaders("usuario1@teste.com.br", "321")), 
							 new ParameterizedTypeReference<List<City>>() {
		});
	}
	
	@Test
	@DisplayName("Teste inserir cidade com permissão de ADMIN")
	@Sql({"classpath:/resources/sql/clean.sql"})
	@Sql({"classpath:/resources/sql/user.sql"})
	void insertAdminTest() {
		City city = new City(null, "Tubarão","SC");
		HttpHeaders headers = getHeaders("usuario1@teste.com.br", "321");
		HttpEntity<City> requestEntity = new HttpEntity<>(city, headers);
		ResponseEntity<City> responseEntity = rest.exchange("/cities", HttpMethod.POST, requestEntity, City.class);
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals("Tubarão", responseEntity.getBody().getName());
	}
	
	@Test
	@DisplayName("Teste inserir cidade com nome duplicado")
	@Sql({"classpath:/resources/sql/clean.sql"})
	@Sql({"classpath:/resources/sql/user.sql"})
	@Sql({"classpath:/resources/sql/city.sql"})
	void insertDuplicatedTest() {
		City city = new City(null, "Tubarão","SC");
		HttpHeaders headers = getHeaders("usuario1@teste.com.br", "321");
		HttpEntity<City> requestEntity = new HttpEntity<>(city, headers);
		ResponseEntity<City> responseEntity = rest.exchange("/cities", HttpMethod.POST, requestEntity, City.class);
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	@DisplayName("Teste inserir cidade inválido")
	@Sql({"classpath:/resources/sql/clean.sql"})
	@Sql({"classpath:/resources/sql/user.sql"})
	@Sql({"classpath:/resources/sql/city.sql"})
	void insertInvalidCityTest() {
		City city = new City(null, "","SC");
		HttpHeaders headers = getHeaders("usuario1@teste.com.br", "321");
		HttpEntity<City> requestEntity = new HttpEntity<>(city, headers);
		ResponseEntity<City> responseEntity = rest.exchange("/cities", HttpMethod.POST, requestEntity, City.class);
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	@Test
	@DisplayName("Teste inserir estado inválido")
	@Sql({"classpath:/resources/sql/clean.sql"})
	@Sql({"classpath:/resources/sql/user.sql"})
	@Sql({"classpath:/resources/sql/city.sql"})
	void insertInvalidStateTest() {
		City city = new City(null, "São paulo","");
		HttpHeaders headers = getHeaders("usuario1@teste.com.br", "321");
		HttpEntity<City> requestEntity = new HttpEntity<>(city, headers);
		ResponseEntity<City> responseEntity = rest.exchange("/cities", HttpMethod.POST, requestEntity, City.class);
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	@DisplayName("Teste alterar cidade com permissão de ADMIN")
	@Sql({"classpath:/resources/sql/clean.sql"})
	@Sql({"classpath:/resources/sql/user.sql"})
	@Sql({"classpath:/resources/sql/city.sql"})
	void updateAdminTest() {
		City city = new City(2, "São Paulo","SP");
		HttpHeaders headers = getHeaders("usuario1@teste.com.br", "321");
		HttpEntity<City> requestEntity = new HttpEntity<>(city, headers);
		ResponseEntity<City> responseEntity = rest.exchange("/cities/2", HttpMethod.PUT, requestEntity, City.class);
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals("São Paulo", responseEntity.getBody().getName());
	}
	
    
    
    @Test
    @DisplayName("Teste deletar cidade com permissão de ADMIN")
    @Sql({"classpath:/resources/sql/clean.sql"})
    @Sql({"classpath:/resources/sql/user.sql"})
    @Sql({"classpath:/resources/sql/city.sql"})
    void deleteAdminTest() {
        HttpHeaders headers = getHeaders("usuario1@teste.com.br", "321");
        HttpEntity<Void> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<Void> response = rest.exchange("/cities/2", HttpMethod.DELETE, requestEntity, Void.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }
    
    @Test
    @DisplayName("Teste deletar cidade inexistente")
    @Sql({"classpath:/resources/sql/clean.sql"})
    @Sql({"classpath:/resources/sql/user.sql"})
    @Sql({"classpath:/resources/sql/city.sql"})
    void deleteNotFoundTest() {
        HttpHeaders headers = getHeaders("usuario1@teste.com.br", "321");
        HttpEntity<Void> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<Void> response = rest.exchange("/cities/10", HttpMethod.DELETE, requestEntity, Void.class);
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }
    
    @Test
    @DisplayName("Teste deletar cidade com permissão de ADMIN")
    @Sql({"classpath:/resources/sql/clean.sql"})
    @Sql({"classpath:/resources/sql/user.sql"})
    @Sql({"classpath:/resources/sql/city.sql"})
    void deleteUserTest() {
        HttpHeaders headers = getHeaders("test2@teste.com.br", "321");
        HttpEntity<Void> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<Void> response = rest.exchange("/cities/2", HttpMethod.DELETE, requestEntity, Void.class);
        assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
    }
    
    @Test
    @DisplayName("Teste listar todos os cidades com permissão de ADMIN")
    @Sql({"classpath:/resources/sql/clean.sql"})
    @Sql({"classpath:/resources/sql/user.sql"})
    @Sql({"classpath:/resources/sql/city.sql"})
    void listAllAdminTest() {
        ResponseEntity<List<City>> response = rest.exchange(
                "/cities", 
                HttpMethod.GET, 
                new HttpEntity<>(getHeaders("usuario1@teste.com.br", "321")),
                new ParameterizedTypeReference<List<City>>() {} 
        );
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(3, response.getBody().size());
    }
    
   
    
    @Test
    @DisplayName("Teste buscar cidades pelo id")
    @Sql({"classpath:/resources/sql/clean.sql"})
    @Sql({"classpath:/resources/sql/user.sql"})
    @Sql({"classpath:/resources/sql/city.sql"})
    void findByIdTest() {
        ResponseEntity<City> response = getCity("/cities/3");
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        
        City city = response.getBody();
        assertEquals("Criciúma", city.getName());
    }
    
    @Test
    @DisplayName("Teste buscar cidades por id inexistente")
    @Sql({"classpath:/resources/sql/clean.sql"})
    @Sql({"classpath:/resources/sql/user.sql"})
    @Sql({"classpath:/resources/sql/city.sql"})
    void findByIdNotFoundTest() {
        ResponseEntity<City> response = getCity("/cities/10");
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }
    
    @Test
    @DisplayName("Teste buscar cidade pela descrição ignorando o case")
    @Sql({"classpath:/resources/sql/clean.sql"})
    @Sql({"classpath:/resources/sql/user.sql"})
    @Sql({"classpath:/resources/sql/city.sql"})
    void findByNameEqualsIgnoreCaseTest() {
        ResponseEntity<List<City>> response = getSpecialties("/cities/name/tubarão");
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(1, response.getBody().size());
    }
}