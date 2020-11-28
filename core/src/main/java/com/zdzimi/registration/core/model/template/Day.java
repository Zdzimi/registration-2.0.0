package com.zdzimi.registration.core.model.template;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Getter
@Setter
public class Day {

    @NotNull
    @Min(1)
    @Max(31)
    private int dayOfMonth;
    private LocalTime workStart;
    private LocalTime workEnd;
    private String PlaceName;
}
