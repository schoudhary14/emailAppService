package com.sctech.emailapp.service;

import com.sctech.emailapp.dto.TemplateRequestDto;
import com.sctech.emailapp.exceptions.AlreadyExistsException;
import com.sctech.emailapp.exceptions.NotExistsException;
import com.sctech.emailapp.model.Template;
import com.sctech.emailapp.repository.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TemplateService {

    @Autowired
    private TemplateRepository templateRepository;

    public List<Template> getAll(){
        return new ArrayList<>(templateRepository.findAll());
    }

    public Template getDetail(String templateId){
        Optional<Template> template = templateRepository.findById(templateId);
        if (template.isEmpty()) {
            throw new NotExistsException();
        }
        return template.get();
    }

    public Template getTemplateNameDetails(String companyId, Integer customTemplateId){
        System.out.println("findByApiKeyId : " + companyId + " | " + customTemplateId);
        Template template = templateRepository.findByTemplateId(customTemplateId);

        if (template == null) {
            throw new NotExistsException();
        }
        return template;
    }

    public Template save(Template company){
        return templateRepository.save(company);
    }


    public Template create(TemplateRequestDto input){
        if(templateRepository.findByTemplateId(input.getTemplateId()) != null){
            throw new AlreadyExistsException();
        }

        Template template = new Template();
        template.setCreatedAt(LocalDateTime.now());
        template.setTemplateId(input.getTemplateId());
        template.setName(input.getName());
        template.setContent(input.getContent());
        template.setContentType(input.getContentType());
        template.setAttachmentRequired(input.getAttachmentRequired());
        template.setTags(input.getTags());
        return templateRepository.save(template);
    }

    public Template update(String companyId, TemplateRequestDto input){
        Optional<Template> optionalTemplate = templateRepository.findById(companyId);
        if (optionalTemplate.isEmpty()) {
            throw new NotExistsException();
        }

        Template template = optionalTemplate.get();

        Boolean updated = false;
        if (!input.getTemplateId().equals(template.getTemplateId())){
            template.setTemplateId(input.getTemplateId());
            updated = true;
        }

        if(!input.getName().equals(template.getName())){
            template.setName(input.getName());
            updated = true;
        }

        if(!input.getContent().equals(template.getContent())){
            template.setContent(input.getContent());
            updated = true;
        }

        if(!input.getContentType().equals(template.getContentType())){
            template.setContentType(input.getContentType());
            updated = true;
        }

        if(!input.getAttachmentRequired().equals(template.getAttachmentRequired())){
            template.setAttachmentRequired(input.getAttachmentRequired());
            updated = true;
        }

        if (input.getTags().equals(template.getTags())) {
            template.setTags(input.getTags());
            updated = true;
        }

        return template;
    }

    public String delete(String templateId){
        Optional<Template> template = templateRepository.findById(templateId);
        if (template.isEmpty()) {
            throw new NotExistsException();
        }
        templateRepository.delete(template.get());
        return "Template Deleted";
    }

}
