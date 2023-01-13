package br.com.attornatuschallenge.service;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.attornatuschallenge.entity.Address;
import br.com.attornatuschallenge.entity.Person;
import br.com.attornatuschallenge.repository.AddressRepository;
import br.com.attornatuschallenge.repository.PersonRepository;
import br.com.attornatuschallenge.utils.Utils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class PersonService {
  @Autowired
  PersonRepository personRepository;

  @Autowired
  AddressRepository addressRepository;

  @Autowired
  ModelMapper modelMapper;

  @PersistenceContext
  private EntityManager entityManager;

  public Person save(Person person) {
    return personRepository.save(person);
  }

  public List<Person> findAll() {
    return personRepository.findAll();
  }

  @Transactional
  public Person update(Long id, Person newPerson) throws Exception {
    try {
      Person person = findById(id);

      person.setName(updateField(person.getName(), newPerson.getName()));
      person.setBirthday(updateField(person.getBirthday(), newPerson.getBirthday()));
      person = updateAddresses(person, newPerson);

      return personRepository.save(person);

    } catch (Exception err) {
      throw err;
    }
  }

  public void delete(Long id) throws Exception {
    try {
      Person person = findById(id);
      personRepository.delete(person);
    } catch (Exception err) {
      throw err;
    }
  }

  public Person findById(Long id) throws Exception {
    return personRepository.findById(id).orElseThrow(() -> new Exception("Pessoa não cadastrada."));
  }

  private String updateField(String oldField, String newField) {
    if (newField == null) {
      return oldField;
    } else {
      return newField;
    }
  }

  private Date updateField(Date oldField, Date newField) {
    if (newField == null) {
      return oldField;
    } else {
      return newField;
    }
  }

  private Person updateAddresses(Person person, Person newPerson) {
    if (Utils.personHasAddress(newPerson.getAddresses())) {
      for (Address address : newPerson.getAddresses()) {
        address.setPerson(person);
      }
  
      person.getAddresses().removeAll(person.getAddresses());
      person.getAddresses().addAll(newPerson.getAddresses());
    }
    return person;
  }
}
