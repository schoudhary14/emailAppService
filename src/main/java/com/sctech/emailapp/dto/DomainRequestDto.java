package com.sctech.emailapp.dto;

import com.sctech.emailapp.enums.DomainStatus;
import com.sctech.emailapp.enums.DomainType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DomainRequestDto extends BaseRequestDto{

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    private DomainType type;

    @NotNull
    private DomainStatus status;

    @NotNull
    private Boolean dkim;

    private String dkimPrivateKey;
    private String dkimPublicKey;

}
