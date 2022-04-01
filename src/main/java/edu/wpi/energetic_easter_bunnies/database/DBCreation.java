package edu.wpi.energetic_easter_bunnies.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBCreation {
  public static void main(String[] args) throws SQLException {
    Connection connection = DriverManager.getConnection("jdbc:derby:myDB;create=true");
  }
}
