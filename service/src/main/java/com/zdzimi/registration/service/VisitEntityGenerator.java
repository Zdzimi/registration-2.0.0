package com.zdzimi.registration.service;

import com.zdzimi.registration.core.model.template.Day;
import com.zdzimi.registration.core.model.template.TimetableTemplate;
import com.zdzimi.registration.data.entity.InstitutionEntity;
import com.zdzimi.registration.data.entity.PlaceEntity;
import com.zdzimi.registration.data.entity.UserEntity;
import com.zdzimi.registration.data.entity.VisitEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class VisitEntityGenerator {

    //      todo - test

    private PlaceService placeService;

    @Autowired
    public VisitEntityGenerator(PlaceService placeService) {
        this.placeService = placeService;
    }

    public List<VisitEntity> createVisits(TimetableTemplate timetableTemplate, UserEntity representativeEntity, InstitutionEntity institutionEntity) {
        LocalDateTime now = LocalDateTime.now();

        int year = timetableTemplate.getYear();
        int month = timetableTemplate.getMonth();
        long visitLength = timetableTemplate.getVisitLength();
        Collection<Day> days = timetableTemplate.getDays();
        List<VisitEntity> visits = new ArrayList<>();

        for (Day day : days) {
            String placeName = day.getPlaceName();
            if (placeName != null) {
                int dayOfMonth = day.getDayOfMonth();
                LocalTime workStart = day.getWorkStart();
                LocalTime workEnd = day.getWorkEnd();
                PlaceEntity placeEntity = placeService.getPlaceEntity(institutionEntity, placeName);

                while (workStart.isBefore(workEnd)) {
                    LocalDateTime visitStart = LocalDateTime.of(year, month, dayOfMonth, workStart.getHour(), workStart.getMinute());
                    if (now.isBefore(visitStart)){
                        LocalDateTime visitEnd = visitStart.plusMinutes(visitLength);
                        VisitEntity visitEntity = new VisitEntity();
                        visitEntity.setVisitStart(Timestamp.valueOf(visitStart));
                        visitEntity.setVisitEnd(Timestamp.valueOf(visitEnd));
                        visitEntity.setRepresentative(representativeEntity);
                        visitEntity.setPlace(placeEntity);
                        visitEntity.setInstitution(institutionEntity);
                        visits.add(visitEntity);
                    }
                    workStart = workStart.plusMinutes(visitLength);
                }
            }
        }
        return visits;
    }
}
