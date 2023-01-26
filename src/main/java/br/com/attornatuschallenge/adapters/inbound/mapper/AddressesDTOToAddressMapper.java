package br.com.attornatuschallenge.adapters.inbound.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import br.com.attornatuschallenge.adapters.inbound.request.dto.AddressDTO;
import br.com.attornatuschallenge.application.core.domain.Address;

@Component
public class AddressesDTOToAddressMapper {
  public List<Address> mapper(List<AddressDTO> addressesDTO) {
    List<Address> addresses = new ArrayList<>();
    for (int i = 0; i < addressesDTO.size(); i++) {
      var address = new Address();
      BeanUtils.copyProperties(addressesDTO.get(i), address);
      addresses.add(address);
    }
    return addresses;
  }
}
