package com.sctech.emailapp.controller;

import com.sctech.emailapp.dto.BaseRequestDto;
import com.sctech.emailapp.dto.CommonResposeDto;
import com.sctech.emailapp.dto.DomainRequestDto;
import com.sctech.emailapp.model.Domain;
import com.sctech.emailapp.service.DomainService;
import com.sctech.emailapp.util.EmailResponseBuilder;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/setting/domain")
@RestController
public class DomainController {

    @Autowired
    private DomainService domainService;

    @Autowired
    private EmailResponseBuilder emailResponseBuilder;

    @PostMapping
    public ResponseEntity<CommonResposeDto> create(@RequestBody @Valid DomainRequestDto domainRequestDto){
        Domain domain = domainService.create(domainRequestDto);
        ArrayList<Domain> arrayList = new ArrayList<>();
        arrayList.add(domain);
        CommonResposeDto commonResposeDto = emailResponseBuilder.commonResponse("success",arrayList);
        return new ResponseEntity<>(commonResposeDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<CommonResposeDto> getAll(){
        List<Domain> domains = domainService.getAll();
        CommonResposeDto commonResposeDto = emailResponseBuilder.commonResponse("success",domains);
        return new ResponseEntity<>(commonResposeDto, HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<CommonResposeDto> getDetail(@PathVariable("id") String domainId, @RequestBody @Valid BaseRequestDto requestDto){
        Domain domain = domainService.getDetail(requestDto.getCompanyId(), domainId);

        ArrayList<Domain> arrayList = new ArrayList<>();
        arrayList.add(domain);
        CommonResposeDto commonResposeDto = emailResponseBuilder.commonResponse("success",arrayList);
        return new ResponseEntity<>(commonResposeDto,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResposeDto> update(@PathVariable("id") String domainId, @RequestBody @Valid DomainRequestDto domainRequestDto) {
        Domain domain = domainService.update(domainId, domainRequestDto);
        ArrayList<Domain> arrayList = new ArrayList<>();
        arrayList.add(domain);
        CommonResposeDto commonResposeDto = emailResponseBuilder.commonResponse("success",arrayList);
        return  new ResponseEntity<>(commonResposeDto,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResposeDto> delete(@PathVariable("id") String domainId, @RequestBody @Valid BaseRequestDto requestDto) {
        domainService.delete(requestDto.getCompanyId(), domainId);
        CommonResposeDto commonResposeDto = emailResponseBuilder.commonResponse("success",null);
        return  new ResponseEntity<>(commonResposeDto,HttpStatus.OK);
    }

    @PostMapping("/verify/{id}")
    public ResponseEntity<CommonResposeDto> verify(@PathVariable("id") String domainId, @RequestBody @Valid DomainRequestDto domainRequestDto){
        Domain domain = domainService.verify(domainId, domainRequestDto);
        ArrayList<Domain> arrayList = new ArrayList<>();
        arrayList.add(domain);
        CommonResposeDto commonResposeDto = emailResponseBuilder.commonResponse("success",arrayList);
        return new ResponseEntity<>(commonResposeDto,HttpStatus.OK);
    }


}
