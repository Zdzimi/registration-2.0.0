package com.zdzimi.registration.core.model;

import com.zdzimi.registration.core.validation.OnCreate;
import com.zdzimi.registration.core.validation.OnUpdate;
import com.zdzimi.registration.core.validation.OnlyLettersAndDigits;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.EntityModel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Getter
@Setter
public class Place extends EntityModel {

    @Null(groups = OnCreate.class)
    @NotNull(groups = OnUpdate.class)
    private Long placeId;
    @NotNull
    @OnlyLettersAndDigits
    private String placeName;
}
