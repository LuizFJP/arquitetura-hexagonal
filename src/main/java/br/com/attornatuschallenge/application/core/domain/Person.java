package br.com.attornatuschallenge.application.core.domain;

import java.util.Date;
import java.util.List;

public class Person {
  private Long id;
  private String name;
  private Date birthday;
  private List<Address> addresses;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getBirthday() {
    return birthday;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }

  public List<Address> getAddresses() {
    return addresses;
  }

  public void setAddresses(List<Address> addresses) {
    this.addresses = addresses;
  }

  @Override
  public String toString() {
    return "Person [id=" + id + ", name=" + name + ", birthday=" + birthday + ", addresses=" + addresses + "]";
  }

}
