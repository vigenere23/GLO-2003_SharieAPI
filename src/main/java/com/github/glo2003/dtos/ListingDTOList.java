package com.github.glo2003.dtos;

import com.github.glo2003.models.Listing;

import java.util.ArrayList;
import java.util.List;

public class ListingDTOList {

    public List<ListingDTO> listings;

    public ListingDTOList(List<Listing> listings) {
        initListings(listings);
    }

    private void initListings(List<Listing> listings) {
        this.listings = new ArrayList<>();
        for (Listing listing : listings) {
            this.listings.add(new ListingDTO(listing));
        }
    }

}
