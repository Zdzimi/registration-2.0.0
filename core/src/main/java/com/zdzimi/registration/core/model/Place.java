package com.zdzimi.registration.core.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.EntityModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class Place extends EntityModel {

    @NotBlank(message = "Pole jest wymagane.")
    @Size(max = 15, message = "Pole może zawierać maksymalnie piętnaście znaków.")
    private String placeName;
}
