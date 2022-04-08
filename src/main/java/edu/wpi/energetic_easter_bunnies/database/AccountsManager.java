package edu.wpi.energetic_easter_bunnies.database;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;

public class AccountsManager {
  static Connection connection = DBConnection.getConnection();

  //TODO create DB table in DBCreation.java
  //TODO Decision on where to store passwords - this could also be just another attribute of the
  //    employees DB 'password'
  /**
   * for use in storing passwords in an 'accounts' database -> "never store plaintext
   * passwords"-Gandhi, probably
   *
   * @param input
   * @return
   * @throws NoSuchAlgorithmException
   */
  public static String getSHA256(String input) throws NoSuchAlgorithmException {
    // Static getInstance method is called with hashing SHA
    MessageDigest md = MessageDigest.getInstance("SHA-256");

    // to calculate message digest of an input
    // and return array of byte
    byte[] hash = md.digest(input.getBytes(StandardCharsets.UTF_8));
    // change byte[] into string
    BigInteger number = new BigInteger(1, hash);

    // Convert message digest into hex value
    StringBuilder hexString = new StringBuilder(number.toString(16));

    // Pad with leading zeros
    while (hexString.length() < 32) {
      hexString.insert(0, '0');
    }

    return hexString.toString();
  }
}
