package br.com.attornatuschallenge.adapters.outbound;

import org.springframework.stereotype.Component;

import br.com.attornatuschallenge.adapters.inbound.entity.PersonEntity;
import br.com.attornatuschallenge.adapters.inbound.mapper.PersonEntityToPersonMapper;
import br.com.attornatuschallenge.adapters.inbound.mapper.PersonToPersonEntityMapper;
import br.com.attornatuschallenge.adapters.outbound.repository.PersonRepository;
import br.com.attornatuschallenge.application.core.domain.Person;
import br.com.attornatuschallenge.application.ports.out.SavePersonPort;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class SavePersonAdapter implements SavePersonPort {

  private final PersonRepository personRepository;
  private final PersonToPersonEntityMapper personToPersonEntityMapper;
  private final PersonEntityToPersonMapper personEntityToPersonMapper;
  @Override
  public Person save(Person person) {
    PersonEntity personEntity = personToPersonEntityMapper.mapper(person);
    
    return personEntityToPersonMapper.mapper(personRepository.save(personEntity));
  }
}
