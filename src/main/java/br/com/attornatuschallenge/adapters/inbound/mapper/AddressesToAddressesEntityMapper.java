package br.com.attornatuschallenge.adapters.inbound.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import br.com.attornatuschallenge.adapters.inbound.entity.AddressEntity;
import br.com.attornatuschallenge.application.core.domain.Address;

@Component
public class AddressesToAddressesEntityMapper {
  public List<AddressEntity> mapper(List<Address> addresses) {
    List<AddressEntity> addressesEntity = new ArrayList<>();
    for (int i = 0; i < addresses.size(); i++) {
      var address = new AddressEntity();
      BeanUtils.copyProperties(addresses.get(i), address);
      addressesEntity.add(address);
    }
    return addressesEntity;
  }
}
