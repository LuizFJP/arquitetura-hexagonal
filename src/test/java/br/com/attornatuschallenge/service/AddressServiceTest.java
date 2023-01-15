package br.com.attornatuschallenge.service;

import static org.junit.Assert.assertThrows;

import java.util.Arrays;
import java.util.List;

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
import br.com.attornatuschallenge.error.ResourceNotFoundException;
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
    Address address = new Address("Rua das Laranjeiras", "12345-789", 1234, "Mangueira", true, null);
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

    BDDMockito.given(personService.findById(1L))
    .willThrow(new ResourceNotFoundException("Person not found with id 1"));
  
    Address address = new Address("Rua das Laranjeiras", "12345-789", 1234, "Mangueira", true, null);

    assertThrows(ResourceNotFoundException.class, () -> addressService.save(1L, address));
  }

  @Test
  @DisplayName("It Gets all addresses")
  public void testGetAddressesSuccessful() throws Exception {
    Person person = PersonCreator.createPerson("Luiz", "1998-05-01");

    List<Address> addresses = Arrays.asList(
        new Address("Rua das Laranjeiras", "19875-789", 4567, "Mangueira", true, person),
        new Address("Rua das Amoras", "12345-789", 1234, "Tapereba", false, person));

    person.setAddresses(addresses);

    BDDMockito.given(personService.findById(1L)).willReturn(person);

    List<Address> addressesFound = addressService.getAddresses(1L);

    Assertions.assertThat(addressesFound).isNotNull();
    Assertions.assertThat(addressesFound).hasSize(2);
    Assertions.assertThat(addressesFound.get(0).isMainAddress()
    ).isEqualTo(true);
    Assertions.assertThat(addressesFound.get(1).getPublicSpace()
    ).isEqualTo("Rua das Amoras");
  }

  @Test
  @DisplayName("It fails when try get addresses when there's non existing person")
  public void testGetAddressesFailure() throws Exception {
    BDDMockito.given(personService.findById(1L))
    .willThrow(new ResourceNotFoundException("Person not found with id 1"));

    assertThrows(ResourceNotFoundException.class, () -> addressService.getAddresses(1L));
  }

  @Test
  @DisplayName("It Gets main address")
  public void testGetMainAddressSuccessful() throws Exception {
    Person person = PersonCreator.createPerson("Luiz", "1998-05-01");
    Address address = new Address("Rua das Laranjeiras", "12345-789", 1234, "Mangueira", true, null);
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
     BDDMockito.given(personService.findById(1L))
    .willThrow(new ResourceNotFoundException("Person not found with id 1"));
    assertThrows(ResourceNotFoundException.class, () -> addressService.getMainAddress(1L));
  }
}
