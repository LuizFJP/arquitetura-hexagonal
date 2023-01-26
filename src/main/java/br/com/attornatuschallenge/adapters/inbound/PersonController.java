package br.com.attornatuschallenge.adapters.inbound;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.attornatuschallenge.adapters.inbound.entity.PersonEntity;
import br.com.attornatuschallenge.adapters.inbound.request.dto.PersonDTO;
import br.com.attornatuschallenge.application.core.domain.Person;
import br.com.attornatuschallenge.application.core.exception.error.ResourceNotFoundException;
import br.com.attornatuschallenge.application.ports.in.SavePersonServicePort;
import br.com.attornatuschallenge.service.PersonService;
import br.com.attornatuschallenge.adapters.inbound.mapper.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class PersonController {
  @Autowired
  private PersonService personService;

  private final PersonDTOToPersonMapper personDTOToPersonMapper;

  private final SavePersonServicePort savePersonServicePort;

  @PostMapping("/person")
  public ResponseEntity<Person> save(@RequestBody PersonDTO personDTO) {
    Person person = personDTOToPersonMapper.mapper(personDTO);
    return new ResponseEntity<Person>(savePersonServicePort.savePerson(person), HttpStatus.CREATED);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  @GetMapping(value = "/person/{id}")
  public ResponseEntity<PersonEntity> get(@PathVariable("id") Long id) {
    try {
      return new ResponseEntity<PersonEntity>(personService.findById(id), HttpStatus.OK);
    } catch (ResourceNotFoundException err) {
      throw new ResourceNotFoundException(err.getMessage());
    }
  }

  @GetMapping(value = "/people")
  public ResponseEntity<List<PersonEntity>> getAll() {
    return ResponseEntity.ok(personService.findAll());
  }

  // @PutMapping("/person/{id}")
  // public ResponseEntity<PersonEntity> update(@PathVariable("id") Long id, @RequestBody PersonDTO personDTO) {
    // try {
    //   Person person = personDTOToPersonMapper.mapper(personDTO);
    //   return new ResponseEntity<PersonEntity>(personService.update(id, person), HttpStatus.OK);
    // } catch (Exception err) {
    //   throw new ResourceNotFoundException(err.getMessage());
    // }
  // }

  @DeleteMapping("/person/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable("id") Long id) {
    try {
      personService.delete(id);
    } catch (Exception err) {
      throw new ResourceNotFoundException(err.getMessage());
    }
  }
}
