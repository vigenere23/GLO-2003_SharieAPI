package com.github.glo2003.models;

import java.util.*;

public class Listing {

  private String title;
  private String description;
  private ArrayList<String> bookings;
  private Owner owner;

  public Listing() {
    setTitle("");
    setDescription("");
    setDate(new ArrayList<>());
    setOwner(new Owner());
  }

  public Listing(String title, String description, ArrayList<String> bookings, Owner owner) {
    setTitle(title);
    setDescription(description);
    setDate(bookings);
    setOwner(owner);
  }

  public Listing(String title, String description, ArrayList<String> bookings, String ownerName, String ownerPhoneNumber, String ownerEmail) {
    this(title, description, bookings, new Owner(ownerName, ownerPhoneNumber, ownerEmail));
  }

  /***** SETTERS ******/

  public void setTitle(String title) {
    this.title = title;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setDate(ArrayList<String> bookings) {this.bookings = bookings;}

  public void setOwner(Owner owner) {
    this.owner = owner;
  }

  /***** GETTERS ******/

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public ArrayList<String> getBookings() { return bookings;}

  public Owner getOwner() {
    return owner;
  }
}