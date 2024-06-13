package com.sctech.emailapp.service;

import com.sctech.emailapp.dto.DomainRequestDto;
import com.sctech.emailapp.enums.DomainStatus;
import com.sctech.emailapp.exceptions.AlreadyExistsException;
import com.sctech.emailapp.exceptions.NotExistsException;
import com.sctech.emailapp.model.Domain;
import com.sctech.emailapp.repository.DomainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DomainService {

    @Autowired
    private DomainRepository domainRepository;

    public List<Domain> getAll(){
        return domainRepository.findAll();
    }

    public Domain getDetail(String companyId, String domainId){
        Optional<Domain> optionalDomain = domainRepository.findById(domainId);
        if(optionalDomain.isEmpty()){
            throw new NotExistsException();
        }
        return optionalDomain.get();
    }

    public Domain create(DomainRequestDto domainRequestDto){
        Optional<Domain> optionalDomain = domainRepository.findByNameAndCompanyId(domainRequestDto.getName(), domainRequestDto.getCompanyId());
        if(optionalDomain.isPresent()){
            throw new AlreadyExistsException();
        }

        Domain domain = new Domain();
        domain.setName(domainRequestDto.getName());
        domain.setDkim(domainRequestDto.getDkim());
        domain.setType(domainRequestDto.getType());
        domain.setStatus(DomainStatus.PENDING);
        domain.setCompanyId(domainRequestDto.getCompanyId());
        domain.setDkimPrivateKey(domainRequestDto.getDkimPrivateKey());
        domain.setDkimPublicKey(domainRequestDto.getDkimPublicKey());
        domain.setCreatedAt(LocalDateTime.now());
        domain.setCreatedBy(domainRequestDto.getUserId());
        return domainRepository.save(domain);
    }

    public Domain update(String domainId, DomainRequestDto domainRequestDto){
        Optional<Domain> optionalDomain = domainRepository.findById(domainId);
        if(optionalDomain.isEmpty()){
            throw new NotExistsException();
        }

        Domain domain = optionalDomain.get();
        Boolean updated = false;

        if(domainRequestDto.getDkim().equals(domain.getDkim())) {
            domain.setDkim(domainRequestDto.getDkim());
            updated = true;
        }

        if(domainRequestDto.getType().equals(domain.getType())) {
            domain.setType(domainRequestDto.getType());
            updated = true;
        }

        if(domainRequestDto.getStatus().equals(domain.getStatus())) {
            domain.setStatus(domainRequestDto.getStatus());
            updated = true;
        }

        if(domainRequestDto.getCompanyId().equals(domain.getCompanyId())) {
            domain.setCompanyId(domainRequestDto.getCompanyId());
            updated = true;
        }

        if(domainRequestDto.getDkimPrivateKey().equals(domain.getDkimPrivateKey())) {
            domain.setDkimPrivateKey(domainRequestDto.getDkimPrivateKey());
            updated = true;
        }

        if(domainRequestDto.getDkimPublicKey().equals(domain.getDkimPublicKey())) {
            domain.setDkimPublicKey(domainRequestDto.getDkimPublicKey());
            updated = true;
        }

        if(updated){
            domain.setCreatedAt(LocalDateTime.now());
            domain.setCreatedBy(domainRequestDto.getUserId());
            domainRepository.save(domain);
        }
        return domain;
    }

    public Domain verify(String domainId, DomainRequestDto domainRequestDto){
        Optional<Domain> optionalDomain = domainRepository.findById(domainId);
        if(optionalDomain.isEmpty()){
            throw new NotExistsException();
        }
        Domain domain = optionalDomain.get();
        domain.setStatus(DomainStatus.VERIFIED);
        return domainRepository.save(domain);
    }

    public void delete(String companyId, String domainId){
        Optional<Domain> optionalDomain = domainRepository.findById(domainId);
        if(optionalDomain.isEmpty()){
            throw new NotExistsException();
        }
        domainRepository.deleteByIdAndCompanyId(domainId, companyId);
    }


}
