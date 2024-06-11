package com.sctech.emailapp.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class LoginUserDto {
    @NonNull
    private String email;

    @NonNull
    private String password;

}