package com.sctech.emailapp.dto;

import com.sctech.emailapp.enums.EntityStatus;
import com.sctech.emailapp.enums.Role;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterUserDto extends BaseRequestDto{

    @NotNull
    @NotEmpty
    private String firstName;

    private String lastName;

    @NotNull
    @NotEmpty
    private String email;

    @NotNull
    private Role role;

    @NotNull
    private EntityStatus status;

    private String password;

}