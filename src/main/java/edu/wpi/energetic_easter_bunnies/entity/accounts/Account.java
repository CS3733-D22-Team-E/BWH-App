package edu.wpi.energetic_easter_bunnies.entity.accounts;

/**
 * - create new user Type by creating a new class that extends this class
 */
public abstract class Account {
  int authorityLevel; // 0 is basic (default) user,
                              // 1 is a staff account,
                              // 2 and above is admin privileges
  private String accountID; // staff assignee
  private String firstName;
  private String lastName;
  private String position;
  private String location; // if not "live tracked", this could be their office etc

  private String passwordHash;

  public Account(
      String accountID,
      String passwordHash,
      int authorityLevel,
      String firstName,
      String lastName,
      String position,
      String location) {
    this.accountID = accountID;
    this.passwordHash = passwordHash;
    this.firstName = firstName;
    this.lastName = lastName;
    this.position = position;
    this.location = location;
    this.authorityLevel = authorityLevel; //0 is basic user account
  }

  //thinking for setters it will require a object of [this]
  // that will check the current user has admin privileges, to change
  // a users attributes. maybe a separate page

  public int getAuthLevel() {
    return this.authorityLevel;
  }

  public String getAccountID() {
    return this.accountID;
  }

  public String getPosition() {
    return this.position;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public String getLastName() {
    return this.lastName;
  }
}
