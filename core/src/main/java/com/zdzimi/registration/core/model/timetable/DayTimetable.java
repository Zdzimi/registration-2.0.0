package com.zdzimi.registration.core.model.timetable;

import com.zdzimi.registration.core.model.Visit;

import java.util.ArrayList;
import java.util.List;

class DayTimetable {

    private final int dayOfMonth;
    private final List<Visit> visits;

    DayTimetable(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
        this.visits = new ArrayList<>();
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public List<Visit> getVisits() {
        return visits;
    }
}
