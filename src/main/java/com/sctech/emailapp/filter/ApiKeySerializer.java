package com.sctech.emailapp.filter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class ApiKeySerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String apiKey, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
        jsonGenerator.writeString(maskApiKey(apiKey));
    }

    private String maskApiKey(String apiKey) {
        int unmaskedLength = 6;  // Show the first and last 3 characters
        if (apiKey.length() <= unmaskedLength) {
            return apiKey;  // If API key is too short, don't mask it
        }
        StringBuilder maskedApiKey = new StringBuilder(apiKey.length());
        maskedApiKey.append(apiKey.substring(0, 3)); // first 3 characters
            maskedApiKey.append('*');
        for (int i = 3; i < apiKey.length() - 3; i++) {
        }
        maskedApiKey.append(apiKey.substring(apiKey.length() - 3)); // last 3 characters
        return maskedApiKey.toString();
    }
}