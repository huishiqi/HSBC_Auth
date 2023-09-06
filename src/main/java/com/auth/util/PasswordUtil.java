package com.auth.util;

import java.security.MessageDigest;

public class PasswordUtil {

    private static MessageDigest digest;

    public static String encode(String password) throws Exception {
        digest = MessageDigest.getInstance("SHA-256");
        digest.update(password.getBytes());
        byte[] bytes = digest.digest();
        StringBuffer sb = new StringBuffer();
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(aByte & 0xFF);
            if (hex.length() < 2) {
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public static boolean passwordMatch(String password, String encodedPassword) throws Exception {
        return encodedPassword.equals(encode(password));
    }

}
