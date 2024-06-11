package com.sctech.emailapp.model;

import com.sctech.emailapp.enums.FileStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@Document(collection = "fileDetail")
public class FileDetail {
    @Id
    private String id;
    private String companyId;
    private String originalFileName;
    private String fileName;
    private String[] tags;
    private FileStatus status;
    private FileMetaInfo fileMetaInfo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;

    @Data
    public static class FileMetaInfo{
        private String filePath;
        private Integer rowCount;
        private Integer invalidCount;
        private Integer validCount;
    }
}
