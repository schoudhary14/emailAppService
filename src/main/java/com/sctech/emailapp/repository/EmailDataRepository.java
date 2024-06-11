package com.sctech.emailapp.repository;

import com.sctech.emailapp.model.EmailData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface EmailDataRepository  extends MongoRepository<EmailData, String> {

    Optional<EmailData> findBySourceId(String fileId);
}
