package edu.wpi.energetic_easter_bunnies.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
  private static Connection connection = null;

  static {
    String url = "jdbc:derby:myDB";
    try {
      Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
      connection = DriverManager.getConnection(url);
    } catch (SQLException | ClassNotFoundException e) {
      System.out.println("Apache Derby Driver not found.");
      e.printStackTrace();
    }
    System.out.println("Apache Derby driver registered!");
  }

  public static Connection getConnection() {
    return connection;
  }

  public static void closeConnection() throws SQLException {
    connection.close();
  }
}
