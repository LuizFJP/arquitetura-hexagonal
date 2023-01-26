package br.com.attornatuschallenge.application.ports.out;

import br.com.attornatuschallenge.application.core.domain.Person;

public interface SavePersonPort {
  Person save(Person person);
}
