package com.github.glo2003.models;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Listing {

  private String title;
  private String description;
  private List<LocalDateTime> availabilities;
  private Owner owner;

  public Listing() {
    setTitle("");
    setDescription("");
    initAvailabilities();
    setOwner(new Owner());
  }

  public Listing(String title, String description, Owner owner) {
    setTitle(title);
    setDescription(description);
    initAvailabilities();
    setOwner(owner);
  }

  public Listing(String title, String description, String ownerName, String ownerPhoneNumber, String ownerEmail) {
    this(title, description, new Owner(ownerName, ownerPhoneNumber, ownerEmail));
  }

  /***** SETTERS ******/

  public void setTitle(String title) {
    this.title = title;
  }

  public void setDescription(String description) { this.description = description; }

  public void initAvailabilities() {
    availabilities = new ArrayList<>();

    for(int i = 0; i < 7; i++) {
      availabilities.add(LocalDateTime.now().plusDays(i));
    }
  }

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

  public List<Instant> getAvailabilities() {
    //TODO Maybe LocalDateTime is not the best
    //TODO Instant is ISO-8601 based
    // But LocalDateTime works best for getting/setting days...
    List<Instant> dates = new ArrayList<>();
    for (LocalDateTime date : availabilities) {
      dates.add(Instant.from(date));
    }
    return dates;
  }

  public Owner getOwner() { return owner; }

  /***** LOGIC *****/

  public void book(List<LocalDateTime> bookings) {
    //TODO remove date from availabilities
    //TODO throw exception if date not in availabilities
  }
}