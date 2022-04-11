package edu.wpi.energetic_easter_bunnies.database;

import edu.wpi.energetic_easter_bunnies.database.daos.*;
import edu.wpi.energetic_easter_bunnies.entity.labRequest;
import edu.wpi.energetic_easter_bunnies.entity.medicalEquipmentRequest;
import edu.wpi.energetic_easter_bunnies.entity.serviceRequest;

import java.io.*;
import java.sql.*;

/**
 * uses format from Iteration 1 final ERD Diagram
 */
public class CSVManager {
  static Connection connection = DBConnect.EMBEDDED_INSTANCE.getConnection();

  /*
       SAVING CSV FILES FROM THE DATABASE
   */

  public static void saveLocationCSV(String fileName) throws IOException, SQLException {
    String format = "nodeID,xcoord,ycoord,floor,building,nodeType,longName,shortName";
    DAO<Location> dao = new LocationDAOImpl();
    //nothing to change here
    BufferedWriter out;
    if ((out = fullSaveHelper(fileName,format))==null) return;
    //change with the proper format in first line of function
    for (Location d : dao.getAll()) {
      String csvLine = "" +
                      d.getNodeID()
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
      //change nothing
      out.write(csvLine);
    }
    out.close();
  }

  public static void saveMedEquipCSV(String fileName) throws IOException, SQLException {
    String format = "ID,isInUse,isClean,cleanLocationID,storageLocationID";
    DAO<MedicalEquipment> equipDAO = new MedicalEquipmentDAOImpl();
    //nothing to change here
    BufferedWriter out;
    if ((out = fullSaveHelper(fileName,format))==null) return;
    //change with the proper format in first line of function
    for (MedicalEquipment equip : equipDAO.getAll()) {
      String csvLine = "" +
              equip.getEquipmentID()
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
      //change nothing
      out.write(csvLine);
    }
    out.close();
  }

  public static void saveMedEquipRequestCSV(String fileName) throws IOException, SQLException {
    String format = "ID,requestDate,deliveryDate,isUrgent,equipment,equipQuantity,staffAssignee,locationID,requestStatus,otherNotes";
     DAO<medicalEquipmentRequest> MESRDAO = new MedicalEquipmentServiceRequestDAOImpl();
    //nothing to change here
    BufferedWriter out;
    if ((out = fullSaveHelper(fileName,format))==null) return;
    //change with the proper format in first line of function
    for (medicalEquipmentRequest mesr : MESRDAO.getAll()) { //MED_EQUIPMENTID,REQUESTDATE,DELIVERYDATE,ISURGENT,EQUIP,EQUIPQUANTITY,STAFFASSIGNEE,LOCATIONID,FLOOR,REQUESTSTATUS,OTHERNOTES
      String csvLine = "" +
              mesr.getServiceRequestID()
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
      //change nothing
      out.write(csvLine);
    }
    out.close();
  }

  public static void saveLabRequestCSV(String fileName) throws IOException, SQLException {
    String format = "ID,labRequestType,StaffAssignee,locationID,timeFrame,requestStatus,otherNotes";
    DAO<labRequest> labRequestDAO = new LabRequestDAOImpl();
    //nothing to change here
    BufferedWriter out;
    if ((out = fullSaveHelper(fileName,format))==null) return;
    //change with the proper format in first line of function
    for (labRequest labRequest : labRequestDAO.getAll()) {
      String csvLine = "" +
              labRequest.getServiceRequestID()
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
      //change nothing
      out.write(csvLine);
    }
    out.close();
  }

  public static void saveEmployeeCSV(String fileName) throws IOException, SQLException {
    String format = "employeeID,name,location,position,available,salary";
    DAO employeeDAO = new EmployeeDAOImpl();
    //nothing to change here
    BufferedWriter out;
    if ((out = fullSaveHelper(fileName,format))==null) return;
    //change with the proper format in first line of function
    for (Employee employee : employeeDAO.getAllEmployees()) {
      String csvLine = "" +
              employee.getEmployeeID()
              + ','
              + employee.getName()
              + ','
              + employee.getLocation()
              + ','
              + employee.getPosition()
              + ','
              + employee.getAvailable()
              + ','
              + employee.getSalary()
              + "\n";
      //change nothing
      out.write(csvLine);
    }
    out.close();
  }

  /*
       LOADING CSV FILES INTO THE DATABASE
   */

  public static void loadLocationCSV(String fileName) throws SQLException, IOException {
    loadCSVGeneral(fileName, "TOWERLOCATIONS", "NODEID, XCOORD, YCOORD, BUILDING, NODETYPE, LONGNAME, SHORTNAME, FLOOR");
  }

  public static void loadEquipmentCSV(String fileName) throws SQLException, IOException {
    loadCSVGeneral(fileName, "EQUIPMENT", "ID, ISINUSE, ISCLEAN, CLEANLOCATIONID, STORAGELOCATIONID");
  }

  public static void loadMedEquipReqCSV(String fileName) throws SQLException, IOException {
    loadCSVGeneral(fileName, "MED_EQUIP_REQ",  "ID, REQUESTDATE, DELIVERYDATE, ISURGENT, EQUIP, EQUIPQUANTITY, STAFFASSIGNEE, LOCATIONID, REQUESTSTATUS, OTHERNOTES");
  }

  public static void loadLabRequestCSV(String fileName) throws SQLException, IOException {
    loadCSVGeneral(fileName,"LAB_REQUEST", "ID, LAB_REQUEST_TYPE, STAFFASSIGNEE, LOCATIONID, TIMEFRAME, REQUESTSTATUS, OTHERNOTES");
  }

  public static void loadEmployeesCSV(String fileName) throws SQLException, IOException {
    loadCSVGeneral(fileName,"EMPLOYEES", "EMPLOYEEID, NAME, LOCATION, POSITION, AVAILABLE, SALARY");
  }

  /*
       HELPER FUNCTIONS
   */

  /**
   *  PUT PARAMETERS IN CAPITALS WHERE POSSIBLE
   *
   * @param fileName - with or without .csv, will be included
   * @param tableName - SQL table name
   * @param ColumnsCSV - comma separated with spaces, the columns of the table
   * @throws SQLException
   * @throws IOException
   */
  public static boolean loadCSVGeneral(String fileName, String tableName, String ColumnsCSV) throws SQLException, IOException {
    int IDindex0 = 0;
    int count = 0;
    for( int i= 0; i < ColumnsCSV.length(); i++) {if(ColumnsCSV.charAt(i) == ',') count++;} //counts # of commas
    count = count + 1; //commas is number of results minus one
    String[] csvData = ColumnsCSV.split(",");//for query later
    BufferedReader in;
    try { in = new BufferedReader(new FileReader(fileName)); }
    catch (IOException e) {
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
    return true;
  }

  /**
   * save CSV function helper,
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
   * @param fileName
   * @param format
   * @return
   * @throws IOException
   */
  private static BufferedWriter fullSaveHelper(String fileName, String format) throws IOException {
    format.replaceAll(" ", "");
    if (!format.endsWith("\n")) { format += "\n"; }
    if (!fileName.toLowerCase().endsWith(".csv")) fileName = "" + fileName + ".csv";
    BufferedWriter out;
    try { out = readyIO(fileName); }
    catch (IOException e) { System.err.println("Error: " + e.getMessage()); return null; }//ends execution
    out.write(format);
    return out;
  }
}
