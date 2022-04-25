package edu.wpi.cs3733.D22.teamE.database;

import edu.wpi.cs3733.D22.teamE.CallAPI;
import edu.wpi.cs3733.D22.teamE.Main;
import edu.wpi.cs3733.D22.teamE.database.daos.*;
import edu.wpi.cs3733.D22.teamE.entity.*;
import edu.wpi.cs3733.D22.teamE.entity.accounts.Account;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/** uses format from Iteration 1 final ERD Diagram */
public class CSVManager {
  static Connection connection = DBConnect.EMBEDDED_INSTANCE.getConnection();

  // TODO saveLocationFile vs TowerLocations
  // TODO Resources not loading if file already there

  private static final String locationFormat =
      "NODEID, XCOORD, YCOORD, FLOOR, BUILDING, NODETYPE, LONGNAME, SHORTNAME";
  private static final String medEquipFormat =
      "EQUIPMENTID, MED_EQUIP_REQ_ID, \"isInUse\", \"isClean\" , \"cleanLocationID\", \"storageLocationID\", \"currentLocationID\", \"equipmentType\"";
  private static final String medEquipRequestFormat =
      "MED_EQUIPMENTID, REQUESTDATE, DELIVERYDATE, ISURGENT, EQUIP, EQUIPQUANTITY, STAFFASSIGNEE, LOCATIONID, FLOOR, REQUESTSTATUS, OTHERNOTES";
  private static final String labRequestFormat =
      "LAB_REQUESTID, LAB_REQUEST_TYPE, STAFFASSIGNEE, LOCATIONID, TIMEFRAME, REQUESTSTATUS, OTHERNOTES";
  private static final String employeeFormat =
      "EMPLOYEEID, NAME, LOCATIONID, POSITION, AVAILABLE, SALARY";
  private static final String serviceRequestFormat =
      "REQUESTID, STATUS, TYPE, ASSIGNEE, REQUEST_DATE, DELIVERY_DATE, ISURGENT";
  private static final String accountFormat =
      "ACCOUNTID, EMPLOYEEID, AUTHORITYLEVEL, PASSWORDHASH, FIRSTNAME, LASTNAME, POSITION";
  private static final String facilitiesRequestFormat =
      "FACILITIESREQID, FACILITIESREQTYPE, TIMEFRAME, FLOORID, ROOMID, ISURGENT, STAFFASSIGNEE, REQUESTSTATUS, REQUESTDATE, DELIVERYDATE, OTHERNOTES";
  private static final String edgesFormat = "EDGEID, START_NODE, END_NODE";

  /*
      SAVING CSV FILES FROM THE DATABASE
  */

  public static void saveLocationCSV(String fileName) throws IOException, SQLException {
    String format = locationFormat;
    DAO<Location> dao = new LocationDAOImpl();
    // nothing to change here
    File out = fullSaveHelper(fileName, format);
    // change with the proper format in first line of function
    for (Location d : dao.getAll()) {
      String csvLine =
          ""
              + d.getNodeID()
              + ','
              + d.getXcoord()
              + ','
              + d.getYcoord()
              + ','
              + d.getFloor()
              + ','
              + d.getBuilding()
              + ','
              + d.getNodeType()
              + ','
              + d.getLongName()
              + ','
              + d.getShortName()
              + "\n";
      // change nothing
      if (!doesFileContainLine(out, csvLine)) {
        FileUtils.writeStringToFile(out, csvLine, (Charset) null, true);
      }
    }
  }

  public static void saveMedEquipCSV(String fileName) throws IOException, SQLException {
    String format = medEquipFormat;
    DAO<MedicalEquipment> equipDAO = new MedicalEquipmentDAOImpl();
    // nothing to change here
    File out = fullSaveHelper(fileName, format);
    // change with the proper format in first line of function
    for (MedicalEquipment equip : equipDAO.getAll()) {
      String csvLine =
          ""
              + equip.getEquipmentID()
              + ','
              + equip.getMed_equipmentID()
              + ','
              + equip.isInUse()
              + ','
              + equip.isClean()
              + ','
              + equip.getCleanLocation()
              + ','
              + equip.getStorageLocation()
              + ','
              + equip.getCurrentLocation()
              + ','
              + equip.getEquipmentType()
              + "\n";
      // change nothing
      if (!doesFileContainLine(out, csvLine)) {
        FileUtils.writeStringToFile(out, csvLine, (Charset) null, true);
      }
    }
  }

  public static void saveMedEquipRequestCSV(String fileName) throws IOException, SQLException {
    String format = medEquipRequestFormat;
    DAO<medicalEquipmentRequest> MESRDAO = new MedicalEquipmentServiceRequestDAOImpl();
    // nothing to change here
    File out = fullSaveHelper(fileName, format);
    // change with the proper format in first line of function
    for (medicalEquipmentRequest mesr : MESRDAO.getAll()) {
      String csvLine =
          ""
              + mesr.getServiceRequestID()
              + ','
              + mesr.getRequestDate()
              + ','
              + mesr.getDeliveryDate()
              + ','
              + mesr.getIsUrgent()
              + ','
              + mesr.getEquipment()
              + ','
              + mesr.getEquipmentQuantity()
              + ','
              + mesr.getStaffAssignee()
              + ','
              + mesr.getRoomID()
              + ','
              + mesr.getFloorID()
              + ','
              + mesr.getRequestStatus()
              + ','
              + mesr.getOtherNotes()
              + "\n";
      // change nothing
      if (!doesFileContainLine(out, csvLine)) {
        FileUtils.writeStringToFile(out, csvLine, (Charset) null, true);
      }
    }
  }

  public static void saveLabRequestCSV(String fileName) throws IOException, SQLException {
    String format = labRequestFormat;
    DAO<labRequest> labRequestDAO = new LabRequestDAOImpl();
    // nothing to change here
    File out = fullSaveHelper(fileName, format);
    // change with the proper format in first line of function
    for (labRequest labRequest : labRequestDAO.getAll()) {
      String csvLine =
          ""
              + labRequest.getServiceRequestID()
              + ','
              + labRequest.getLabRequestType()
              + ','
              + labRequest.getStaffAssignee()
              + ','
              + labRequest.getRoomID()
              + ','
              + labRequest.getTimeFrame()
              + ','
              + labRequest.getRequestStatus()
              + ','
              + labRequest.getOtherNotes()
              + "\n";
      // change nothing
      if (!doesFileContainLine(out, csvLine)) {
        FileUtils.writeStringToFile(out, csvLine, (Charset) null, true);
      }
    }
  }

  public static void saveEmployeeCSV(String fileName) throws IOException, SQLException {
    String format = employeeFormat;
    DAO<Employee> employeeDAO = new EmployeeDAOImpl();
    // nothing to change here
    File out = fullSaveHelper(fileName, format);
    // change with the proper format in first line of function
    for (Employee employee : employeeDAO.getAll()) {
      String csvLine =
          ""
              + employee.getEmployeeID()
              + ','
              + employee.getName()
              + ','
              + employee.getLocation()
              + ','
              + employee.getPosition()
              + ','
              + employee.isAvailable()
              + ','
              + employee.getSalary()
              + "\n";
      // change nothing
      if (!doesFileContainLine(out, csvLine)) {
        FileUtils.writeStringToFile(out, csvLine, (Charset) null, true);
      }
    }
  }

  public static void saveExternalTransportCSV() {
    CallAPI.getInstance()
        .getExternalTransportAPI()
        .exportExternalTransportsToCSV(new File("CSVsaveFiles/TransportExt.csv"));
  }

  public static void saveServiceRequestCSV(String fileName) throws IOException, SQLException {
    String format = serviceRequestFormat;
    DAO<RequestInterface> dao = new ServiceRequestDAOImpl();
    // nothing to change here
    File out = fullSaveHelper(fileName, format);
    // change with the proper format in first line of function
    for (RequestInterface d : dao.getAll()) {
      String csvLine =
          ""
              + d.getServiceRequestID()
              + ','
              + d.getRequestStatus()
              + ','
              + d.getRequestType()
              + ','
              + d.getDeliveryDate()
              + ','
              + d.getRequestDate()
              + ','
              + d.getIsUrgent()
              + "\n";
      // change nothing
      if (!doesFileContainLine(out, csvLine)) {
        FileUtils.writeStringToFile(out, csvLine, (Charset) null, true);
      }
    }
  }

  public static void saveAccountCSV(String fileName) throws IOException, SQLException {
    String format = accountFormat;
    DAO<Account> dao = new AccountDAOImpl();
    // nothing to change here
    File out = fullSaveHelper(fileName, format);
    // change with the proper format in first line of function
    for (Account d : dao.getAll()) {
      String csvLine =
          ""
              + d.getAccountID()
              + ','
              + d.getEmployeeID()
              + ','
              + d.getAuthorityLevel()
              + ','
              + d.getPasswordHash()
              + ','
              + d.getFirstName()
              + ','
              + d.getLastName()
              + ','
              + d.getPosition()
              + "\n";
      // change nothing
      if (!doesFileContainLine(out, csvLine)) {
        FileUtils.writeStringToFile(out, csvLine, (Charset) null, true);
      }
    }
  }

  /*
      LOADING CSV FILES INTO THE DATABASE
  */

  public static boolean loadLocationCSV(String fileName) throws SQLException, IOException {
    return loadCSVGeneral(fileName, "TOWERLOCATIONS", locationFormat);
  }

  public static boolean loadEquipmentCSV(String fileName) throws SQLException, IOException {
    return loadCSVGeneral(fileName, "EQUIPMENT", medEquipFormat);
  }

  public static boolean loadMedEquipReqCSV(String fileName) throws SQLException, IOException {
    return loadCSVGeneral(fileName, "MED_EQUIP_REQ", medEquipRequestFormat);
  }

  public static boolean loadLabRequestCSV(String fileName) throws SQLException, IOException {
    return loadCSVGeneral(fileName, "LAB_REQUEST", labRequestFormat);
  }

  public static boolean loadEmployeesCSV(String fileName) throws SQLException, IOException {
    return loadCSVGeneral(fileName, "EMPLOYEES", employeeFormat);
  }

  public static boolean loadServiceRequestCSV(String fileName) throws SQLException, IOException {
    return loadCSVGeneral(fileName, "SERVICEREQUEST", serviceRequestFormat);
  }

  public static boolean loadAccountCSV(String fileName) throws SQLException, IOException {
    return loadCSVGeneral(fileName, "ACCOUNTS", accountFormat);
  }

  public static boolean loadEdgesCSV(String fileName) throws SQLException, IOException {
    return loadCSVGeneral(fileName, "EDGES", edgesFormat);
  }

  /*
      HELPER FUNCTIONS
  */

  /**
   * PUT PARAMETERS IN CAPITALS WHERE POSSIBLE
   *
   * @param fileName - with or without .csv, will be included
   * @param tableName - SQL table name
   * @param ColumnsCSV - comma separated with spaces, the columns of the table
   * @throws SQLException
   * @throws IOException
   */
  public static boolean loadCSVGeneral(String fileName, String tableName, String ColumnsCSV)
      throws SQLException, IOException {
    int IDindex0 =
        0; // by default 0 (ID element, first), change where implemented w/ helper caller function
    int count = 0;

    for (int i = 0; i < ColumnsCSV.length(); i++) {
      if (ColumnsCSV.charAt(i) == ',') count++; // count Commas
    }
    count = count + 1; // results = #commas + 1
    String[] csvData = ColumnsCSV.split(","); // for query later

    // check if a save file is in CSVsaveFiles, if so, read from that
    BufferedReader in;
    File file = new File("CSVsaveFiles/" + fileName);
    if (file.exists()) // load from save file
    {
      in = new BufferedReader(new FileReader(file));
    } else // if not in saveFiles, read from default resource folder
    {
      InputStream is = Main.class.getResourceAsStream("CsvFiles/" + fileName);
      if (is == null) {
        System.err.println(
            "Cannot Retrieve CSV resource '"
                + fileName
                + "'. You may have spelled the filename wrong. ");
        return false; // spelled wrong probably
      }
      in = new BufferedReader(new InputStreamReader(is));
    }
    // using BufferedReader readLine() to read from files
    String line;
    in.readLine(); // to skip over format
    String[] lineArrayCSV;
    while ((line = in.readLine()) != null) {
      lineArrayCSV = line.split(",");
      String Identification = lineArrayCSV[IDindex0];
      PreparedStatement statement;
      try {
        // check if nodeID is already in the database
        // ensures the database is up to date and correct without overwriting
        String query =
            "SELECT * FROM "
                + tableName
                + " WHERE "
                + csvData[IDindex0]
                + " = '"
                + Identification
                + "'";
        statement = connection.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) { // true if exists, false if does not exist
          continue; // to next line of the csv
        }
      } catch (SQLException sqlException) {
        System.err.println("Database access error: " + sqlException);
        return false;
      }
      String questionMarks = "";
      for (int i = 0; i < count - 1; i++) questionMarks += "?, ";
      questionMarks += "?";
      String insertQuery =
          "INSERT INTO " + tableName + " (" + ColumnsCSV + ")" + " VALUES (" + questionMarks + ")";
      statement = connection.prepareStatement(insertQuery);

      for (int i = 0; i < count; i++) { // sets the question marks in the query
        statement.setObject(i + 1, lineArrayCSV[i]);
      }
      statement.executeUpdate(); // runs the query
    }
    in.close();
    connection.commit();
    return true;
  }

  /**
   * full helper - makes sure Strings are in the right format, and sets up a file (File object) for
   * writing in csv, which it returns
   *
   * @param fileName
   * @param format
   * @return File ready to write to csv
   * @throws IOException
   */
  private static File fullSaveHelper(String fileName, String format) throws IOException {
    format.replaceAll(" ", "");
    if (!format.endsWith("\n")) {
      format += "\n";
    }
    if (!fileName.toLowerCase().endsWith(".csv")) fileName = "" + fileName + ".csv";

    File file = new File("CSVsaveFiles/" + fileName);
    if (!file.exists()) {
      System.err.println(
          "file should have already been created. "
              + "Resource line may not have been added to the App class init() method");
      file.createNewFile(); // fine
    }
    if (!doesFileContainLine(file, format)) { // only looks for first line or no lines
      FileUtils.writeStringToFile(file, format, (Charset) null, true);
    }
    ; // true means append=true

    return file;
  }

  /**
   * Checks an entire file to see if a line is included (character for chracter). Slow for every
   * call, I know, but very useful for file management
   *
   * @param file
   * @param line
   * @return if the file contains the line
   * @throws IOException
   */
  private static boolean doesFileContainLine(File file, String line) throws IOException {
    //string manipulation
    line = line.toLowerCase().replace(" ","");
    String fileName = file.getName();
    BufferedReader in = new BufferedReader(new FileReader(file));
    in.readLine();
    String curLine;
    while ((curLine = in.readLine().toLowerCase().replace(" ","")) != null) {
      if (curLine.equals(line)) {
        in.close();
        return true;
      }
    }
    in.close();
    return false;
  }

  /**
   * Helper function - Writes an InputStream to a File
   *
   * @param stream
   * @param target
   * @throws IOException
   */
  private static void writeInputStreamToFile(InputStream stream, File target) throws IOException {

    OutputStream outStream = new FileOutputStream(target);

    byte[] buffer = stream.readAllBytes();
    outStream.write(buffer);

    IOUtils.closeQuietly(outStream);
  }

  /**
   * IF a save file does not exist, creates one [filename] and pulls data from app csv resources
   * folder with the same [filename]
   *
   * <p>Assures there WILL be a save file on running program
   *
   * @param filename
   */
  public static void generateNewSaveFileFromResources(String filename) {
    Path tempDirectory = null;
    File file = null;
    try {
      tempDirectory = Paths.get("CSVsaveFiles");
      if (Files.notExists(tempDirectory)) {
        Files.createDirectory(tempDirectory);
      }
      // if no pre-existing save file, load a sample one from resources
      file = new File("CSVsaveFiles/" + filename);
      if (file.createNewFile()) { // if file was created
        URL u = Main.class.getResource("CsvFiles/" + filename);
        if (u != null) {
          InputStream is = Main.class.getResourceAsStream("CsvFiles/" + filename);
          assert is != null;
          writeInputStreamToFile(is, file);
        }
      } else { // if file already there, do nothing besides special case
        if (filename.equals("TransportExt.csv")) {
          if (file.length() != 0) {
            try {
              CallAPI.getInstance().getExternalTransportAPI().importExternalTransportsFromCSV(file);
            } catch (NullPointerException e) {
              e.printStackTrace();
              System.out.println(e.getCause() + " : " + e.getMessage());
            }
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
