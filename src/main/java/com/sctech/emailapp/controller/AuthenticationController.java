package com.sctech.emailapp.controller;

import com.sctech.emailapp.dto.LoginResponseDto;
import com.sctech.emailapp.dto.LoginUserDto;
import com.sctech.emailapp.dto.RegisterUserDto;
import com.sctech.emailapp.model.User;
import com.sctech.emailapp.service.AuthenticationService;
import com.sctech.emailapp.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> authenticate(@RequestBody @Valid LoginUserDto loginUserDto) {
        System.out.println("in loging : " + loginUserDto);
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        System.out.println("User Details : " + authenticatedUser);

        String jwtToken = jwtService.generateToken(authenticatedUser);
        System.out.println("JWT : " + jwtToken);

        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setToken(jwtToken);
        loginResponseDto.setExpiresIn(jwtService.getExpirationTime());
        System.out.println("response : " + loginResponseDto);

        return ResponseEntity.ok(loginResponseDto);
    }
}