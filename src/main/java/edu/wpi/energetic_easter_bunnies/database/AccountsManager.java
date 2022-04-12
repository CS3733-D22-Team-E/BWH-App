package edu.wpi.energetic_easter_bunnies.database;

import edu.wpi.energetic_easter_bunnies.entity.accounts.Account;
import java.util.Random;

public class AccountsManager {
  private Account account;
  private Employee employee;
  private static AccountsManager AM_instance = null;

  private AccountsManager() {}

  public static AccountsManager getInstance() {
    if (AM_instance == null) AM_instance = new AccountsManager();

    return AM_instance;
  }

  public Account getAccount() {
    return account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  public static String generateRandom8DigitID() {
    return String.format("%8d", new Random().nextInt((int) Math.pow(10, 8)));
  }

  /**
   * for use in storing passwords in an 'accounts' database -> "never store plaintext
   * passwords"-Gandhi, probably
   *
   * @param input
   * @return
   * @throws NoSuchAlgorithmException
   */
  /*public static String getSHA256(String input) throws NoSuchAlgorithmException {
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
  */
}
