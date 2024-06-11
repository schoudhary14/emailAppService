package com.sctech.emailapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sctech.emailapp.enums.CompanyType;
import com.sctech.emailapp.enums.EntityStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Document(collection = "emailCompanyDetails")
public class Company {
    @Id
    private String id;
    private String name;
    private String email;
    private EntityStatus status;
    private CompanyType billType;
    private Long credits;
    private Integer alertLevel;
    @JsonIgnore
    private List<ApiKey> apiKeys;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;

    public void addApiKeys(ApiKey apiKey) {
        if (apiKey != null) {
            if (apiKeys == null) {
                apiKeys = new ArrayList<>();
            }

            if (apiKey.getApiId() == null || apiKey.getApiId().isEmpty()) {
                apiKey.setApiId(UUID.randomUUID().toString());
            }

            for(int i = 0; i < this.apiKeys.size();i++){
                if(this.apiKeys.get(i).getApiId().equals(apiKey.getApiId())){
                    this.apiKeys.remove(i);
                }
            }

            this.apiKeys.add(apiKey);
        }
    }

    @Data
    public static class ApiKey {
        private String apiId;
        private String companyId;
        private String name;
        private String key;
        private EntityStatus status;
        private String[] ipAddress;
        private String createdBy;
        private LocalDateTime createdAt;
        private String updatedBy;
        private LocalDateTime updatedAt;
    }

}
