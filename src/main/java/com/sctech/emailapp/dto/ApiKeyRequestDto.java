package com.sctech.emailapp.dto;

import com.sctech.emailapp.enums.EntityStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ApiKeyRequestDto extends BaseRequestDto{

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String[] ipAddress;

    @NotNull
    private EntityStatus status;
}
