package edu.wpi.cs3733.D22.teamE.entity.accounts;

public class adminAccount extends Account {

  public adminAccount(
      String accountID,
      String employeeID,
      int authorityLevel,
      String passwordHash,
      String firstName,
      String lastName,
      String position) {
    super(accountID, employeeID, authorityLevel, passwordHash, firstName, lastName, position);
  }

  public adminAccount() {
    super();
    this.setAuthorityLevel(2); // admin
  }
}
