package com.github.glo2003.models;

public class Listing {

  private String title;
  private String description;
  private Owner owner;

  public Listing() {
    setTitle("");
    setDescription("");
    setOwner(new Owner());
  }

  public Listing(String title, String description, Owner owner) {
    setTitle(title);
    setDescription(description);
    setOwner(owner);
  }

  public Listing(String title, String description, String ownerName, String ownerPhoneNumber, String ownerEmail) {
    this(title, description, new Owner(ownerName, ownerPhoneNumber, ownerEmail));
  }

  /***** SETTERS ******/

  public void setTitle(String title) {
    this.title = title;
  }

  public void setDescription(String description) {
    this.description = description;
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

  public Owner getOwner() {
    return owner;
  }
}