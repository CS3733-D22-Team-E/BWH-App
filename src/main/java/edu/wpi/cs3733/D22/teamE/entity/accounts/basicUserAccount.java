package edu.wpi.cs3733.D22.teamE.entity.accounts;

public class basicUserAccount extends Account {

  public basicUserAccount(
      String accountID,
      String employeeID,
      String passwordHash,
      String firstName,
      String lastName,
      String position,
      String phoneNumber) {
    super(accountID, employeeID, 0, passwordHash, firstName, lastName, position, phoneNumber);
  }

  public basicUserAccount() {
    super();
    this.setAuthorityLevel(0); // admin
  }
}
