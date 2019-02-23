package com.github.glo2003.daos;

import com.github.glo2003.helpers.MathHelper;
import com.github.glo2003.models.Listing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListingsDAO implements IDAO<Listing> {

  private Map<Long, Listing> listings = new HashMap<>();

  @Override
  public Listing get(long id) {
    return listings.get(id);
  }

  @Override
  public long save(Listing listing) {
    if (listings.containsValue(listing)) {
      // TODO return 0, the key or throw exception
    }

    long id = MathHelper.randomLong();
    while (listings.containsKey(id)) {
      id = MathHelper.randomLong();
    }
    listings.put(id, listing);
    return id;
  }

  @Override
  public List<Listing> getAll() {
    return new ArrayList<>(listings.values());
  }
}