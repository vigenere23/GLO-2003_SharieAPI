package com.github.glo2003.daos;

import com.github.glo2003.helpers.ItemAlreadyExistsException;
import com.github.glo2003.helpers.ItemNotFoundException;

import java.util.List;

public interface IDAO<T> {
  T get(long id) throws ItemNotFoundException;

  List<T> getAll();

  long save(T t) throws ItemAlreadyExistsException;
  // void update(T t, T newT);
  // void delete(T t);
  // void delete(long id);
}