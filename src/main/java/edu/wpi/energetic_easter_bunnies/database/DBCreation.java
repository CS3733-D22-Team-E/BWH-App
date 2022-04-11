package edu.wpi.energetic_easter_bunnies.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBCreation {
  static Connection connection = DBConnect.EMBEDDED_INSTANCE.getConnection();

  public static void createEmployeesTable() throws SQLException {
    String query =
        "create table EMPLOYEES\n"
            + "(\n"
            + "    EMPLOYEEID VARCHAR(35) not null,\n"
            + "    NAME       VARCHAR(25) not null,\n"
            + "    LOCATIONID VARCHAR(25) not null,\n"
            + "    POSITION   VARCHAR(25) not null,\n"
            + "    AVAILABLE  BOOLEAN     not null,\n"
            + "    SALARY     DOUBLE      not null\n"
            + ")";
    Statement statement = connection.createStatement();
    statement.executeUpdate(query);
  }

  public static void createEquipmentTable() throws SQLException, IOException {
    String query =
        "create table EQUIPMENT\n"
            + "(\n"
            + "    EQUIPMENTID         VARCHAR(35),\n"
            + "    MED_EQUIP_REQ_ID    VARCHAR(35),\n"
            + "    \"isInUse\"           BOOLEAN,\n"
            + "    \"isClean\"           BOOLEAN,\n"
            + "    \"cleanLocationID\"   VARCHAR(35),\n"
            + "    \"storageLocationID\" VARCHAR(35),\n"
            + "    \"currentLocationID\" VARCHAR(35),\n"
            + "    \"equipmentType\"     VARCHAR(35)\n"
            + ")";
    Statement statement = connection.createStatement();
    statement.executeUpdate(query);
  }

  public static void createLabRequestTable() throws SQLException, IOException {
    String query =
        "create table LAB_REQUEST\n"
            + "(\n"
            + "    LAB_REQUESTID    VARCHAR(35) not null,\n"
            + "    LAB_REQUEST_TYPE VARCHAR(35) not null,\n"
            + "    STAFFASSIGNEE    VARCHAR(35) not null,\n"
            + "    LOCATIONID       VARCHAR(35) not null,\n"
            + "    TIMEFRAME        VARCHAR(35) not null,\n"
            + "    REQUESTSTATUS    VARCHAR(35) not null,\n"
            + "    OTHERNOTES       VARCHAR(35) not null\n"
            + ")";
    Statement statement = connection.createStatement();
    statement.executeUpdate(query);
  }

  public static void createMedEquipReqTable() throws SQLException, IOException {
    String query =
        "create table MED_EQUIP_REQ\n"
            + "(\n"
            + "    MED_EQUIPMENTID VARCHAR(31)  not null,\n"
            + "    REQUESTDATE     DATE,\n"
            + "    DELIVERYDATE    DATE  not null,\n"
            + "    ISURGENT        BOOLEAN      not null,\n"
            + "    EQUIP           VARCHAR(63)  not null,\n"
            + "    EQUIPQUANTITY   INTEGER      not null,\n"
            + "    STAFFASSIGNEE   VARCHAR(63)  not null,\n"
            + "    LOCATIONID      VARCHAR(31)  not null,\n"
            + "    FLOOR           VARCHAR(31)  not null,\n"
            + "    REQUESTSTATUS   VARCHAR(31)  not null,\n"
            + "    OTHERNOTES      VARCHAR(255) not null\n"
            + ")";
    Statement statement = connection.createStatement();
    statement.executeUpdate(query);
    // CSVManager.loadMedEquipReqCSV("MedEquipRequest.csv");
  }

  public static void createTowerLocationTable() throws SQLException, IOException {
    String query =
        "create table TOWERLOCATIONS\n"
            + "(\n"
            + "    NODEID    VARCHAR(35) not null,\n"
            + "    XCOORD    INTEGER     not null,\n"
            + "    YCOORD    INTEGER     not null,\n"
            + "    BUILDING  VARCHAR(35) not null,\n"
            + "    NODETYPE  VARCHAR(35) not null,\n"
            + "    LONGNAME  VARCHAR(50) not null,\n"
            + "    SHORTNAME VARCHAR(35) not null,\n"
            + "    FLOOR     VARCHAR(25) not null\n"
            + ")";
    Statement statement = connection.createStatement();
    statement.executeUpdate(query);
    CSVManager.loadLocationCSV("TowerLocations.csv");
  }

  public static void createServiceRequestTable() throws SQLException {
    String query =
        "create table SERVICEREQUEST\n"
            + "(\n"
            + "    REQUESTID     VARCHAR(35) not null,\n"
            + "    STATUS        VARCHAR(35) not null,\n"
            + "    TYPE          VARCHAR(35) not null,\n"
            + "    ASSIGNEE      VARCHAR(35),\n"
            + "    REQUEST_DATE  DATE        not null,\n"
            + "    DELIVERY_DATE DATE,\n"
            + "    ISURGENT      BOOLEAN     not null\n"
            + ")";
    Statement statement = connection.createStatement();
    statement.executeUpdate(query);
  }

  public static void createMedicineRequestTable() throws SQLException {
    String query =
            "create table MEDICINEREQUEST\n"
                    + "(\n"
                    + "    MEDICINE_REQ_ID    VARCHAR(35), \n"
                    + "    REQUEST_DATE       DATE, \n"
                    + "    DELIVERY_DATE      DATE, \n"
                    + "    STATUS             VARCHAR(35), \n"
                    + "    ASSIGNEE           VARCHAR(35),\n"
                    + "    ISURGENT           BOOLEAN, \n"
                    + "    DELIVERYLOCATIONID VARCHAR(35), \n "
                    + "    FLOOR              VARCHAR(25), \n "
                    + "    MEDICINETYPE       VARCHAR(25), \n "
                    + "    MEDICINEQUANTITY   VARCHAR(25), \n"
                    + "    MEDICINEUNIT       VARCHAR(25), \n"
                    + "    REOCURRINGDAYS     VARCHAR(255), \n"
                    + "    OTHERNOTES         VARCHAR(255), \n"
                    + "    DELIVERYTIME       VARCHAR(35) \n"
                    + ")";
    Statement statement = connection.createStatement();
    statement.executeUpdate(query);
  }

    public static void createSanitationRequestTable() throws SQLException {
      String query = "create table SANITATIONREQUEST\n" +
              "(\n"
              + "    MEDICINE_REQ_ID    VARCHAR(35), \n"
              + "    REQUEST_DATE       DATE, \n"
              + "    DELIVERY_DATE      DATE, \n"
              + "    STATUS             VARCHAR(35), \n"
              + "    ASSIGNEE           VARCHAR(35),\n"
              + "    ISURGENT           BOOLEAN, \n"
              + "    ROOMID             VARCHAR(35), \n "
              + "    FLOOR              VARCHAR(25), \n "
              + "    CLEANINGSIZE       VARCHAR(25), \n"
              + "    ISBIOHAZARD        VARCHAR(25), \n"
              + "    OTHERNOTES         VARCHAR(255)\n"
              + ")";
      Statement statement = connection.createStatement();
      statement.executeUpdate(query);
    }

    public static void createMealRequestTable() throws SQLException {
      String query = "create table MEALDELIVERYREQUEST\n" +
              "(\n"
              + "    MEAL_REQ_ID    VARCHAR(35), \n"
              + "    REQUEST_DATE       DATE, \n"
              + "    DELIVERY_DATE      DATE, \n"
              + "    STATUS             VARCHAR(35), \n"
              + "    ASSIGNEE           VARCHAR(35),\n"
              + "    ISURGENT           BOOLEAN, \n"
              + "    ROOMID             VARCHAR(35), \n "
              + "    FLOOR              VARCHAR(25), \n "
              + "    ENTREE             VARCHAR(25), \n"
              + "    BEVERAGE           VARCHAR(25), \n"
              + "    DESSERT            VARCHAR(25), \n"
              + "    OTHERNOTES         VARCHAR(255)\n"
              + ")";
      Statement statement = connection.createStatement();
      statement.executeUpdate(query);
    }

  public static void createLanguageInterpreterRequestTable() throws SQLException {
    String query = "create table LANGUAGEREQUEST\n" +
            "(\n"
            + "    LAN_INTERP_REQ_ID    VARCHAR(35), \n"
            + "    REQUEST_DATE       DATE, \n"
            + "    DELIVERY_DATE      DATE, \n"
            + "    STATUS             VARCHAR(35), \n"
            + "    ASSIGNEE           VARCHAR(35),\n"
            + "    ISURGENT           BOOLEAN, \n"
            + "    ROOMID             VARCHAR(35), \n "
            + "    FLOOR              VARCHAR(25), \n "
            + "    LANGUAGE           VARCHAR(25), \n"
            + "    OTHERNOTES         VARCHAR(255)\n"
            + ")";
    Statement statement = connection.createStatement();
    statement.executeUpdate(query);
  }

  public static void createTables() {
    try {
      createEmployeesTable();
      createEquipmentTable();
      createLabRequestTable();
      createMedEquipReqTable();
      createTowerLocationTable();
      createServiceRequestTable();
      createMedicineRequestTable();
      createSanitationRequestTable();
      createMealRequestTable();
      createLanguageInterpreterRequestTable();
    } catch (SQLException | IOException e) {
      e.printStackTrace();
    }
  }
}
