package com.github.glo2003.models;

public class Owner {

  private String name;
  private String phoneNumber;
  private String email;

  public Owner() {
    setName("");
    setPhoneNumber("");
    setEmail("");
  }

  private Owner(
    String name,
    String phoneNumber,
    String email
  ) {
    setName(name);
    setPhoneNumber(phoneNumber);
    setEmail(email);
  }

  public static Owner create(Object params) {
    return new Owner(/*TODO parse params, call the private constructor*/);
  }

  /****** SETTERS ******/

  public void setName(String name) {
    this.name = name;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  /****** GETTERS ******/

  public String getName() {
    return name;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public String getEmail() {
    return email;
  }
}