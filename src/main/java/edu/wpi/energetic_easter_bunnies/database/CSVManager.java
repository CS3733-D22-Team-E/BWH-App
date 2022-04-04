package edu.wpi.energetic_easter_bunnies.database;

import edu.wpi.energetic_easter_bunnies.entity.serviceRequest;
import java.io.*;
import java.sql.*;

public class CSVManager {
  static Connection connection = DBConnection.getConnection();
  /**
   * Loads the location database from the location csv
   *
   * @param fileName - The file name where the database will be loaded from
   */
  public static void loadLocationCSV(String fileName) throws SQLException, IOException {
    BufferedReader in = new BufferedReader(new FileReader(fileName));
    String line;
    in.readLine();
    String[] data;
    while ((line = in.readLine()) != null) {
      data = line.split(",");
      String nodeID = data[0];

      // check if nodeID is already in the database
      // ensures the database is up to date and correct without overwriting
      String query = "SELECT * FROM TOWERLOCATIONS WHERE NODEID = '" + nodeID + "'";
      PreparedStatement statement = connection.prepareStatement(query);
      ResultSet rs = statement.executeQuery();
      if (rs.next()) continue;

      String insertQuery =
          "INSERT INTO TOWERLOCATIONS (NODEID, XCOORD, YCOORD, FLOOR, BUILDING, "
              + "NODETYPE, LONGNAME, SHORTNAME) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
      statement = connection.prepareStatement(insertQuery);

      statement.setString(1, data[0]);
      statement.setInt(2, Integer.parseInt(data[1])); // xcoord
      statement.setInt(3, Integer.parseInt(data[2])); // ycoord
      statement.setString(4, data[3]); // floor
      statement.setString(5, data[4]); // building
      statement.setString(6, data[5]); // nodetype
      statement.setString(7, data[6]); // longname
      statement.setString(8, data[7]); // shortname

      statement.executeUpdate();
    }
    in.close();
    connection.commit();
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
    BufferedReader in = new BufferedReader(new FileReader(fileName));
    String line;
    in.readLine();
    String[] data;
    while ((line = in.readLine()) != null) {
      data = line.split(",");
      String nodeID = data[0];

      // check if nodeID is already in the database
      // ensures the database is up to date and correct without overwriting
      String query = "SELECT * FROM MedEquipReqTable WHERE NODEID = '" + nodeID + "'";
      PreparedStatement statement = connection.prepareStatement(query);
      ResultSet rs = statement.executeQuery(query);
      if (rs.next()) { // true if exists, false if does not exist
        continue; // so it does not add a duplicate item into the database - issue from meeting
        // 3/28/2022
      }
      String insertQuery = "INSERT INTO MedEquipReqTable (floorID, roomID) VALUES (?, ?)";
      statement = connection.prepareStatement(insertQuery);
      statement.setString(1, data[0]);
      statement.setString(2, data[1]); // floor
      statement.executeUpdate();
    }
    in.close();
    connection.commit();
  }

  /**
   * The program first loads all of the contents of the SQL Medical Equipment Request table into
   * Java Location objects. Then the CSV file is created from the Java objects.
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
      out.write("floorID,roomID\n");
      // write actual data
      for (serviceRequest medEquipServReq : MESR.getAllMedicalEquipmentServiceRequests()) {
        String csvLine =
            "" + medEquipServReq.getFloorID() + ',' + medEquipServReq.getRoomID() + "\n";
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
    BufferedReader in = new BufferedReader(new FileReader(fileName));
    String line;
    in.readLine();
    String[] data;
    while ((line = in.readLine()) != null) {
      data = line.split(",");
      String equipID = data[0];

      // check if nodeID is already in the database
      // ensures the database is up to date and correct without overwriting
      String query = "SELECT * FROM EQUIPMENT WHERE ID = '" + equipID + "'";
      PreparedStatement statement = connection.prepareStatement(query);
      ResultSet rs = statement.executeQuery(query);
      if (rs.next()) { // true if exists, false if does not exist
        continue; // so it does not add a duplicate item into the database - issue from meeting
        // 3/28/2022
      }
      String insertQuery =
          "INSERT INTO MED_EQUIP_REQ (EQUIPID, INUSE, ISCLEAN, CLEANLOCATION, STORAGELOCATION)"
              + " VALUES (?, ?, ?, ?, ?)";
      statement = connection.prepareStatement(insertQuery);
      statement.setString(1, data[0]); // equipID
      statement.setString(2, String.valueOf(data[1])); // inuse
      statement.setString(3, String.valueOf(data[2])); // isclean
      statement.setString(4, data[3]); // cleanlocation
      statement.setString(5, data[4]); // storagelocation
      statement.executeUpdate();
    }
    in.close();
    connection.commit();
  }

  /**
   * The program first loads all of the contents of the SQL Medical Equipment table into Java
   * Location objects. Then the CSV file is created from the Java objects.
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
        String csvLine =
            ""
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
   * loads CSV file of Lab Requests into the database
   *
   * @param fileName - The file name where the database will be loaded from
   */
  public static void loadLabRequestCSV(String fileName) throws SQLException, IOException {
    loadCSVGeneral(fileName,"LAB_REQUEST",0,"LAB_REQUESTID, LAB_REQUEST_TYPE, STAFFASSIGNEE, LOCATIONID, TIMEFRAME, REQUESTSTATUS, OTHERNOTES");
  }


  /**
   *  PUT PARAMETERS IN CAPITALS WHERE POSSIBLE
   *
   * @param fileName - with or without .csv, will be included
   * @param tableName
   * @param IDindex0 - which column uniquely identifies each row (0,count
   * @param ColumnsCSV
   * @throws SQLException
   * @throws IOException
   */
  public static void loadCSVGeneral(String fileName, String tableName, int IDindex0, String ColumnsCSV) throws SQLException, IOException {

    int count = 0;
    for( int i= 0; i < ColumnsCSV.length(); i++) {if(ColumnsCSV.charAt(i) == ',') count++;} //counts # of commas
    count = count + 1; //commas is number of results minus one
    String[] csvData = ColumnsCSV.split(",");//for query later

    BufferedReader in = new BufferedReader(new FileReader(fileName));
    String line;
    in.readLine();
    String[] data;
    while ((line = in.readLine()) != null) {
      data = line.split(",");
      String Identification = data[IDindex0];

      // check if nodeID is already in the database
      // ensures the database is up to date and correct without overwriting
      String query = "SELECT * FROM "+tableName+" WHERE "+csvData[IDindex0]+" = '" + Identification + "'";
      PreparedStatement statement = connection.prepareStatement(query);
      ResultSet rs = statement.executeQuery(query);
      if (rs.next()) { // true if exists, false if does not exist
        continue;
      }
      String questionMarks="";
      for (int i = 0; i<count-1;i++) questionMarks += "?, ";
      questionMarks += "?";
      String insertQuery =
              "INSERT INTO "+tableName+" ("+ColumnsCSV+")"
                      + " VALUES ("+questionMarks+")";
      statement = connection.prepareStatement(insertQuery);

      for (int i=0; i<count-1; i++) { // sets the question marks in the query
        statement.setString(i+1, String.valueOf(data[i]));
      }
      statement.executeUpdate(); //runs the query
    }
    in.close();
    connection.commit();
  }

  public static void saveLabRequestCSV(String fileName) throws IOException, SQLException {
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
        String csvLine =
            ""
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

  // Iteration 2 / not iteration 1

  /**
   * loads CSV information of employees into the database
   *
   * @param fileName - The file name where the database will be loaded from
   */
  public static void loadEmployeeCSV(String fileName) throws SQLException, IOException {
    BufferedReader in = new BufferedReader(new FileReader(fileName));
    String line;
    in.readLine();
    String[] data;
    while ((line = in.readLine()) != null) {
      data = line.split(",");
      String employeeID = data[0];

      // check if nodeID is already in the database
      // ensures the database is up to date and correct without overwriting
      String query = "SELECT * FROM EmployeeTable WHERE NODEID = '" + employeeID + "'";
      PreparedStatement statement = connection.prepareStatement(query);
      ResultSet rs = statement.executeQuery(query);
      if (rs.next()) { // true if exists, false if does not exist
        continue; // so it does not add a duplicate item into the database - issue from meeting
        // 3/28/2022
      }
      String insertQuery =
          "INSERT INTO EmployeeTable (ID, NAME, LOCATION, POSITION, AVAILABLE, SALARY)"
              + " VALUES (?, ?, ?, ?, ?, ?)";
      statement = connection.prepareStatement(insertQuery);
      statement.setString(1, data[0]); // employeeID
      statement.setString(2, data[1]); // name
      statement.setString(3, data[3]); // position
      statement.setString(4, String.valueOf(data[4])); // isavailable
      statement.executeUpdate();
    }
    in.close();
    connection.commit();
  }

  /**
   * The program first loads all of the contents of the SQL employee table into Java Location
   * objects. Then the CSV file is created from the Java objects.
   *
   * @param fileName - The file name where the CSV will be saved
   * @throws IOException - Writing to the CSV file
   */
  public static void saveEmployeeCSV(String fileName) throws IOException, SQLException {
    EmployeeDAO employeeDAO = new EmployeeDAOImpl();
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
      for (Employee employee : employeeDAO.getAllEmployees()) {
        String csvLine =
            ""
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
