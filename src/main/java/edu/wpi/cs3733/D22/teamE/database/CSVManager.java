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
import java.util.ArrayList;
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
      "ACCOUNTID, EMPLOYEEID, AUTHORITYLEVEL, PASSWORDHASH, FIRSTNAME, LASTNAME, POSITION, PHONENUMBER";
  private static final String edgesFormat = "EDGEID, START_NODE, END_NODE";
  private static final String medicineRequestFormat =
      "MEDICINE_REQ_ID, REQUEST_ID, DELIVERY_DATE, STATUS, ASSIGNEE, ISURGENT, DELIVERYLOCATIONID, FLOOR, MEDICINETYPE, MEDICINEQUANTITY, MEDICINEUNIT, REOCCURINGDAYS, OTHERNOTES, DELIVERYTIME";
  private static final String sanitationRequestFormat =
      "SANITATION_REQ_ID, REQUEST_DATE, DELIVERY_DATE, STATUS, ASSIGNEE, ISURGENT, ROOMID, FLOOR, CLEANINGSIZE, ISBIOHAZARD, OTHERNOTES";
  private static final String mealDeliveryRequestFormat =
      "MEAL_REQ_ID, REQUEST_DATE, DELIVERY_DATE, STATUS, ASSIGNEE, ISURGENT, ROOMID, FLOOR, ENTREE, BEVERAGE, DESSERT, DELIVERYTIME, OTHERNOTES";
  private static final String languageInterpreterRequestFormat =
      "LAN_INTERP_REQ, REQUEST_DATE, DELIVERY_DATE, STATUS, ASSIGNEE, ISURGENT, ROOMID, FLOOR, LANGUAGE, OTHERNOTES";
  private static final String facilitiesRequestFormat =
      "FACILITIESREQID, FACILITIESREQTYPE, TIMEFRAME, FLOORID, ROOMID, ISURGENT, STAFFASSIGNEE, REQUESTSTATUS, REQUESTDATE, DELIVERYDATE, OTHERNOTES";
  private static final String securityRequestFormat =
      "SECURITY_REQUESTID, SECURITY_REQUEST_TYPE, TIMEFRAME, LOCATIONID, FLOORID, ISURGENT, STAFFASSISNEE, REQUESTSTATUS, REQUESTDATE, DELIVERYDATE, OTHERNOTES";
  private static final String giftDeliveryRequestFormat =
      "GIFT_REQ_ID, REQUEST_DATE, DELIVERY_DATE, STATUS, ASSIGNEE, ISURGENT, ROOMID, FLOOR, PATIENTNAME, GIFTTYPE, OTHERNOTES";

  public static String getDefaultLocationFilename() {
    return "TowerLocations.csv";
  }

  public static String getDefaultEquipmentFilename() {
    return "MedEquip.csv";
  }

  public static String getDefaultEmployeesFilename() {
    return "Employees.csv";
  }

  public static String getDefaultLabRequestFilename() {
    return "LabRequests.csv";
  }

  public static String getDefaultMedEquipRequestFilename() {
    return "MedEquipRequest.csv";
  }

  public static String getDefaultServiceRequestFilename() {
    return "ServiceRequests.csv";
  }

  public static String getDefaultMedicineRequestFilename() {
    return "MedicineRequests.csv";
  }

  public static String getDefaultSanitationRequestFilename() {
    return "SanitationRequests.csv";
  }

  public static String getDefaultMealRequestFilename() {
    return "MealRequests.csv";
  }

  public static String getDefaultLangInterpRequestFilename() {
    return "LanguageRequests.csv";
  }

  public static String getDefaultFacilitiesRequestFilename() {
    return "FacilitiesRequests.csv";
  }

  public static String getDefaultSecurityRequestFilename() {
    return "SecurityRequests.csv";
  }

  public static String getDefaultGiftRequestFilename() {
    return "GiftRequests.csv";
  }

  public static String getDefaultAccountsFilename() {
    return "Accounts.csv";
  }

  public static String getDefaultEdgesFilename() {
    return "AllEdges.csv";
  }

  /*
      SAVING CSV FILES FROM THE DATABASE
  */

  public static void saveLocationCSV(String fileName) throws IOException, SQLException {
    String format = locationFormat;
    // nothing to change here
    File out = fullSaveHelper(fileName, format);
    // change with the proper format in first line of function
    for (Location d : DAOSystemSingleton.INSTANCE.getSystem().getAllLocations()) {
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
    // nothing to change here
    File out = fullSaveHelper(fileName, format);
    // change with the proper format in first line of function
    for (MedicalEquipment equip :
        DAOSystemSingleton.INSTANCE.getSystem().getAllMedicalEquipments()) {
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
    // nothing to change here
    File out = fullSaveHelper(fileName, format);
    // change with the proper format in first line of function
    for (medicalEquipmentRequest mesr :
        DAOSystemSingleton.INSTANCE.getSystem().getAllMedicalEquipmentRequests()) {
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
    // nothing to change here
    File out = fullSaveHelper(fileName, format);
    // change with the proper format in first line of function
    for (labRequest labRequest : DAOSystemSingleton.INSTANCE.getSystem().getAllLabRequests()) {
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
    // nothing to change here
    File out = fullSaveHelper(fileName, format);
    // change with the proper format in first line of function
    for (Employee employee : DAOSystemSingleton.INSTANCE.getSystem().getAllEmployee()) {
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
    // nothing to change here
    File out = fullSaveHelper(fileName, format);
    // change with the proper format in first line of function
    for (RequestInterface d : DAOSystemSingleton.INSTANCE.getSystem().getAllServiceRequests()) {
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
    // nothing to change here
    File out = fullSaveHelper(fileName, format);
    // change with the proper format in first line of function
    for (Account d : DAOSystemSingleton.INSTANCE.getSystem().getAllAccounts()) {
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
              + ','
              + d.getPhoneNumber()
              + "\n";
      // change nothing
      if (!doesFileContainLine(out, csvLine)) {
        FileUtils.writeStringToFile(out, csvLine, (Charset) null, true);
      }
    }
  }

  public static void saveMedicineRequestCSV(String fileName) throws IOException, SQLException {
    String format = medicineRequestFormat;
    // nothing to change here
    File out = fullSaveHelper(fileName, format);
    // change with the proper format in first line of function
    for (medicineDelivery d : DAOSystemSingleton.INSTANCE.getSystem().getAllMedicineRequests()) {
      String csvLine =
          ""
              + d.getServiceRequestID()
              + ','
              + d.getRequestDate()
              + ','
              + d.getDeliveryDate()
              + ','
              + d.getRequestStatus()
              + ','
              + d.getStaffAssignee()
              + ','
              + d.getIsUrgent()
              + ','
              + d.getRoomID()
              + ','
              + d.getFloorID()
              + ','
              + d.getMedicine()
              + ','
              + d.getAmount()
              + ','
              + d.getUnit()
              + ','
              + d.getReocurringDays()
              + ','
              + d.getOtherNotes()
              + ','
              + d.getDeliveryTime()
              + "\n";
      // change nothing
      if (!doesFileContainLine(out, csvLine)) {
        FileUtils.writeStringToFile(out, csvLine, (Charset) null, true);
      }
    }
  }

  public static void saveSanitationRequestCSV(String fileName) throws IOException, SQLException {
    String format = sanitationRequestFormat;
    // nothing to change here
    File out = fullSaveHelper(fileName, format);
    // change with the proper format in first line of function
    for (sanitationRequest d : DAOSystemSingleton.INSTANCE.getSystem().getAllSanitationRequests()) {
      String csvLine =
          ""
              + d.getServiceRequestID()
              + ','
              + d.getRequestDate()
              + ','
              + d.getDeliveryDate()
              + ','
              + d.getRequestStatus()
              + ','
              + d.getStaffAssignee()
              + ','
              + d.getIsUrgent()
              + ','
              + d.getRoomID()
              + ','
              + d.getFloorID()
              + ','
              + d.getSizeOfCleaning()
              + ','
              + d.getBiohazardOnSite()
              + ','
              + d.getOtherNotes()
              + "\n";
      // change nothing
      if (!doesFileContainLine(out, csvLine)) {
        FileUtils.writeStringToFile(out, csvLine, (Charset) null, true);
      }
    }
  }

  public static void saveMealDeliveryCSV(String fileName) throws IOException, SQLException {
    String format = mealDeliveryRequestFormat;
    // nothing to change here
    File out = fullSaveHelper(fileName, format);
    // change with the proper format in first line of function
    for (mealDeliveryRequest d : DAOSystemSingleton.INSTANCE.getSystem().getAllMealRequests()) {
      String csvLine =
          ""
              + d.getServiceRequestID()
              + ','
              + d.getRequestDate()
              + ','
              + d.getDeliveryDate()
              + ','
              + d.getRequestStatus()
              + ','
              + d.getStaffAssignee()
              + ','
              + d.getIsUrgent()
              + ','
              + d.getRoomID()
              + ','
              + d.getFloorID()
              + ','
              + d.getEntreeType()
              + ','
              + d.getBeverageType()
              + ','
              + d.getDessertType()
              + ','
              + d.getDeliveryTime()
              + ','
              + d.getOtherNotes()
              + "\n";
      // change nothing
      if (!doesFileContainLine(out, csvLine)) {
        FileUtils.writeStringToFile(out, csvLine, (Charset) null, true);
      }
    }
  }

  public static void saveLanguageInterpreterRequestCSV(String fileName)
      throws IOException, SQLException {
    String format = languageInterpreterRequestFormat;
    // nothing to change here
    File out = fullSaveHelper(fileName, format);
    // change with the proper format in first line of function
    for (languageInterpreterRequest d :
        DAOSystemSingleton.INSTANCE.getSystem().getAllLanguageRequests()) {
      String csvLine =
          ""
              + d.getServiceRequestID()
              + ','
              + d.getRequestDate()
              + ','
              + d.getDeliveryDate()
              + ','
              + d.getRequestStatus()
              + ','
              + d.getStaffAssignee()
              + ','
              + d.getIsUrgent()
              + ','
              + d.getRoomID()
              + ','
              + d.getFloorID()
              + ','
              + d.getLanguage()
              + ','
              + d.getOtherNotes()
              + "\n";
      // change nothing
      if (!doesFileContainLine(out, csvLine)) {
        FileUtils.writeStringToFile(out, csvLine, (Charset) null, true);
      }
    }
  }

  public static void saveFacilitiesRequestCSV(String fileName) throws IOException, SQLException {
    String format = facilitiesRequestFormat;
    // nothing to change here
    File out = fullSaveHelper(fileName, format);
    // change with the proper format in first line of function
    for (facilitiesRequest d : DAOSystemSingleton.INSTANCE.getSystem().getAllFacilitiesRequests()) {
      String csvLine =
          ""
              + d.getServiceRequestID()
              + ','
              + d.getFacilitiesReqType()
              + ','
              + d.getTimeFrame()
              + ','
              + d.getFloorID()
              + ','
              + d.getRoomID()
              + ','
              + d.getIsUrgent()
              + ','
              + d.getStaffAssignee()
              + ','
              + d.getRequestStatus()
              + ','
              + d.getRequestDate()
              + ','
              + d.getDeliveryDate()
              + ','
              + d.getOtherNotes()
              + "\n";
      // change nothing
      if (!doesFileContainLine(out, csvLine)) {
        FileUtils.writeStringToFile(out, csvLine, (Charset) null, true);
      }
    }
  }

  public static void saveSecurityRequestCSV(String fileName) throws IOException, SQLException {
    String format = securityRequestFormat;

    // nothing to change here
    File out = fullSaveHelper(fileName, format);
    // change with the proper format in first line of function
    for (securityRequest d : DAOSystemSingleton.INSTANCE.getSystem().getAllSecurityRequests()) {
      String csvLine =
          ""
              + d.getServiceRequestID()
              + ','
              + d.getSecurityRequestType()
              + ','
              + d.getTimeFrame()
              + ','
              + d.getRoomID()
              + ','
              + d.getFloorID()
              + ','
              + d.getIsUrgent()
              + ','
              + d.getStaffAssignee()
              + ','
              + d.getRequestStatus()
              + ','
              + d.getRequestDate()
              + ','
              + d.getDeliveryDate()
              + ','
              + d.getOtherNotes()
              + "\n";
      // change nothing
      if (!doesFileContainLine(out, csvLine)) {
        FileUtils.writeStringToFile(out, csvLine, (Charset) null, true);
      }
    }
  }

  public static void saveGiftDeliveryRequestCSV(String fileName) throws IOException, SQLException {
    String format = giftDeliveryRequestFormat;
    // nothing to change here
    File out = fullSaveHelper(fileName, format);
    // change with the proper format in first line of function
    for (giftDeliveryRequest d : DAOSystemSingleton.INSTANCE.getSystem().getAllGiftRequests()) {
      String csvLine =
          ""
              + d.getServiceRequestID()
              + ','
              + d.getRequestDate()
              + ','
              + d.getDeliveryDate()
              + ','
              + d.getRequestStatus()
              + ','
              + d.getStaffAssignee()
              + ','
              + d.getIsUrgent()
              + ','
              + d.getRoomID()
              + ','
              + d.getFloorID()
              + ','
              + d.getPatientName()
              + ','
              + d.getGift()
              + ','
              + d.getOtherNotes()
              + "\n";
      // change nothing
      if (!doesFileContainLine(out, csvLine)) {
        FileUtils.writeStringToFile(out, csvLine, (Charset) null, true);
      }
    }
  }

  /*public static void saveEdgesCSV(String fileName) throws IOException, SQLException {
    String format = edgesFormat;
    // nothing to change here
    File out = fullSaveHelper(fileName, format);
    // change with the proper format in first line of function
    for (Account d : DAOSystemSingleton.INSTANCE.getSystem().getAllEdges()?) {
      String csvLine =
              ""
                      + d.getEdgeID()
                      + ','
                      + d.getStartNode()
                      + ','
                      + d.getEndNode()
                      + "\n";
      // change nothing
      if (!doesFileContainLine(out, csvLine)) {
        FileUtils.writeStringToFile(out, csvLine, (Charset) null, true);
      }
    }
  }*/

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

  public static boolean loadMedicineRequestCSV(String fileName) throws SQLException, IOException {
    return loadCSVGeneral(fileName, "MEDICINEREQUEST", medicineRequestFormat);
  }

  public static boolean loadSanitationRequestCSV(String fileName) throws SQLException, IOException {
    return loadCSVGeneral(fileName, "SANITATIONREQUEST", sanitationRequestFormat);
  }

  public static boolean loadMealRequestCSV(String fileName) throws SQLException, IOException {
    return loadCSVGeneral(fileName, "MEALDELIVERYREQUEST", mealDeliveryRequestFormat);
  }

  public static boolean loadLanguageRequestCSV(String fileName) throws SQLException, IOException {
    return loadCSVGeneral(fileName, "LANGUAGEREQUEST", languageInterpreterRequestFormat);
  }

  public static boolean loadFacilitiesRequestCSV(String fileName) throws SQLException, IOException {
    return loadCSVGeneral(fileName, "FACILITIESREQUEST", facilitiesRequestFormat);
  }

  public static boolean loadSecurityRequestCSV(String fileName) throws SQLException, IOException {
    return loadCSVGeneral(fileName, "SECURITYREQUEST", securityRequestFormat);
  }

  public static boolean loadGiftDeliveryCSV(String fileName) throws SQLException, IOException {
    return loadCSVGeneral(fileName, "GIFTREQUEST", giftDeliveryRequestFormat);
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
    format.replaceAll(" ", "").toLowerCase();
    if (!format.endsWith("\n")) {
      format += "\n";
    }
    if (!fileName.toLowerCase().endsWith(".csv")) fileName = "" + fileName + ".csv";

    File file = new File("CSVsaveFiles/" + fileName);
    if (!file.exists()) {
      System.err.println(
          "file should have already been created. "
              + "Resource line may not have been added to the App class init() method");
      generateNewSaveFileFromResources(fileName); // should exist now
    }

    if (!doesFileContainLine(file, format)) { // only looks for first line or no lines
      FileUtils.writeStringToFile(file, format, (Charset) null, false); //false enables overwriting if there is no format
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
    // string manipulation
    line = line.toLowerCase().replace(" ", "");
    String fileName = file.getName();
    BufferedReader in = new BufferedReader(new FileReader(file));
    in.readLine();
    String curLine;
    while ((curLine = in.readLine()) != null) {
      curLine = curLine.toLowerCase().replace(" ", "");
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
      if (!file.exists()) { // if file was created
        assert file.createNewFile();
        URL u = Main.class.getResource("CsvFiles/" + filename);
        if (u != null) {
          InputStream is = Main.class.getResourceAsStream("CsvFiles/" + filename);
          assert is != null;
          writeInputStreamToFile(is, file);
        } // if no resource file then do nothing
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

  /** Generates new save files for each specific resource file name */
  public static void generateNewSaves() {
    ArrayList<String> fileNames = new ArrayList<>();
    fileNames.add(CSVManager.getDefaultEdgesFilename());
    fileNames.add(CSVManager.getDefaultEmployeesFilename());
    fileNames.add(CSVManager.getDefaultEquipmentFilename());
    fileNames.add(CSVManager.getDefaultMedEquipRequestFilename());
    fileNames.add(CSVManager.getDefaultLocationFilename());
    fileNames.add("TransportExt.csv");
    fileNames.add(CSVManager.getDefaultLabRequestFilename());
    fileNames.add(CSVManager.getDefaultServiceRequestFilename());
    fileNames.add(CSVManager.getDefaultMedicineRequestFilename());
    fileNames.add(CSVManager.getDefaultSanitationRequestFilename());
    fileNames.add(CSVManager.getDefaultMealRequestFilename());
    fileNames.add(CSVManager.getDefaultLangInterpRequestFilename());
    fileNames.add(CSVManager.getDefaultFacilitiesRequestFilename());
    fileNames.add(CSVManager.getDefaultSecurityRequestFilename());
    fileNames.add(CSVManager.getDefaultGiftRequestFilename());
    // fileNames.add("Requests.csv");
    fileNames.add(CSVManager.getDefaultAccountsFilename());

    DBCreation.createTables();
    for (String s : fileNames) {
      generateNewSaveFileFromResources(s);
    }
  }

  /**
   * runs the saveCSV method of every entity type
   *
   * @throws SQLException
   * @throws IOException
   */
  public static void saveAllCSVs() throws SQLException, IOException {
    CSVManager.saveLocationCSV(CSVManager.getDefaultLocationFilename());
    CSVManager.saveEmployeeCSV(CSVManager.getDefaultEmployeesFilename());
    CSVManager.saveMedEquipCSV(CSVManager.getDefaultEquipmentFilename());
    CSVManager.saveLabRequestCSV(CSVManager.getDefaultLabRequestFilename());
    CSVManager.saveMedEquipRequestCSV(CSVManager.getDefaultMedEquipRequestFilename());
    CSVManager.saveServiceRequestCSV(CSVManager.getDefaultServiceRequestFilename());
    CSVManager.saveMedicineRequestCSV(CSVManager.getDefaultMedicineRequestFilename());
    CSVManager.saveSanitationRequestCSV(CSVManager.getDefaultSanitationRequestFilename());
    CSVManager.saveMealDeliveryCSV(CSVManager.getDefaultMealRequestFilename());
    CSVManager.saveLanguageInterpreterRequestCSV(CSVManager.getDefaultLangInterpRequestFilename());
    CSVManager.saveFacilitiesRequestCSV(CSVManager.getDefaultFacilitiesRequestFilename());
    CSVManager.saveSecurityRequestCSV(CSVManager.getDefaultSecurityRequestFilename());
    CSVManager.saveGiftDeliveryRequestCSV(CSVManager.getDefaultGiftRequestFilename());
    CSVManager.saveAccountCSV(CSVManager.getDefaultAccountsFilename());

    // CSVManager.saveEdgesCSV();
  }
}
