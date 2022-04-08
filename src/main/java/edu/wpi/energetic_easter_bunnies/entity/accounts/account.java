package edu.wpi.energetic_easter_bunnies.entity.accounts;

import edu.wpi.energetic_easter_bunnies.database.AccountsManager;
import edu.wpi.energetic_easter_bunnies.database.AccountsManager.*;

public class account {
  private int authorityLevel; // thinking 0 - 5 of getting more access the higher it is
  private String accountID; // staff assignee
  private String passwordHash;
  private String firstName;
  private String lastName;
  private String position;
  private String location; // if not "live tracked", this could be their office etc

  /**
   * realistically used to load in from a CSV of accounts
   *
   * @param accountID
   * @param authorityLevel
   * @param passwordHash
   * @param firstName
   * @param lastName
   * @param position
   * @param location
   */
  public account(
      String accountID,
      Integer authorityLevel,
      String passwordHash,
      String firstName,
      String lastName,
      String position,
      String location) {
    this.accountID = accountID;
    this.authorityLevel = authorityLevel;
    this.passwordHash = passwordHash;
    this.firstName = firstName;
    this.lastName = lastName;
    this.position = position;
    this.location = location;
  }

  public account() {
    this.accountID = AccountsManager.generateRandom8DigitID(); // 8 digits, 0-99,999,999
    this.authorityLevel = 0;
    this.passwordHash = "0"; // ?? (formatted to 8 digits)
    this.firstName = "First";
    this.lastName = "Last";
    this.position = "basicUser";
    this.location = "yourMom";
  }

  /**
   * set all attributes of an account (note not static)
   *
   * <p>FUNCTIONALITY: to leave a field unchanged, put an empty String ("") in the parameter -> this
   * also prevents empty or null strings from being manually set
   *
   * <p>for ease of setting attributes from fields in a UI
   *
   * @param accountID
   * @param authorityLevel
   * @param passwordHash
   * @param firstName
   * @param lastName
   * @param position
   * @param location
   * @return
   */
  public boolean setAttributes(
      String accountID,
      Integer authorityLevel,
      String passwordHash,
      String firstName,
      String lastName,
      String position,
      String location) { // "" for unchanged attributes
    if (!accountID.equals("")) this.accountID = accountID;
    if (!passwordHash.equals("")) this.passwordHash = passwordHash;
    if (!firstName.equals("")) this.firstName = firstName;
    if (!lastName.equals("")) this.lastName = lastName;
    if (!position.equals("")) this.position = position;
    if (!location.equals("")) this.location = location;

    return true;
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
}
