package br.com.attornatuschallenge.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.attornatuschallenge.adapters.inbound.entity.AddressEntity;
import br.com.attornatuschallenge.adapters.inbound.entity.PersonEntity;
import br.com.attornatuschallenge.adapters.outbound.repository.AddressRepository;
import br.com.attornatuschallenge.application.core.exception.error.ResourceNotFoundException;
import br.com.attornatuschallenge.utils.Utils;

@Service
public class AddressService {
  @Autowired
  PersonService personService;

  @Autowired
  AddressRepository addressRepository;

  public AddressEntity save(Long id, AddressEntity address) throws Exception {
      PersonEntity person = personService.findById(id);
      if (Utils.personHasAddress(person.getAddresses()) && address.isMainAddress()) {
        updateMainAddress(person.getAddresses());
      }
      address.setPerson(person);
      return addressRepository.save(address);
  }

  public List<AddressEntity> getAddresses(Long id) throws Exception {
    PersonEntity person = personService.findById(id);
    return person.getAddresses();
  }

  public AddressEntity getMainAddress(Long id) throws Exception {
      PersonEntity person = personService.findById(id);
      for (AddressEntity address : person.getAddresses()) {
        if (address.isMainAddress()) {
          return address;
        }
      }
      throw new ResourceNotFoundException("Pessoa não possui endereço cadastrado.");
  }

  private void updateMainAddress(List<AddressEntity> addresses) {
    for (AddressEntity address : addresses) {
      if (address.isMainAddress()) {
        address.setMainAddress(false);
        addressRepository.save(address);
        break;
      }
    }
  }
}
