package br.com.attornatuschallenge.http.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.attornatuschallenge.entity.Address;
import br.com.attornatuschallenge.service.AddressService;

@RestController
@RequestMapping("/api")
public class AddressController {
  @Autowired
  AddressService addressService;

  @PostMapping("/address/{id}")
  @ResponseStatus(HttpStatus.CREATED)
  public Address save(@PathVariable("id") Long id, @RequestBody Address address) {
    try {
      return addressService.save(id, address);
    } catch (Exception err) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, err.getMessage());
    }

  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/address/{id}")
  public Address getMainAddress(@PathVariable("id") Long id) {
    try {
      return addressService.getMainAddress(id);
    } catch (Exception err) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, err.getMessage());
    }
  }
}
