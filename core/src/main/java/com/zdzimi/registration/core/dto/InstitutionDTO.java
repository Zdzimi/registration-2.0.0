package com.zdzimi.registration.core.dto;

import com.zdzimi.registration.core.validation.OnCreate;
import com.zdzimi.registration.core.validation.OnUpdate;
import com.zdzimi.registration.core.validation.OnlyLettersAndDigits;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Getter
@Setter
public class InstitutionDTO {

    @Null(groups = OnCreate.class)
    @NotNull(groups = OnUpdate.class)
    private long institutionId;
    @NotNull
    @OnlyLettersAndDigits
    private String institutionName;
    @NotNull
    private String province;
    @NotNull
    private String city;
    @NotNull
    private String street;
    @NotNull
    private String gateNumber;
    private String premisesNumber;
    @NotNull
    private String typeOfService;
    @NotNull
    private String description;
}
