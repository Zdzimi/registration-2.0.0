package com.zdzimi.registration.core.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Visit {

    private Long visitId;
    private LocalDateTime visitStart;
    private LocalDateTime visitEnd;
    private User user;
    private User representative;
    private Place place;
    private Institution institution;
}
