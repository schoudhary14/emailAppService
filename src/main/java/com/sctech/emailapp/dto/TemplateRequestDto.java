package com.sctech.emailapp.dto;

import com.sctech.emailapp.enums.EmailContentType;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
public class TemplateRequestDto  extends BaseRequestDto{
    @NonNull
    private Integer templateId;
    @NonNull
    private String name;
    @NonNull
    private String content;
    @NonNull
    private EmailContentType contentType;
    @NonNull
    private Boolean attachmentRequired;
    @NonNull
    private String[] tags;
}
