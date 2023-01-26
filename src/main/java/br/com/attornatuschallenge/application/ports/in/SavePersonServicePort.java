package br.com.attornatuschallenge.application.ports.in;

import br.com.attornatuschallenge.application.core.domain.Person;

public interface SavePersonServicePort {
  Person savePerson(Person person);
}
