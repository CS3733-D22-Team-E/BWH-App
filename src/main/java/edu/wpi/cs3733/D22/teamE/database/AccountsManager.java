package edu.wpi.cs3733.D22.teamE.database;

import edu.wpi.cs3733.D22.teamE.database.daos.DAOSystemSingleton;
import edu.wpi.cs3733.D22.teamE.entity.Employee;
import edu.wpi.cs3733.D22.teamE.entity.accounts.Account;

public class AccountsManager {
  private String accountID;
  private String employeeID;
  private static AccountsManager AM_instance = null;

  private AccountsManager() {}

  public static AccountsManager getInstance() {
    if (AM_instance == null) AM_instance = new AccountsManager();

    return AM_instance;
  }

  public Account getAccount() {
    return DAOSystemSingleton.INSTANCE.getSystem().getAccount(accountID);
  }

  public void setAccount(Account account) {
    this.accountID = account.getAccountID();
  }

  public Employee getEmployee() {
    return DAOSystemSingleton.INSTANCE.getSystem().getEmployee(employeeID);
  }

  public void setEmployee(Employee employee) {
    this.employeeID = employee.getEmployeeID();
  }
}
