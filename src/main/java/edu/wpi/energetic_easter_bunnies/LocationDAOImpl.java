package edu.wpi.energetic_easter_bunnies;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocationDAOImpl implements LocationDAO {
  List<Location> locations;

  /**
   * No args constructor that connects to the database to populate the locations list
   *
   * @throws SQLException - Database Connection
   */
  public LocationDAOImpl() throws SQLException {
    locations = new ArrayList<>();
    String url = "jdbc:derby://localhost:1527/BWDB;";
    Connection connection = DriverManager.getConnection(url);
    Statement statement = connection.createStatement();
    String query = "SELECT * FROM TOWERLOCATIONS ORDER BY FLOOR DESC";
    ResultSet rs = statement.executeQuery(query);
    int numID = 0;
    while (rs.next()) {
      String nodeID = rs.getString("nodeID");
      int xcoord = rs.getInt("xcoord");
      int ycoord = rs.getInt("ycoord");
      String floor = rs.getString("floor");
      String building = rs.getString("building");
      String nodeType = rs.getString("nodeType");
      String longName = rs.getString("longName");
      String shortName = rs.getString("shortName");

      Location location =
          new Location(
              nodeID, xcoord, ycoord, floor, building, nodeType, longName, shortName, numID);

      locations.add(location);
      numID++;
    }
    rs.close();
  }
  /**
   * Creates an arraylist of all locations from the db
   *
   * @return Arraylist of locations
   */
  @Override
  public List<Location> getAllLocations() {
    return locations;
  }

  /**
   * @param numID - The numerical id of the location node
   * @return the location requested
   */
  @Override
  public Location getLocation(int numID) {
    return locations.get(numID);
  }

  /**
   * Updates a given location's floor and node type
   *
   * @param location - The location to be added
   */
  @Override
  public void updateLocation(Location location) {
    locations.add(location);
  }

  /**
   * Deletes a location entry
   *
   * @param location - The location to be deleted
   */
  @Override
  public void deleteLocation(Location location) {
    locations.remove(location);
  }
}
