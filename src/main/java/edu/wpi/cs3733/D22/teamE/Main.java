package edu.wpi.cs3733.D22.teamE;

// import edu.wpi.cs3733.D22.teamE.database.DBConnection;

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
    //    ardComm comm = new ardComm();
    //    System.out.println(comm.readData());
    //    Thread thread =
    //        new Thread(
    //            () -> {
    App.launch(App.class, args);
    //            });
    //    thread.start();
    // CallAPI.getInstance().openAPI();
    // closeConnection();
  }
}
