package edu.wpi.energetic_easter_bunnies.entity.accounts;

public class staffAccount extends Account {
  public staffAccount(
      String accountID,
      String employeeID,
      int authorityLevel,
      String passwordHash,
      String firstName,
      String lastName,
      String position) {
    super(accountID, employeeID, authorityLevel, passwordHash, firstName, lastName, position);
    this.setAuthorityLevel(1);
  }

  public staffAccount() {
    super();
    this.setAuthorityLevel(1); // admin
  }
}
