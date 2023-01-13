package br.com.attornatuschallenge.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.attornatuschallenge.entity.Address;
import br.com.attornatuschallenge.entity.Person;
import br.com.attornatuschallenge.repository.AddressRepository;
import br.com.attornatuschallenge.utils.Utils;

@Service
public class AddressService {
  @Autowired
  PersonService personService;

  @Autowired
  AddressRepository addressRepository;

  public Address save(Long id, Address address) throws Exception {
    try {
      Person person = personService.findById(id);
      if (Utils.personHasAddress(person.getAddresses()) && address.isMainAddress()) {
        updateMainAddress(person.getAddresses());
      }
      address.setPerson(person);
      return addressRepository.save(address);
    } catch (Exception err) {
      throw err;
    }
  }

  public Address getMainAddress(Long id) throws Exception {
    try {
      Person person = personService.findById(id);
      for (Address address : person.getAddresses()) {
        if (address.isMainAddress()) {
          return address;
        }
      }
      throw new Exception("Pessoa não possui endereço cadastrado.");
    } catch (Exception err) {
      throw err;
    }
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
