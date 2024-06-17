package com.sctech.emailapp.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class FileRequestDto  extends BaseRequestDto{

    @NonNull
    private String fileName;

    @NonNull
    private Boolean clickTracking;

    @NonNull
    private Boolean openTracking;
    private String[] tags;

    @NonNull
    private SendingDetails sendingDetails;

    @Data
    public static class SendingDetails {
        private String fromName;
        private String fromEmail;
        private String replyTo;
        private String subject;
        private String templateId;
    }
}
