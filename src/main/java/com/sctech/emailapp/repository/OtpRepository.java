package com.sctech.emailapp.repository;

import com.sctech.emailapp.model.Otp;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface OtpRepository extends MongoRepository<Otp, String> {
    Optional<Otp> findByEmail(String username);
}
