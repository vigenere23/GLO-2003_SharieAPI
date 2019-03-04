package com.github.glo2003.models;

import java.util.*;
import java.time.LocalDateTime;

public class Listing {

  private String title;
  private String description;
  private LocalDateTime[] availabilities;
  private Owner owner;

  public Listing() {
    setTitle("");
    setDescription("");

    // TODO : Mettre le for à bonne place?
    for(int i=0;i<7;i++)
      availabilities[i] = LocalDateTime.now().plusDays(i);
    setAvailabilities(availabilities);

    setOwner(new Owner());
  }

  public Listing(String title, String description, LocalDateTime[] availabilities, Owner owner) {
    setTitle(title);
    setDescription(description);
    setAvailabilities(availabilities);
    setOwner(owner);
  }

  public Listing(String title, String description, LocalDateTime[] availabilities, String ownerName, String ownerPhoneNumber, String ownerEmail) {
    this(title, description, availabilities, new Owner(ownerName, ownerPhoneNumber, ownerEmail));
  }

  /***** SETTERS ******/

  public void setTitle(String title) {
    this.title = title;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setAvailabilities(LocalDateTime[] availabilities) {this.availabilities = availabilities;}

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

  public LocalDateTime[] getAvailabilities() { return availabilities;}

  public Owner getOwner() {
    return owner;
  }
}