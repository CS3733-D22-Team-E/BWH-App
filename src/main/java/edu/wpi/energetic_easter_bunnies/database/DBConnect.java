package edu.wpi.energetic_easter_bunnies.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public enum DBConnect {
  EMBEDDED_INSTANCE("jdbc:derby:myDB;create=true", "org.apache.derby.jdbc.EmbeddedDriver");
  // CLIENT_INSTANCE("jdbc:derby://localhost:1527/BWDB", "org.apache.derby.jdbc.ClientDriver");

  private Connection connection;

  DBConnect(String url, String className) {
    try {
      Class.forName(className);
      connection = DriverManager.getConnection(url);
    } catch (SQLException | ClassNotFoundException e) {
      System.out.println("Apache Derby Driver not found.");
      e.printStackTrace();
    }
  }

  /**
   * Method to get the embedded instance
   *
   * @return EMBEDDED_INSTANCE
   */
  public static DBConnect getEmbeddedInstance() {
    return EMBEDDED_INSTANCE;
  }

  /**
   * Method to get the client instance
   *
   * @return CLIENT_INSTANCE
   */
  /*public static DBConnect getClientInstance() {
    return CLIENT_INSTANCE;
  }*/

  /**
   * Method to get connection
   *
   * @return connection
   */
  public Connection getConnection() {
    return connection;
  }
}
