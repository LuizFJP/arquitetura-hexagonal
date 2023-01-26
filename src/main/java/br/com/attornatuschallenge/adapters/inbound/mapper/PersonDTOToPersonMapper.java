package br.com.attornatuschallenge.adapters.inbound.mapper;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import br.com.attornatuschallenge.adapters.inbound.request.dto.PersonDTO;
import br.com.attornatuschallenge.application.core.domain.Address;
import br.com.attornatuschallenge.application.core.domain.Person;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PersonDTOToPersonMapper {
  AddressesDTOToAddressMapper addressesDTOToAddressMapper;

  public Person mapper(PersonDTO personDTO) {
    Person person = new Person();
    BeanUtils.copyProperties(personDTO, person);
    List<Address> addresses = addressesDTOToAddressMapper.mapper(personDTO.getAddresses());
    person.setAddresses(addresses);
    return person;
  }
}