package com.sctech.emailapp.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BaseRequestDto {
    @NotNull
    @NotEmpty
    private String companyId;

    private String userId;
}
