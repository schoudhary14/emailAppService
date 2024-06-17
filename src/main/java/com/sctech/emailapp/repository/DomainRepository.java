package com.sctech.emailapp.repository;

import com.sctech.emailapp.enums.DomainType;
import com.sctech.emailapp.model.Domain;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface DomainRepository extends MongoRepository<Domain, String> {
    void deleteByIdAndCompanyId(String companyId, String domainId);

    Optional<Domain> findByName(String name);
    Optional<Domain> findByIdAndCompanyId(String domainId, String companyId);
    Optional<Domain> findByNameAndCompanyId(String name, String companyId);
    List<Domain> findByType(DomainType type);
}
