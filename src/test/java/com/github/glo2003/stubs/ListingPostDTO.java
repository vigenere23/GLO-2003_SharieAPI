package com.github.glo2003.stubs;

import com.github.glo2003.models.Listing;
import com.github.glo2003.models.Owner;

public class ListingPostDTO {
    public String title;
    public String description;
    public Owner owner;

    public ListingPostDTO(Listing listing) {
        title = listing.getTitle();
        description = listing.getDescription();
        owner = listing.getOwner();
    }
}
