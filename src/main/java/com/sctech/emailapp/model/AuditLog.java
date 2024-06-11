package com.sctech.emailapp.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "auditLogs")
public class AuditLog {
    @Id
    private String id;
    private String companyId;
    private String userId;
    private String eventType;
    private String description;
    private String level;
    private LocalDateTime eventTime;
}