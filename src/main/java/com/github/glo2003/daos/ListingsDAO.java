package com.github.glo2003.daos;

import com.github.glo2003.exceptions.ItemAlreadyExistsException;
import com.github.glo2003.exceptions.ItemNotFoundException;
import com.github.glo2003.exceptions.ParameterParsingException;
import com.github.glo2003.models.Listing;

import java.util.List;

public interface ListingsDAO {

    Listing get(String id) throws ItemNotFoundException;

    List<Listing> getAll();

    String save(Listing listing) throws ItemAlreadyExistsException;

    void reset();

    void addRating(String listingId, Integer rating) throws ParameterParsingException, ItemNotFoundException;
}
