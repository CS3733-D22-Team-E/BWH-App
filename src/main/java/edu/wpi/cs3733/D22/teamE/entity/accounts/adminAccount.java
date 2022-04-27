package edu.wpi.cs3733.D22.teamE.entity.accounts;

public class adminAccount extends Account {

  public adminAccount(
      String accountID,
      String employeeID,
      String passwordHash,
      String firstName,
      String lastName,
      String position,
      String phoneNum) {
    super(accountID, employeeID, 2, passwordHash, firstName, lastName, position, phoneNum);
  }

  public adminAccount() {
    super();
    this.setAuthorityLevel(2); // admin
  }
}
