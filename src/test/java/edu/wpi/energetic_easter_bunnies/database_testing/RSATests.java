package edu.wpi.energetic_easter_bunnies.database_testing;

import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.energetic_easter_bunnies.RSAEncryption;
import java.io.*;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import org.junit.jupiter.api.Test;

public class RSATests {

  @Test
  public void testRSA() {
    BufferedReader reader;
    try {
      reader = new BufferedReader(new FileReader("rsa/test/testPasswordPLAIN.txt"));
      String line = reader.readLine();
      int c = 0;
      String[] plaintext = new String[3];
      while (line != null) {
        plaintext[c] = line;
        c++;
        // read next line
        line = reader.readLine();
      }
      reader.close();

      reader = new BufferedReader(new FileReader("rsa/test/testPasswordHASH.txt"));
      line = reader.readLine();
      c = 0;
      String[] hashtext = new String[3];
      while (line != null) {
        hashtext[c] = line;
        c++;
        // read next line
        line = reader.readLine();
      }
      reader.close();

      for (int i = 0; i < 3; i++) {
        assertTrue(RSAEncryption.validatePassword(plaintext[i], hashtext[i]));
        if (i != 0) assertFalse(RSAEncryption.validatePassword(plaintext[0], hashtext[i]));
        if (i != 1) assertFalse(RSAEncryption.validatePassword(plaintext[1], hashtext[i]));
        if (i != 2) assertFalse(RSAEncryption.validatePassword(plaintext[2], hashtext[i]));
      }

    } catch (IOException | IllegalBlockSizeException | BadPaddingException e) {
      e.printStackTrace();
    }
  }
}
