package com.sctech.emailapp.controller;

import com.sctech.emailapp.dto.CommonResposeDto;
import com.sctech.emailapp.dto.CompanyRequestDto;
import com.sctech.emailapp.model.Company;
import com.sctech.emailapp.service.CompanyService;
import com.sctech.emailapp.util.EmailResponseBuilder;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/company")
@RestController
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private EmailResponseBuilder emailResponseBuilder;

    @GetMapping
    public ResponseEntity<CommonResposeDto> getAll() {
        List<Company> companies = companyService.getAll();
        CommonResposeDto commonResposeDto = emailResponseBuilder.commonResponse("success",companies);
        return ResponseEntity.ok(commonResposeDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResposeDto> getDetail(@PathVariable("id") String companyId){
        Company company = companyService.getDetail(companyId);

        ArrayList<Company> arrayList = new ArrayList<>();
        arrayList.add(company);
        CommonResposeDto commonResposeDto = emailResponseBuilder.commonResponse("success",arrayList);
        return new ResponseEntity<>(commonResposeDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CommonResposeDto> create(@RequestBody @Valid CompanyRequestDto request){
        Company company = companyService.create(request);
        ArrayList<Company> arrayList = new ArrayList<>();
        arrayList.add(company);
        CommonResposeDto commonResposeDto = emailResponseBuilder.commonResponse("success",arrayList);
        return new ResponseEntity<>(commonResposeDto,HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<CommonResposeDto> update(@PathVariable("id") String companyId, @RequestBody @Valid CompanyRequestDto request){
        Company company = companyService.update(companyId, request);
        ArrayList<Company> arrayList = new ArrayList<>();
        arrayList.add(company);
        CommonResposeDto commonResposeDto = emailResponseBuilder.commonResponse("success",arrayList);
        return new ResponseEntity<>(commonResposeDto,HttpStatus.OK);
    }

    @PostMapping("/credits/{id}")
    public ResponseEntity<CommonResposeDto> credits(@PathVariable("id") String companyId, @RequestBody @Valid CompanyRequestDto request){
        Company company = companyService.credits(companyId, request);
        ArrayList<Company> arrayList = new ArrayList<>();
        arrayList.add(company);
        CommonResposeDto commonResposeDto = emailResponseBuilder.commonResponse("success",arrayList);
        return new ResponseEntity<>(commonResposeDto,HttpStatus.OK);
    }

}
