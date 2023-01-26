package br.com.attornatuschallenge.adapters.inbound.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import br.com.attornatuschallenge.adapters.inbound.entity.AddressEntity;
import br.com.attornatuschallenge.application.core.domain.Address;

@Component
public class AddressEntityToAddressMapper {
  public Address mapper(AddressEntity addressEntity) {
    Address address = new Address();
    BeanUtils.copyProperties(addressEntity, address);
    return address;
  }
}
