package edu.wpi.cs3733.D22.teamE.database.daos;

import edu.wpi.cs3733.D22.teamE.database.DBConnect;
import edu.wpi.cs3733.D22.teamE.entity.Location;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LocationDAOImpl implements DAO<Location> {
  static Connection connection = DBConnect.EMBEDDED_INSTANCE.getConnection();
  List<Location> locations;

  /**
   * No args constructor that connects to the database to populate the locations list
   *
   * @throws SQLException - Database Connection
   */
  public LocationDAOImpl() throws SQLException {
    locations = new ArrayList<>();
    String query = "SELECT * FROM TOWERLOCATIONS ORDER BY FLOOR DESC";
    PreparedStatement statement = connection.prepareStatement(query);
    ResultSet rs = statement.executeQuery();
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
  public List<Location> getAll() {
    return locations;
  }

  /**
   * Gets location with given NodeID
   *
   * @param NodeID the NodeID of the location
   * @return location requested
   */
  public Location get(String NodeID) {
    for (Location location : locations) {
      if (Objects.equals(location.getNodeID(), NodeID)) {
        return location;
      }
    }
    throw new NullPointerException();
  }

  /**
   * Gets location with a given X and Y coordinate
   *
   * @param xCoord the X coordinate to be searched
   * @param yCoord the Y coordinate to be searched
   * @return location requested
   */
  public Location get(int xCoord, int yCoord) {
    for (Location location : locations) {
      if ((location.getXCoord() == xCoord) && (location.getYCoord() == yCoord)) {
        return location;
      }
    }
    throw new NullPointerException();
  }

  /**
   * Gets a location with given numID in the arrayList
   *
   * @param numID - The numerical id of the location node
   * @return the location requested
   */
  public Location getLocationWithNumID(int numID) {
    if (numID >= locations.size()) return null;
    return locations.get(numID);
  }

  /**
   * Creates a new location entry in the database
   *
   * @param location - new location to be added
   */
  @Override
  public void update(Location location) {
    /* System.out.println("Yo");
    if (getLocationWithNumID(location.getNumID()) != null) {
      locations.remove(location);
      delete(location);
    }*/
    if (getLocationWithNumID(location.getNumID()) == null) {
      locations.add(location);
      try {
        String query =
            "INSERT INTO TOWERLOCATIONS (nodeID, xCoord, yCoord, floor, building, nodetype, longname, shortname) VALUES ('"
                + location.getNodeID()
                + "',"
                + location.getXCoord()
                + ","
                + location.getYCoord()
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
        PreparedStatement statement = connection.prepareStatement(query);
        System.out.println(statement.executeUpdate());
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } else {

    }
  }

  /**
   * Updates a given location's floor and node type
   *
   * @param location - the location node to be updated
   * @param newFloor - the new floor value
   * @param newNodeType - the new node type
   * @throws SQLException - Accesses Database
   */
  public void updateLocation(Location location, String newFloor, String newNodeType)
      throws SQLException {

    // Updating location floor and node type
    location.setFloor(newFloor);
    location.setNodeType(newNodeType);

    // Update location floor and node type in the db
    String query =
        "UPDATE TOWERLOCATIONS SET FLOOR = '"
            + newFloor
            + "', NODETYPE = '"
            + newNodeType
            + "' WHERE NODEID = '"
            + location.getNodeID()
            + "'";
    PreparedStatement statement = connection.prepareStatement(query);
    statement.executeUpdate();
  }

  /**
   * Updates a given location's x and y coordinates
   *
   * @param location - the location node to be updated
   * @param newXCoord - the new X coordinate
   * @param newYCoord - the new Y coordinate
   * @throws SQLException - Accesses Database
   */
  public void updateCoord(Location location, int newXCoord, int newYCoord) throws SQLException {

    // Updating location XCoord and YCoord
    location.setXCoord(newXCoord);
    location.setYCoord(newYCoord);

    // Update location XCoord and YCoord in the db
    String query =
        "UPDATE TOWERLOCATIONS SET XCOORD = "
            + newXCoord
            + ", YCOORD = "
            + newYCoord
            + " WHERE NODEID = '"
            + location.getNodeID()
            + "'";
    PreparedStatement statement = connection.prepareStatement(query);
    statement.executeUpdate();
  }

  /**
   * Deletes a location entry in the database
   *
   * @param location - location to be deleted
   * @throws SQLException - Accesses Database
   */
  @Override
  public void delete(Location location) {

    // Deleting the location from the array list
    locations.remove(location);

    // Remove location in the db
    try {
      String query = "DELETE FROM TOWERLOCATIONS WHERE nodeID = ('" + location.getNodeID() + "')";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private double getDist(int x1, int y1, int x2, int y2) {

    double xdist = Math.pow((x2 - x1), 2);
    double ydist = Math.pow((y2 - y1), 2);

    return Math.sqrt(xdist + ydist);
  }

  public Location getClosest(int x, int y, String floor) {
    Location closest = getLocationWithNumID(0);

    double minDist = getDist(x, y, closest.getXCoord(), closest.getYCoord());

    for (Location l : locations) {
      if (l.getFloor().equals(floor)) {
        double distFrom = getDist(x, y, l.getXCoord(), l.getYCoord());

        if (distFrom < minDist) closest = l;
      }
    }

    return closest;
  }
}
