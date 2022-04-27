package edu.wpi.cs3733.D22.teamE.entity.accounts;

public class adminAccount extends Account {

  public adminAccount(
      String accountID,
      String employeeID,
      String passwordHash,
      String firstName,
      String lastName,
      String position,
      String phoneNumber) {
    super(accountID, employeeID, 2, passwordHash, firstName, lastName, position, phoneNumber);
  }

  public adminAccount() {
    super();
    this.setAuthorityLevel(2); // admin
  }
}
