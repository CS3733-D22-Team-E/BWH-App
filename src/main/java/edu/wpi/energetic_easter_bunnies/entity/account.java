package edu.wpi.energetic_easter_bunnies.entity;

public class account {
  private int
      authorityLevel; // thinking 0 - 3 of getting more access in the program the greater you go
  private String accountID; // staff assignee
  private String firstName;
  private String lastName;
  private String position;
  private String location; // if not "live tracked", this could be their office etc

  public account(
      String accountID,
      Integer authorityLevel,
      String firstName,
      String lastName,
      String position,
      String location) {
    this.accountID = accountID;
    this.authorityLevel = authorityLevel;
    this.firstName = firstName;
    this.lastName = lastName;
    this.position = position;
    this.location = location;
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
