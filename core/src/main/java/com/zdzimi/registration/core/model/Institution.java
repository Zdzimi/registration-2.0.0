package com.zdzimi.registration.core.model;

import com.zdzimi.registration.core.validation.OnCreate;
import com.zdzimi.registration.core.validation.OnUpdate;
import com.zdzimi.registration.core.validation.OnlyLettersAndDigits;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.EntityModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

@Getter
@Setter
public class Institution extends EntityModel {

    @Null(groups = OnCreate.class)
    @NotNull(groups = OnUpdate.class)
    private Long institutionId;
    @OnlyLettersAndDigits
    private String institutionName;
    @NotBlank(message = "Pole jest wymagane.")
    @Size(min = 4, message = "Pole musi zawierać conajmniej cztery znaki.")
    private String province;
    @NotBlank(message = "Pole jest wymagane.")
    @Size(min = 4, message = "Pole musi zawierać conajmniej cztery znaki.")
    private String city;
    @NotBlank(message = "Pole jest wymagane.")
    @Size(min = 4, message = "Pole musi zawierać conajmniej cztery znaki.")
    private String street;
    @NotBlank(message = "Pole jest wymagane.")
    private String gateNumber;
    private String premisesNumber;
    @NotBlank(message = "Pole jest wymagane.")
    @Size(min = 4, message = "Pole musi zawierać conajmniej cztery znaki.")
    private String typeOfService;
    @NotBlank(message = "Pole jest wymagane.")
    @Size(min = 4, message = "Pole musi zawierać conajmniej cztery znaki.")
    private String description;
}
