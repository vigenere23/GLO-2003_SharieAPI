package com.github.glo2003.models;

import java.util.*;
import java.time.LocalDateTime;

public class Listing {

  private String title;
  private String description;
  private ArrayList<LocalDateTime> availabilities;
  private Owner owner;

  public Listing() {
    setTitle("");
    setDescription("");
    setAvailabilities(new ArrayList<>());
    setOwner(new Owner());
  }

  public Listing(String title, String description, ArrayList<LocalDateTime> availabilities, Owner owner) {
    setTitle(title);
    setDescription(description);
    setAvailabilities(availabilities);
    setOwner(owner);
  }

  public Listing(String title, String description, ArrayList<LocalDateTime> availabilities, String ownerName, String ownerPhoneNumber, String ownerEmail) {
    this(title, description, availabilities, new Owner(ownerName, ownerPhoneNumber, ownerEmail));
  }

  /***** SETTERS ******/

  public void setTitle(String title) {
    this.title = title;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setAvailabilities(ArrayList<LocalDateTime> availabilities) {this.availabilities = availabilities;}

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

  public ArrayList<LocalDateTime> getAvailabilities() { return availabilities;}

  public Owner getOwner() {
    return owner;
  }
}