package com.sctech.emailapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sctech.emailapp.enums.CompanyType;
import com.sctech.emailapp.enums.EmailContentType;
import com.sctech.emailapp.enums.EntityStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Document(collection = "emailTemplates")
public class Template {
    @Id
    private String id;
    private String companyId;
    private Integer templateId;
    private String name;
    private String content;
    private EmailContentType contentType;
    private Boolean attachmentRequired;
    private String[] tags;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
