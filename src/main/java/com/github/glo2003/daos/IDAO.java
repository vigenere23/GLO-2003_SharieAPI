package com.github.glo2003.daos;

public interface IDAO<T> {
  T get(int id);
  // List<T> getAll();

  int save(T t);
  // void update(T t, T newT);
  // void delete(T t);
  // void delete(long id);
}