package br.com.attornatuschallenge.adapters.outbound.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.attornatuschallenge.adapters.inbound.entity.PersonEntity;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {
}
