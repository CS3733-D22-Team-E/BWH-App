package edu.wpi.energetic_easter_bunnies.database_testing;

import static org.junit.jupiter.api.Assertions.assertTrue;
import edu.wpi.energetic_easter_bunnies.entity.accounts.*;
import edu.wpi.energetic_easter_bunnies.database.AccountsManager;
import java.security.NoSuchAlgorithmException;
import org.junit.jupiter.api.Test;

public class AccountsManagerTesting {
  AccountsManager AM;

  @Test
  public void testSHA256Func() throws NoSuchAlgorithmException {
    String plainText = "password";
    String SHA256hash = "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8";
    String testHash = AccountsManager.getSHA256(plainText);
    assertTrue(SHA256hash.equals(testHash));
  }

  @Test
  public void testRandIDGenerator() {
    account a = new adminAccount();
    assertTrue(a.getAuthorityLevel()==5);
    assertTrue(a.getAccountID().length()==8);
    assertTrue(a.getFirstName()=="first");//by default
  }
}
