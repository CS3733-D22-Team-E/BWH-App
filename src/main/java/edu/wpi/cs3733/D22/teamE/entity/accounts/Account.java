package edu.wpi.cs3733.D22.teamE.entity.accounts;

public abstract class Account {
  public static int basicPerm = 0;
  public static int staffPerm = 1;
  public static int adminPerm = 2;

  private String accountID; // staff assignee
  private String employeeID; // if empty
  private int authorityLevel; // 0 basic user, 1 staff, 2... admin
  private String passwordHash;
  private String firstName;
  private String lastName;
  private String position;
  private String phoneNumber;

  /**
   * realistically used to load in from a CSV of accounts
   *
   * @param accountID
   * @param employeeID
   * @param authorityLevel
   * @param passwordHash
   * @param firstName
   * @param lastName
   * @param position
   * @param phoneNumber
   */
  public Account(
      String accountID,
      String employeeID,
      int authorityLevel,
      String passwordHash,
      String firstName,
      String lastName,
      String position,
      String phoneNumber) {
    this.accountID = accountID; // AccountsManager.generateRandom8DigitID();;
    this.employeeID = employeeID;
    this.authorityLevel = authorityLevel;
    this.passwordHash = passwordHash;
    this.firstName = firstName;
    this.lastName = lastName;
    this.position = position;
    this.phoneNumber = phoneNumber;
  }

  /** generally for testing purposes */
  public Account() {
    this.accountID = "00000000";
    this.employeeID = "00000000";
    this.authorityLevel = 0;
    this.passwordHash = "00000000";
    this.firstName = "defaultFirstName";
    this.lastName = "defaultLastName";
    this.position = "defaultPosition";
    this.phoneNumber = "0000000000";
  }

  /**
   * temporary function? in future we should check for current user permissions to set their own
   * authority higher
   *
   * @param level
   */
  public void setAuthorityLevel(int level) {
    if (level > 1) this.authorityLevel = 2; // admin
    if (level == 1) this.authorityLevel = 1; // staff
    if (level < 1) this.authorityLevel = 0; // basic user
  }

  public int getAuthorityLevel() {
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

  public String getPasswordHash() {
    return this.passwordHash;
  }

  public String getEmployeeID() {
    return employeeID;
  }

  public void setPasswordHash(String newPasswordHash) {
    passwordHash = newPasswordHash;
  }

    public String getPhoneNumber() { return this.phoneNumber; }

}
