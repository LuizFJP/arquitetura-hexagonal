package br.com.attornatuschallenge.utils;

import java.text.SimpleDateFormat;
import java.util.List;

import br.com.attornatuschallenge.adapters.inbound.entity.AddressEntity;
import br.com.attornatuschallenge.adapters.inbound.entity.PersonEntity;

public abstract class PersonCreator {
  static private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

  static public PersonEntity createPerson(String name, String birthday) throws Exception {
    PersonEntity person;
    if (birthday == null ) {
      person = new PersonEntity(name, null);
    } else {
      person = new PersonEntity(name,formatter.parse(birthday));
    }
    return person;
  }

  static public PersonEntity createPersonWithAddresses(PersonEntity person, List<AddressEntity> addresses) {
    for (AddressEntity address : addresses) {
      address.setPerson(person);
    }
    person.setAddresses(addresses);
    return person;
  }
}
