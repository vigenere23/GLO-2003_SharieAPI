package com.github.glo2003.dtos;

import com.github.glo2003.models.Listing;
import com.github.glo2003.models.Owner;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ListingDTO {

    public String title;
    public String description;
    public List<String> availabilities;
    public Owner owner;

    public ListingDTO(Listing listing) {
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
