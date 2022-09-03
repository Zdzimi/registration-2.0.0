package com.zdzimi.registration.core.model.timetable;

import com.zdzimi.registration.core.model.Visit;

import java.time.LocalDate;
import java.util.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class MonthTimetable {

    private final int year;
    private final int month;
    private final DayTimetable[] dayTimetables;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MonthTimetable that = (MonthTimetable) o;
        return year == that.year && month == that.month;
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, month);
    }

    public static List<MonthTimetable> createTimetable(List<Visit> visits) {
        List<MonthTimetable> timetables = new ArrayList<>();
        for (Visit visit : visits) {
            int year = visit.getVisitStart().getYear();
            int monthValue = visit.getVisitStart().getMonthValue();
            int dayOfMonth = visit.getVisitStart().getDayOfMonth();
            MonthTimetable monthTimetable = createMonthTimetable(year, monthValue);
            if (!timetables.contains(monthTimetable)) {
                timetables.add(monthTimetable);
            }
            int index = timetables.indexOf(monthTimetable);
            timetables.get(index).dayTimetables[dayOfMonth - 1].getVisits().add(visit);
        }
        return timetables;
    }

    private static MonthTimetable createMonthTimetable(int year, int month) {
        LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
        int monthLength = firstDayOfMonth.getMonth().length(firstDayOfMonth.isLeapYear());
        DayTimetable[] dayTimetables = new DayTimetable[monthLength];
        for (int i = 0; i < monthLength; i++) {
            dayTimetables[i] = new DayTimetable(i + 1);
        }
        return new MonthTimetable(year, month, dayTimetables);
    }
}
