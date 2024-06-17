package com.sctech.emailapp.controller;

import com.sctech.emailapp.dto.CommonResposeDto;
import com.sctech.emailapp.dto.TemplateRequestDto;
import com.sctech.emailapp.model.Template;
import com.sctech.emailapp.service.TemplateService;
import com.sctech.emailapp.util.EmailResponseBuilder;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/template")
@RestController
public class TemplateController {

    @Autowired
    private TemplateService templateService;

    @Autowired
    private EmailResponseBuilder emailResponseBuilder;

    @GetMapping
    public ResponseEntity<CommonResposeDto> getAll() {
        List<Template> templates = templateService.getAll();
        CommonResposeDto commonResposeDto = emailResponseBuilder.commonResponse("success",templates);
        return ResponseEntity.ok(commonResposeDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResposeDto> getDetail(@PathVariable("id") String templateId){
        Template template = templateService.getDetail(templateId);

        ArrayList<Template> arrayList = new ArrayList<>();
        arrayList.add(template);
        CommonResposeDto commonResposeDto = emailResponseBuilder.commonResponse("success",arrayList);
        return new ResponseEntity<>(commonResposeDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CommonResposeDto> create(@RequestBody @Valid TemplateRequestDto request){
        Template template = templateService.create(request);
        ArrayList<Template> arrayList = new ArrayList<>();
        arrayList.add(template);
        CommonResposeDto commonResposeDto = emailResponseBuilder.commonResponse("success",arrayList);
        return new ResponseEntity<>(commonResposeDto,HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<CommonResposeDto> update(@PathVariable("id") String templateId, @RequestBody @Valid TemplateRequestDto request){
        Template template = templateService.update(templateId, request);
        ArrayList<Template> arrayList = new ArrayList<>();
        arrayList.add(template);
        CommonResposeDto commonResposeDto = emailResponseBuilder.commonResponse("success",arrayList);
        return new ResponseEntity<>(commonResposeDto,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResposeDto> delete(@PathVariable("id") String templateId){
        String resp = templateService.delete(templateId);
        return new ResponseEntity<>(emailResponseBuilder.commonResponse(resp,null),HttpStatus.OK);
    }

}
