package com.zdzimi.registration.service.mapper;

import com.zdzimi.registration.core.model.Place;
import com.zdzimi.registration.core.model.User;
import com.zdzimi.registration.core.model.Visit;
import com.zdzimi.registration.data.entity.PlaceEntity;
import com.zdzimi.registration.data.entity.UserEntity;
import com.zdzimi.registration.data.entity.VisitEntity;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.zdzimi.registration.service.mapper.PlaceMapperTest.*;
import static com.zdzimi.registration.service.mapper.PlaceMapperTest.getPlace;
import static com.zdzimi.registration.service.mapper.UserMapperTest.*;
import static com.zdzimi.registration.service.mapper.UserMapperTest.getUser;
import static org.junit.jupiter.api.Assertions.*;

class VisitMapperTest {

    private static final long VISIT_ID = 34;
    private static final Date VISIT_DATE = Date.valueOf(LocalDate.of(2020, 2, 15));
    private static final Time VISIT_TIME = Time.valueOf(LocalTime.of(8, 30));
    private static final LocalDateTime VISIT_DATE_TIME = LocalDateTime.of(2020, 2, 15, 8, 30);
    private static final long VISIT_LENGTH = 30;

    private VisitMapper visitMapper = new VisitMapper(new ModelMapper());

    @Test
    void shouldConvertToVisit() {
        VisitEntity visitEntity = getVisitEntity();

        Visit visit = visitMapper.convertToVisit(visitEntity);

        assertEquals(VISIT_ID, visit.getVisitId());
        assertEquals(VISIT_DATE_TIME, visit.getVisitDateTime());
        assertEquals(VISIT_LENGTH, visit.getVisitLength());

        assertEquals(USER_ID, visit.getUser().getUserId());
        assertEquals(USERNAME, visit.getUser().getUsername());
        assertEquals(NAME, visit.getUser().getName());
        assertEquals(SURNAME, visit.getUser().getSurname());
        assertEquals(EMAIL, visit.getUser().getEmail());
        assertEquals(PASSWORD, visit.getUser().getPassword());
        assertEquals(ROLE, visit.getUser().getRole());

        assertEquals(USER_ID, visit.getRepresentative().getUserId());
        assertEquals(USERNAME, visit.getRepresentative().getUsername());
        assertEquals(NAME, visit.getRepresentative().getName());
        assertEquals(SURNAME, visit.getRepresentative().getSurname());
        assertEquals(EMAIL, visit.getRepresentative().getEmail());
        assertEquals(PASSWORD, visit.getRepresentative().getPassword());
        assertEquals(ROLE, visit.getRepresentative().getRole());

        assertEquals(PLACE_ID, visit.getPlace().getPlaceId());
        assertEquals(PLACE_NAME, visit.getPlace().getPlaceName());
    }

    @Test
    void shouldConvertToVisitEntity() {
        Visit visit = getVisit();

        VisitEntity visitEntity = visitMapper.convertToVisitEntity(visit);

        assertEquals(VISIT_ID, visitEntity.getVisitId());
        assertEquals(VISIT_DATE, visitEntity.getVisitDate());
        assertEquals(VISIT_TIME, visitEntity.getVisitTime());
        assertEquals(VISIT_LENGTH, visitEntity.getVisitLength());

        assertEquals(USER_ID, visitEntity.getUser().getUserId());
        assertEquals(USERNAME, visitEntity.getUser().getUsername());
        assertEquals(NAME, visitEntity.getUser().getName());
        assertEquals(SURNAME, visitEntity.getUser().getSurname());
        assertEquals(EMAIL, visitEntity.getUser().getEmail());
        assertEquals(PASSWORD, visitEntity.getUser().getPassword());
        assertEquals(ROLE, visitEntity.getUser().getRole());

        assertEquals(USER_ID, visitEntity.getRepresentative().getUserId());
        assertEquals(USERNAME, visitEntity.getRepresentative().getUsername());
        assertEquals(NAME, visitEntity.getRepresentative().getName());
        assertEquals(SURNAME, visitEntity.getRepresentative().getSurname());
        assertEquals(EMAIL, visitEntity.getRepresentative().getEmail());
        assertEquals(PASSWORD, visitEntity.getRepresentative().getPassword());
        assertEquals(ROLE, visitEntity.getRepresentative().getRole());

        assertEquals(PLACE_ID, visitEntity.getPlace().getPlaceId());
        assertEquals(PLACE_NAME, visitEntity.getPlace().getPlaceName());
    }

    static VisitEntity getVisitEntity() {
        VisitEntity visitEntity = new VisitEntity();
        visitEntity.setVisitId(VISIT_ID);
        visitEntity.setVisitDate(VISIT_DATE);
        visitEntity.setVisitTime(VISIT_TIME);
        visitEntity.setVisitLength(VISIT_LENGTH);

        UserEntity userEntity = getUserEntity();
        visitEntity.setUser(userEntity);

        UserEntity representativeEntity = getUserEntity();
        visitEntity.setRepresentative(representativeEntity);

        PlaceEntity placeEntity = getPlaceEntity();
        visitEntity.setPlace(placeEntity);

        return visitEntity;
    }

    static Visit getVisit() {
        Visit visit = new Visit();
        visit.setVisitId(VISIT_ID);
        visit.setVisitDateTime(VISIT_DATE_TIME);
        visit.setVisitLength(VISIT_LENGTH);

        User user = getUser();
        visit.setUser(user);

        User representative = getUser();
        visit.setRepresentative(representative);

        Place place = getPlace();
        visit.setPlace(place);

        return visit;
    }
}