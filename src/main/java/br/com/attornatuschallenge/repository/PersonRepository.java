package br.com.attornatuschallenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.attornatuschallenge.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
