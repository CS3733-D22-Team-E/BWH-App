package edu.wpi.energetic_easter_bunnies.database;

import java.io.*;
import java.sql.*;

public class CSVManager {

  /**
   * Loads the location database from the location csv
   * @param fileName - The file name where the database will be loaded from
   */
  public static void loadLocationCSV(String fileName) throws SQLException, IOException {
    Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/BWDB;");

    String query = "INSERT INTO TOWERLOCATIONS (NODEID, XCOORD, YCOORD, FLOOR, BUILDING, " +
            "NODETYPE, LONGNAME, SHORTNAME) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
    PreparedStatement statement = connection.prepareStatement(query);

    BufferedReader in = new BufferedReader(new FileReader(fileName));
    String line;
    in.readLine();

    while((line = in.readLine()) != null) {
      String[] data = line.split(",");
      String nodeID = data[0];
      int xcoord = Integer.parseInt(data[1]);
      int ycoord = Integer.parseInt(data[2]);
      String floor = data[3];
      String building = data[4];
      String nodeType = data[5];
      String longName = data[6];
      String shortName = data[7];

      statement.setString(1, nodeID);
      statement.setInt(2, xcoord);
      statement.setInt(3, ycoord);
      statement.setString(4, floor);
      statement.setString(5, building);
      statement.setString(6, nodeType);
      statement.setString(7, longName);
      statement.setString(8, shortName);

      statement.addBatch();
    }
    in.close();
    statement.executeBatch();
    connection.commit();
    connection.close();
  }
  /**
   * The program first loads all of the contents of the SQL Location table
   * into Java Location objects. Then the CSV file is created from the
   * Java objects.
   *
   * @param fileName - The file name where the CSV will be saved
   * @throws IOException - Writing to the CSV file
   */
  public static void saveLocationCSV(String fileName) throws IOException, SQLException {
    LocationDAO locationDAO = new LocationDAOImpl();
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
      for (Location location : locationDAO.getAllLocations()) {
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
