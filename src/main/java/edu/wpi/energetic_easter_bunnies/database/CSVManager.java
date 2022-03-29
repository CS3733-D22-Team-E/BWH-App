package edu.wpi.energetic_easter_bunnies.database;

import java.io.*;
import java.sql.*;

public class CSVManager {


// not a lot of overhead, I can always make another implementation,
// and we can always refactor code later if we see a better pattern.
// can be left alone for now, it should work fine         -jb


  //Blocked before can merge + is functional:
  // ServiceRequest Class
  // MedicalEquipmentDAOImpl
  // MedicalEquipmentServiceRequestDAOImpl
  // getAllEmployees() method


  /**
   * Loads the location database from the location csv
   *
   * @param fileName - The file name where the database will be loaded from
   */
  public static void loadLocationCSV(String fileName) throws SQLException, IOException {
    Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/BWDB;");
    BufferedReader in = new BufferedReader(new FileReader(fileName));
    String line;
    in.readLine();
    String[] data;
    while ((line = in.readLine()) != null) {
      data = line.split(",");
      String nodeID = data[0];

      //check if nodeID is already in the database
      //ensures the database is up to date and correct without overwriting
      String query = "SELECT * FROM TOWERLOCATIONS WHERE NODEID = "+ nodeID;
      PreparedStatement statement = connection.prepareStatement(query);
      ResultSet rs = statement.executeQuery(query);
      if (rs.next()) {     //true if exists, false if does not exist
        continue; // so it does not add a duplicate item into the database - issue from meeting 3/28/2022
      }

      String insertQuery = "INSERT INTO TOWERLOCATIONS (NODEID, XCOORD, YCOORD, FLOOR, BUILDING, "
                      + "NODETYPE, LONGNAME, SHORTNAME) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
      statement = connection.prepareStatement(insertQuery);

      statement.setString(1, data[0]);
      statement.setInt(2, Integer.parseInt(data[1])); //xcoord
      statement.setInt(3, Integer.parseInt(data[2])); //ycoord
      statement.setString(4, data[3]); //floor
      statement.setString(5, data[4]); //building
      statement.setString(6, data[5]); //nodetype
      statement.setString(7, data[6]); //longname
      statement.setString(8, data[7]); //shortname

      statement.executeUpdate();
    }
    in.close();
    connection.commit();
    connection.close();
  }

  /**
   * The program first loads all of the contents of the SQL Location table into Java Location
   * objects. Then the CSV file is created from the Java objects.
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



  /**
   * Loads the location database from the location csv
   *
   * @param fileName - The file name where the database will be loaded from
   */
  public static void loadMedEquipReqCSV(String fileName) throws SQLException, IOException {
    Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/BWDB;");
    BufferedReader in = new BufferedReader(new FileReader(fileName));
    String line;
    in.readLine();
    String[] data;
    while ((line = in.readLine()) != null) {
      data = line.split(",");
      String nodeID = data[0];

      //check if nodeID is already in the database
      //ensures the database is up to date and correct without overwriting
      String query = "SELECT * FROM MedEquipReqTable WHERE NODEID = "+ nodeID;
      PreparedStatement statement = connection.prepareStatement(query);
      ResultSet rs = statement.executeQuery(query);
      if (rs.next()) {     //true if exists, false if does not exist
        continue; // so it does not add a duplicate item into the database - issue from meeting 3/28/2022
      }
      String insertQuery = "INSERT INTO MedEquipReqTable (NODEID, EQUIPID) VALUES (?, ?)";
      statement = connection.prepareStatement(insertQuery);
      statement.setString(1, data[0]);
      statement.setString(2, data[1]); //floor
      statement.executeUpdate();
    }
    in.close();
    connection.commit();
    connection.close();
  }

  /**
   * The program first loads all of the contents of the SQL Medical Equipment Request
   * table into Java Location objects. Then the CSV file is created from the Java objects.
   *
   * @param fileName - The file name where the CSV will be saved
   * @throws IOException - Writing to the CSV file
   */
  public static void saveMedEquipReqCSV(String fileName) throws IOException, SQLException {
    MedicalEquipmentServiceRequestDAO MESR = new MedicalEquipmentServiceRequestDAOImpl();
    if (!fileName.toLowerCase().endsWith(".csv")) fileName = "" + fileName + ".csv";
    File tempFile = new File(fileName);
    boolean exists = tempFile.exists();
    if (exists) tempFile.delete();

    BufferedWriter out = null;

    try {
      FileWriter fstream = new FileWriter(fileName, true); // appending each line.
      out = new BufferedWriter(fstream); // ready to write
      // write format
      out.write("nodeID,equipID\n");
      // write actual data
      for (serviceRequest medEquipServReq : MESR.getAllMedicalEquipmentServiceRequests()) {
        String csvLine = ""
                        + medEquipServReq.getNodeID()
                        + ','
                        + medEquipServReq.getEquipmentID()
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



  /**
   * loads CSV information of medical equipment into the database
   *
   * @param fileName - The file name where the database will be loaded from
   */
  public static void loadMedEquipCSV(String fileName) throws SQLException, IOException {
    Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/BWDB;");
    BufferedReader in = new BufferedReader(new FileReader(fileName));
    String line;
    in.readLine();
    String[] data;
    while ((line = in.readLine()) != null) {
      data = line.split(",");
      String equipID = data[0];

      //check if nodeID is already in the database
      //ensures the database is up to date and correct without overwriting
      String query = "SELECT * FROM MedEquipTable WHERE NODEID = "+ equipID;
      PreparedStatement statement = connection.prepareStatement(query);
      ResultSet rs = statement.executeQuery(query);
      if (rs.next()) {     //true if exists, false if does not exist
        continue; // so it does not add a duplicate item into the database - issue from meeting 3/28/2022
      }
      String insertQuery = "INSERT INTO MedEquipTable (EQUIPID, INUSE, ISCLEAN, CLEANLOCATION, STORAGELOCATION)"
              + " VALUES (?, ?, ?, ?, ?)";
      statement = connection.prepareStatement(insertQuery);
      statement.setString(1, data[0]); //equipID
      statement.setString(2, String.valueOf(data[1])); //inuse
      statement.setString(3, String.valueOf(data[2])); //isclean
      statement.setString(4, data[3]); //cleanlocation
      statement.setString(5, data[4]); //storagelocation
      statement.executeUpdate();
    }
    in.close();
    connection.commit();
    connection.close();
  }

  /**
   * The program first loads all of the contents of the SQL Medical Equipment table into Java Location
   * objects. Then the CSV file is created from the Java objects.
   *
   * @param fileName - The file name where the CSV will be saved
   * @throws IOException - Writing to the CSV file
   */
  public static void saveMedEquipCSV(String fileName) throws IOException, SQLException {
    MedicalEquipmentDAO equipment = new MedicalEquipmentDAOImpl();
    if (!fileName.toLowerCase().endsWith(".csv")) fileName = "" + fileName + ".csv";
    File tempFile = new File(fileName);
    boolean exists = tempFile.exists();
    if (exists) tempFile.delete();

    BufferedWriter out = null;

    try {
      FileWriter fstream = new FileWriter(fileName, true); // appending each line.
      out = new BufferedWriter(fstream); // ready to write
      // write format
      out.write("ID,isInUse,isClean,cleanLocation,storageLocation\n");
      // write actual data
      for (Equipment medEquip : equipment.getAllMedicalEquipment()) {
        String csvLine = ""
                + medEquip.getEquipmentID()
                + ','
                + String.valueOf(medEquip.isInUse())
                + ','
                + String.valueOf(medEquip.isClean())
                + ','
                + medEquip.getCleanLocation()
                + ','
                + medEquip.getStorageLocation()
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




  /**
   * loads CSV information of employees into the database
   *
   * @param fileName - The file name where the database will be loaded from
   */
  public static void loadEmployeeCSV(String fileName) throws SQLException, IOException {
    Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/BWDB;");
    BufferedReader in = new BufferedReader(new FileReader(fileName));
    String line;
    in.readLine();
    String[] data;
    while ((line = in.readLine()) != null) {
      data = line.split(",");
      String employeeID = data[0];

      //check if nodeID is already in the database
      //ensures the database is up to date and correct without overwriting
      String query = "SELECT * FROM EmployeeTable WHERE NODEID = "+ employeeID;
      PreparedStatement statement = connection.prepareStatement(query);
      ResultSet rs = statement.executeQuery(query);
      if (rs.next()) {     //true if exists, false if does not exist
        continue; // so it does not add a duplicate item into the database - issue from meeting 3/28/2022
      }
      String insertQuery = "INSERT INTO EmployeeTable (ID, NAME, POSITION, ISAVAILABLE)"
              + " VALUES (?, ?, ?, ?)";
      statement = connection.prepareStatement(insertQuery);
      statement.setString(1, data[0]); //employeeID
      statement.setString(2, data[1]); //name
      statement.setString(3, data[3]); //position
      statement.setString(4, String.valueOf(data[4])); //isavailable
      statement.executeUpdate();
    }
    in.close();
    connection.commit();
    connection.close();
  }

  /**
   * The program first loads all of the contents of the SQL employee table into Java Location
   * objects. Then the CSV file is created from the Java objects.
   *
   * @param fileName - The file name where the CSV will be saved
   * @throws IOException - Writing to the CSV file
   */
  public static void saveEmployeeCSV(String fileName) throws IOException, SQLException {
    if (!fileName.toLowerCase().endsWith(".csv")) fileName = "" + fileName + ".csv";
    File tempFile = new File(fileName);
    boolean exists = tempFile.exists();
    if (exists) tempFile.delete();

    BufferedWriter out = null;

    try {
      FileWriter fstream = new FileWriter(fileName, true); // appending each line.
      out = new BufferedWriter(fstream); // ready to write
      // write format
      out.write("ID,name,position,isFree\n");
      // write actual data
      for (Employee employee : getAllEmployees()) {
        String csvLine = ""
                + employee.getEmployeeID()
                + ','
                + employee.getName()
                + ','
                + employee.getPosition()
                + ','
                + String.valueOf(employee.isAvailable())
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
