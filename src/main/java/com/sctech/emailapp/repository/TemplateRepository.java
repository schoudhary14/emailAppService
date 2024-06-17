package com.sctech.emailapp.repository;

import com.sctech.emailapp.model.Template;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TemplateRepository extends MongoRepository<Template, String> {
    Template findByTemplateId(Integer templateId);
}
