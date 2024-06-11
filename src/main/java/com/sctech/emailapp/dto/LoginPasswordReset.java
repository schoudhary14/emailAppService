package com.sctech.emailapp.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginPasswordReset {

    @NotNull
    @NotEmpty
    private String email;

    @NotNull
    @NotEmpty
    private String otp;

    @NotNull
    @NotEmpty
    private String newPassword;
}
