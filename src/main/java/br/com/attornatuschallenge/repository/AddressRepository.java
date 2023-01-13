package br.com.attornatuschallenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.attornatuschallenge.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
