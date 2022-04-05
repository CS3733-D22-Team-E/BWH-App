package edu.wpi.energetic_easter_bunnies.database;

import java.sql.SQLException;
import java.util.List;

public interface LocationDAO {
  List<Location> getAllLocations();

  void printLocations();

  void addLocation(Location location) throws SQLException;

  void updateLocation(Location location, String newFloor, String newNodeType) throws SQLException;

  void updateCoord(Location location, int newXCoord, int newYCoord) throws SQLException;

  void deleteLocation(Location location) throws SQLException;
}
