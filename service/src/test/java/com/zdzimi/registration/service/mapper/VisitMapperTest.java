package com.zdzimi.registration.service.mapper;

import com.zdzimi.registration.core.model.Institution;
import com.zdzimi.registration.core.model.Place;
import com.zdzimi.registration.core.model.User;
import com.zdzimi.registration.core.model.Visit;
import com.zdzimi.registration.data.entity.InstitutionEntity;
import com.zdzimi.registration.data.entity.PlaceEntity;
import com.zdzimi.registration.data.entity.UserEntity;
import com.zdzimi.registration.data.entity.VisitEntity;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static com.zdzimi.registration.service.mapper.InstitutionMapperTest.*;
import static com.zdzimi.registration.service.mapper.PlaceMapperTest.*;
import static com.zdzimi.registration.service.mapper.PlaceMapperTest.getPlace;
import static com.zdzimi.registration.service.mapper.UserMapperTest.*;
import static com.zdzimi.registration.service.mapper.UserMapperTest.getUser;
import static org.junit.jupiter.api.Assertions.*;

class VisitMapperTest {

    private static final long VISIT_ID = 34;
    private static final LocalDateTime LOCAL_DATE_TIME_START = LocalDateTime.of(2020, 2, 15, 8, 30);
    private static final LocalDateTime LOCAL_DATE_TIME_END = LocalDateTime.of(2020, 2, 15, 9, 30);
    private static final Timestamp TIMESTAMP_START = Timestamp.valueOf(LOCAL_DATE_TIME_START);
    private static final Timestamp TIMESTAMP_END = Timestamp.valueOf(LOCAL_DATE_TIME_END);

    private ModelMapper modelMapper = new ModelMapper();
    private UserMapper userMapper = new UserMapper(modelMapper);
    private PlaceMapper placeMapper = new PlaceMapper(modelMapper);
    private InstitutionMapper institutionMapper = new InstitutionMapper(modelMapper);
    private VisitMapper visitMapper = new VisitMapper(modelMapper, userMapper, placeMapper, institutionMapper);

    @Test
    void shouldConvertToVisit() {
        VisitEntity visitEntity = getVisitEntity();

        Visit result = visitMapper.convertToVisit(visitEntity);

        assertEquals(VISIT_ID, result.getVisitId());
        assertEquals(LOCAL_DATE_TIME_START, result.getVisitStart());
        assertEquals(LOCAL_DATE_TIME_END, result.getVisitEnd());

        assertEquals(USERNAME, result.getUser().getUsername());
        assertEquals(NAME, result.getUser().getName());
        assertEquals(SURNAME, result.getUser().getSurname());
        assertEquals(EMAIL, result.getUser().getEmail());
        assertNull(result.getUser().getPassword());

        assertEquals(USERNAME, result.getRepresentative().getUsername());
        assertEquals(NAME, result.getRepresentative().getName());
        assertEquals(SURNAME, result.getRepresentative().getSurname());
        assertEquals(EMAIL, result.getRepresentative().getEmail());
        assertNull(result.getRepresentative().getPassword());

        assertEquals(PLACE_NAME, result.getPlace().getPlaceName());

        assertEquals(INSTITUTION_ID, result.getInstitution().getInstitutionId());
    }

    @Test
    void shouldConvertToVisitEntity() {
        Visit visit = getVisit();

        VisitEntity result = visitMapper.convertToVisitEntity(visit);

        assertEquals(VISIT_ID, result.getVisitId());
        assertEquals(TIMESTAMP_START, result.getVisitStart());
        assertEquals(TIMESTAMP_END, result.getVisitEnd());

        assertEquals(0, result.getUser().getUserId());
        assertEquals(USERNAME, result.getUser().getUsername());
        assertEquals(NAME, result.getUser().getName());
        assertEquals(SURNAME, result.getUser().getSurname());
        assertEquals(EMAIL, result.getUser().getEmail());
        assertEquals(PASSWORD, result.getUser().getPassword());
        assertEquals(ROLE, result.getUser().getRole());

        assertEquals(0, result.getRepresentative().getUserId());
        assertEquals(USERNAME, result.getRepresentative().getUsername());
        assertEquals(NAME, result.getRepresentative().getName());
        assertEquals(SURNAME, result.getRepresentative().getSurname());
        assertEquals(EMAIL, result.getRepresentative().getEmail());
        assertEquals(PASSWORD, result.getRepresentative().getPassword());
        assertEquals(ROLE, result.getRepresentative().getRole());

        assertEquals(0, result.getPlace().getPlaceId());
        assertEquals(PLACE_NAME, result.getPlace().getPlaceName());

        assertEquals(INSTITUTION_ID, result.getInstitution().getInstitutionId());
    }

    static VisitEntity getVisitEntity() {
        VisitEntity visitEntity = new VisitEntity();
        visitEntity.setVisitId(VISIT_ID);
        visitEntity.setVisitStart(TIMESTAMP_START);
        visitEntity.setVisitEnd(TIMESTAMP_END);

        UserEntity userEntity = getUserEntity();
        visitEntity.setUser(userEntity);

        UserEntity representativeEntity = getUserEntity();
        visitEntity.setRepresentative(representativeEntity);

        PlaceEntity placeEntity = getPlaceEntity();
        visitEntity.setPlace(placeEntity);

        InstitutionEntity institutionEntity = getInstitutionEntity();
        visitEntity.setInstitution(institutionEntity);

        return visitEntity;
    }

    static Visit getVisit() {
        Visit visit = new Visit();
        visit.setVisitId(VISIT_ID);
        visit.setVisitStart(LOCAL_DATE_TIME_START);
        visit.setVisitEnd(LOCAL_DATE_TIME_END);

        User user = getUser();
        visit.setUser(user);

        User representative = getUser();
        visit.setRepresentative(representative);

        Place place = getPlace();
        visit.setPlace(place);

        Institution institution = getInstitution();
        visit.setInstitution(institution);

        return visit;
    }
}