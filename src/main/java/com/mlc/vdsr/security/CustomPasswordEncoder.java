package com.mlc.vdsr.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.context.annotation.Bean;

/**
 * Custom password encoder to encode passwords.
 */
public class CustomPasswordEncoder implements PasswordEncoder {

    /**
     * Encodes a given password using SHA-256.
     *
     * @param rawPassword is the initial password.
     *
     * @return encoded password.
     */
    @Override
    public String encode(CharSequence rawPassword) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(rawPassword.toString().getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error encoding password", e);
        }
    }

    /**
     * Checks if a password matches the encoded version of another password.
     *
     * @param rawPassword is the initial password.
     * @param encodedPassword is the encoded password.
     *
     * @return <code>true</code> if password matches and <code>false</code> otherwise.
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
    }
}
