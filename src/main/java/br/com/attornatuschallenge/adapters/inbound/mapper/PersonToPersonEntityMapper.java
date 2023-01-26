package br.com.attornatuschallenge.adapters.inbound.mapper;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import br.com.attornatuschallenge.adapters.inbound.entity.AddressEntity;
import br.com.attornatuschallenge.adapters.inbound.entity.PersonEntity;
import br.com.attornatuschallenge.application.core.domain.Person;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PersonToPersonEntityMapper {
  AddressesToAddressesEntityMapper addressesToAddressesEntityMapper;

  public PersonEntity mapper(Person person) {
    PersonEntity personEntity = new PersonEntity();
    BeanUtils.copyProperties(person, personEntity);
    List<AddressEntity> addressEntity = addressesToAddressesEntityMapper.mapper(person.getAddresses());
    personEntity.setAddresses(addressEntity); 
    return personEntity;
}
}
