package com.sctech.emailapp.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommonResposeDto {
    private Integer statusCode;
    private String requestId;
    private LocalDateTime timestamp;
    private String message;
    private List<?> data;
}
