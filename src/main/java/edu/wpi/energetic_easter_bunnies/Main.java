package edu.wpi.energetic_easter_bunnies;

import edu.wpi.energetic_easter_bunnies.database.DBConnect;
import edu.wpi.energetic_easter_bunnies.database.DBCreation;
import java.sql.SQLException;

public class Main {

  public static void closeConnection() {
    try {
      DBConnect.INSTANCE.closeConnection();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    System.out.println("Apache Derby driver closed!");
  }

  public static void main(String[] args) {
    DBCreation.createTables();
    App.launch(App.class, args);
    closeConnection();
  }
}
