package com.sctech.emailapp.controller;

import com.sctech.emailapp.dto.CommonResposeDto;
import com.sctech.emailapp.dto.LoginPasswordReset;
import com.sctech.emailapp.service.PasswordResetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/password")
public class PasswordResetController {

    @Autowired
    private PasswordResetService passwordResetService;

    @GetMapping("/forgot")
    public ResponseEntity<CommonResposeDto> forgotPassword(@RequestParam String email) {
        CommonResposeDto commonResposeDto = new CommonResposeDto();
        try {
            passwordResetService.createOtpForUser(email);
            commonResposeDto.setStatusCode(HttpStatus.OK.value());
            commonResposeDto.setMessage("OTP sent to email");
        } catch (Exception e) {
            commonResposeDto.setStatusCode(HttpStatus.BAD_GATEWAY.value());
            commonResposeDto.setMessage(e.getMessage());
        }


        return new ResponseEntity<>(commonResposeDto,HttpStatus.OK);
    }

    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(@RequestBody @Valid LoginPasswordReset loginPasswordReset) {
        try {
            passwordResetService.resetPassword(loginPasswordReset.getEmail(), loginPasswordReset.getOtp(), loginPasswordReset.getNewPassword());
            return new ResponseEntity("Password successfully reset",HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.OK);
        }
    }
}