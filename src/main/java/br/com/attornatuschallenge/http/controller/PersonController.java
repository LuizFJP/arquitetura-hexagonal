package br.com.attornatuschallenge.http.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
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

import br.com.attornatuschallenge.dto.PersonDTO;
import br.com.attornatuschallenge.entity.Person;
import br.com.attornatuschallenge.error.ResourceNotFoundException;
import br.com.attornatuschallenge.service.PersonService;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api")
public class PersonController {
  @Autowired
  private PersonService personService;

  @Autowired
  private ModelMapper modelMapper;

  @PostMapping("/person")
  public ResponseEntity<Person> save(@RequestBody Person person) {
    return new ResponseEntity<Person>(personService.save(person), HttpStatus.CREATED);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  @GetMapping(value = "/person/{id}")
  public ResponseEntity<Person> get(@PathVariable("id") Long id) {
    try {
      return new ResponseEntity<Person>(personService.findById(id), HttpStatus.OK);
    } catch (ResourceNotFoundException err) {
      throw new ResourceNotFoundException(err.getMessage());
    }
  }

  @GetMapping(value = "/people")
  public ResponseEntity<List<Person>> getAll() {
    return ResponseEntity.ok(personService.findAll());
  }

  @PutMapping("/person/{id}")
  public ResponseEntity<Person> update(@PathVariable("id") Long id, @RequestBody PersonDTO personDTO) {
    try {
      Person personRequest = modelMapper.map(personDTO, Person.class);
      return new ResponseEntity<Person>(personService.update(id, personRequest), HttpStatus.OK);
    } catch (Exception err) {
      throw new ResourceNotFoundException(err.getMessage());
    }
  }

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
