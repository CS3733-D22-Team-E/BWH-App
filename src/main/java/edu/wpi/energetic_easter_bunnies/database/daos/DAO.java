package edu.wpi.energetic_easter_bunnies.database.daos;

import java.util.List;

public interface DAO<T> {
  List<T> getAll();

  T get(int id);

  void update(T item);

  void delete(T item);
}
