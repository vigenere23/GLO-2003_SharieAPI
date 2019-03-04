package com.github.glo2003.models;

import com.github.glo2003.helpers.ItemNotFoundException;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

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
    Calendar now = Calendar.getInstance();
    now.setTimeZone(TimeZone.getTimeZone("UTC"));
    now.set(Calendar.HOUR_OF_DAY, 0);
    now.set(Calendar.MINUTE, 0);
    now.set(Calendar.SECOND, 0);
    now.set(Calendar.MILLISECOND, 0);
    Instant today = now.toInstant();

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

  public List<String> getAvailabilities() {

    //TODO Maybe LocalDateTime is not the best
    //TODO Instant is ISO-8601 based
    // But LocalDateTime works best for getting/setting days...

    List<String> ISO8601Dates = new ArrayList<>();
    for (Instant instant : availabilities) {
      ISO8601Dates.add(instant.toString());
    }
    return ISO8601Dates;
  }

  public Owner getOwner() { return owner; }

  /***** LOGIC *****/

  public void book(List<LocalDateTime> bookings) throws ItemNotFoundException {
    //TODO remove date from availabilities
    //TODO throw exception if date not in availabilities

    // On itère dans la liste booking et on recherche les entrées similaires
    for(LocalDateTime localDateTime : bookings) {
      if(availabilities.contains(localDateTime))
        availabilities.remove(localDateTime);
      else{
        throw new ItemNotFoundException("One of the date is not available");
      }
    }


  }
}
