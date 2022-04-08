package edu.wpi.energetic_easter_bunnies.entity.accounts;

public class adminAccount extends account {

  public adminAccount() {
    super();
  }

  @Override
  public int getAuthorityLevel() {
    return 5; // 0-5?
  }
}
