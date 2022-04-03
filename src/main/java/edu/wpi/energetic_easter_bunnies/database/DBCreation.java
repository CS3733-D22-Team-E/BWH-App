package edu.wpi.energetic_easter_bunnies.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBCreation {
  static Connection connection = DBConnection.getConnection();

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

  public static void createEquipmentTable() throws SQLException {
    String query =
        "create table EQUIPMENT\n"
            + "(\n"
            + "    EQUIPMENTID         VARCHAR(25),\n"
            + "    \"isInUse\"           BOOLEAN,\n"
            + "    \"isClean\"           BOOLEAN,\n"
            + "    \"cleanLocationID\"   VARCHAR(35),\n"
            + "    \"storageLocationID\" VARCHAR(35)\n"
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
            + "    REQUESTDATE     VARCHAR(31),\n"
            + "    DELIVERYDATE    VARCHAR(31)  not null,\n"
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
    CSVManager.loadMedEquipReqCSV("MedEquipRequest.csv");
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

  public static void createTables() {
    try {
      createEmployeesTable();
      createEquipmentTable();
      createLabRequestTable();
      createMedEquipReqTable();
      createTowerLocationTable();
    } catch (SQLException | IOException e) {
      e.printStackTrace();
    }
  }
}
