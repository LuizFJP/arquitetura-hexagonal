package br.com.attornatuschallenge.entity;

import java.io.Serializable;
import java.util.*;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Cacheable(true)
public class Person implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "name")
  private String name;

  @Override
  public String toString() {
    return "Person [id=" + id + ", name=" + name + ", birthday=" + birthday + ", addresses=" + addresses + "]";
  }

  @Column(name = "birthday")
  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date birthday;

  @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)

  @Column(name = "addresses")
  @JsonManagedReference
  private List<Address> addresses = new ArrayList<>();

  public Person() {}

  public Person(String name, Date birthday) {
    this.name = name;
    this.birthday = birthday;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }

  public void setAddresses(List<Address> addresses) {
    this.addresses = addresses;
  }

  public String getName() {
    return name;
  }

  public Date getBirthday() {
    return birthday;
  }

  public List<Address> getAddresses() {
    return addresses;
  }
}
