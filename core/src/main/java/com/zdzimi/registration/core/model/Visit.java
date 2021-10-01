package com.zdzimi.registration.core.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.EntityModel;

import java.time.LocalDateTime;

@Getter
@Setter
public class Visit extends EntityModel {

    private Long visitId;
    private LocalDateTime visitStart;
    private LocalDateTime visitEnd;
    private User user;
    private User representative;
    private String placeName;
    private Institution institution;
}
