package edu.wpi.cs3733.D22.teamE.entity.accounts;

public class staffAccount extends Account {
  public staffAccount(
      String accountID,
      String employeeID,
      int authorityLevel,
      String passwordHash,
      String firstName,
      String lastName,
      String position,
      String phoneNumber) {
    super(
        accountID,
        employeeID,
        authorityLevel,
        passwordHash,
        firstName,
        lastName,
        position,
        phoneNumber);
    this.setAuthorityLevel(1);
  }

  public staffAccount() {
    super();
    this.setAuthorityLevel(1); // admin
  }
}
