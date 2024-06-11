package com.sctech.emailapp.repository;

import com.sctech.emailapp.model.Domain;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DomainRepository extends MongoRepository<Domain, String> {
    void deleteByIdAndCompanyId(String companyId, String domainId);
}
