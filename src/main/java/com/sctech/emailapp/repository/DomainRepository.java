package com.sctech.emailapp.repository;

import com.sctech.emailapp.model.Domain;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface DomainRepository extends MongoRepository<Domain, String> {
    void deleteByIdAndCompanyId(String companyId, String domainId);

    Optional<Domain> findByName(String name);
    Optional<Domain> findByIdAndCompanyId(String domainId, String companyId);
    Optional<Domain> findByNameAndCompanyId(String name, String companyId);
}
