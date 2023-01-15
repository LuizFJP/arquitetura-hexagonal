package br.com.attornatuschallenge.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.attornatuschallenge.entity.Address;
import br.com.attornatuschallenge.entity.Person;
import br.com.attornatuschallenge.error.ResourceNotFoundException;
import br.com.attornatuschallenge.repository.AddressRepository;
import br.com.attornatuschallenge.utils.Utils;

@Service
public class AddressService {
  @Autowired
  PersonService personService;

  @Autowired
  AddressRepository addressRepository;

  public Address save(Long id, Address address) throws Exception {
      Person person = personService.findById(id);
      if (Utils.personHasAddress(person.getAddresses()) && address.isMainAddress()) {
        updateMainAddress(person.getAddresses());
      }
      address.setPerson(person);
      return addressRepository.save(address);
  }

  public List<Address> getAddresses(Long id) throws Exception {
    Person person = personService.findById(id);
    return person.getAddresses();
  }

  public Address getMainAddress(Long id) throws Exception {
      Person person = personService.findById(id);
      for (Address address : person.getAddresses()) {
        if (address.isMainAddress()) {
          return address;
        }
      }
      throw new ResourceNotFoundException("Pessoa não possui endereço cadastrado.");
  }

  private void updateMainAddress(List<Address> addresses) {
    for (Address address : addresses) {
      if (address.isMainAddress()) {
        address.setMainAddress(false);
        addressRepository.save(address);
        break;
      }
    }
  }
}
