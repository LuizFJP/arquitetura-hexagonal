package br.com.attornatuschallenge.utils;

import java.text.SimpleDateFormat;
import java.util.List;

import br.com.attornatuschallenge.entity.Address;
import br.com.attornatuschallenge.entity.Person;

public abstract class PersonCreator {
  static private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

  static public Person createPerson(String name, String birthday) throws Exception {
    Person person;
    if (birthday == null ) {
      person = new Person(name, null);
    } else {
      person = new Person(name,formatter.parse(birthday));
    }
    return person;
  }

  static public Person createPersonWithAddresses(Person person, List<Address> addresses) {
    for (Address address : addresses) {
      address.setPerson(person);
    }
    person.setAddresses(addresses);
    return person;
  }
}
