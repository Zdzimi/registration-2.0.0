package com.zdzimi.registration.service.mapper;

import com.zdzimi.registration.core.model.Place;
import com.zdzimi.registration.core.model.User;
import com.zdzimi.registration.core.model.Visit;
import com.zdzimi.registration.data.entity.PlaceEntity;
import com.zdzimi.registration.data.entity.UserEntity;
import com.zdzimi.registration.data.entity.VisitEntity;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static com.zdzimi.registration.service.mapper.PlaceMapperTest.*;
import static com.zdzimi.registration.service.mapper.PlaceMapperTest.getPlace;
import static com.zdzimi.registration.service.mapper.UserMapperTest.*;
import static com.zdzimi.registration.service.mapper.UserMapperTest.getUser;
import static org.junit.jupiter.api.Assertions.*;

class VisitMapperTest {

    private static final long VISIT_ID = 34;
    private static final LocalDateTime VISIT_DATE_TIME = LocalDateTime.of(2020, 2, 15, 8, 30);
    private static final Timestamp VISIT_TIMESTAMP = Timestamp.valueOf(VISIT_DATE_TIME);
    private static final long VISIT_LENGTH = 30;

    private VisitMapper visitMapper = new VisitMapper(new ModelMapper());

    @Test
    void shouldConvertToVisit() {
        VisitEntity visitEntity = getVisitEntity();

        Visit result = visitMapper.convertToVisit(visitEntity);

        assertEquals(VISIT_ID, result.getVisitId());
        assertEquals(VISIT_DATE_TIME, result.getVisitDateTime());
        assertEquals(VISIT_LENGTH, result.getVisitLength());

        assertEquals(USER_ID, result.getUser().getUserId());
        assertEquals(USERNAME, result.getUser().getUsername());
        assertEquals(NAME, result.getUser().getName());
        assertEquals(SURNAME, result.getUser().getSurname());
        assertEquals(EMAIL, result.getUser().getEmail());
        assertEquals(PASSWORD, result.getUser().getPassword());
        assertEquals(ROLE_ROLE, result.getUser().getRole());

        assertEquals(USER_ID, result.getRepresentative().getUserId());
        assertEquals(USERNAME, result.getRepresentative().getUsername());
        assertEquals(NAME, result.getRepresentative().getName());
        assertEquals(SURNAME, result.getRepresentative().getSurname());
        assertEquals(EMAIL, result.getRepresentative().getEmail());
        assertEquals(PASSWORD, result.getRepresentative().getPassword());
        assertEquals(ROLE_ROLE, result.getRepresentative().getRole());

        assertEquals(PLACE_ID, result.getPlace().getPlaceId());
        assertEquals(PLACE_NAME, result.getPlace().getPlaceName());
    }

    @Test
    void shouldConvertToVisitEntity() {
        Visit visit = getVisit();

        VisitEntity result = visitMapper.convertToVisitEntity(visit);

        assertEquals(VISIT_ID, result.getVisitId());
        assertEquals(VISIT_TIMESTAMP, result.getVisitDateTime());
        assertEquals(VISIT_LENGTH, result.getVisitLength());

        assertEquals(USER_ID, result.getUser().getUserId());
        assertEquals(USERNAME, result.getUser().getUsername());
        assertEquals(NAME, result.getUser().getName());
        assertEquals(SURNAME, result.getUser().getSurname());
        assertEquals(EMAIL, result.getUser().getEmail());
        assertEquals(PASSWORD, result.getUser().getPassword());
        assertEquals(ROLE, result.getUser().getRole());

        assertEquals(USER_ID, result.getRepresentative().getUserId());
        assertEquals(USERNAME, result.getRepresentative().getUsername());
        assertEquals(NAME, result.getRepresentative().getName());
        assertEquals(SURNAME, result.getRepresentative().getSurname());
        assertEquals(EMAIL, result.getRepresentative().getEmail());
        assertEquals(PASSWORD, result.getRepresentative().getPassword());
        assertEquals(ROLE, result.getRepresentative().getRole());

        assertEquals(PLACE_ID, result.getPlace().getPlaceId());
        assertEquals(PLACE_NAME, result.getPlace().getPlaceName());
    }

    static VisitEntity getVisitEntity() {
        VisitEntity visitEntity = new VisitEntity();
        visitEntity.setVisitId(VISIT_ID);
        visitEntity.setVisitDateTime(VISIT_TIMESTAMP);
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