package com.github.glo2003.dtos;

import com.github.glo2003.models.Listing;
import com.github.glo2003.models.Owner;
import org.bson.types.ObjectId;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ListingDTO {

    protected ObjectId id;
    public String title;
    public String description;
    public List<String> availabilities;
    public Owner owner;

    public ListingDTO() {
        id = new ObjectId("");
        title = "";
        description = "";
        owner = new Owner();
        availabilities = new ArrayList<>();
    }

    public ListingDTO(Listing listing) {
        id = listing.getId();
        title = listing.getTitle();
        description = listing.getDescription();
        owner = listing.getOwner();
        initAvailabilities(listing.getAvailabilities());
    }

    public void initAvailabilities(List<Instant> instants) {
        availabilities = new ArrayList<>();
        for (Instant instant : instants) {
            availabilities.add(instant.toString());
        }
    }
}
