package edu.wpi.cs3733.D22.teamE.database.daos;

import java.util.List;

public interface DAO<T> {
  /**
   * Method to get all Items in the list
   *
   * @return List of items
   */
  List<T> getAll();

  /**
   * Method to get a specific item in the list
   *
   * @param id The id of the specific item wanted
   * @return the item requested
   */
  T get(String id);

  /**
   * Method to add a specific item
   *
   * @param item the item to be added
   */
  void add(T item);

  /**
   * Method to update a specific item
   *
   * @param item the item to be added
   */
  void update(T item);

  /**
   * Method to delete a specific item
   *
   * @param item the item to be deleted
   */
  void delete(T item);
}
