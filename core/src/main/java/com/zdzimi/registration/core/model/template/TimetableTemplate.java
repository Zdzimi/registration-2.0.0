package com.zdzimi.registration.core.model.template;

import com.zdzimi.registration.core.model.Place;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class TimetableTemplate {

    private int year;
    @Min(1)
    @Max(12)
    private int month;
    @NotNull
    private Collection<Day> days;
    @Min(1)
    private long visitLength;
    private List<Place> places;
}
