package br.com.attornatuschallenge.http.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import static org.hamcrest.Matchers.*;

import java.util.Arrays;
import java.util.List;

import br.com.attornatuschallenge.entity.Address;
import br.com.attornatuschallenge.entity.Person;
import br.com.attornatuschallenge.error.ResourceNotFoundException;
import br.com.attornatuschallenge.service.AddressService;
import br.com.attornatuschallenge.service.PersonService;
import br.com.attornatuschallenge.utils.PersonCreator;

@WebMvcTest(AddressController.class)
public class AddressControllerTest {
  @InjectMocks
  private AddressController addressController;
  @Autowired
  private MockMvc mvc;

  @MockBean
  AddressService addressService;

  ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

  PersonService personService;

  @Test
  @DisplayName("Create an address with success")
  public void testCreateAddressSuccessful() throws Exception {
    Person person = PersonCreator.createPerson("Luiz", "1998-05-01");
    Address address = new Address("Rua das Laranjeiras", "12345-789", 1234, "Mangueira", false, person);

    String addressRequest = objectWriter.writeValueAsString(address);

    BDDMockito.given(addressService.save(ArgumentMatchers.any(), ArgumentMatchers.any())).willReturn(address);

    mvc.perform(MockMvcRequestBuilders.post("/api/address/1")
        .content(addressRequest)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$", aMapWithSize(6)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.publicSpace", is("Rua das Laranjeiras")))
        .andExpect(MockMvcResultMatchers.jsonPath("$.zipCode", is("12345-789")))
        .andExpect(MockMvcResultMatchers.jsonPath("$.number", is(1234)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.city", is("Mangueira")))
        .andExpect(MockMvcResultMatchers.jsonPath("$.mainAddress", is(false)));
  }

  @Test
  @DisplayName("It fails to create an address with non existing person")
  public void testCreateAddressFailure() throws Exception {
    Person person = PersonCreator.createPerson("Luiz", "1998-05-01");
    Address address = new Address("Rua das Laranjeiras", "12345-789", 1234, "Mangueira", true, person);

    String addressRequest = objectWriter.writeValueAsString(address);

    BDDMockito.given(addressService.save(ArgumentMatchers.any(), ArgumentMatchers.any()))
        .willThrow(new ResourceNotFoundException("Person not found with id 1"));

    mvc.perform(
        MockMvcRequestBuilders.post("/api/address/1")
            .content(addressRequest)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isNotFound())
        .andExpect(MockMvcResultMatchers.jsonPath("$.detail", is("Person not found with id 1")));
  }

  @Test
  @DisplayName("Get all addresses with success")
  public void testGetAddressesSuccessful() throws Exception {
    Person person = PersonCreator.createPerson("Luiz", "1998-05-01");

    List<Address> addresses = Arrays.asList(
        new Address("Rua das Laranjeiras", "19875-789", 4567, "Mangueira", true, person),
        new Address("Rua das Amoras", "12345-789", 1234, "Tapereba", false, person));

    person.setAddresses(addresses);

    BDDMockito.given(addressService.getAddresses(ArgumentMatchers.any())).willReturn(addresses);

    mvc.perform(MockMvcRequestBuilders.get("/api/addresses/1"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].publicSpace", is("Rua das Laranjeiras")))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].zipCode", is("12345-789")))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].number", is(4567)))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].city", is("Tapereba")))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].mainAddress", is(true)));
  }

  @Test
  @DisplayName("It fails to get addresses with non existing person")
  public void testGetAddressesFailure() throws Exception {
    BDDMockito.given(addressService.getAddresses(1L))
        .willThrow(new ResourceNotFoundException("Person not found with id 1"));

    mvc.perform(
        MockMvcRequestBuilders.get("/api/addresses/1"))
        .andExpect(MockMvcResultMatchers.status().isNotFound())
        .andExpect(MockMvcResultMatchers.jsonPath("$.detail", is("Person not found with id 1")));
  }

  @Test
  @DisplayName("Get a main address with success")
  public void testGetAddressSuccessful() throws Exception {
    Person person = PersonCreator.createPerson("Luiz", "1998-05-01");
    Address address = new Address("Rua das Laranjeiras", "12345-789", 1234, "Mangueira", true, person);

    BDDMockito.given(addressService.getMainAddress(ArgumentMatchers.any())).willReturn(address);

    mvc.perform(MockMvcRequestBuilders.get("/api/address/1"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.publicSpace", is("Rua das Laranjeiras")))
        .andExpect(MockMvcResultMatchers.jsonPath("$.zipCode", is("12345-789")))
        .andExpect(MockMvcResultMatchers.jsonPath("$.number", is(1234)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.city", is("Mangueira")))
        .andExpect(MockMvcResultMatchers.jsonPath("$.mainAddress", is(true)));
  }

  @Test
  @DisplayName("It fails to get a main address with non existing person")
  public void testGetAddressFailure() throws Exception {
    BDDMockito.given(addressService.getMainAddress(ArgumentMatchers.any()))
        .willThrow(new ResourceNotFoundException("Person not found with id 1"));

    mvc.perform(
        MockMvcRequestBuilders.get("/api/address/1"))
        .andExpect(MockMvcResultMatchers.status().isNotFound())
        .andExpect(MockMvcResultMatchers.jsonPath("$.detail", is("Person not found with id 1")));
  }
}
