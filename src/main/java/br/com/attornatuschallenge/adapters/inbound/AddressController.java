package br.com.attornatuschallenge.adapters.inbound;

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

import br.com.attornatuschallenge.adapters.inbound.entity.AddressEntity;
import br.com.attornatuschallenge.application.core.exception.error.ResourceNotFoundException;
import br.com.attornatuschallenge.service.AddressService;

@RestController
@RequestMapping("/api")
public class AddressController {
  @Autowired
  AddressService addressService;

  @PostMapping("/address/{id}")
  public ResponseEntity<AddressEntity> save(@PathVariable("id") Long id, @RequestBody AddressEntity address) {
    try {
      return new ResponseEntity<AddressEntity>(
        addressService.save(id, address),
        HttpStatus.CREATED);
    } catch (Exception err) {
      throw new ResourceNotFoundException(err.getMessage());
    }
  }

  @GetMapping("/addresses/{id}")
  public ResponseEntity<List<AddressEntity>> getAddresses(@PathVariable("id") Long id) {
    try {
      return ResponseEntity.ok(addressService.getAddresses(id));
    } catch (Exception err) {
      throw new ResourceNotFoundException(err.getMessage());
    }
  }

  @GetMapping("/address/{id}")
  public ResponseEntity<AddressEntity> getMainAddress(@PathVariable("id") Long id) {
    try {
      return new ResponseEntity<AddressEntity>(addressService.getMainAddress(id), HttpStatus.OK) ;
    } catch (Exception err) {
      throw new ResourceNotFoundException(err.getMessage());
    }
  }
}
