package com.sctech.emailapp.dto;

import com.sctech.emailapp.enums.CompanyType;
import com.sctech.emailapp.enums.EntityStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CompanyRequestDto {

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String userId;

    @NotNull
    private EntityStatus status;

    @NotNull
    @NotEmpty
    private String email;

    @NotNull
    private CompanyType billType;

    @NotNull
    private Long credits;

    @NotNull
    private Integer alertLevel;
}
