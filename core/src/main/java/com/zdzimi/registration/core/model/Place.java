package com.zdzimi.registration.core.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Place {

    private long placeId;
    private String placeName;
    private Institution institution;
}
