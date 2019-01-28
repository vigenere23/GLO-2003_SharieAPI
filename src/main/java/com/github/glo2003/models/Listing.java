package com.github.glo2003.models;

public class Listing {

  private String _title;
  private String _description;
  private Owner _owner;

  public Listing() {
    setTitle("");
    setDescription("");
    setOwner(new Owner());
  }

  private Listing(
    String title,
    String description,
    Owner owner
  ) {
    setTitle(title);
    setDescription(description);
    setOwner(owner);
  }

  public static Listing create(Object params /*TODO really an Object here?*/) {
    return new Listing(/*TODO parse params, call private constructor*/);
  }

  /***** SETTERS ******/

  public void setTitle(String title) {
    // Some validation / filtering
    _title = title;
  }

  public void setDescription(String description) {
    // Some validation / filtering
    _description = description;
  }

  public void setOwner(Owner owner) {
    // Validation should be done when owner is created
    _owner = owner;
  }

  /***** GETTERS ******/

  public String getTitle() {
    return _title;
  }

  public String getDescription() {
    return _description;
  }

  public Owner getOwner() {
    return _owner;
  }
}