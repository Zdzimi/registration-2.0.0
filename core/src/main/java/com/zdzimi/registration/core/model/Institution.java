package com.zdzimi.registration.core.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Institution {

    private long institutionId;
    private String institutionName;
    private String province;
    private String city;
    private String street;
    private String gateNumber;
    private String premisesNumber;
    private String typeOfService;
    private String description;
}
