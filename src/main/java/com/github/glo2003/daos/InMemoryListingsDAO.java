package com.github.glo2003.daos;

import com.github.glo2003.exceptions.ItemAlreadyExistsException;
import com.github.glo2003.exceptions.ItemNotFoundException;
import com.github.glo2003.exceptions.ParameterParsingException;
import com.github.glo2003.models.Listing;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.*;

public class InMemoryListingsDAO implements ListingsDAO {
    private Map<String, Listing> listings = new HashMap<>();

    @Override
    public Listing get(String id) throws ItemNotFoundException {
        Listing listing = listings.get(id);
        if (listing == null) {
            throw new ItemNotFoundException(String.format("No listing with id '%s' was found", id));
        }
        return listing;
    }

    @Override
    public List<Listing> getAll() {
        return new ArrayList<>(listings.values());
    }

    @Override
    public List<Listing> getAllWithTitle(String title) {
        ArrayList<Listing> listOfListings = new ArrayList<>();
        listings.forEach((k, v) -> {
            if(title.equals(v.getTitle())) {
                listOfListings.add(listings.get(k));
            }
        });
        return listOfListings;
    }
    
    public List<Listing> getAllSpecificDate(LocalDate date) {
        List<Listing> filteredListings = new ArrayList<>();
        for (Listing listing: listings.values()) {
            for (Instant availability:listing.getAvailabilities()) {
                if(availability.atZone(ZoneOffset.systemDefault()).getYear() == date.getYear()
                        && availability.atZone(ZoneOffset.systemDefault()).getMonthValue() == date.getMonthValue()
                        && availability.atZone(ZoneOffset.systemDefault()).getDayOfMonth() == date.getDayOfMonth()){
                    filteredListings.add(listing);
                }
            }
        }
        return filteredListings;
    }

    @Override
    public String save(Listing listing) throws ItemAlreadyExistsException {
        if (listings.containsValue(listing)) {
            throw new ItemAlreadyExistsException("The listing already exists");
        }

        String id = UUID.randomUUID().toString();
        while (listings.containsKey(id)) {
            id = UUID.randomUUID().toString();
        }
        listings.put(id, listing);
        return id;
    }

    @Override
    public void reset() {
        listings.clear();
    }

    @Override
    public void addRating(String listingId, Integer rating) throws ParameterParsingException {
        Listing listing = listings.get(listingId);
        listing.addRating(rating);
    }
}
