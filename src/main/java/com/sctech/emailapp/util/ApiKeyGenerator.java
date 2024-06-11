package com.sctech.emailapp.util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

@Component
public class ApiKeyGenerator {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom SECURE_RANDOM = createSecureRandom();

    private static SecureRandom createSecureRandom() {
        try {
            return SecureRandom.getInstance("SHA1PRNG", "SUN");
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new RuntimeException("SecureRandom instance not available", e);
        }
    }

    public static String generateApiKey(int length) {
        if (length < 32 || length > 64) {
            throw new IllegalArgumentException("API key length must be between 32 and 64 characters");
        }

        StringBuilder apiKey = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            apiKey.append(CHARACTERS.charAt(SECURE_RANDOM.nextInt(CHARACTERS.length())));
        }

        return apiKey.toString();
    }
}