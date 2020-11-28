package com.zdzimi.registration.core.model;

import com.zdzimi.registration.core.validation.OnCreate;
import com.zdzimi.registration.core.validation.OnUpdate;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Getter
@Setter
public class Visit {

    @Null(groups = OnCreate.class)
    @NotNull(groups = OnUpdate.class)
    private Long visitId;
    @NotNull
    @PastOrPresent
    private LocalDateTime visitStart;
    @NotNull
    @Past
    private LocalDateTime visitEnd;
    private User user;
    private User representative;
    private Place place;
    private Institution institution;
}
