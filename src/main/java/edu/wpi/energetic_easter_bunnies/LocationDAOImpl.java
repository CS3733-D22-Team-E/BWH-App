package edu.wpi.energetic_easter_bunnies;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

  /**
   * The user is prompted for the name of the CSV file. The program first loads all of the contents
   * of the SQL Location table into Java Location objects. Then the CSV file is created from the
   * Java objects.
   *
   * @param fileName - The file name where the CSV will be saved
   * @throws IOException - Writing to the CSV file
   */
  public void saveFile(String fileName) throws IOException { //TODO Implement adding new information to db
    locations = getAllLocations();
    if (!fileName.toLowerCase().endsWith(".csv")) fileName = "" + fileName + ".csv";
    File tempFile = new File(fileName);
    boolean exists = tempFile.exists();
    if (exists) tempFile.delete();

    BufferedWriter out = null;

    try {
      FileWriter fstream = new FileWriter(fileName, true); // appending each line.
      out = new BufferedWriter(fstream); // ready to write
      // write format
      out.write("nodeID,xcoord,ycoord,floor,building,nodeType,longName,shortName\n");
      // write actual data
      for (Location location : locations) {
        String csvLine =
            ""
                + // might be extraneous, shouldn't affect anything
                location.getNodeID()
                + ','
                + location.getXcoord()
                + ','
                + location.getYcoord()
                + ','
                + location.getFloor()
                + ','
                + location.getBuilding()
                + ','
                + location.getNodeType()
                + ','
                + location.getLongName()
                + ','
                + location.getShortName()
                + "\n";
        out.write(csvLine);
      }
    } catch (IOException e) {
      System.err.println("Error: " + e.getMessage());
    } finally {
      if (out != null) {
        out.close();
      }
    }
  }
}
