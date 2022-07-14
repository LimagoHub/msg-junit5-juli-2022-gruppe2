package de.msg.webapp.controller;

import de.msg.webapp.controller.dtos.PersonDto;
import de.msg.webapp.services.PersonenService;
import de.msg.webapp.services.PersonenServiceException;
import de.msg.webapp.services.models.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
//@Sql({"/create.sql", "/insert.sql"})
class PersonenControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private PersonenService serviceMock;

    @Test
    void test1() throws PersonenServiceException {

        Person person = Person.builder().id("1").vorname("John").nachname("Doe").build();

        when(serviceMock.findeNachId(anyString())).thenReturn(Optional.of(person));

        PersonDto dto = restTemplate.getForObject("/personen/b2e24e74-8686-43ea-baff-d9396b4202e0", PersonDto.class);

        assertEquals("1", dto.getId());
        assertEquals("John", dto.getVorname());


    }
    @Test
    void test2() throws PersonenServiceException {

        Person person = Person.builder().id("1").vorname("John").nachname("Doe").build();

        when(serviceMock.findeNachId(anyString())).thenReturn(Optional.of(person));

        ResponseEntity<PersonDto> entity = restTemplate.getForEntity("/personen/b2e24e74-8686-43ea-baff-d9396b4202e0", PersonDto.class);

        PersonDto dto = entity.getBody();

        assertEquals("1", dto.getId());
        assertEquals("John", dto.getVorname());
        assertEquals(HttpStatus.OK,entity.getStatusCode());

        verify(serviceMock, times(1)).findeNachId("b2e24e74-8686-43ea-baff-d9396b4202e0");

    }

    @Test
    void test3() throws PersonenServiceException {



        when(serviceMock.findeNachId(anyString())).thenReturn(Optional.empty());

        ResponseEntity<PersonDto> entity = restTemplate.getForEntity("/personen/b2e24e74-8686-43ea-baff-d9396b4202e0", PersonDto.class);


        assertEquals(HttpStatus.NOT_FOUND,entity.getStatusCode());

        verify(serviceMock, times(1)).findeNachId("b2e24e74-8686-43ea-baff-d9396b4202e0");

    }

    @Test
    void test4() throws PersonenServiceException {



        when(serviceMock.findeNachId(anyString())).thenReturn(Optional.empty());

        ResponseEntity<PersonDto> entity = restTemplate.exchange("/personen/b2e24e74-8686-43ea-baff-d9396b4202e0", HttpMethod.GET, null, PersonDto.class);


        assertEquals(HttpStatus.NOT_FOUND,entity.getStatusCode());

        verify(serviceMock, times(1)).findeNachId("b2e24e74-8686-43ea-baff-d9396b4202e0");

    }

    @Test
    void test5() throws PersonenServiceException {

        List<Person> personen = List.of(
                Person.builder().id("1").vorname("John").nachname("Doe").build(),
                Person.builder().id("2").vorname("John").nachname("Wick").build(),
                Person.builder().id("3").vorname("John").nachname("Rambo").build(),
                Person.builder().id("4").vorname("John").nachname("Wayne").build()
        );

        HttpEntity<PersonDto> entity = new HttpEntity<>(new PersonDto());

        when(serviceMock.findeAlle()).thenReturn(personen);

        ResponseEntity<List<PersonDto>> result = restTemplate.exchange("/personen", HttpMethod.GET, entity,new ParameterizedTypeReference<List<PersonDto>>() {});


        assertEquals(4,result.getBody().size());



    }
}