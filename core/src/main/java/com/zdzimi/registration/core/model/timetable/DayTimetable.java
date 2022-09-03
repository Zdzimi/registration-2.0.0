package com.zdzimi.registration.core.model.timetable;

import com.zdzimi.registration.core.model.Visit;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
class DayTimetable {

    private final int dayOfMonth;
    private final List<Visit> visits = new ArrayList<>();
}
