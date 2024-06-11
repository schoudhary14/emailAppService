package com.sctech.emailapp.util;

import com.sctech.emailapp.dto.CommonResposeDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class EmailResponseBuilder {

    public CommonResposeDto commonResponse(String message, List<?> data){
        CommonResposeDto commonResposeDto = new CommonResposeDto();
        commonResposeDto.setStatusCode(HttpStatus.OK.value());
        commonResposeDto.setMessage(message);
        commonResposeDto.setTimestamp(LocalDateTime.now());

        if(data != null && !data.isEmpty()){
            commonResposeDto.setData(data);
        }
        return commonResposeDto;
    }
}
