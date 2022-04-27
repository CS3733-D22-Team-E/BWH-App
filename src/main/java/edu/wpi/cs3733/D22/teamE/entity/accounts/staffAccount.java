package edu.wpi.cs3733.D22.teamE.entity.accounts;

public class staffAccount extends Account {

  public staffAccount(
      String accountID,
      String employeeID,
      String passwordHash,
      String firstName,
      String lastName,
      String position,
      String phoneNumber) {
    super(accountID, employeeID, 1, passwordHash, firstName, lastName, position, phoneNumber);
  }

  public staffAccount() {
    super();
    this.setAuthorityLevel(1); // admin
  }
}
