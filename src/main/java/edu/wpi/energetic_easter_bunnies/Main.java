package edu.wpi.energetic_easter_bunnies;

import edu.wpi.energetic_easter_bunnies.database.DBConnection;
import java.sql.SQLException;

public class Main {

  public static void closeConnection() {
    try {
      DBConnection.closeConnection();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    System.out.println("Apache Derby driver closed!");
  }

  public static void main(String[] args) {
    App.launch(App.class, args);
    closeConnection();
  }
}
