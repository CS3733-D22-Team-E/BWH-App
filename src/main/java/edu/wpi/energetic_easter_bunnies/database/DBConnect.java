package edu.wpi.energetic_easter_bunnies.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public enum DBConnect {
  INSTANCE;
  private Connection connection;

  DBConnect() {
    String url = "jdbc:derby:myDB;create=true";
    try {
      Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
      connection = DriverManager.getConnection(url);
    } catch (SQLException | ClassNotFoundException e) {
      System.out.println("Apache Derby Driver not found.");
      e.printStackTrace();
    }
  }

  public static DBConnect getInstance() {
    return INSTANCE;
  }

  public Connection getConnection() {
    return connection;
  }
}
