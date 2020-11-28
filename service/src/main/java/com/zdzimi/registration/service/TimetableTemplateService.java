package com.zdzimi.registration.service;

import com.zdzimi.registration.core.model.Visit;
import com.zdzimi.registration.core.model.template.Day;
import com.zdzimi.registration.core.model.template.TimetableTemplate;
import com.zdzimi.registration.core.model.template.TimetableTemplateException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class TimetableTemplateService {

    //      todo tests

    public TimetableTemplate prepareTemplate(Visit lastProvidedVisit) {
        LocalDateTime now = LocalDateTime.now();
        if (lastProvidedVisit != null) {
            LocalDateTime visitDateTime = lastProvidedVisit.getVisitStart();
            LocalDateTime nextMonth = getNexMonthDate(visitDateTime);
            return createTemplate(nextMonth);
        } else {
            return createTemplate(now);
        }
    }

    public TimetableTemplate prepareTemplate(int year, int month) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime firstDayOfMonth = LocalDateTime.of(year, month, 1, 0, 0);
        int monthLength = firstDayOfMonth.getMonth().length(firstDayOfMonth.toLocalDate().isLeapYear());
        LocalDateTime lastDayOfMonth = LocalDateTime.of(year, month, monthLength, 23, 59);
        if (now.isBefore(firstDayOfMonth)) {
            return createTemplate(firstDayOfMonth);
        } else if (now.isAfter(firstDayOfMonth) && now.isBefore(lastDayOfMonth)) {
            return createTemplate(now);
        } else {
            throw new TimetableTemplateException(year, month);
        }
    }

    private TimetableTemplate createTemplate(LocalDateTime date) {
        TimetableTemplate timetableTemplate = new TimetableTemplate();
        timetableTemplate.setYear(date.getYear());
        timetableTemplate.setMonth(date.getMonthValue());
        Collection<Day> days = new ArrayList<>();

        int monthLength = date.getMonth().length(date.toLocalDate().isLeapYear());

        for (int i = date.getDayOfMonth(); i < monthLength + 1; i++) {
            Day day = new Day();
            day.setDayOfMonth(i);
            // fixme - delete ->
            day.setWorkStart(LocalTime.of(0,0));
            day.setWorkEnd(LocalTime.of(0,0));
            //  fixme -> delete
            days.add(day);
        }
        timetableTemplate.setDays(days);

        return timetableTemplate;
    }

    private LocalDateTime getNexMonthDate(LocalDateTime visitDateTime) {
        return visitDateTime.plusMonths(1).withDayOfMonth(1);
    }
}
