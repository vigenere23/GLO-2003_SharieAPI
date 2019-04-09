package com.github.glo2003.daos;

import com.github.glo2003.exceptions.ItemAlreadyExistsException;
import com.github.glo2003.exceptions.ItemNotFoundException;
import com.github.glo2003.exceptions.ParameterParsingException;
import com.github.glo2003.models.Listing;

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
