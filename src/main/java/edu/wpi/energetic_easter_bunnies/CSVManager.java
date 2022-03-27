package edu.wpi.energetic_easter_bunnies;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

public class CSVManager {
  /**
   * The user is prompted for the name of the CSV file. The program first loads all of the contents
   * of the SQL Location table into Java Location objects. Then the CSV file is created from the
   * Java objects.
   *
   * @param fileName - The file name where the CSV will be saved
   * @throws IOException - Writing to the CSV file
   */
  public static void saveLocationCSV(String fileName)
      throws IOException, SQLException {
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
