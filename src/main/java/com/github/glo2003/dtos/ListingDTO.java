package com.github.glo2003.dtos;

import com.github.glo2003.enums.ListingCategory;
import com.github.glo2003.models.Listing;
import com.github.glo2003.models.Owner;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.github.glo2003.models.Rating;
import org.bson.types.ObjectId;


public class ListingDTO {

    protected ObjectId id;
    public String title;
    public String description;
    public String category;
    public List<String> availabilities;
    public List<Rating> ratings;
    public Owner owner;

    public ListingDTO() {
        id = new ObjectId("");
        title = "";
        description = "";
        category = "";
        owner = new Owner();
        availabilities = new ArrayList<>();
        ratings = new ArrayList<Rating>();
    }

    public ListingDTO(Listing listing) {
        id = listing.getId();
        title = listing.getTitle();
        description = listing.getDescription();
        owner = listing.getOwner();
        initCategory(listing.getCategory());
        initAvailabilities(listing.getAvailabilities());
        ratings = listing.getRatings();
    }

    public void initCategory(ListingCategory category) {
        this.category = category.name().toLowerCase();
    }

    public void initAvailabilities(List<Instant> instants) {
        availabilities = new ArrayList<>();
        for (Instant instant : instants) {
            availabilities.add(instant.toString());
        }
    }
}
