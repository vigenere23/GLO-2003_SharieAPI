package com.github.glo2003.daos;

public interface IDAO<T> {
  T get(long id);
  // List<T> getAll();

  long save(T t);
  // void update(T t, T newT);
  // void delete(T t);
  // void delete(long id);
}