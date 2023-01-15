package br.com.attornatuschallenge.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.attornatuschallenge.entity.Address;
import br.com.attornatuschallenge.entity.Person;
import br.com.attornatuschallenge.repository.PersonRepository;
import br.com.attornatuschallenge.utils.PersonCreator;

@ExtendWith(SpringExtension.class)
public class PersonServiceTest {
  @InjectMocks
  private PersonService personService;

  @Mock
  private PersonRepository personRepository;

  @Test
  @DisplayName("Create a person with success")
  public void testCreatePersonSuccessful() throws Exception {
    Person person = PersonCreator.createPerson("Luiz", "1998-05-01");
    List<Address> addresses = Arrays.asList(
        new Address("Rua das Laranjeiras", "12345-789", 1234, "Mangueira", false, person),
        new Address("Rua Cupuacu", "98765-432", 5678, "Avocada", true, person));
    person = PersonCreator.createPersonWithAddresses(person, addresses);

    BDDMockito.given(personRepository.save(ArgumentMatchers.any())).willReturn(person);

    Person personFound = personService.save(ArgumentMatchers.any());

    Assertions.assertThat(personFound).isNotNull();
    Assertions.assertThat(personFound).isInstanceOf(Person.class);
    Assertions.assertThat(personFound.getName()).isEqualTo("Luiz");
  }

  @Test
  @DisplayName("It gets a person")
  public void testGetPersonSucessful() throws Exception {
    Person person = PersonCreator.createPerson("Luiz", "1998-05-01");
    List<Address> addresses = Arrays.asList(
        new Address("Rua das Laranjeiras", "12345-789", 1234, "Mangueira", true, person),
        new Address("Rua Cupuacu", "98765-432", 5678, "Avocada", false, person));
    person = PersonCreator.createPersonWithAddresses(person, addresses);

    BDDMockito.given(personRepository.findById(1L)).willReturn(Optional.of(person));

    Person personFound = personService.findById(1L);

    Assertions.assertThat(personFound).isNotNull();
    Assertions.assertThat(personFound).isInstanceOf(Person.class);
    Assertions.assertThat(personFound.getName()).isEqualTo("Luiz");    
  }

  @Test
  @DisplayName("It fails when try to get a non existing person")
  public void testGetPersonFailure() {
    assertThrows(Exception.class, () -> personService.findById(1L));
  }

  @Test
  @DisplayName("It returns a list of people")
  public void testGetPeopleSuccessful() throws Exception {

    BDDMockito.given(personRepository.findAll()).willReturn(
        Arrays.asList(
            PersonCreator.createPerson("Luiz", "1998-05-01"),
            PersonCreator.createPerson("Fernando", "1949-09-12"),
            PersonCreator.createPerson("Julio", "2005-04-07")));

    List<Person> people = personService.findAll();

    Assertions.assertThat(people).isNotNull();
    Assertions.assertThat(people.size()).isNotEqualTo(0);
    Assertions.assertThat(people.get(1).getBirthday()).isEqualTo("1949-09-12");
  }

  @Test
  @DisplayName("It returns an empty array when there's no registred person")
  public void testGetPeopleEmpty() throws Exception {

    BDDMockito.given(personRepository.findAll()).willReturn(Arrays.asList());

    List<Person> people = personService.findAll();

    Assertions.assertThat(people).hasSize(0);
    Assertions.assertThat(people).isEmpty();
  }

  @Test
  @DisplayName("It returns a person with their data when is updated")
  public void testUpdatePersonSuccesful() throws Exception {
    Person newPerson = PersonCreator.createPerson("Luizinho", "1999-12-01");
    Person oldPerson = PersonCreator.createPerson("Luiz", "1998-05-01");
    List<Address> addresses = Arrays.asList(
        new Address("Rua das Laranjeiras", "12345-789", 1234, "Mangueira", true, oldPerson));
    oldPerson = PersonCreator.createPersonWithAddresses(oldPerson, addresses);

    BDDMockito.given(personRepository.findById(ArgumentMatchers.any())).willReturn(Optional.of(oldPerson));
    BDDMockito.given(personRepository.save(ArgumentMatchers.any())).willReturn(oldPerson);

    Person personUpdated = personService.update(ArgumentMatchers.any(), newPerson);
    Assertions.assertThat(personUpdated).isNotNull();
    Assertions.assertThat(personUpdated.getName()).isEqualTo("Luizinho");
    Assertions.assertThat(personUpdated.getAddresses().get(0).getCity()).isEqualTo("Mangueira");
    Assertions.assertThat(personUpdated).isInstanceOf(Person.class);
  }

  @Test
  @DisplayName("It throws an error when there's non existing person")
  public void testUpdatePersonFailure() throws Exception {
    assertThrows(Exception.class, () -> {
      personService.update(ArgumentMatchers.any(), ArgumentMatchers.any());
    });
  }

  @Test
  @DisplayName("It deletes a person")
  public void testDeletePersonSuccessful() throws Exception {
    Person person = PersonCreator.createPerson("Luiz", "1998-05-01");
    List<Address> addresses = Arrays.asList(
        new Address("Rua das Laranjeiras", "12345-789", 1234, "Mangueira", true, person));
    person = PersonCreator.createPersonWithAddresses(person, addresses);

    BDDMockito.given(personRepository.findById(ArgumentMatchers.any())).willReturn(Optional.of(person));

    assertDoesNotThrow(() -> personService.delete(1L));
  }

  @Test
  @DisplayName("It fails when try to delete a non existing person")
  public void testDeletePersonFailure() {
    assertThrows(Exception.class, () -> personService.delete(1L));
  }
}
