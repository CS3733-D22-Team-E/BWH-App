package edu.wpi.energetic_easter_bunnies.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocationDAOImpl implements LocationDAO {
  static Connection connection = DBConnection.getConnection();
  List<Location> locations;

  /**
   * No args constructor that connects to the database to populate the locations list
   *
   * @throws SQLException - Database Connection
   */
  public LocationDAOImpl() throws SQLException {
    locations = new ArrayList<>();
    String url = "jdbc:derby:myDB;";
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
   * Gets a location with given numID in the arrayList
   *
   * @param numID - The numerical id of the location node
   * @return the location requested
   */
  @Override
  public Location getLocation(int numID) {
    return locations.get(numID);
  }

  /** Prints all the location information */
  @Override
  public void printLocations() {
    for (Location location : locations) {
      System.out.println(location);
    }
  }

  /**
   * Creates a new location entry in the database
   *
   * @param location - new location to be added
   * @throws SQLException - Accesses Database
   */
  @Override
  public void addLocation(Location location) throws SQLException {

    String url = "jdbc:derby:myDB";
    Statement statement = connection.createStatement();
    locations.add(location);

    String query =
        "INSERT INTO TOWERLOCATIONS (nodeID, xCoord, yCoord, floor, building, nodetype, longname, shortname) VALUES ('"
            + location.getNodeID()
            + "',"
            + location.getXcoord()
            + ","
            + location.getYcoord()
            + ",'"
            + location.getFloor()
            + "','"
            + location.getBuilding()
            + "','"
            + location.getNodeType()
            + "','"
            + location.getLongName()
            + "','"
            + location.getShortName()
            + "')"; // Insert into database; does not check if the nodeID already exists
    statement.executeUpdate(query);
  }

  /**
   * Updates a given location's floor and node type
   *
   * @param location - the location node to be updated
   * @param newFloor - the new floor value
   * @param newNodeType - the new node type
   * @throws SQLException - Accesses Database
   */
  @Override
  public void updateLocation(Location location, String newFloor, String newNodeType)
      throws SQLException {

    // Updating location floor and node type
    location.setFloor(newFloor);
    location.setNodeType(newNodeType);

    // Update location floor and node type in the db
    String url = "jdbc:derby:myDB;";
    Statement statement = connection.createStatement();
    String query =
        "UPDATE TOWERLOCATIONS SET FLOOR = '"
            + newFloor
            + "', NODETYPE = '"
            + newNodeType
            + "' WHERE NODEID = '"
            + location.getNodeID()
            + "'";
    statement.executeUpdate(query);
  }

  /**
   * Deletes a location entry in the database
   *
   * @param location - location to be deleted
   * @throws SQLException - Accesses Database
   */
  @Override
  public void deleteLocation(Location location) throws SQLException {

    // Deleting the location from the array list
    locations.remove(location);

    // Remove location in the db
    String url = "jdbc:derby:myDB";
    Statement statement = connection.createStatement();
    String query = "DELETE FROM TOWERLOCATIONS WHERE nodeID = ('" + location.getNodeID() + "')";
    statement.executeUpdate(query);
  }
}
