package com.github.glo2003.daos;

import com.github.glo2003.exceptions.ItemAlreadyExistsException;
import com.github.glo2003.exceptions.ItemNotFoundException;
import com.github.glo2003.models.Listing;

import java.util.List;

public interface ListingsDAO {
    Listing get(long id) throws ItemNotFoundException;
    List<Listing> getAll();
    long save(Listing listing) throws ItemAlreadyExistsException;

}
