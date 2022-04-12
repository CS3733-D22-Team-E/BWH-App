package edu.wpi.energetic_easter_bunnies.database;

import edu.wpi.energetic_easter_bunnies.database.daos.*;
import edu.wpi.energetic_easter_bunnies.entity.labRequest;
import edu.wpi.energetic_easter_bunnies.entity.medicalEquipmentRequest;
import edu.wpi.energetic_easter_bunnies.entity.serviceRequest;
import java.io.*;
import java.sql.*;

/** uses format from Iteration 1 final ERD Diagram */
public class CSVManager {
  static Connection connection = DBConnect.EMBEDDED_INSTANCE.getConnection();

  private static final String locationFormat =
      "NODEID, XCOORD, YCOORD, FLOOR, BUILDING, NODETYPE, LONGNAME, SHORTNAME";
  private static final String medEquipFormat = "";
  private static final String medEquipRequestFormat = "";
  private static final String labRequestFormat = "";
  private static final String employeeFormat = "";
  private static final String serviceRequestFormat =
      "REQUESTID, STATUS, TYPE, ASSIGNEE, REQUEST_DATE, DELIVERY_DATE, ISURGENT";

  /*
      SAVING CSV FILES FROM THE DATABASE
  */

  public static void saveLocationCSV(String fileName) throws IOException, SQLException {
    String format = locationFormat;
    DAO<Location> dao = new LocationDAOImpl();
    // nothing to change here
    BufferedWriter out;
    if ((out = fullSaveHelper(fileName, format)) == null) return;
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
      out.write(csvLine);
    }
    out.close();
  }

  public static void saveMedEquipCSV(String fileName) throws IOException, SQLException {
    String format = medEquipFormat; // "ID,isInUse,isClean,cleanLocationID,storageLocationID";
    DAO<MedicalEquipment> equipDAO = new MedicalEquipmentDAOImpl();
    // nothing to change here
    BufferedWriter out;
    if ((out = fullSaveHelper(fileName, format)) == null) return;
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
      out.write(csvLine);
    }
    out.close();
  }

  public static void saveMedEquipRequestCSV(String fileName) throws IOException, SQLException {
    String format =
        medEquipRequestFormat; // "ID,requestDate,deliveryDate,isUrgent,equipment,equipQuantity,staffAssignee,locationID,requestStatus,otherNotes";
    DAO<medicalEquipmentRequest> MESRDAO = new MedicalEquipmentServiceRequestDAOImpl();
    // nothing to change here
    BufferedWriter out;
    if ((out = fullSaveHelper(fileName, format)) == null) return;
    // change with the proper format in first line of function
    for (medicalEquipmentRequest mesr :
        MESRDAO
            .getAll()) { // MED_EQUIPMENTID,REQUESTDATE,DELIVERYDATE,ISURGENT,EQUIP,EQUIPQUANTITY,STAFFASSIGNEE,LOCATIONID,FLOOR,REQUESTSTATUS,OTHERNOTES
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
      out.write(csvLine);
    }
    out.close();
  }

  public static void saveLabRequestCSV(String fileName) throws IOException, SQLException {
    String format =
        labRequestFormat; // "ID,labRequestType,StaffAssignee,locationID,timeFrame,requestStatus,otherNotes";
    DAO<labRequest> labRequestDAO = new LabRequestDAOImpl();
    // nothing to change here
    BufferedWriter out;
    if ((out = fullSaveHelper(fileName, format)) == null) return;
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
      out.write(csvLine);
    }
    out.close();
  }

  public static void saveEmployeeCSV(String fileName) throws IOException, SQLException {
    String format = employeeFormat; // "employeeID,name,location,position,available,salary";
    DAO<Employee> employeeDAO = new EmployeeDAOImpl();
    // nothing to change here
    BufferedWriter out;
    if ((out = fullSaveHelper(fileName, format)) == null) return;
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
      out.write(csvLine);
    }
    out.close();
  }

  public static void saveServiceRequestCSV(String fileName) throws IOException, SQLException {
    String format = serviceRequestFormat;
    DAO<serviceRequest> dao = new ServiceRequestDAOImpl();
    // nothing to change here
    BufferedWriter out;
    if ((out = fullSaveHelper(fileName, format)) == null) return;
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
      out.write(csvLine);
    }
    out.close();
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

  /*public static void loadUsersCSV(String fileName) throws SQLException, IOException {
  loadCSVGeneral(fileName,"USERS", usersFormat);
  }*/

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
      in = new BufferedReader(new FileReader(fileName));
    } catch (IOException e) {
      System.out.println("ERROR: " + e.getMessage());
      return false; // shouldnt do anything if there's nothing to load
    }
    String line;
    in.readLine();
    String[] data;
    while ((line = in.readLine()) != null) {
      data = line.split(",");
      String Identification = data[IDindex0];

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
      PreparedStatement statement = connection.prepareStatement(query);
      ResultSet rs = statement.executeQuery();
      if (rs.next()) { // true if exists, false if does not exist
        continue;
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
   * save CSV function helper,
   *
   * @param fileName
   * @return
   * @throws IOException
   */
  private static BufferedWriter readyIO(String fileName) throws IOException {
    File tempFile = new File(fileName);
    boolean exists = tempFile.exists();
    if (exists) tempFile.delete(); // this makes append=true work fine
    BufferedWriter out = null;
    FileWriter fstream = new FileWriter(fileName, true); // appending each line.
    out = new BufferedWriter(fstream); // ready to write
    return out;
  }

  /**
   * full helper - does a lot of ugly stuff be hind the scenes
   *
   * @param fileName
   * @param format
   * @return
   * @throws IOException
   */
  private static BufferedWriter fullSaveHelper(String fileName, String format) throws IOException {
    format.replaceAll(" ", "");
    if (!format.endsWith("\n")) {
      format += "\n";
    }
    if (!fileName.toLowerCase().endsWith(".csv")) fileName = "" + fileName + ".csv";
    BufferedWriter out;
    try {
      out = readyIO(fileName);
    } catch (IOException e) {
      System.err.println("Error: " + e.getMessage());
      return null;
    } // ends execution
    out.write(format);
    return out;
  }
}
