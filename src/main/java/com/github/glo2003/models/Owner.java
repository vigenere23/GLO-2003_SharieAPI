package com.github.glo2003.models;

public class Owner {

  private String _name;
  private String _phoneNumber;
  private String _email;

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
    _name = name;
  }

  public void setPhoneNumber(String phoneNumber) {
    _phoneNumber = phoneNumber;
  }

  public void setEmail(String email) {
    _email = email;
  }

  /****** GETTERS ******/

  public String getName() {
    return _name;
  }

  public String getPhoneNumber() {
    return _phoneNumber;
  }

  public String getEmail() {
    return _email;
  }
}