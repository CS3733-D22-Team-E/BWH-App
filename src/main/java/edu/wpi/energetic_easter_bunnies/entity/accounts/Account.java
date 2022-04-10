package edu.wpi.energetic_easter_bunnies.entity.accounts;

public abstract class Account {

  private String accountID; // staff assignee
  private String employeeID; //if empty
  private int authorityLevel; // 0 basic user, 1 staff, 2... admin
  private String passwordHash;
  private String firstName;
  private String lastName;
  private String position;
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
   */
  public Account(
      String accountID,
      String employeeID,
      int authorityLevel,
      String passwordHash,
      String firstName,
      String lastName,
      String position) {
    this.accountID = accountID;//AccountsManager.generateRandom8DigitID();;
    this.employeeID = employeeID;
    this.authorityLevel = authorityLevel;
    this.passwordHash = passwordHash;
    this.firstName = firstName;
    this.lastName = lastName;
    this.position = position;
  }

  /**
   * generally for testing purposes
   */
  public Account() {
    this.accountID = "00000000";
    this.employeeID = "00000000";
    this.authorityLevel = 0;
    this.passwordHash = "00000000";
    this.firstName = "defaultFirstName";
    this.lastName = "defaultLastName";
    this.position = "defaultPosition";
  }

  /**
   * temporary function? in future we should check for current user permissions
   * to set their own authority higher
   * @param level
   */
  public void setAuthorityLevel(int level) {
    if (level > 1) this.authorityLevel = 2;//admin
    if (level == 1) this.authorityLevel = 1;//staff
    if (level < 1) this.authorityLevel = 0;//basic user
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

  private /* ? */ String getPasswordHash() {
    return this.passwordHash;
  }
}
