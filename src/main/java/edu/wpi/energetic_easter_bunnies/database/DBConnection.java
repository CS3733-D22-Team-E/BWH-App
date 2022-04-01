package edu.wpi.energetic_easter_bunnies.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
  private static Connection connection = null;

  private DBConnection() {
    String url = "jdbc:derby:myDB;";
    try {
      connection = DriverManager.getConnection("jdbc:derby:myDB;");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static Connection getConnection() {
    return connection;
  }
}
