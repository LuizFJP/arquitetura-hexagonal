package br.com.attornatuschallenge.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.attornatuschallenge.adapters.inbound.entity.AddressEntity;
import br.com.attornatuschallenge.adapters.inbound.entity.PersonEntity;
import br.com.attornatuschallenge.adapters.outbound.repository.AddressRepository;
import br.com.attornatuschallenge.adapters.outbound.repository.PersonRepository;
import br.com.attornatuschallenge.application.core.exception.error.ResourceNotFoundException;
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

  @PersistenceContext
  private EntityManager entityManager;

  public PersonEntity save(PersonEntity person) {
    return personRepository.save(person);
  }

  public List<PersonEntity> findAll() {
    return personRepository.findAll();
  }

  @Transactional
  public PersonEntity update(Long id, PersonEntity newPerson) throws ResourceNotFoundException {
    PersonEntity person = findById(id);

    person.setName(updateField(person.getName(), newPerson.getName()));
    person.setBirthday(updateField(person.getBirthday(), newPerson.getBirthday()));
    person = updateAddresses(person, newPerson);

    return personRepository.save(person);
  }

  public void delete(Long id) throws ResourceNotFoundException {
    PersonEntity person = findById(id);
    personRepository.delete(person);
  }

  public PersonEntity findById(Long id) throws ResourceNotFoundException {
    return personRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Person not found with id " + id));
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

  private PersonEntity updateAddresses(PersonEntity person, PersonEntity newPerson) {
    if (Utils.personHasAddress(newPerson.getAddresses())) {
      for (AddressEntity address : newPerson.getAddresses()) {
        address.setPerson(person);
      }

      person.getAddresses().removeAll(person.getAddresses());
      person.getAddresses().addAll(newPerson.getAddresses());
    }
    return person;
  }
}
