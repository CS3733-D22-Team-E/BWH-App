package edu.wpi.cs3733.D22.teamE;

// import edu.wpi.cs3733.D22.teamE.database.DBConnection;
// import edu.wpi.energetic_easter_bunnies.database.DBConnection;
import com.sun.javafx.application.LauncherImpl;

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
    LauncherImpl.launchApplication(App.class, AppPreloader.class, args);
    // closeConnection();
  }
}
