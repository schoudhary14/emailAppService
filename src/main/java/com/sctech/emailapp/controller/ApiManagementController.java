package com.sctech.emailapp.controller;

import com.sctech.emailapp.dto.ApiKeyRequestDto;
import com.sctech.emailapp.dto.BaseRequestDto;
import com.sctech.emailapp.dto.CommonResposeDto;
import com.sctech.emailapp.model.Company;
import com.sctech.emailapp.service.ApiKeyService;
import com.sctech.emailapp.util.EmailResponseBuilder;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/setting/development")
@RestController
public class ApiManagementController {

    @Autowired
    private ApiKeyService apiKeyService;

    @Autowired
    private EmailResponseBuilder emailResponseBuilder;

    @GetMapping
    public ResponseEntity<CommonResposeDto> getAll(){
        List<Company.ApiKey> apiKeys = apiKeyService.getAll();
        CommonResposeDto commonResposeDto = emailResponseBuilder.commonResponse("success",apiKeys);
        return new ResponseEntity<>(commonResposeDto, HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<CommonResposeDto> getDetail(@PathVariable("id") String apiId, @RequestBody @Valid BaseRequestDto requestDto){
        Company.ApiKey apiKeys = apiKeyService.getDetail(requestDto.getCompanyId(),apiId);

        ArrayList<Company.ApiKey> arrayList = new ArrayList<>();
        arrayList.add(apiKeys);
        CommonResposeDto commonResposeDto = emailResponseBuilder.commonResponse("success",arrayList);
        return new ResponseEntity<>(commonResposeDto,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CommonResposeDto> create(@RequestBody @Valid ApiKeyRequestDto request){
        Company.ApiKey apiKeys = apiKeyService.generate(request);
        ArrayList<Company.ApiKey> arrayList = new ArrayList<>();
        arrayList.add(apiKeys);
        CommonResposeDto commonResposeDto = emailResponseBuilder.commonResponse("success",arrayList);
        return new ResponseEntity<>(commonResposeDto,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResposeDto> update(@PathVariable("id") String apiId, @RequestBody @Valid ApiKeyRequestDto apiKeyRequestDto) {
        Company.ApiKey apiKey = apiKeyService.update(apiId, apiKeyRequestDto);
        ArrayList<Company.ApiKey> arrayList = new ArrayList<>();
        arrayList.add(apiKey);
        CommonResposeDto commonResposeDto = emailResponseBuilder.commonResponse("success",arrayList);
        return ResponseEntity.ok(commonResposeDto);
    }

}
