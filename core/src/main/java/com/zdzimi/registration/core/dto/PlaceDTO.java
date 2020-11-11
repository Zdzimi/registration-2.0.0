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
public class PlaceDTO {

    @Null(groups = OnCreate.class)
    @NotNull(groups = OnUpdate.class)
    private long placeId;
    @NotNull
    @OnlyLettersAndDigits
    private String placeName;
    private InstitutionDTO institutionDTO;
}
