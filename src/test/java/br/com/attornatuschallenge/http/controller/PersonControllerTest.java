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

import br.com.attornatuschallenge.entity.Address;
import br.com.attornatuschallenge.entity.Person;
import br.com.attornatuschallenge.service.PersonService;
import br.com.attornatuschallenge.utils.PersonCreator;

import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PersonController.class)
public class PersonControllerTest {
  @InjectMocks
  private PersonController personController;
  @Autowired
  private MockMvc mvc;

  @MockBean
  private PersonService personService;

  ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

  @Test
  @DisplayName("Create a person with success")
  public void testCreatePersonSuccessful() throws Exception {

    Person person = PersonCreator.createPerson("Luiz", "1998-05-01");
    List<Address> addresses = Arrays.asList(
        new Address("Rua das Laranjeiras", "12345-789", 1234, "Mangueira", person),
        new Address("Rua Cupuacu", "98765-432", 5678, "Avocada", person));
    person = PersonCreator.createPersonWithAddresses(person, addresses);

    BDDMockito.given(personService.save(ArgumentMatchers.any())).willReturn(person);

    String personRequest = objectWriter.writeValueAsString(person);

    mvc.perform(
        MockMvcRequestBuilders.post("/api/person")
            .content(personRequest)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$", aMapWithSize(3)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("Luiz")))
        .andExpect(MockMvcResultMatchers.jsonPath("$.addresses[0].publicSpace", is("Rua das Laranjeiras")));
  }

  @Test
  @DisplayName("Get a person with success")
  public void testGetPersonSuccessful() throws Exception {
    Person person = PersonCreator.createPerson("Luiz", "1998-05-01");
    List<Address> addresses = Arrays.asList(
        new Address("Rua das Laranjeiras", "12345-789", 1234, "Mangueira", null),
        new Address("Rua do Acai", "98765-432", 5678, "Avocada", null));
    person = PersonCreator.createPersonWithAddresses(person, addresses);

    BDDMockito.given(personService.findById(ArgumentMatchers.any())).willReturn(person);

    mvc.perform(
        MockMvcRequestBuilders.get("/api/person/1"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$", aMapWithSize(3)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("Luiz")))
        .andExpect(MockMvcResultMatchers.jsonPath("$.addresses[1].publicSpace", is("Rua do Acai")));
  }

  @Test
  @DisplayName("It fails when getting a person non existing")
  public void testGetPersonFailure() throws Exception {

    BDDMockito.given(personService.findById(ArgumentMatchers.any())).willThrow(new Exception("Person not found."));

    mvc.perform(
        MockMvcRequestBuilders.get("/api/person/1"))
        .andExpect(MockMvcResultMatchers.status().isNotFound())
        .andExpect(MockMvcResultMatchers.status().reason(("Person not found.")));
  }

  @Test
  @DisplayName("It returns all people registred")
  public void testGetPeopleSuccesful() throws Exception {
    List<Person> people = Arrays.asList(
        PersonCreator.createPerson("Luiz", "1998-05-01"),
        PersonCreator.createPerson("Fernando", "1949-09-12"),
        PersonCreator.createPerson("Julio", "2005-04-07"));

    BDDMockito.given(personService.findAll()).willReturn(people);

    mvc.perform(
        MockMvcRequestBuilders.get("/api/people"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].birthday", is("1998-05-01")))
        .andExpect(MockMvcResultMatchers.jsonPath("$[2].name", is("Julio")));
  }

  @Test
  @DisplayName("It returns an empty array when there's no anyone registred")
  public void testGetPeopleEmpty() throws Exception {
    List<Person> people = new ArrayList<>();

    BDDMockito.given(personService.findAll()).willReturn(people);

    mvc.perform(
        MockMvcRequestBuilders.get("/api/people"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(0)));
  }

  @Test
  @DisplayName("It returns an empty array when there's no anyone registred")
  public void testPutPersonSuccesful() throws Exception {

    Person person = PersonCreator.createPerson("Luiz", "1997-09-20");
    Address address = new Address("Rua das Laranjeiras", "12345-789", 1234, "Mangueira", person);
    address.setMainAddress(true);
    person.setAddresses(Arrays.asList(address));

    BDDMockito.given(personService.update(ArgumentMatchers.any(), ArgumentMatchers.any())).willReturn(person);
    String personRequest = objectWriter.writeValueAsString(person);

    mvc.perform(
        MockMvcRequestBuilders.put("/api/person/1")
            .content(personRequest)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$", aMapWithSize(3)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.addresses[0].mainAddress", is(true)));
  }

  @Test
  @DisplayName("It fails when update a person non existing")
  public void testPutPersonFailure() throws Exception {
    Person person = PersonCreator.createPerson("Luiz", "1997-09-20");
    Address address = new Address("Rua das Laranjeiras", "12345-789", 1234, "Mangueira", person);
    address.setMainAddress(true);
    person.setAddresses(Arrays.asList(address));

    String personRequest = objectWriter.writeValueAsString(person);

    BDDMockito.given(personService.update(ArgumentMatchers.any(), ArgumentMatchers.any()))
        .willThrow(new Exception("Person not found."));

    mvc.perform(
        MockMvcRequestBuilders.put("/api/person/1")
            .content(personRequest)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isNotFound())
        .andExpect(MockMvcResultMatchers.status().reason(("Person not found.")));
  }

  @Test
  @DisplayName("It deletes a person succesful")
  public void testDeletePersonSucessful() throws Exception {
    BDDMockito.doNothing().when(personService).delete(ArgumentMatchers.any());

    mvc.perform(
        MockMvcRequestBuilders.delete("/api/person/1"))
        .andExpect(MockMvcResultMatchers.status().isNoContent());
  }

  @Test
  @DisplayName("It can't delete a person non existing")
  public void testDeletePersonFailure() throws Exception {
    BDDMockito.doThrow(new Exception("Person not found.")).when(personService).delete(ArgumentMatchers.any());

    mvc.perform(
        MockMvcRequestBuilders.delete("/api/person/1"))
        .andExpect(MockMvcResultMatchers.status().isNotFound())
        .andExpect(MockMvcResultMatchers.status().reason(("Person not found.")));
  }
}
