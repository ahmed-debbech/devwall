package com.debbech.generator.logic.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Crypto {

    public static String generateSHA1(String input) {
        try {
            // Get an instance of the SHA-1 MessageDigest
            MessageDigest md = MessageDigest.getInstance("SHA-1");

            // Convert the input string to bytes
            byte[] inputBytes = input.getBytes();

            // Compute the SHA-1 hash
            byte[] hashBytes = md.digest(inputBytes);

            // Convert the byte array to a hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
