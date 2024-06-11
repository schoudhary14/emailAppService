package com.sctech.emailapp.repository;

import com.sctech.emailapp.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {
}