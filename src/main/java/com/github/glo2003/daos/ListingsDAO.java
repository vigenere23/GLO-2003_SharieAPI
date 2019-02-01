package com.github.glo2003.daos;

import java.util.HashMap;
import java.util.Map;

import com.github.glo2003.helpers.MathHelper;
import com.github.glo2003.models.Listing;

public class ListingsDAO implements IDAO<Listing> {

  private Map<Integer, Listing> listings = new HashMap<>();

  @Override
  public Listing get(int id) {
    return listings.get(id);
  }

  @Override
  public int save(Listing listing) {
    if (listings.containsValue(listing)) {
      // TODO return 0, the key or throw exception
    }

    int id = MathHelper.randomInt();
    while (listings.containsKey(id)) {
      id = MathHelper.randomInt();
    }
    listings.put(id, listing);
    return id;
  }
}