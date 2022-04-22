package edu.wpi.cs3733.D22.teamE.database;

import static edu.wpi.cs3733.D22.teamE.RSAEncryption.generatePasswordHASH;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBCreation {
  static Connection connection = DBConnect.EMBEDDED_INSTANCE.getConnection();

  public static void createTowerLocationTable() throws SQLException, IOException {
    String query =
        "create table TOWERLOCATIONS\n"
            + "(\n"
            + "    NODEID    VARCHAR(35) not null primary key,\n"
            + "    XCOORD    INTEGER     not null,\n"
            + "    YCOORD    INTEGER     not null,\n"
            + "    BUILDING  VARCHAR(35) not null,\n"
            + "    NODETYPE  VARCHAR(35) not null,\n"
            + "    LONGNAME  VARCHAR(50) not null,\n"
            + "    SHORTNAME VARCHAR(35) not null,\n"
            + "    FLOOR     VARCHAR(25) not null\n"
            + ")";
    PreparedStatement statement = connection.prepareStatement(query);
    statement.executeUpdate();
    CSVManager.loadLocationCSV("TowerLocations.csv");
  }

  public static void createEmployeesTable() throws SQLException {
    String query =
        "create table EMPLOYEES\n"
            + "(\n"
            + "    EMPLOYEEID VARCHAR(35) not null primary key,\n"
            + "    NAME       VARCHAR(25) not null,\n"
            + "    LOCATIONID VARCHAR(35) not null references TOWERLOCATIONS (NODEID),\n"
            + "    POSITION   VARCHAR(25) not null,\n"
            + "    AVAILABLE  BOOLEAN     not null,\n"
            + "    SALARY     DOUBLE      not null\n"
            + ")\n"
            + "\n";
    PreparedStatement statement = connection.prepareStatement(query);
    statement.executeUpdate();
    query =
        "INSERT INTO EMPLOYEES (EMPLOYEEID, NAME , LOCATIONID, POSITION, AVAILABLE, SALARY) VALUES "
            + "('admin', 'admin' , 'FDEPT00101', 'admin', TRUE, 3)";
    statement = connection.prepareStatement(query);
    statement.executeUpdate();
    query =
        "INSERT INTO EMPLOYEES (EMPLOYEEID, NAME , LOCATIONID, POSITION, AVAILABLE, SALARY) VALUES "
            + "('staff', 'staff' , 'FDEPT00101', 'staff', TRUE, 1)";
    statement = connection.prepareStatement(query);
    statement.executeUpdate();
  }

  public static void createEquipmentTable() throws SQLException, IOException {
    String query =
        "create table EQUIPMENT\n"
            + "(\n"
            + "    EQUIPMENTID         VARCHAR(35) primary key,\n"
            + "    MED_EQUIP_REQ_ID    VARCHAR(35),\n"
            + "    \"isInUse\"           BOOLEAN,\n"
            + "    \"isClean\"           BOOLEAN,\n"
            + "    \"cleanLocationID\"   VARCHAR(35) references TOWERLOCATIONS (NODEID),\n"
            + "    \"storageLocationID\" VARCHAR(35) references TOWERLOCATIONS (NODEID),\n"
            + "    \"currentLocationID\" VARCHAR(35),\n"
            + "    \"equipmentType\"     VARCHAR(35)\n"
            + ")";
    PreparedStatement statement = connection.prepareStatement(query);
    statement.executeUpdate();
  }

  public static void createLabRequestTable() throws SQLException, IOException {
    String query =
        "create table LAB_REQUEST\n"
            + "(\n"
            + "    LAB_REQUESTID    VARCHAR(35) not null primary key,\n"
            + "    LAB_REQUEST_TYPE VARCHAR(35) not null,\n"
            + "    STAFFASSIGNEE    VARCHAR(35) not null references EMPLOYEES(EMPLOYEEID),\n"
            + "    LOCATIONID       VARCHAR(35) not null references TOWERLOCATIONS (NODEID),\n"
            + "    TIMEFRAME        VARCHAR(35) not null,\n"
            + "    REQUESTSTATUS    VARCHAR(35) not null,\n"
            + "    OTHERNOTES       VARCHAR(35) not null\n"
            + ")";
    PreparedStatement statement = connection.prepareStatement(query);
    statement.executeUpdate();
  }

  public static void createMedEquipReqTable() throws SQLException, IOException {
    String query =
        "create table MED_EQUIP_REQ\n"
            + "(\n"
            + "    MED_EQUIPMENTID VARCHAR(31)  not null primary key,\n"
            + "    REQUESTDATE     DATE,\n"
            + "    DELIVERYDATE    DATE  not null,\n"
            + "    ISURGENT        BOOLEAN      not null,\n"
            + "    EQUIP           VARCHAR(63)  not null,\n"
            + "    EQUIPQUANTITY   INTEGER      not null,\n"
            + "    STAFFASSIGNEE   VARCHAR(63)  not null,\n"
            + "    LOCATIONID      VARCHAR(35)  not null references TOWERLOCATIONS (NODEID),\n"
            + "    FLOOR           VARCHAR(31)  not null,\n"
            + "    REQUESTSTATUS   VARCHAR(31)  not null,\n"
            + "    OTHERNOTES      VARCHAR(255) not null\n"
            + ")";
    PreparedStatement statement = connection.prepareStatement(query);
    statement.executeUpdate();
    // CSVManager.loadMedEquipReqCSV("MedEquipRequest.csv");
  }

  public static void createServiceRequestTable() throws SQLException {
    String query =
        "create table SERVICEREQUEST\n"
            + "(\n"
            + "    REQUESTID     VARCHAR(35) not null primary key,\n"
            + "    STATUS        VARCHAR(35) not null,\n"
            + "    TYPE          VARCHAR(35) not null,\n"
            + "    ASSIGNEE      VARCHAR(35) references EMPLOYEES(EMPLOYEEID),\n"
            + "    REQUEST_DATE  DATE        not null,\n"
            + "    DELIVERY_DATE DATE,\n"
            + "    ISURGENT      BOOLEAN     not null\n"
            + ")";
    PreparedStatement statement = connection.prepareStatement(query);
    statement.executeUpdate();
  }

  public static void createMedicineRequestTable() throws SQLException {
    String query =
        "create table MEDICINEREQUEST\n"
            + "(\n"
            + "    MEDICINE_REQ_ID    VARCHAR(35) primary key, \n"
            + "    REQUEST_DATE       DATE, \n"
            + "    DELIVERY_DATE      DATE, \n"
            + "    STATUS             VARCHAR(35), \n"
            + "    ASSIGNEE           VARCHAR(35),\n"
            + "    ISURGENT           BOOLEAN, \n"
            + "    DELIVERYLOCATIONID VARCHAR(35) references TOWERLOCATIONS(NODEID), \n "
            + "    FLOOR              VARCHAR(25), \n "
            + "    MEDICINETYPE       VARCHAR(25), \n "
            + "    MEDICINEQUANTITY   VARCHAR(25), \n"
            + "    MEDICINEUNIT       VARCHAR(25), \n"
            + "    REOCURRINGDAYS     VARCHAR(255), \n"
            + "    OTHERNOTES         VARCHAR(255), \n"
            + "    DELIVERYTIME       VARCHAR(35) \n"
            + ")";
    PreparedStatement statement = connection.prepareStatement(query);
    statement.executeUpdate();
  }

  public static void createSanitationRequestTable() throws SQLException {
    String query =
        "create table SANITATIONREQUEST\n"
            + "(\n"
            + "    SANITATION_REQ_ID    VARCHAR(35) primary key, \n"
            + "    REQUEST_DATE       DATE, \n"
            + "    DELIVERY_DATE      DATE, \n"
            + "    STATUS             VARCHAR(35), \n"
            + "    ASSIGNEE           VARCHAR(35),\n"
            + "    ISURGENT           BOOLEAN, \n"
            + "    ROOMID             VARCHAR(35) references TOWERLOCATIONS(NODEID), \n "
            + "    FLOOR              VARCHAR(25), \n "
            + "    CLEANINGSIZE       VARCHAR(25), \n"
            + "    ISBIOHAZARD        VARCHAR(25), \n"
            + "    OTHERNOTES         VARCHAR(255)\n"
            + ")";
    PreparedStatement statement = connection.prepareStatement(query);
    statement.executeUpdate();
  }

  public static void createMealRequestTable() throws SQLException {
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
  }

  public static void createLanguageInterpreterRequestTable() throws SQLException {
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
  }

  public static void createFacilitiesRequestTable() throws SQLException, IOException {
    String query =
        "create table FACILITIESREQUEST\n"
            + "(\n"
            + "    FACILITIESREQID    VARCHAR(35) not null primary key,\n"
            + "    FACILITIESREQTYPE VARCHAR(35) not null,\n"
            + "    TIMEFRAME    VARCHAR(35) not null,\n"
            + "    FLOORID       VARCHAR(35) not null references TOWERLOCATIONS (NODEID),\n"
            + "    ROOMID        VARCHAR(35) not null,\n"
            + "    ISURGENT    BOOLEAN not null,\n"
            + "    STAFFASSIGNEE       VARCHAR(35) not null references EMPLOYEES(EMPLOYEEID),\n"
            + "    REQUESTSTATUS       VARCHAR(35) not null,\n"
            + "    REQUESTDATE       DATE, \n"
            + "    DELIVERYDATE       DATE, \n"
            + "    OTHERNOTES       VARCHAR(35) not null\n"
            + ")";
    PreparedStatement statement = connection.prepareStatement(query);
    statement.executeUpdate();
  }

  public static void createSecurityRequestTable() throws SQLException {
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
  }

  public static void createGiftDeliveryRequestTable() throws SQLException {
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
  }

  public static void createAccountsTable() throws SQLException {
    String query =
        "create table ACCOUNTS\n"
            + "(\n"
            + "    ACCOUNTID      VARCHAR(35)  not null primary key,\n"
            + "    EMPLOYEEID     VARCHAR(35) references EMPLOYEES(EMPLOYEEID),          \n"
            + "    AUTHORITYLEVEL INTEGER,              \n"
            + "    PASSWORDHASH   VARCHAR(500) not null,\n"
            + "    FIRSTNAME      VARCHAR(35)  not null,\n"
            + "    LASTNAME       VARCHAR(35),          \n"
            + "    POSITION       VARCHAR(35)           \n"
            + ")";
    PreparedStatement statement = connection.prepareStatement(query);
    statement.executeUpdate();
    System.out.println(generatePasswordHASH("admin"));
    query =
        "INSERT INTO ACCOUNTS (ACCOUNTID, EMPLOYEEID , AUTHORITYLEVEL, PASSWORDHASH, FIRSTNAME, LASTNAME, POSITION) VALUES "
            + "('admin', 'admin' , 3, '"
            + generatePasswordHASH("admin")
            + "', 'admin', 'admin', 'admin')";
    statement = connection.prepareStatement(query);
    statement.executeUpdate();
    query =
        "INSERT INTO ACCOUNTS (ACCOUNTID, EMPLOYEEID , AUTHORITYLEVEL, PASSWORDHASH, FIRSTNAME, LASTNAME, POSITION) VALUES "
            + "('staff', 'staff' , 1, '"
            + generatePasswordHASH("staff")
            + "', 'staff', 'staff', 'staff')";
    statement = connection.prepareStatement(query);
    statement.executeUpdate();
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
    CSVManager.loadEdgesCSV("AllEdges.csv");
  }

  public static void createTables() {
    try {
      edu.wpi.cs3733.D22.teamEAPI.database.DBCreation.createFloralRequestTable();
      createTowerLocationTable();
      createEmployeesTable();
      createEquipmentTable();
      createLabRequestTable();
      createMedEquipReqTable();
      createServiceRequestTable();
      createMedicineRequestTable();
      createSanitationRequestTable();
      createMealRequestTable();
      createLanguageInterpreterRequestTable();
      createFacilitiesRequestTable();
      createSecurityRequestTable();
      createGiftDeliveryRequestTable();
      createAccountsTable();
      createEdgesTable();
    } catch (SQLException | IOException e) {
      e.printStackTrace();
    }
  }
}
