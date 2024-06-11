package com.sctech.emailapp.repository;

import com.sctech.emailapp.model.FileDetail;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FileDetailsRepository  extends MongoRepository<FileDetail, String> {
}
