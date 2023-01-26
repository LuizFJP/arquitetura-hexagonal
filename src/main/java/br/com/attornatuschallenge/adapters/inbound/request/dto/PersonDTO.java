package br.com.attornatuschallenge.adapters.inbound.request.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class PersonDTO {
  private String name;
  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date birthday;
  private List<AddressDTO> addresses;

}
