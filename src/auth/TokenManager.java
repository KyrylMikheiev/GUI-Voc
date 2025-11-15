package src.auth;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class TokenManager {
    private static final String filename = "token.txt";
    private static final String secretKey = "YourSecretKey123"; // Change this to your secret key

    public static void saveToken(String token) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] encryptedToken = cipher.doFinal(token.getBytes(StandardCharsets.UTF_8));

            try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filename))) {
                outputStream.write(encryptedToken);
                System.out.println("Token saved successfully.");
            }
        } catch (Exception e) {
            System.out.println("Error saving token: " + e.getMessage());
        }
    }

    public static String loadToken() {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

            byte[] encryptedToken;
            try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(filename))) {
                encryptedToken = inputStream.readAllBytes();
            }

            byte[] decryptedBytes = cipher.doFinal(encryptedToken);
            String token = new String(decryptedBytes, StandardCharsets.UTF_8);
            System.out.println("Token loaded successfully.");
            return token;
        } catch (Exception e) {
            System.out.println("Error loading token: " + e.getMessage());
            return null;
        }
    }

}
