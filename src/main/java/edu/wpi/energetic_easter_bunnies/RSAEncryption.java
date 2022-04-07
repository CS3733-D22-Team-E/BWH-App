package edu.wpi.energetic_easter_bunnies;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSAEncryption {
  private static KeyFactory kf;
  private static PrivateKey privateKey;
  private static PublicKey publicKey;
  private static Cipher encryptCipher;
  private static Cipher decryptCipher;

  static {
    try {
      kf = KeyFactory.getInstance("RSA");

      FileInputStream inputStream = new FileInputStream("rsa/public.key");

      byte[] publicKeyBytes = inputStream.readAllBytes();
      X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);

      publicKey = kf.generatePublic(publicKeySpec);

      inputStream = new FileInputStream("rsa/private.key");

      byte[] privateKeyBytes = inputStream.readAllBytes();
      PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);

      privateKey = kf.generatePrivate(privateKeySpec);

      encryptCipher = Cipher.getInstance("RSA");
      encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);

      decryptCipher = Cipher.getInstance("RSA");
      decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);

    } catch (NoSuchAlgorithmException
        | IOException
        | InvalidKeySpecException
        | NoSuchPaddingException
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
