package com.sctech.emailapp.controller;

import com.sctech.emailapp.dto.CommonResposeDto;
import com.sctech.emailapp.service.AppConfigService;
import com.sctech.emailapp.util.EmailResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequestMapping("/setting/appconfig")
@RestController
public class AppConfigController {

    @Autowired
    private AppConfigService appConfigService;

    @Autowired
    private EmailResponseBuilder emailResponseBuilder;

    @GetMapping
    public ResponseEntity<CommonResposeDto> getAppConfig() {

        ArrayList<Map<String, List<String>>> arrayList = new ArrayList<>();
        arrayList.add(appConfigService.getAppConfig());
        CommonResposeDto commonResposeDto = emailResponseBuilder.commonResponse("success",arrayList);

        return new ResponseEntity<>(commonResposeDto, HttpStatus.OK);
    }


}
