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

  public static Owner create(Object params /*TODO really an Object here?*/) {
    return new Owner(/*TODO parse params, call the private constructor*/);
  }

  /****** SETTERS ******/

  public void setName(String name) {
    // Some validation/filtering
    _name = name;
  }

  public void setPhoneNumber(String phoneNumber) {
    // Some validation/filtering
    _phoneNumber = phoneNumber;
  }

  public void setEmail(String email) {
    // Some validation/filtering
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