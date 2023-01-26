package br.com.attornatuschallenge.application.core.domain;

public class Address {
  private Long id;
  private String publicSpace;
  private String zipCode;
  private int number;
  private String city;
  private boolean mainAddress;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getPublicSpace() {
    return publicSpace;
  }

  public void setPublicSpace(String publicSpace) {
    this.publicSpace = publicSpace;
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  public int getNumber() {
    return number;
  }

  public void setNumber(int number) {
    this.number = number;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public boolean isMainAddress() {
    return mainAddress;
  }

  public void setMainAddress(boolean mainAddress) {
    this.mainAddress = mainAddress;
  }

  @Override
  public String toString() {
    return "Address [id=" + id + ", publicSpace=" + publicSpace + ", zipCode=" + zipCode + ", number=" + number
        + ", city=" + city + ", mainAddress=" + mainAddress + "]";
  }

}
