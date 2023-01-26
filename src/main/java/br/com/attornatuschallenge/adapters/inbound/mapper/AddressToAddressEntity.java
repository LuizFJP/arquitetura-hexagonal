package br.com.attornatuschallenge.adapters.inbound.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import br.com.attornatuschallenge.adapters.inbound.entity.AddressEntity;
import br.com.attornatuschallenge.application.core.domain.Address;

@Component
public class AddressToAddressEntity {
  public AddressEntity mapper(Address address) {
    AddressEntity addressEntity = new AddressEntity();
    BeanUtils.copyProperties(address, addressEntity);
    return addressEntity;
  }
}
