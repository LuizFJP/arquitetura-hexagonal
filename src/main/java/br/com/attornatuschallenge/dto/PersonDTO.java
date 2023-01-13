package br.com.attornatuschallenge.dto;

import java.util.Date;
import java.util.List;

import br.com.attornatuschallenge.entity.Address;
import lombok.Data;

@Data
public class PersonDTO {
  private String name;
  private Date birthday;
  private List<Address> addresses;

  @Override
  public String toString() {
    return "Person [name=" + name + ", birthday=" + birthday + ", addresses=" + addresses + "]";
  }
}
