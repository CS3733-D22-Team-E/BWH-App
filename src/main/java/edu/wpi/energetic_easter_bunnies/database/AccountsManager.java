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
}
