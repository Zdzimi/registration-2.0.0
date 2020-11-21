package com.zdzimi.registration.controller.restController;

import com.zdzimi.registration.core.model.Visit;
import com.zdzimi.registration.data.entity.InstitutionEntity;
import com.zdzimi.registration.data.entity.UserEntity;
import com.zdzimi.registration.service.InstitutionService;
import com.zdzimi.registration.service.UserService;
import com.zdzimi.registration.service.VisitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class TimetableControllerTest {

    private static final String USERNAME = "Laura";
    private static final String REPRESENTATIVE_NAME = "Ula";
    private static final String INSTITUTION_NAME = "WESRIP";
    private static final long VISIT_ID = 4322592;

    private TimetableController timetableController;
    private VisitService visitService;
    private InstitutionService institutionService;
    private UserService userService;

    @BeforeEach
    void setUp() {
        visitService = mock(VisitService.class);
        institutionService = mock(InstitutionService.class);
        userService = mock(UserService.class);
        initMocks(this);
        timetableController = new TimetableController(visitService, institutionService, userService);
    }

    @Test
    void shouldGetVisits() {
        //      given
        UserEntity representativeEntity = new UserEntity();
        when(userService.getUserEntityByUsername(REPRESENTATIVE_NAME)).thenReturn(representativeEntity);
        InstitutionEntity institutionEntity = new InstitutionEntity();
        when(institutionService.getInstitutionEntityByInstitutionName(INSTITUTION_NAME)).thenReturn(institutionEntity);
        Visit visit = new Visit();
        when(visitService.getCurrentVisits(representativeEntity, institutionEntity)).thenReturn(Arrays.asList(visit));
        //      when
        List<Visit> result = timetableController.getVisits(USERNAME, INSTITUTION_NAME, REPRESENTATIVE_NAME);
        //      then
        assertEquals(1, result.size());
        verify(userService, times(1)).getUserEntityByUsername(REPRESENTATIVE_NAME);
        verify(institutionService, times(1)).getInstitutionEntityByInstitutionName(INSTITUTION_NAME);
        verify(visitService, times(1)).getCurrentVisits(representativeEntity, institutionEntity);
        verifyNoMoreInteractions(userService);
        verifyNoMoreInteractions(institutionService);
        verifyNoMoreInteractions(visitService);
    }

    @Test
    void shouldGetVisit() {
        //      given
        UserEntity representativeEntity = new UserEntity();
        when(userService.getUserEntityByUsername(REPRESENTATIVE_NAME)).thenReturn(representativeEntity);
        InstitutionEntity institutionEntity = new InstitutionEntity();
        when(institutionService.getInstitutionEntityByInstitutionName(INSTITUTION_NAME)).thenReturn(institutionEntity);
        Visit visit = new Visit();
        when(visitService.getCurrentVisit(representativeEntity, institutionEntity, VISIT_ID)).thenReturn(visit);
        //      when
        timetableController.getVisit(USERNAME, INSTITUTION_NAME, REPRESENTATIVE_NAME, VISIT_ID);
        //      then
        verify(userService, times(1)).getUserEntityByUsername(REPRESENTATIVE_NAME);
        verify(institutionService, times(1)).getInstitutionEntityByInstitutionName(INSTITUTION_NAME);
        verify(visitService, times(1)).getCurrentVisit(representativeEntity, institutionEntity, VISIT_ID);
        verifyNoMoreInteractions(userService);
        verifyNoMoreInteractions(institutionService);
        verifyNoMoreInteractions(visitService);
    }

    @Test
    void shouldBookVisit() {
        //      given
        UserEntity userEntity = new UserEntity();
        when(userService.getUserEntityByUsername(USERNAME)).thenReturn(userEntity);
        UserEntity representativeEntity = new UserEntity();
        when(userService.getUserEntityByUsername(REPRESENTATIVE_NAME)).thenReturn(representativeEntity);
        InstitutionEntity institutionEntity = new InstitutionEntity();
        when(institutionService.getInstitutionEntityByInstitutionName(INSTITUTION_NAME)).thenReturn(institutionEntity);
        Visit visit = new Visit();
        when(visitService.bookVisit(userEntity, representativeEntity, institutionEntity, VISIT_ID)).thenReturn(visit);
        //      when
        Visit result = timetableController.bookVisit(USERNAME, INSTITUTION_NAME, REPRESENTATIVE_NAME, VISIT_ID);
        //      then
        assertEquals(visit, result);
        verify(userService, times(1)).getUserEntityByUsername(USERNAME);
        verify(userService, times(1)).getUserEntityByUsername(REPRESENTATIVE_NAME);
        verify(institutionService, times(1)).getInstitutionEntityByInstitutionName(INSTITUTION_NAME);
        verify(visitService, times(1)).bookVisit(userEntity, representativeEntity, institutionEntity, VISIT_ID);
        verifyNoMoreInteractions(userService);
        verifyNoMoreInteractions(institutionService);
        verifyNoMoreInteractions(visitService);
    }
}