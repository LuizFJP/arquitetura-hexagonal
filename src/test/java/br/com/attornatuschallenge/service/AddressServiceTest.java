package br.com.attornatuschallenge.service;

import static org.junit.Assert.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.attornatuschallenge.entity.Address;
import br.com.attornatuschallenge.entity.Person;
import br.com.attornatuschallenge.repository.AddressRepository;
import br.com.attornatuschallenge.repository.PersonRepository;
import br.com.attornatuschallenge.utils.PersonCreator;

@ExtendWith(SpringExtension.class)
public class AddressServiceTest {
  @InjectMocks
  private AddressService addressService;

  @Mock
  private AddressRepository addressRepository;
  @Mock
  private PersonRepository personRepository;
  @Mock
  private PersonService personService;

  @Test
  @DisplayName("Create an address with success")
  public void testCreateAddressSuccessful() throws Exception {
    Person person = PersonCreator.createPerson("Luiz", "1998-05-01");
    Address address = new Address("Rua das Laranjeiras", "12345-789", 1234, "Mangueira", null);
    address.setMainAddress(true);
    person.setAddresses(Arrays.asList(address));

    BDDMockito.given(personService.findById(1L)).willReturn(person);
    BDDMockito.given(addressRepository.save(address)).willReturn(address);

    Address addressUpdated = addressService.save(1L, address);

    Assertions.assertThat(addressUpdated).isNotNull();
    Assertions.assertThat(addressUpdated.getPerson()).isEqualTo(person);
  }

  @Test
  @DisplayName("It fails when try to create an address when there's non existing person")
  public void testCreateAddressFailure() throws Exception {
    Address address = new Address("Rua das Laranjeiras", "12345-789", 1234, "Mangueira", null);
    assertThrows(Exception.class, () -> addressService.save(1L, address));
  }

  @Test
  @DisplayName("It Gets main address")
  public void testGetMainAddressSuccessful() throws Exception {
    Person person = PersonCreator.createPerson("Luiz", "1998-05-01");
    Address address = new Address("Rua das Laranjeiras", "12345-789", 1234, "Mangueira", null);
    address.setMainAddress(true);
    person.setAddresses(Arrays.asList(address));

    BDDMockito.given(personService.findById(1L)).willReturn(person);

    Address addressFound = addressService.getMainAddress(1L);

    Assertions.assertThat(addressFound).isNotNull();
    Assertions.assertThat(addressFound.isMainAddress()
    ).isEqualTo(true);
  }

  @Test
  @DisplayName("It fails when try get a main address when there's non existing person")
  public void testGetMainAddressFailure() throws Exception {
    assertThrows(Exception.class, () -> addressService.getMainAddress(1L));
  }
}
