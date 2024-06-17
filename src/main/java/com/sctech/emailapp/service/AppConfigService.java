package com.sctech.emailapp.service;

import com.sctech.emailapp.enums.*;
import com.sctech.emailapp.model.Company;
import com.sctech.emailapp.util.EnumHelper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AppConfigService {

    public Map<String, List<String>> getAppConfig() {
        Map<String, List<String>> appConfig = new HashMap<>();

        // Convert enum values to a list of strings
        appConfig.put("domainType", EnumHelper.toStringList(DomainType.class));
        appConfig.put("companyType", EnumHelper.toStringList(CompanyType.class));
        appConfig.put("domainStatus", EnumHelper.toStringList(DomainStatus.class));
        appConfig.put("EmailContentType", EnumHelper.toStringList(EmailContentType.class));
        appConfig.put("companyStatus", EnumHelper.toStringList(EntityStatus.class));
        appConfig.put("fileStatus", EnumHelper.toStringList(FileStatus.class));
        appConfig.put("roles", EnumHelper.toStringList(Role.class));

        return appConfig;
    }
}
