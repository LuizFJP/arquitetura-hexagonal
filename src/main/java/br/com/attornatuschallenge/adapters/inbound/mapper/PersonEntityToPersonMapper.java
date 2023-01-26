package br.com.attornatuschallenge.adapters.inbound.mapper;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import br.com.attornatuschallenge.adapters.inbound.entity.PersonEntity;
import br.com.attornatuschallenge.application.core.domain.Address;
import br.com.attornatuschallenge.application.core.domain.Person;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PersonEntityToPersonMapper {
  AddressesEntityToAddressMapper addressesEntityToAddressMapper;

  public Person mapper(PersonEntity personEntity) {
    Person person = new Person();
    BeanUtils.copyProperties(personEntity, person);
    List<Address> addresses = addressesEntityToAddressMapper.mapper(personEntity.getAddresses());
    person.setAddresses(addresses);
    return person;
  }
}