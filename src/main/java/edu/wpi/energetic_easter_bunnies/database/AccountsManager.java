package edu.wpi.energetic_easter_bunnies.database;

import java.sql.Connection;
import java.util.Random;

public class AccountsManager {
  static Connection connection = DBConnect.EMBEDDED_INSTANCE.getConnection();

  // TODO create DB table in DBCreation.java
  // TODO Decision on where to store passwords - this could also be just another attribute of the
  //    employees DB 'password'

  public static String generateRandom8DigitID() {
    return String.format("%8d", new Random().nextInt((int) Math.pow(10, 8)));
  }
}
