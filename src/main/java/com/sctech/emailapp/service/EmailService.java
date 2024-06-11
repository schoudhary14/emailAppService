package com.sctech.emailapp.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("${email.service.api.endpoint}")
    private String API_BASE_URL;

    @Value("${email.service.api.key}")
    private String API_AUTH_KEY;

    public void sendEmail(String to, String message) {
        System.out.println("Your OTP is: " + message);
    }
}