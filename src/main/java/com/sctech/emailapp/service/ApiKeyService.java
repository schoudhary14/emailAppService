package com.sctech.emailapp.service;

import com.sctech.emailapp.dto.ApiKeyRequestDto;
import com.sctech.emailapp.model.Company;
import com.sctech.emailapp.util.ApiKeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ApiKeyService {

    @Autowired
    private CompanyService companyService;

    public List<Company.ApiKey> getAll(){
        ArrayList<Company.ApiKey> apiKeys = new ArrayList<>();
        for(Company company : companyService.getAll()){
            if(company.getApiKeys() == null){
                continue;
            }
            for(Company.ApiKey apiKey : company.getApiKeys()) {
                apiKey.setKey(apiKey.getKey().substring(0, 3) + "**************************" + apiKey.getKey().substring(apiKey.getKey().length() - 3));
                apiKeys.add(apiKey);
            }
        }
        System.out.println("Api Keys : " + apiKeys);
        return apiKeys;
    }

    public Company.ApiKey getDetail(String companyId ,String apiId){
        for(Company.ApiKey apiKey : companyService.getApiKeyDetails(companyId, apiId).getApiKeys()){
            if(apiKey.getApiId().equals(apiId)){
                apiKey.setKey(apiKey.getKey().substring(0, 3) + "**************************" + apiKey.getKey().substring(apiKey.getKey().length() - 3));
                return apiKey;
            }
        }
        return null;
    }

    public Company.ApiKey generate(ApiKeyRequestDto input) {
        Company company = companyService.getDetail(input.getCompanyId());
        String apiKeyValue = ApiKeyGenerator.generateApiKey(32);

        while (companyService.getApiKeyDetailsByKey(input.getCompanyId(), apiKeyValue).isPresent()){
            apiKeyValue = ApiKeyGenerator.generateApiKey(32);
        }

        Company.ApiKey apiKey = new Company.ApiKey();
        apiKey.setCompanyId(input.getCompanyId());
        apiKey.setName(input.getName());
        apiKey.setKey(apiKeyValue);
        apiKey.setIpAddress(input.getIpAddress());
        apiKey.setStatus(input.getStatus());
        apiKey.setCreatedBy(input.getUserId());
        apiKey.setCreatedAt(LocalDateTime.now());

        company.addApiKeys(apiKey);
        companyService.save(company);
        return apiKey;
    }

    public Company.ApiKey update(String apiId,ApiKeyRequestDto input) {
        Company company = companyService.getApiKeyDetails(input.getCompanyId(), apiId);
        for(Company.ApiKey apiKey : company.getApiKeys()){
            if(apiKey.getApiId().equals(apiId)){

                boolean updated = false;

                if(!input.getCompanyId().equals(apiKey.getCompanyId())){
                    apiKey.setCompanyId(input.getCompanyId());
                    updated = true;
                    System.out.println("Entity updated for id : " + apiKey.getApiId());
                }

                if (!input.getName().equals(apiKey.getName())){
                    apiKey.setName(input.getName());
                    updated = true;
                    System.out.println("Name updated for Id : " + apiKey.getApiId());
                }

                if (!input.getStatus().equals(apiKey.getStatus())){
                    apiKey.setStatus(input.getStatus());
                    updated = true;
                    System.out.println("Status updated for Id : " + apiKey.getApiId());
                }

                if(updated) {
                    apiKey.setUpdatedBy(input.getUserId());
                    apiKey.setUpdatedAt(LocalDateTime.now());
                    company.addApiKeys(apiKey);
                    companyService.save(company);
                }

                apiKey.setKey(apiKey.getKey().substring(0, 3) + "**************************" + apiKey.getKey().substring(apiKey.getKey().length() - 3));
                return apiKey;
            }
        }
        return null;
    }

}
