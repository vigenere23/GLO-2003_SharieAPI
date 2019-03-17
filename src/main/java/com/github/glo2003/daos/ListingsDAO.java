package com.github.glo2003.daos;

import com.github.glo2003.helpers.ItemAlreadyExistsException;
import com.github.glo2003.helpers.ItemNotFoundException;
import com.github.glo2003.models.Listing;

import java.util.List;

public interface ListingsDAO {
    Listing get(long id) throws ItemNotFoundException;
    List<Listing> getAll();
    long save(Listing listing) throws ItemAlreadyExistsException;
}
