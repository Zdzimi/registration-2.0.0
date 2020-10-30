package com.zdzimi.registration.core.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Visit {

    private long visitId;
    private LocalDateTime visitDateTime;
    private long visitLength;
    private User user;
    private User representative;
    private Place place;
}
