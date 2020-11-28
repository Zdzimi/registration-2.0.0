package com.zdzimi.registration.core.model.template;

public class TimetableTemplateException extends RuntimeException {

    public TimetableTemplateException(int year, int month) {
        super("You can not create timetable: " + year + "." + month);
    }
}
