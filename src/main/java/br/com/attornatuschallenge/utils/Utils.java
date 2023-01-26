package br.com.attornatuschallenge.utils;

import java.util.List;

import br.com.attornatuschallenge.adapters.inbound.entity.AddressEntity;

public abstract class Utils {
  static public boolean personHasAddress(List<AddressEntity> addresses) {
    return !addresses.isEmpty();
  }
}
