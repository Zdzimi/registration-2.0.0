package com.zdzimi.registration.core.model;

import com.zdzimi.registration.core.validation.OnCreate;
import com.zdzimi.registration.core.validation.OnUpdate;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

@Getter
@Setter
public class Visit {

    @Null(groups = OnCreate.class)
    @NotNull(groups = OnUpdate.class)
    private long visitId;
    @NotNull
    @PastOrPresent
    private LocalDateTime visitDateTime;
    @NotNull
    @Min(1)
    private long visitLength;
    private User user;
    private User representative;
    private Place place;
}
