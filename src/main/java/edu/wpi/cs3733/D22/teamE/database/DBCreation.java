package edu.wpi.cs3733.D22.teamE.database;

import static edu.wpi.cs3733.D22.teamE.RSAEncryption.generatePasswordHASH;

import edu.wpi.cs3733.D22.teamE.CallAPI;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBCreation {
  static Connection connection = DBConnect.EMBEDDED_INSTANCE.getConnection();

  public static void createTowerLocationTable() throws SQLException, IOException {

    System.out.println();
    String query =
        "create table TOWERLOCATIONS\n"
            + "(\n"
            + "    \"NodeID\"    VARCHAR(35) not null primary key,\n"
            + "    \"XCoord\"     INTEGER     not null,\n"
            + "    \"YCoord\"     INTEGER     not null,\n"
            + "    \"Building\"   VARCHAR(35) not null,\n"
            + "    \"NodeType\"   VARCHAR(35) not null,\n"
            + "    \"LongName\"   VARCHAR(50) not null,\n"
            + "    \"ShortName\"  VARCHAR(35) not null,\n"
            + "    \"Floor\"      VARCHAR(25) not null\n"
            + ")";
    PreparedStatement statement = connection.prepareStatement(query);
    statement.executeUpdate();
    CSVManager.loadLocationCSV(CSVManager.getDefaultLocationFilename());
  }

  public static void createEmployeesTable() throws SQLException, IOException {
    String query =
        "create table EMPLOYEES\n"
            + "(\n"
            + "    \"EmployeeID\"  VARCHAR(35) not null primary key,\n"
            + "    \"Name\"        VARCHAR(25) not null,\n"
            + "    \"LocationID\"  VARCHAR(35) not null references TOWERLOCATIONS (NODEID),\n"
            + "    \"Position\"    VARCHAR(25) not null,\n"
            + "    \"Available\"   BOOLEAN     not null,\n"
            + "    \"Salary\"      DOUBLE      not null,\n"
            + "    \"ProfilePic\"  BLOB  \n"
            + ")";
    PreparedStatement statement = connection.prepareStatement(query);
    statement.executeUpdate();
    CSVManager.loadEmployeesCSV(CSVManager.getDefaultEmployeesFilename());
  }

  public static void createEquipmentTable() throws SQLException, IOException {
    String query =
        "create table EQUIPMENT\n"
            + "(\n"
            + "    \"EquipmentID\"          VARCHAR(35) primary key,\n"
            + "    \"MedEquipReqID\"     VARCHAR(35),\n"
            + "    \"IsInUse\"           BOOLEAN,\n"
            + "    \"IsClean\"           BOOLEAN,\n"
            + "    \"CleanLocationID\"   VARCHAR(35) references TOWERLOCATIONS (NODEID),\n"
            + "    \"StorageLocationID\" VARCHAR(35) references TOWERLOCATIONS (NODEID),\n"
            + "    \"CurrentLocationID\" VARCHAR(35),\n"
            + "    \"EquipmentType\"     VARCHAR(35)\n"
            + ")";
    PreparedStatement statement = connection.prepareStatement(query);
    statement.executeUpdate();
    CSVManager.loadEquipmentCSV(CSVManager.getDefaultEquipmentFilename());
  }

  public static void createLabRequestTable() throws SQLException, IOException {
    String query =
        "create table LAB_REQUEST\n"
            + "(\n"
            + "    \"LabRequestID\"     VARCHAR(35) not null primary key,\n"
            + "    \"LabRequestType\"  VARCHAR(35) not null,\n"
            + "    \"StaffAssignee\"     VARCHAR(35) not null references EMPLOYEES(EMPLOYEEID),\n"
            + "    \"LocationID\"        VARCHAR(35) not null references TOWERLOCATIONS (NODEID),\n"
            + "    \"Floor\"            VARCHAR(31)  not null,\n"
            + "    \"TimeFrame\"         VARCHAR(35) not null,\n"
            + "    \"RequestStatus\"     VARCHAR(35) not null,\n"
            + "    \"OtherNotes\"        VARCHAR(35) not null\n"
            + ")";
    PreparedStatement statement = connection.prepareStatement(query);
    statement.executeUpdate();
    CSVManager.loadLabRequestCSV(CSVManager.getDefaultLabRequestFilename());
  }

  public static void createMedEquipReqTable() throws SQLException, IOException {
    String query =
        "create table MED_EQUIP_REQ\n"
            + "(\n"
            + "    \"MedEquipmentID\"  VARCHAR(31)  not null primary key,\n"
            + "    \"RequestDate\"      DATE,\n"
            + "    \"DeliveryDate\"     DATE  not null,\n"
            + "    \"IsUrgent\"         BOOLEAN      not null,\n"
            + "    \"Equip\"            VARCHAR(63)  not null,\n"
            + "    \"EquipQuantity\"    INTEGER      not null,\n"
            + "    \"StaffAssignee\"    VARCHAR(63)  not null,\n"
            + "    \"LocationID\"       VARCHAR(35)  not null references TOWERLOCATIONS (NODEID),\n"
            + "    \"Floor\"            VARCHAR(31)  not null,\n"
            + "    \"RequestStatus\"    VARCHAR(31)  not null,\n"
            + "    \"OtherNotes\"       VARCHAR(255) not null\n"
            + ")";
    PreparedStatement statement = connection.prepareStatement(query);
    statement.executeUpdate();
    CSVManager.loadMedEquipReqCSV(CSVManager.getDefaultMedEquipRequestFilename());
  }

  public static void createServiceRequestTable() throws SQLException, IOException {
    String query =
        "create table SERVICEREQUEST\n"
            + "(\n"
            + "    \"RequestID\"      VARCHAR(35) not null primary key,\n"
            + "    \"Status\"         VARCHAR(35) not null,\n"
            + "    \"Type\"           VARCHAR(35) not null,\n"
            + "    \"Assignee\"       VARCHAR(35) references EMPLOYEES(EMPLOYEEID),\n"
            + "    \"RequestDate\"   DATE        not null,\n"
            + "    \"DeliveryDate\"  DATE,\n"
            + "    \"IsUrgent\"       BOOLEAN     not null\n"
            + ")";
    PreparedStatement statement = connection.prepareStatement(query);
    statement.executeUpdate();
    CSVManager.loadServiceRequestCSV(CSVManager.getDefaultServiceRequestFilename());
  }

  public static void createMedicineRequestTable() throws SQLException, IOException {
    String query =
        "create table MEDICINEREQUEST\n"
            + "(\n"
            + "    \"MedicineReqID\"    VARCHAR(35) primary key, \n"
            + "    \"RequestDate\"       DATE, \n"
            + "    \"DeliveryDate\"      DATE, \n"
            + "    \"Status\"             VARCHAR(35), \n"
            + "    \"Assignee\"           VARCHAR(35),\n"
            + "    \"IsUrgent\"           BOOLEAN, \n"
            + "    \"DeliveryLocationID\" VARCHAR(35) references TOWERLOCATIONS(NODEID), \n "
            + "    \"Floor\"              VARCHAR(25), \n "
            + "    \"MedicineType\"       VARCHAR(25), \n "
            + "    \"MedicineQuantity\"   VARCHAR(25), \n"
            + "    \"MedicineUnit\"       VARCHAR(25), \n"
            + "    \"ReoccuringDays\"     VARCHAR(255), \n"
            + "    \"OtherNotes\"         VARCHAR(255), \n"
            + "    \"DeliveryTime\"       VARCHAR(35) \n"
            + ")";
    PreparedStatement statement = connection.prepareStatement(query);
    statement.executeUpdate();
    CSVManager.loadMedicineRequestCSV(CSVManager.getDefaultMedicineRequestFilename());
  }

  public static void createSanitationRequestTable() throws SQLException, IOException {
    String query =
        "create table SANITATIONREQUEST\n"
            + "(\n"
            + "    \"SanitationRequestID\"    VARCHAR(35) primary key, \n"
            + "    \"RequestDate\"       DATE, \n"
            + "    \"DeliveryDate\"      DATE, \n"
            + "    \"Status\"             VARCHAR(35), \n"
            + "    \"Assignee\"           VARCHAR(35),\n"
            + "    \"IsUrgent\"           BOOLEAN, \n"
            + "    \"RoomID\"             VARCHAR(35) references TOWERLOCATIONS(NODEID), \n "
            + "    \"Floor\"              VARCHAR(25), \n "
            + "    CLEANINGSIZE       VARCHAR(25), \n"
            + "    ISBIOHAZARD        VARCHAR(25), \n"
            + "    OTHERNOTES         VARCHAR(255)\n"
            + ")";
    PreparedStatement statement = connection.prepareStatement(query);
    statement.executeUpdate();
    CSVManager.loadSanitationRequestCSV(CSVManager.getDefaultSanitationRequestFilename());
  }

  public static void createMealRequestTable() throws SQLException, IOException {
    String query =
        "create table MEALDELIVERYREQUEST\n"
            + "(\n"
            + "    MEAL_REQ_ID    VARCHAR(35) primary key, \n"
            + "    REQUEST_DATE       DATE, \n"
            + "    DELIVERY_DATE      DATE, \n"
            + "    STATUS             VARCHAR(35), \n"
            + "    ASSIGNEE           VARCHAR(35),\n"
            + "    ISURGENT           BOOLEAN, \n"
            + "    ROOMID             VARCHAR(35) references TOWERLOCATIONS(NODEID), \n "
            + "    FLOOR              VARCHAR(25), \n "
            + "    ENTREE             VARCHAR(25), \n"
            + "    BEVERAGE           VARCHAR(25), \n"
            + "    DESSERT            VARCHAR(25), \n"
            + "    DELIVERYTIME       VARCHAR(25), \n"
            + "    OTHERNOTES         VARCHAR(255)\n"
            + ")";
    PreparedStatement statement = connection.prepareStatement(query);
    statement.executeUpdate();
    CSVManager.loadMealRequestCSV(CSVManager.getDefaultMealRequestFilename());
  }

  public static void createLanguageInterpreterRequestTable() throws SQLException, IOException {
    String query =
        "create table LANGUAGEREQUEST\n"
            + "(\n"
            + "    LAN_INTERP_REQ_ID    VARCHAR(35) primary key, \n"
            + "    REQUEST_DATE       DATE, \n"
            + "    DELIVERY_DATE      DATE, \n"
            + "    STATUS             VARCHAR(35), \n"
            + "    ASSIGNEE           VARCHAR(35),\n"
            + "    ISURGENT           BOOLEAN, \n"
            + "    ROOMID             VARCHAR(35) references TOWERLOCATIONS(NODEID), \n "
            + "    FLOOR              VARCHAR(25), \n "
            + "    LANGUAGE           VARCHAR(25), \n"
            + "    OTHERNOTES         VARCHAR(255)\n"
            + ")";
    PreparedStatement statement = connection.prepareStatement(query);
    statement.executeUpdate();
    CSVManager.loadLanguageRequestCSV(CSVManager.getDefaultLangInterpRequestFilename());
  }

  public static void createFacilitiesRequestTable() throws SQLException, IOException {
    String query =
        "create table FACILITIESREQUEST\n"
            + "(\n"
            + "    FACILITIESREQID    VARCHAR(35) not null primary key,\n"
            + "    FACILITIESREQTYPE VARCHAR(35) not null,\n"
            + "    TIMEFRAME    VARCHAR(35) not null,\n"
            + "    FLOORID       VARCHAR(35) not null, \n"
            + "    ROOMID        VARCHAR(35) not null references TOWERLOCATIONS (NODEID),\n"
            + "    ISURGENT    BOOLEAN not null,\n"
            + "    STAFFASSIGNEE       VARCHAR(35) not null references EMPLOYEES(EMPLOYEEID),\n"
            + "    REQUESTSTATUS       VARCHAR(35) not null,\n"
            + "    REQUESTDATE       DATE, \n"
            + "    DELIVERYDATE       DATE, \n"
            + "    OTHERNOTES       VARCHAR(35) not null\n"
            + ")";
    PreparedStatement statement = connection.prepareStatement(query);
    statement.executeUpdate();
    CSVManager.loadFacilitiesRequestCSV(CSVManager.getDefaultFacilitiesRequestFilename());
  }

  public static void createSecurityRequestTable() throws SQLException, IOException {
    String query =
        "create table SECURITYREQUEST\n"
            + "(\n"
            + "    SECURITY_REQUESTID    VARCHAR(35) not null primary key,\n"
            + "    SECURITY_REQUEST_TYPE VARCHAR(35) not null,\n"
            + "    TIMEFRAME    VARCHAR(35) not null,\n"
            + "    LOCATIONID       VARCHAR(35) not null references TOWERLOCATIONS (NODEID),\n"
            + "    FLOORID        VARCHAR(35),\n"
            + "    ISURGENT    BOOLEAN not null,\n"
            + "    STAFFASSIGNEE       VARCHAR(35) not null references EMPLOYEES(EMPLOYEEID),\n"
            + "    REQUESTSTATUS       VARCHAR(35) not null,\n"
            + "    REQUESTDATE       DATE, \n"
            + "    DELIVERYDATE       DATE, \n"
            + "    OTHERNOTES       VARCHAR(35) not null\n"
            + ")";
    PreparedStatement statement = connection.prepareStatement(query);
    statement.executeUpdate();
    CSVManager.loadSecurityRequestCSV(CSVManager.getDefaultSecurityRequestFilename());
  }

  public static void createGiftDeliveryRequestTable() throws SQLException, IOException {
    String query =
        "create table GIFTREQUEST\n"
            + "(\n"
            + "    GIFT_REQ_ID    VARCHAR(35) primary key, \n"
            + "    REQUEST_DATE       DATE, \n"
            + "    DELIVERY_DATE      DATE, \n"
            + "    STATUS             VARCHAR(35), \n"
            + "    ASSIGNEE           VARCHAR(35),\n"
            + "    ISURGENT           BOOLEAN, \n"
            + "    ROOMID             VARCHAR(35) references TOWERLOCATIONS(NODEID), \n "
            + "    FLOOR              VARCHAR(25), \n "
            + "    PATIENTNAME        VARCHAR(35), \n"
            + "    GIFTTYPE           VARCHAR(25), \n"
            + "    OTHERNOTES         VARCHAR(255)\n"
            + ")";
    PreparedStatement statement = connection.prepareStatement(query);
    statement.executeUpdate();
    CSVManager.loadGiftDeliveryCSV(CSVManager.getDefaultGiftRequestFilename());
  }

  public static void createAccountsTable() throws SQLException, IOException {
    String query =
        "create table ACCOUNTS\n"
            + "(\n"
            + "    ACCOUNTID      VARCHAR(35)  not null primary key,\n"
            + "    EMPLOYEEID     VARCHAR(35) references EMPLOYEES(EMPLOYEEID),          \n"
            + "    AUTHORITYLEVEL INTEGER,              \n"
            + "    PASSWORDHASH   VARCHAR(500) not null,\n"
            + "    FIRSTNAME      VARCHAR(35)  not null,\n"
            + "    LASTNAME       VARCHAR(35),          \n"
            + "    POSITION       VARCHAR(35),           \n"
            + "    PHONENUMBER       VARCHAR(35)           \n"
            + ")";
    PreparedStatement statement = connection.prepareStatement(query);
    statement.executeUpdate();
    System.out.println(generatePasswordHASH("admin"));
    query =
        "INSERT INTO ACCOUNTS (ACCOUNTID, EMPLOYEEID , AUTHORITYLEVEL, PASSWORDHASH, FIRSTNAME, LASTNAME, POSITION, PHONENUMBER) VALUES "
            + "('admin', 'admin' , 3, '"
            + generatePasswordHASH("admin")
            + "', 'admin', 'admin', 'admin', '0000000000')";
    statement = connection.prepareStatement(query);
    statement.executeUpdate();
    if (connection == DBConnect.EMBEDDED_INSTANCE.getConnection()) {
      System.out.println("e");

    } else {
      System.out.println("c");
    }
    query =
        "INSERT INTO ACCOUNTS (ACCOUNTID, EMPLOYEEID , AUTHORITYLEVEL, PASSWORDHASH, FIRSTNAME, LASTNAME, POSITION, PHONENUMBER) VALUES "
            + "('staff', 'staff' , 1, '"
            + generatePasswordHASH("staff")
            + "', 'staff', 'staff', 'staff', '0000000000')";
    statement = connection.prepareStatement(query);
    statement.executeUpdate();
    CSVManager.loadAccountCSV(CSVManager.getDefaultAccountsFilename());
  }

  public static void createEdgesTable() throws SQLException, IOException {
    String query =
        "create table EDGES\n"
            + "(\n"
            + "    EDGEID    VARCHAR(35), \n"
            + "    START_NODE       VARCHAR(35), \n"
            + "    END_NODE      VARCHAR(35) \n"
            + ")";
    PreparedStatement statement = connection.prepareStatement(query);
    statement.executeUpdate();
    CSVManager.loadEdgesCSV(CSVManager.getDefaultEdgesFilename());
  }

  public static void createTables() {
    try {
      // HAVE ALL LOAD (EXCEPT FLORAL REQUEST)
      CallAPI.getInstance().getExternalTransportAPI();
      createTowerLocationTable(); //
      createEmployeesTable(); //
      createEquipmentTable(); //
      createLabRequestTable(); //
      createMedEquipReqTable(); //
      createServiceRequestTable(); //

      createMedicineRequestTable();
      createSanitationRequestTable();
      createMealRequestTable();
      createLanguageInterpreterRequestTable();
      createFacilitiesRequestTable();
      createSecurityRequestTable();
      createGiftDeliveryRequestTable();

      createAccountsTable(); //

      createEdgesTable();
    } catch (SQLException | IOException e) {
      e.printStackTrace();
    }
  }

  public static void createClientTables() {
    connection = DBConnect.CLIENT_INSTANCE.getConnection();
    createTables();
  }
}
