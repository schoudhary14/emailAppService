package com.sctech.emailapp.service;

import com.sctech.emailapp.dto.CompanyRequestDto;
import com.sctech.emailapp.exceptions.NotExistsException;
import com.sctech.emailapp.model.Company;
import com.sctech.emailapp.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public List<Company> getAll(){
        return new ArrayList<>(companyRepository.findAll());
    }

    public Company getDetail(String companyId){
        Optional<Company> company = companyRepository.findById(companyId);
        if (company.isEmpty()) {
            throw new NotExistsException();
        }
        return company.get();
    }

    public Company getApiKeyDetails(String companyId, String apiId){
        System.out.println("findByApiKeyId : " + companyId + " | " + apiId);
        Optional<Company> companies = companyRepository.findByApiKeyId(companyId, apiId);

        if (companies.isEmpty()) {
            throw new NotExistsException();
        }
        return companies.get();
    }

    public Optional<Company> getApiKeyDetailsByKey(String companyId, String apiKey){
        return companyRepository.findByApiKeyKey(companyId, apiKey);
    }

    public Company save(Company company){
        return companyRepository.save(company);
    }


    public Company create(CompanyRequestDto input){
        Company company = new Company();
        company.setName(input.getName());
        company.setEmail(input.getEmail());
        company.setStatus(input.getStatus());
        company.setCreatedAt(LocalDateTime.now());
        company.setAlertLevel(input.getAlertLevel());
        company.setBillType(input.getBillType());
        company.setCreatedBy(input.getUserId());
        company.setCredits(input.getCredits());
        return companyRepository.save(company);
    }

    public Company update(String companyId, CompanyRequestDto input){
        Optional<Company> optionalCompany = companyRepository.findById(companyId);
        if (optionalCompany.isEmpty()) {
            throw new NotExistsException();
        }

        Company company = optionalCompany.get();
        Boolean updated = false;
        if(!input.getName().equals(company.getName())){
            company.setName(input.getName());
            updated = true;
            System.out.println("Name updated");
        }

        if(!input.getEmail().toLowerCase().equals(company.getEmail())){
            company.setEmail(input.getEmail());
            updated = true;
            System.out.println("Email updated");
        }

        if(!input.getAlertLevel().equals(company.getAlertLevel())){
            company.setAlertLevel(input.getAlertLevel());
            updated = true;
            System.out.println("Alert Level updated");
        }

        if(!input.getBillType().equals(company.getBillType())){
            company.setBillType(input.getBillType());
            updated = true;
            System.out.println("Bill Type updated");
        }

        if(!input.getStatus().equals(company.getStatus())){
            company.setStatus(input.getStatus());
            updated = true;
            System.out.println("Status updated");
        }

        if(updated){
            company.setUpdatedAt(LocalDateTime.now());
            company.setUpdatedBy(input.getUserId());
            companyRepository.save(company);
        }

        return company;

    }

    public Company credits(String companyId, CompanyRequestDto input){
        Optional<Company> optionalCompany = companyRepository.findById(companyId);
        if (optionalCompany.isEmpty()) {
            throw new NotExistsException();
        }
        Company company = optionalCompany.get();
        if(company.getCredits() == null){
            company.setCredits(0L);
        }
        company.setCredits(company.getCredits() + (input.getCredits()));
        return companyRepository.save(company);
    }

}
