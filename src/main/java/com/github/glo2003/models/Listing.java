package com.github.glo2003.models;

import com.github.glo2003.exceptions.ItemNotFoundException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Listing {

  private String title;
  private String description;
  private List<Instant> availabilities;
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
    Instant today = Instant.now().truncatedTo(ChronoUnit.DAYS);

    for(int i = 0; i < 7; i++) {
      availabilities.add(today.plus(i, ChronoUnit.DAYS));
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

  public List<Instant> getAvailabilities() { return availabilities; }

  public Owner getOwner() { return owner; }

  /***** LOGIC *****/

  public void book(List<Instant> bookings) throws ItemNotFoundException {
    for(Instant instant : bookings) {
      if (availabilities.contains(instant))
        availabilities.remove(instant);
      else {
        throw new ItemNotFoundException("One of the date is not available");
      }
    }
  }
}
