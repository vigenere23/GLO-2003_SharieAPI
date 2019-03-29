package com.github.glo2003.daos;

import com.github.glo2003.exceptions.ItemAlreadyExistsException;
import com.github.glo2003.exceptions.ItemNotFoundException;
import com.github.glo2003.models.Listing;

import java.time.LocalDate;
import java.util.List;

public class MorphiaListingsDAO implements ListingsDAO {

    public MorphiaListingsDAO(String url) {
        //TODO connect to database
    }

    @Override
    public Listing get(long id) throws ItemNotFoundException {
        return null;
    }

    @Override
    public List<Listing> getAll() {
        return null;
    }

    @Override
    public List<Listing> getAllSpecificDate(LocalDate date) {
        return null;
    }

    @Override
    public long save(Listing listing) throws ItemAlreadyExistsException {
        return 0;
    }
}
