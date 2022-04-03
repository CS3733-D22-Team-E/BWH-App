package edu.wpi.energetic_easter_bunnies.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBCreation {
  public static void createTowerLocationTable() throws SQLException, IOException {
    String query = "create table TOWERLOCATIONS\n" +
            "(\n" +
            "    NODEID    VARCHAR(35) not null,\n" +
            "    XCOORD    INTEGER     not null,\n" +
            "    YCOORD    INTEGER     not null,\n" +
            "    BUILDING  VARCHAR(35) not null,\n" +
            "    NODETYPE  VARCHAR(35) not null,\n" +
            "    LONGNAME  VARCHAR(50) not null,\n" +
            "    SHORTNAME VARCHAR(35) not null,\n" +
            "    FLOOR     VARCHAR(25) not null\n" +
            ");";
    Connection connection = DBConnection.getConnection();
    Statement statement = connection.createStatement();
    statement.executeQuery(query);
    CSVManager.loadLocationCSV("TowerLocations.csv");
  }
  public static void main(String[] args) throws SQLException, IOException {
    Connection connection = DriverManager.getConnection("jdbc:derby:myDB;create=true");
    createTowerLocationTable();
  }
}
