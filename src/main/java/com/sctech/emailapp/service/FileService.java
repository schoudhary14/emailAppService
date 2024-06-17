package com.sctech.emailapp.service;

import com.opencsv.CSVReader;
import com.sctech.emailapp.dto.FileRequestDto;
import com.sctech.emailapp.enums.EmailDataStatus;
import com.sctech.emailapp.enums.FileAction;
import com.sctech.emailapp.enums.FileStatus;
import com.sctech.emailapp.kafka.KafkaProducer;
import com.sctech.emailapp.model.EmailData;
import com.sctech.emailapp.model.FileDetail;
import com.sctech.emailapp.repository.EmailDataRepository;
import com.sctech.emailapp.repository.FileDetailsRepository;
import com.sctech.emailapp.util.EmailAddressValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FileService {

    @Value("${email.app.file.upload.dir}")
    private String uploadDir;

    @Value("${email.app.file.batch.size}")
    private Integer fileBatchSize;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private FileDetailsRepository fileDetailsRepository;

    @Autowired
    private EmailDataRepository emailDataRepository;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private EmailAddressValidator emailAddressValidator;

    public List<FileDetail> getFileDetails(){
        return mongoTemplate.findAll(FileDetail.class);
    }

    public FileDetail fileUpload(MultipartFile file, FileRequestDto fileRequestDto){
        FileDetail fileDetail = new FileDetail();
        fileDetail.setStatus(FileStatus.LOADING);

        FileDetail.FileMetaInfo fileMetaInfo = new FileDetail.FileMetaInfo();
        fileMetaInfo.setRowCount(0);
        fileMetaInfo.setValidCount(0);
        fileMetaInfo.setInvalidCount(0);

        try {
            String originalFileName = file.getOriginalFilename();
            String fileName = UUID.randomUUID().toString();
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath);

            fileDetail.setOriginalFileName(originalFileName);
            fileDetail.setFileName(fileName);
            fileMetaInfo.setFilePath(filePath.toString());
            fileDetail.setCreatedAt(LocalDateTime.now());
            mongoTemplate.save(fileDetail,"fileDetail");

            String fileId = fileDetail.getId();

        } catch (Exception e) {
            fileDetail.setStatus(FileStatus.FAILED);
            System.out.println("failed : " + e.getMessage());
        }

        processFile(fileDetail);

        return fileDetail;
    }

    @Async
    private void processFile(FileDetail fileDetail) {
        if(fileDetail.getStatus().equals(FileStatus.KILLED)){
            return;
        }

        if(fileDetail.getStatus().equals(FileStatus.COMPLETE)){
            return;
        }

        Integer fileRowCounter = 0;
        Integer fileRowInvalidCounter = 0;
        Integer fileRowValidCounter = 0;

        try {
            CSVReader reader = new CSVReader(new FileReader(fileDetail.getFileMetaInfo().getFilePath()));
            String[] CurnextLine;
            List<EmailData> emailDataEntities = new ArrayList<>();
            while ((CurnextLine = reader.readNext()) != null) {
                EmailData request = new EmailData();
                request.setFromName(CurnextLine[0]);
                request.setFrom(CurnextLine[1]);
                request.setTo(CurnextLine[2]);
                request.setBcc(CurnextLine[3].split(";"));
                request.setCc(CurnextLine[4].split(";"));
                request.setSubject(CurnextLine[5]);
                request.setContent(CurnextLine[6]);
                request.setType(CurnextLine[7]);
                request.setSourceId(fileDetail.getId());

                if (EmailAddressValidator.isValidEmail(request.getTo())) {
                    request.setStatus(EmailDataStatus.VALID);
                    fileRowValidCounter = fileRowValidCounter + 1;
                } else {
                    request.setStatus(EmailDataStatus.INVALID);
                    fileRowInvalidCounter = fileRowInvalidCounter + 1;
                }

                emailDataEntities.add(request);
                //kafkaProducer.sendToQueue(request);

                fileRowCounter = fileRowCounter + 1;

                if (fileRowCounter.equals(fileBatchSize)) {
                    Optional<FileDetail> fileDetails = fileDetailsRepository.findById(fileDetail.getId());
                    fileDetail = fileDetails.get();

                    if (fileDetail.getStatus() == FileStatus.KILLED ) {
                        System.out.println("stopping file process for : " + fileDetail.getId());
                    }

                    mongoTemplate.save(emailDataEntities, "emailData");
                    fileDetail.getFileMetaInfo().setRowCount(fileDetail.getFileMetaInfo().getRowCount() + fileRowCounter);
                    fileDetail.getFileMetaInfo().setInvalidCount(fileDetail.getFileMetaInfo().getInvalidCount() + fileRowInvalidCounter);
                    fileDetail.getFileMetaInfo().setValidCount(fileDetail.getFileMetaInfo().getValidCount() + fileRowValidCounter);
                    mongoTemplate.save(fileDetail);

                    fileRowCounter = 0;
                    fileRowInvalidCounter = 0;
                    fileRowValidCounter = 0;
                    emailDataEntities.clear();
                }

            }

            if (!emailDataEntities.isEmpty()) {
                mongoTemplate.save(emailDataEntities, "emailData");
                fileRowCounter = 0;
                emailDataEntities.clear();
                fileDetail.getFileMetaInfo().setRowCount(fileDetail.getFileMetaInfo().getRowCount() + fileRowCounter);
                fileDetail.getFileMetaInfo().setInvalidCount(fileDetail.getFileMetaInfo().getInvalidCount() + fileRowInvalidCounter);
                fileDetail.getFileMetaInfo().setValidCount(fileDetail.getFileMetaInfo().getValidCount() + fileRowValidCounter);
            }

            fileDetail.setUpdatedAt(LocalDateTime.now());
            fileDetail.setStatus(FileStatus.LOADED);
            mongoTemplate.save(fileDetail);

        }catch (Exception e){
            fileDetail.setStatus(FileStatus.FAILED);
            System.out.println(e.getMessage());
        }

    }

    private void emailRequest(FileDetail fileDetail){

    }

    public String setFileAction(String fileId, FileAction fileAction){
        Optional<FileDetail> fileDetails =  fileDetailsRepository.findById(fileId);

        if (fileDetails.isPresent()){
            FileDetail fileDetail = fileDetails.get();
            if (fileDetail.getStatus().equals(FileStatus.LOADING) && fileAction != FileAction.KILL){
                return "FileLadingInProcess";
            }

            if (fileAction.equals(FileAction.START) || fileAction.equals(FileAction.RETRY)){
                emailRequest(fileDetail);
                fileDetail.setStatus(FileStatus.SENDING);
                mongoTemplate.save(fileDetail);
            }
            return "FileActionUpdated";
        }
        return "FileIdNotFound";
    }

    public String fileDelete(String fileId) {
        Optional<FileDetail> fileDetails = fileDetailsRepository.findById(fileId);

        if (fileDetails.isPresent()) {
            FileDetail fileDetail = fileDetails.get();
            if (fileDetail.getStatus().equals(FileStatus.SENDING) || fileDetail.getStatus().equals(FileStatus.LOADING)) {
                return "FileInUse";
            }
            fileDetailsRepository.deleteById(fileId);
            return "Done";
        }
        return "FileNotFound";
    }
}
