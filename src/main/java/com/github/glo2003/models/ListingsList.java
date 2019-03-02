package com.github.glo2003.models;

import java.util.List;

public class ListingsList {

    private List<Listing> listings;

    public ListingsList(List<Listing> listings) {
        setListings(listings);
    }

    /***** SETTERS ******/

    public void setListings(List<Listing> listings) {
        this.listings = listings;
    }

    /***** GETTERS ******/

    public List<Listing> getListings(){return listings;}

}
