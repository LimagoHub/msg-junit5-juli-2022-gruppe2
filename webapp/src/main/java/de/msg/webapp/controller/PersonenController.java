package de.msg.webapp.controller;

import de.msg.webapp.controller.dtos.PersonDto;
import de.msg.webapp.controller.mapper.PersonDtoMapper;
import de.msg.webapp.services.PersonenService;
import de.msg.webapp.services.PersonenServiceException;
import de.msg.webapp.services.models.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/personen")
@RequiredArgsConstructor
public class PersonenController {

    private final PersonenService service;
    private final PersonDtoMapper mapper;

    @GetMapping(path="/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<PersonDto> findPersonById(@PathVariable String id) throws PersonenServiceException {
        return ResponseEntity.of(service.findeNachId(id).map(mapper::convert));
    }

    @GetMapping(path="", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Iterable<PersonDto>> findAll() throws PersonenServiceException {
        return ResponseEntity.ok(mapper.convert(service.findeAlle()));
    }

    @PutMapping(path="", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveOrUpdate(@Valid @RequestBody PersonDto dto) throws PersonenServiceException{
        if(service.speichern(mapper.convert(dto))){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<Void> loeschen(@PathVariable String id) throws PersonenServiceException{
        if(service.loesche(id)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(path="", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> loeschen(@RequestBody PersonDto dto) throws PersonenServiceException{
        if(service.loesche(mapper.convert(dto))){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
