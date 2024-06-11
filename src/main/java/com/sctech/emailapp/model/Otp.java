package com.sctech.emailapp.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@Document(collection = "otps")
public class Otp {
    @Id
    private String id;
    private String otp;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime expiryDate;
    private boolean isValid;
}