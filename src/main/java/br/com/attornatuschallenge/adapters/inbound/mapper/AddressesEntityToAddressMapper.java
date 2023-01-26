package br.com.attornatuschallenge.adapters.inbound.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import br.com.attornatuschallenge.adapters.inbound.entity.AddressEntity;
import br.com.attornatuschallenge.application.core.domain.Address;

@Component
public class AddressesEntityToAddressMapper {
  public List<Address> mapper(List<AddressEntity> addressesEntity) {
    List<Address> addresses = new ArrayList<>();
    for (int i = 0; i < addressesEntity.size(); i++) {
      var address = new Address();
      BeanUtils.copyProperties(addressesEntity.get(i), address);
      addresses.add(address);
    }
    return addresses;
  }
}
