package br.com.trier.spring_matutino.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import br.com.trier.spring_matutino.TestBase;
import br.com.trier.spring_matutino.domain.City;
import br.com.trier.spring_matutino.services.exceptions.IntegrityViolationException;
import br.com.trier.spring_matutino.services.exceptions.ObjectNotFoundException;

@Transactional
public class CityServiceTest extends TestBase {

    @Autowired
    private CityService service;

    @Test
    @DisplayName("Test inserting a city")
    void insertTest() {
        City city = new City(null, "Tubarão","SC");
        service.insert(city);
        List<City> cities = service.listAll();
        assertEquals(1, cities.size());
        assertEquals("Tubarão", cities.get(0).getName());
        assertEquals("SC", cities.get(0).getState());
    }

    @Test
    @DisplayName("Test inserting a duplicated city")
    @Sql({ "classpath:/resources/sql/city.sql" })
    void insertDuplicatedTest() {
        City city = new City(null, "Tubarão","SC");
        IntegrityViolationException exception = assertThrows(IntegrityViolationException.class,
                () -> service.insert(city));
        assertEquals("Esta cidade já existe", exception.getMessage());
    }

    @Test
    @DisplayName("Test inserting an invalid city")
    @Sql({ "classpath:/resources/sql/city.sql" })
    void insertInvalidCityNameTest() {
        City city = new City(null, "","SC");
        IntegrityViolationException exception = assertThrows(IntegrityViolationException.class,
                () -> service.insert(city));
        assertEquals("É preciso fornecer o nome da cidade", exception.getMessage());
    }

    @Test
    @DisplayName("Test inserting an invalid state")
    @Sql({ "classpath:/resources/sql/city.sql" })
    void insertInvalidStateTest() {
        City city = new City(null, "Imbituba","");
        IntegrityViolationException exception = assertThrows(IntegrityViolationException.class,
                () -> service.insert(city));
        assertEquals("É preciso fornecer o estado", exception.getMessage());
    }
    
    @Test
    @DisplayName("Test updating a city")
    @Sql({ "classpath:/resources/sql/city.sql" })
    void updateTest() {
        City city = new City(1, "Imbituba", "SC");
        service.update(city);
        List<City> cities = service.listAll();
        assertEquals("Imbituba", cities.get(0).getName());
    }

    @Test
    @DisplayName("Test deleting a city")
    @Sql({ "classpath:/resources/sql/city.sql" })
    void deleteTest() {
        service.delete(1);
        List<City> cities = service.listAll();
        assertEquals(2, cities.size());
    }

    @Test
    @DisplayName("Test deleting a non-existent city ID")
    @Sql({ "classpath:/resources/sql/city.sql" })
    void deleteInexistentTest() {
        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> service.delete(10));
        assertEquals("Cidade 10 não existe", exception.getMessage());
    }

    @Test
    @DisplayName("Test listing cities")
    @Sql({ "classpath:/resources/sql/city.sql" })
    void listAllTest() {
        List<City> cities = service.listAll();
        assertEquals(3, cities.size());
    }

    @Test
    @DisplayName("Test listing empty cities")
    void listAllEmptyTest() {
        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> service.listAll());
        assertEquals("Nenhuma cidade encontrada", exception.getMessage());
    }

    @Test
    @DisplayName("Test finding city by ID")
    @Sql({ "classpath:/resources/sql/city.sql" })
    void findByIdTest() {
        City city = service.findById(1);
        assertThat(city).isNotNull();
        assertEquals("Tubarão", city.getName());
    }

    @Test
    @DisplayName("Test finding non-existent city by ID")
    @Sql({ "classpath:/resources/sql/city.sql" })
    void findByIdNotFoundTest() {
        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> service.findById(10));
        assertEquals("Cidade 10 não existe", exception.getMessage());
    }

    @Test
    @DisplayName("Test finding city by name ignoring case (not found)")
    @Sql({ "classpath:/resources/sql/city.sql" })
    void findByNameContainingIgnoreCaseNotFoundTest() {
        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class,
                () -> service.findByNameContainingIgnoreCase("São Paulo"));
        assertEquals("Nenhuma cidade encontrada com o nome: São Paulo", exception.getMessage());
    }

    @Test
    @DisplayName("Test finding cities by name")
    @Sql({ "classpath:/resources/sql/city.sql" })
    void findByNameContainingIgnoreCaseTest() {
        List<City> cities = service.findByNameContainingIgnoreCase("Tu");
        assertThat(cities).isNotNull();
        assertEquals(1, cities.size());
    }
}