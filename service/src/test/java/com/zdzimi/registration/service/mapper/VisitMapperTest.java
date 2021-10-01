package com.zdzimi.registration.service.mapper;

import com.zdzimi.registration.core.model.Visit;
import com.zdzimi.registration.data.entity.InstitutionEntity;
import com.zdzimi.registration.data.entity.UserEntity;
import com.zdzimi.registration.data.entity.VisitEntity;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static com.zdzimi.registration.service.mapper.InstitutionMapperTest.*;
import static com.zdzimi.registration.service.mapper.UserMapperTest.*;
import static org.junit.jupiter.api.Assertions.*;

class VisitMapperTest {

    private static final long VISIT_ID = 34;
    private static final LocalDateTime LOCAL_DATE_TIME_START = LocalDateTime.of(2020, 2, 15, 8, 30);
    private static final LocalDateTime LOCAL_DATE_TIME_END = LocalDateTime.of(2020, 2, 15, 9, 30);
    private static final Timestamp TIMESTAMP_START = Timestamp.valueOf(LOCAL_DATE_TIME_START);
    private static final Timestamp TIMESTAMP_END = Timestamp.valueOf(LOCAL_DATE_TIME_END);
    private static final String PLACE_NAME = "Sale no: 1";

    private final ModelMapper modelMapper = new ModelMapper();
    private final UserMapper userMapper = new UserMapper(modelMapper);
    private final InstitutionMapper institutionMapper = new InstitutionMapper(modelMapper);
    private final VisitMapper visitMapper = new VisitMapper(modelMapper, userMapper, institutionMapper);

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

        assertEquals(PLACE_NAME, result.getPlaceName());

        assertEquals(INSTITUTION_NAME, result.getInstitution().getInstitutionName());
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

        visitEntity.setPlaceName(PLACE_NAME);

        InstitutionEntity institutionEntity = getInstitutionEntity();
        visitEntity.setInstitution(institutionEntity);

        return visitEntity;
    }
}