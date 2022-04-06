package edu.wpi.energetic_easter_bunnies.database.daos;

import edu.wpi.energetic_easter_bunnies.database.DBConnection;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class DAOImpl<T> implements DAO<T> {
  //TODO For iteration 2
  static Connection connection = DBConnection.getConnection();
  List<T> items;

  public DAOImpl(String query) {
    items = new ArrayList<>();
    populateItems(query);
  }

  public void populateItems(String query) {}

  @Override
  public List<T> getAll() {
    return items;
  }

  @Override
  public T get(int id) {
    return items.get(id);
  }

  @Override
  public void update(T item) {
    items.add(item);
  }

  @Override
  public void delete(T item) {
    items.remove(item);
  }
}
