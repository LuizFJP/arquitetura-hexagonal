package br.com.attornatuschallenge.application.core.useCases;

import br.com.attornatuschallenge.application.core.domain.Person;
import br.com.attornatuschallenge.application.ports.in.SavePersonServicePort;
import br.com.attornatuschallenge.application.ports.out.SavePersonPort;

public class SavePerson implements SavePersonServicePort {
  private final SavePersonPort savePersonPort;

  public SavePerson(SavePersonPort savePersonPort) {
    this.savePersonPort = savePersonPort;
  } 

  @Override
  public Person savePerson(Person person) {
    return savePersonPort.save(person);
  }
  
}
