package br.com.attornatuschallenge.http.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.attornatuschallenge.entity.Address;
import br.com.attornatuschallenge.error.ResourceNotFoundException;
import br.com.attornatuschallenge.service.AddressService;

@RestController
@RequestMapping("/api")
public class AddressController {
  @Autowired
  AddressService addressService;

  @PostMapping("/address/{id}")
  public ResponseEntity<Address> save(@PathVariable("id") Long id, @RequestBody Address address) {
    try {
      return new ResponseEntity<Address>(
        addressService.save(id, address),
        HttpStatus.CREATED);
    } catch (Exception err) {
      throw new ResourceNotFoundException(err.getMessage());
    }
  }

  @GetMapping("/addresses/{id}")
  public ResponseEntity<List<Address>> getAddresses(@PathVariable("id") Long id) {
    try {
      return ResponseEntity.ok(addressService.getAddresses(id));
    } catch (Exception err) {
      throw new ResourceNotFoundException(err.getMessage());
    }
  }

  @GetMapping("/address/{id}")
  public ResponseEntity<Address> getMainAddress(@PathVariable("id") Long id) {
    try {
      return new ResponseEntity<Address>(addressService.getMainAddress(id), HttpStatus.OK) ;
    } catch (Exception err) {
      throw new ResourceNotFoundException(err.getMessage());
    }
  }
}
