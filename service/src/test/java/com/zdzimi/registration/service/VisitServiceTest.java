package com.zdzimi.registration.service;

import com.zdzimi.registration.core.model.Visit;
import com.zdzimi.registration.data.entity.UserEntity;
import com.zdzimi.registration.data.entity.VisitEntity;
import com.zdzimi.registration.data.repository.VisitRepository;
import com.zdzimi.registration.service.mapper.VisitMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class VisitServiceTest {

    private static final LocalDateTime LOCAL_DATE_TIME = LocalDateTime.of(1999,4,30,12,30);
    private static final Date DATE = Date.valueOf(LOCAL_DATE_TIME.toLocalDate());
    private static final Time TIME = Time.valueOf(LOCAL_DATE_TIME.toLocalTime());
    private static final long VISIT_ID = 131423;

    private VisitService visitService;
    private VisitRepository visitRepository;
    private VisitMapper visitMapper = new VisitMapper(new ModelMapper());

    @BeforeEach
    void setUp() {
        visitRepository = mock(VisitRepository.class);
        initMocks(this);
        visitService = new VisitService(visitRepository, visitMapper);
    }

    @Test
    void shouldGetAllByUser() {
        //      given
        UserEntity userEntity = new UserEntity();
        VisitEntity visitEntity = new VisitEntity();
        visitEntity.setVisitDate(DATE);
        visitEntity.setVisitTime(TIME);
        when(visitRepository.findByUser(userEntity)).thenReturn(Arrays.asList(visitEntity));
        //      when
        List<Visit> result = visitService.getAllByUser(userEntity);
        //      then
        assertEquals(1, result.size());
        assertEquals(LOCAL_DATE_TIME, result.get(0).getVisitDateTime());
        verify(visitRepository, times(1)).findByUser(userEntity);
        verifyNoMoreInteractions(visitRepository);
    }

    @Test
    void shouldGetByUserAndVisitId() {
        //      given
        UserEntity userEntity = new UserEntity();
        VisitEntity visitEntity = new VisitEntity();
        visitEntity.setVisitId(VISIT_ID);
        visitEntity.setVisitDate(DATE);
        visitEntity.setVisitTime(TIME);
        when(visitRepository.findByUserAndVisitId(userEntity, VISIT_ID)).thenReturn(visitEntity);
        //      when
        Visit result = visitService.getByUserAndVisitId(userEntity, VISIT_ID);
        //      then
        assertEquals(VISIT_ID, result.getVisitId());
        assertEquals(LOCAL_DATE_TIME, result.getVisitDateTime());
        verify(visitRepository, times(1)).findByUserAndVisitId(userEntity, VISIT_ID);
        verifyNoMoreInteractions(visitRepository);
    }
}