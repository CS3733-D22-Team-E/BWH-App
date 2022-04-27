package edu.wpi.cs3733.D22.teamE.database;

import edu.wpi.cs3733.D22.teamE.entity.Employee;
import edu.wpi.cs3733.D22.teamE.entity.accounts.Account;

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
}
