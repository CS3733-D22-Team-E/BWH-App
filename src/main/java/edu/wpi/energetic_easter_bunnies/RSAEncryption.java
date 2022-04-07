package edu.wpi.energetic_easter_bunnies;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSAEncryption {
  private static KeyPairGenerator generator;
  private static KeyPair pair;
  private static PrivateKey privateKey;
  private static PublicKey publicKey;
  private static Cipher encryptCipher;
  private static Cipher decryptCipher;

  static {
    try {
      generator = KeyPairGenerator.getInstance("RSA");
      generator.initialize(2048);
      pair = generator.generateKeyPair();
      privateKey = pair.getPrivate();
      publicKey = pair.getPublic();

      InputStream stream = Main.class.getResourceAsStream("rsa/public.key");
      assert stream != null;
      byte[] publicKeyBytes = stream.readAllBytes();
      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
      keyFactory.generatePublic(publicKeySpec);

      stream = Main.class.getResourceAsStream("rsa/private.key");
      assert stream != null;
      byte[] privateKeyBytes = stream.readAllBytes();
      EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
      keyFactory.generatePrivate(privateKeySpec);

      encryptCipher = Cipher.getInstance("RSA");
      encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);

      decryptCipher = Cipher.getInstance("RSA");
      decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
    } catch (NoSuchPaddingException
        | NoSuchAlgorithmException
        | IOException
        | InvalidKeySpecException
        | InvalidKeyException e) {
      e.printStackTrace();
    }
  }

  public static String generatePasswordHASH(String password)
      throws IllegalBlockSizeException, BadPaddingException {

    byte[] secretMessageBytes = password.getBytes(StandardCharsets.UTF_8);

    byte[] e = encryptCipher.doFinal(secretMessageBytes);

    return Base64.getEncoder().encodeToString(e);
  }

  private static String decryptHASH(String hash)
      throws IllegalBlockSizeException, BadPaddingException {
    byte[] hashBytes = Base64.getDecoder().decode(hash);
    byte[] decryptedMessageBytes = decryptCipher.doFinal(hashBytes);
    return new String(decryptedMessageBytes, StandardCharsets.UTF_8);
  }

  public static boolean validatePassword(String enteredPassword, String userPassword)
      throws IllegalBlockSizeException, BadPaddingException {
    return enteredPassword.equals(decryptHASH(userPassword));
  }

  public static void main(String[] args)
      throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException,
          IOException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
    String e = generatePasswordHASH("password");
    System.out.println(validatePassword("password", e));
  }
}
