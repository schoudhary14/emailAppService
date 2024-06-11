package com.sctech.emailapp.model;

import com.sctech.emailapp.enums.EmailDataStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;


@Data
@Document(collection = "emailData")
public class EmailData {

    @Id
    private String id;
    private String fromName;
    private String from;
    private String replyTo;
    private String to;
    private String[] bcc;
    private String[] cc;
    private String subject;
    private String content;
    private String type;
    private List<Attachment> attachment;
    private EmailDataStatus status;
    private TrackingFlags trackingFlags;
    private String clientChannelId;
    private String requestMode;
    private String vendorResponseId;
    private String callbackStatus;
    private String sourceId;

    public static class TrackingFlags {
        private Boolean open;
        private Boolean links;
    }

    public static class Attachment{
        private String filename;
        private String content;
        private String contentType;

    }

}
