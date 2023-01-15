package br.com.attornatuschallenge.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Cacheable(true)
@Entity
@Table(name = "Address")
public class Address implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "publicSpace", nullable = false)
  private String publicSpace;

  @Column(name = "zipCode", nullable = false)
  private String zipCode;

  @Column(name = "number", nullable = false)
  private int number;

  @Column(name = "city", nullable = false)
  private String city;

  @Column(name = "mainAdress", nullable = true)
  private boolean mainAddress;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "person_id")
  @JsonBackReference
  private Person person;

  public Address() {}

  public Address(String publicSpace, String zipCode, int number, String city, Person person) {
    this.publicSpace = publicSpace;
    this.zipCode = zipCode;
    this.number = number;
    this.city = city;
    this.person = person;
  }

  public void setPublicSpace(String publicSpace) {
    this.publicSpace = publicSpace;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  public void setNumber(int number) {
    this.number = number;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public void setMainAddress(boolean mainAddress) {
    this.mainAddress = mainAddress;
  }

  public void setPerson(Person person) {
    this.person = person;
  }

  public String getPublicSpace() {
    return publicSpace;
  }

  public String getZipCode() {
    return zipCode;
  }

  public int getNumber() {
    return number;
  }

  public String getCity() {
    return city;
  }

  public boolean isMainAddress() {
    return mainAddress;
  }

  public Person getPerson() {
    return person;
  }

  public Long getId() {
    return id;
  }
}
