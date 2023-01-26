package br.com.attornatuschallenge.adapters.inbound.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import br.com.attornatuschallenge.adapters.inbound.request.dto.AddressDTO;
import br.com.attornatuschallenge.application.core.domain.Address;

@Component
public class AddressDTOToAddressMapper {
  public Address mapper(AddressDTO addressDTO) {
    Address address = new Address();
    BeanUtils.copyProperties(addressDTO, address);
    return address;
  }
}
