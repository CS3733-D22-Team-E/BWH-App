package edu.wpi.energetic_easter_bunnies.database_testing;

import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.wpi.energetic_easter_bunnies.database.DBCreation;
import edu.wpi.energetic_easter_bunnies.entity.accounts.Account;
import edu.wpi.energetic_easter_bunnies.entity.accounts.adminAccount;
import org.junit.jupiter.api.Test;

public class AccountsManagerTesting extends DBCreation {

  @Test
  public void testAccountCreation() {
    Account a;
    for (int i = 0; i < 20; i++) {
      a = new adminAccount();
      assertTrue(a.getAuthorityLevel() == 2);
      assertTrue(a.getAccountID().length() == 8);
      assertTrue(a.getFirstName() == "defaultFirstName"); // by default
    }
  }
}
