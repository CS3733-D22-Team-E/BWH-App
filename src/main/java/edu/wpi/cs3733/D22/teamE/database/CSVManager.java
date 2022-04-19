package edu.wpi.cs3733.D22.teamE.database;

import edu.wpi.cs3733.D22.teamE.database.daos.*;
import edu.wpi.cs3733.D22.teamE.entity.accounts.Account;
import edu.wpi.cs3733.D22.teamE.entity.labRequest;
import edu.wpi.cs3733.D22.teamE.entity.medicalEquipmentRequest;
import edu.wpi.cs3733.D22.teamE.entity.serviceRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.io.FileUtils;

/** uses format from Iteration 1 final ERD Diagram */
public class CSVManager {
  static Connection connection = DBConnect.EMBEDDED_INSTANCE.getConnection();

  // ends with a slash (used with path+filename)
  public static String CSVFilePath = "src/main/resources/edu/wpi/cs3733/D22/teamE/CsvFiles/";

  private static final String locationFormat =
      "NODEID, XCOORD, YCOORD, FLOOR, BUILDING, NODETYPE, LONGNAME, SHORTNAME";
  private static final String medEquipFormat =
      "EQUIPMENTID, MED_EQUIP_REQ_ID, ISINUSE, ISCLEAN, CLEANLOCATIONID, STORAGELOCATIONID, CURRENTLOCATIONID, EQUIPMENTTYPE";
  private static final String medEquipRequestFormat =
      "MED_EQUIPMENTID, REQUESTDATE, ISURGENT, EQUIP, EQUIPQUANTITY, STAFFASSIGNEE, LOCATIONID, FLOOR, REQUESTSTATUS, OTHERNOTES";
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
      FileUtils.writeStringToFile(out, csvLine, (Charset) null, true);
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
      FileUtils.writeStringToFile(out, csvLine, (Charset) null, true);
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
      FileUtils.writeStringToFile(out, csvLine, (Charset) null, true);
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
      FileUtils.writeStringToFile(out, csvLine, (Charset) null, true);
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
      FileUtils.writeStringToFile(out, csvLine, (Charset) null, true);
    }
  }

  public static void saveServiceRequestCSV(String fileName) throws IOException, SQLException {
    String format = serviceRequestFormat;
    DAO<serviceRequest> dao = new ServiceRequestDAOImpl();
    // nothing to change here
    File out = fullSaveHelper(fileName, format);
    // change with the proper format in first line of function
    for (serviceRequest d : dao.getAll()) {
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
              + d.isUrgent()
              + "\n";
      // change nothing
      FileUtils.writeStringToFile(out, csvLine, (Charset) null, true);
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
      FileUtils.writeStringToFile(out, csvLine, (Charset) null, true);
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
    int IDindex0 = 0;
    int count = 0;
    for (int i = 0; i < ColumnsCSV.length(); i++) {
      if (ColumnsCSV.charAt(i) == ',') count++;
    } // counts # of commas
    count = count + 1; // commas is number of results minus one
    String[] csvData = ColumnsCSV.split(","); // for query later
    BufferedReader in;
    try {
      String filePath = CSVFilePath;
      in = new BufferedReader(new FileReader(CSVFilePath + fileName));
    } catch (IOException e) {
      System.err.println("ERROR: " + e.getMessage());
      return false; // shouldnt do anything if there's nothing to load
    }
    String line;
    in.readLine();
    String[] data;
    while ((line = in.readLine()) != null) {
      data = line.split(",");
      String Identification = data[IDindex0];
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
          continue;
        }
      } catch (SQLException sqlException) {
        System.err.println("Database access error" + sqlException);
        return false;
      }
      String questionMarks = "";
      for (int i = 0; i < count - 1; i++) questionMarks += "?, ";
      questionMarks += "?";
      String insertQuery =
          "INSERT INTO " + tableName + " (" + ColumnsCSV + ")" + " VALUES (" + questionMarks + ")";
      statement = connection.prepareStatement(insertQuery);

      for (int i = 0; i < count; i++) { // sets the question marks in the query
        statement.setObject(i + 1, data[i]);
      }
      statement.executeUpdate(); // runs the query
    }
    in.close();
    connection.commit();
    return true;
  }

  /**
   * full helper - does a lot of ugly stuff be hind the scenes
   *
   * @param fileName
   * @param format
   * @return
   * @throws IOException
   */
  private static File fullSaveHelper(String fileName, String format) throws IOException {
    format.replaceAll(" ", "");
    if (!format.endsWith("\n")) {
      format += "\n";
    }
    if (!fileName.toLowerCase().endsWith(".csv")) fileName = "" + fileName + ".csv";

    File tempFile = new File(fileName);
    boolean exists = tempFile.exists();
    if (exists) tempFile.delete(); // this makes append=true work

    File file = new File("./CSVsaveFiles/" + fileName);
    FileUtils.writeStringToFile(file, format, (Charset) null, true);
    ; // true means append=true

    return file;
  }
}
