package com.github.glo2003.models;

import com.github.glo2003.exceptions.ItemNotFoundException;
import com.github.glo2003.helpers.DateTimeHelper;
import dev.morphia.annotations.Id;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;


public class Listing {

    @Id
    protected ObjectId id;
    private String title;
    private String description;
    private List<Instant> availabilities;
    private Owner owner;

    //<editor-fold desc="Constructors">

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

    //</editor-fold>

    public void book(List<Instant> bookings) throws ItemNotFoundException{
        for (Instant instant : bookings){
            if (availabilities.contains(instant)) {
                availabilities.remove(instant);
            } else {
                throw new ItemNotFoundException("One of the date is not available");
            }
        }
    }

    //<editor-fold desc="Getters & Setters">

    public ObjectId getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Instant> getAvailabilities() {
        return availabilities;
    }

    public void initAvailabilities() {
        availabilities = new ArrayList<>();
        Instant today = DateTimeHelper.removeTimeFromInstant(Instant.now());

        for(int i = 0; i < 7; i++) {
            availabilities.add(today.plus(i, ChronoUnit.DAYS));
        }
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    //</editor-fold>

}
