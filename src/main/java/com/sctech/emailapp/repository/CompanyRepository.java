package com.sctech.emailapp.repository;

import com.sctech.emailapp.model.Company;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface CompanyRepository extends MongoRepository<Company, String> {

    @Query("{'id' : ?0, 'apiKeys.apiId' : ?1 }")
    Optional<Company> findByApiKeyId(String companyId, String apiKeyId);

    @Query("{'id' : ?0, 'apiKeys.key' : ?1 }")
    Optional<Company> findByApiKeyKey(String companyId, String apiKey);

}
