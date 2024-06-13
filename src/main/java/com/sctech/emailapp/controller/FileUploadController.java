package com.sctech.emailapp.controller;

import com.sctech.emailapp.dto.CommonResposeDto;
import com.sctech.emailapp.enums.FileStatus;
import com.sctech.emailapp.exceptions.InvalidRequestException;
import com.sctech.emailapp.model.FileDetail;
import com.sctech.emailapp.service.FileService;
import com.sctech.emailapp.util.EmailResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/file")
public class FileUploadController {

    @Autowired
    private FileService fileService;

    @Autowired
    private EmailResponseBuilder emailResponseBuilder;

    @PostMapping
    public ResponseEntity<CommonResposeDto> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            throw new InvalidRequestException("Please select a file to upload");
        }

        try {
            FileDetail fileDetail = fileService.fileUpload(file);
            return new ResponseEntity<>(emailResponseBuilder.commonResponse("File uploaded successfully",null), HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException("File upload failed");
        }
    }

    @GetMapping
    public ResponseEntity<CommonResposeDto> getFileDetail(){
        List<FileDetail> fileDetailEntities = fileService.getFileDetails();
        return new ResponseEntity<>(emailResponseBuilder.commonResponse("success",fileDetailEntities),HttpStatus.OK);
    }

    @GetMapping("/action/{fileId}")
    public ResponseEntity<CommonResposeDto> setAction(@PathVariable("fileId") String fileId, @RequestParam("event") FileStatus fileAction){
        String resp = fileService.setFileAction(fileId, fileAction);
        return new ResponseEntity<>(emailResponseBuilder.commonResponse(resp,null),HttpStatus.OK);
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<CommonResposeDto> deleteFile(@PathVariable("fileId") String fileId){
        String resp = fileService.fileDelete(fileId);
        return new ResponseEntity<>(emailResponseBuilder.commonResponse(resp, null),HttpStatus.OK);
    }

}