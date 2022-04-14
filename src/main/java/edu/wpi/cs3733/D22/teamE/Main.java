package edu.wpi.cs3733.D22.teamE;

// import edu.wpi.cs3733.D22.teamE.database.DBConnection;
import edu.wpi.cs3733.D22.teamE.database.DBCreation;

public class Main {

  // public static void closeConnection() {
  //   try {
  //    DBConnection.closeConnection();
  //  } catch (SQLException e) {
  //     e.printStackTrace();
  //  }
  //   System.out.println("Apache Derby driver closed!");
  //  }

  public static void main(String[] args) {
    DBCreation.createTables();
    App.launch(App.class, args);
    // closeConnection();
  }
}
