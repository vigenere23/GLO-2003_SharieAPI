package com.github.glo2003.models;

import com.github.glo2003.enums.ListingCategory;
import com.github.glo2003.exceptions.ItemNotFoundException;
import com.github.glo2003.exceptions.ParameterParsingException;
import com.github.glo2003.helpers.DateTimeHelper;
import dev.morphia.annotations.Id;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


import org.bson.types.ObjectId;


public class Listing {

    @Id protected ObjectId id;
    private String title;
    private String description;
    private ListingCategory category;
    private List<Instant> availabilities;
    private Owner owner;
    private List<Rating> ratings;

    public Listing() {
        setTitle("");
        setDescription("");
        setCategory(ListingCategory.OTHER);
        initAvailabilities();
        setOwner(new Owner());
        ratings = new ArrayList<Rating>();
    }

    public Listing(String title, String description, ListingCategory category, Owner owner) {
        setTitle(title);
        setDescription(description);
        setCategory(category);
        initAvailabilities();
        setOwner(owner);
        ratings = new ArrayList<Rating>();
    }

    public Listing(String title, String description, String category, String ownerName, String ownerPhoneNumber, String ownerEmail) {
        this(
            title,
            description,
            ListingCategory.OTHER,
            new Owner(ownerName, ownerPhoneNumber, ownerEmail)
        );
        setCategory(category);
    }

    public void book(List<Instant> bookings) throws ItemNotFoundException{
        for (Instant instant : bookings){
            if (availabilities.contains(instant)) {
                availabilities.remove(instant);
            } else {
                throw new ItemNotFoundException("One of the date is not available");
            }
        }
    }

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

    public ListingCategory getCategory() {
        return category;
    }

    public void setCategory(ListingCategory category) {
        this.category = category;
    }

    public void setCategory(String category) {
        try {
            this.category = ListingCategory.valueOf(category.toUpperCase());
        }
        catch (Exception e) {
            this.category = ListingCategory.OTHER;
        }
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

    public List<Rating> getRatings() {
        return ratings;
    }

    public void addRating(Integer score) throws ParameterParsingException {
        ratings.add(new Rating(score));
    }

}
