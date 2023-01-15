package br.com.attornatuschallenge.http.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import static org.hamcrest.Matchers.*;

import br.com.attornatuschallenge.entity.Address;
import br.com.attornatuschallenge.entity.Person;
import br.com.attornatuschallenge.service.AddressService;
import br.com.attornatuschallenge.utils.PersonCreator;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AddressController.class)
public class AddressControllerTest {
  @InjectMocks
  private AddressController addressController;
  @Autowired
  private MockMvc mvc;

  @MockBean
  AddressService addressService;

  ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

  @Test
  @DisplayName("Create an address with success")
  public void testCreateAddressSuccessful() throws Exception {
    Person person = PersonCreator.createPerson("Luiz", "1998-05-01");
    Address address = new Address("Rua das Laranjeiras", "12345-789", 1234, "Mangueira", person);

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
  public void testCreatePersonFailure() throws Exception {
    Person person = PersonCreator.createPerson("Luiz", "1998-05-01");
    Address address = new Address("Rua das Laranjeiras", "12345-789", 1234, "Mangueira", person);

    String addressRequest = objectWriter.writeValueAsString(address);

    BDDMockito.given(addressService.save(ArgumentMatchers.any(), ArgumentMatchers.any()))
        .willThrow(new Exception("Person not found."));

    mvc.perform(
        MockMvcRequestBuilders.post("/api/address/1")
            .content(addressRequest)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isNotFound())
        .andExpect(MockMvcResultMatchers.status().reason(("Person not found.")));
  }

  @Test
  @DisplayName("Get a main address with success")
  public void testGetAddressSuccessful() throws Exception {
    Person person = PersonCreator.createPerson("Luiz", "1998-05-01");
    Address address = new Address("Rua das Laranjeiras", "12345-789", 1234, "Mangueira", person);
    address.setMainAddress(true);

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
    .willThrow(new Exception("Person not found."));

mvc.perform(
    MockMvcRequestBuilders.get("/api/address/1"))
    .andExpect(MockMvcResultMatchers.status().isNotFound())
    .andExpect(MockMvcResultMatchers.status().reason(("Person not found.")));
  }
}
