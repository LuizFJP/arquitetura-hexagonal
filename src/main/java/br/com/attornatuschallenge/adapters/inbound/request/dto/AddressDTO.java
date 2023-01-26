package br.com.attornatuschallenge.adapters.inbound.request.dto;

import lombok.Data;

@Data
public class AddressDTO {
  private String publicSpace;
  private String zipCode;
  private int number;
  private String city;
  private boolean mainAddress;
}
