package edu.wpi.energetic_easter_bunnies;

public class Main {

  public static void main(String[] args) {
    try {
      Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
    } catch (ClassNotFoundException e) {
      System.out.println("Apache Derby Driver not found.");
      e.printStackTrace();
      return;
    }

    System.out.println("Apache Derby driver registered!");
    App.launch(App.class, args);
  }
}
