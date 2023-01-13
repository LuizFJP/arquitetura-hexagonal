package br.com.attornatuschallenge.http.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.attornatuschallenge.dto.PersonDTO;
import br.com.attornatuschallenge.entity.Person;
import br.com.attornatuschallenge.service.PersonService;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api")
public class PersonController {
  @Autowired
  private PersonService personService;

  @Autowired
  private ModelMapper modelMapper;

  @PostMapping("/person")
  @ResponseStatus(HttpStatus.CREATED)
  public Person save(@RequestBody Person person) {
    return personService.save(person);
  }

  @GetMapping(value="/person/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Person get(@PathVariable("id") Long id) {
    try {
      return personService.findById(id);
    } catch (Exception err) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, err.getMessage());
    }
  }

  @GetMapping(value="/people")
  @ResponseStatus(HttpStatus.OK)
  public List<Person> getAll() {
        return personService.findAll();
  }

  @PutMapping("/person/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Person update(@PathVariable("id") Long id, @RequestBody PersonDTO personDTO) {
    try {
      Person personRequest = modelMapper.map(personDTO, Person.class);
      return personService.update(id, personRequest);
    } catch (Exception err) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, err.getMessage());
    }
  }

  @DeleteMapping("/person/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable("id") Long id) {
    try {
      personService.delete(id);
    } catch (Exception err) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, err.getMessage());
    }
  }
}
