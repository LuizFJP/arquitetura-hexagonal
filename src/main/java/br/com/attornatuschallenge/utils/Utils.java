package br.com.attornatuschallenge.utils;

import java.util.List;

import br.com.attornatuschallenge.entity.Address;

public abstract class Utils {
  static public boolean personHasAddress(List<Address> addresses) {
    return !addresses.isEmpty();
  }
}
