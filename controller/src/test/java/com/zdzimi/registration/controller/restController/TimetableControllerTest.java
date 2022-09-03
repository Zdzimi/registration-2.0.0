package com.zdzimi.registration.controller.restController;

import com.zdzimi.registration.controller.link.LinkCreator;
import com.zdzimi.registration.core.model.Visit;
import com.zdzimi.registration.data.entity.InstitutionEntity;
import com.zdzimi.registration.data.entity.UserEntity;
import com.zdzimi.registration.data.entity.VisitEntity;
import com.zdzimi.registration.service.InstitutionService;
import com.zdzimi.registration.service.UserService;
import com.zdzimi.registration.service.VisitService;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TimetableControllerTest {

    private static final String USERNAME = "Laura";
    private static final String REPRESENTATIVE_NAME = "Ula";
    private static final String INSTITUTION_NAME = "WESRIP";
    private static final long VISIT_ID = 4322592;

    @Mock
    private LoggedUserProvider loggedUserProvider;
    @Mock
    private UserService userService;
    @Mock
    private VisitService visitService;
    @Mock
    private InstitutionService institutionService;
    @Mock
    private LinkCreator linkCreator;
    @InjectMocks
    private TimetableController timetableController;

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
        Visit result = timetableController.getVisit(USERNAME, INSTITUTION_NAME, REPRESENTATIVE_NAME, VISIT_ID);
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
        when(loggedUserProvider.provideLoggedUser(USERNAME)).thenReturn(userEntity);

        UserEntity representativeEntity = new UserEntity();
        when(userService.getUserEntityByUsername(REPRESENTATIVE_NAME)).thenReturn(representativeEntity);

        InstitutionEntity institutionEntity = new InstitutionEntity();
        when(institutionService.getInstitutionEntityByInstitutionName(INSTITUTION_NAME)).thenReturn(institutionEntity);

        VisitEntity visitEntity = new VisitEntity();
        when(visitService.getCurrentByVisitIdAndRepresentativeAndInstitution(representativeEntity, institutionEntity, VISIT_ID)).thenReturn(visitEntity);

        Visit visit = new Visit();
        when(visitService.bookVisit(visitEntity, userEntity)).thenReturn(visit);
        //      when
        Visit result = timetableController.bookVisit(USERNAME, INSTITUTION_NAME, REPRESENTATIVE_NAME, VISIT_ID);
        //      then
        assertEquals(visit, result);
        verify(loggedUserProvider, times(1)).provideLoggedUser(USERNAME);
        verify(userService, times(1)).getUserEntityByUsername(REPRESENTATIVE_NAME);
        verify(institutionService, times(1)).getInstitutionEntityByInstitutionName(INSTITUTION_NAME);
        verify(visitService, times(1)).getCurrentByVisitIdAndRepresentativeAndInstitution(representativeEntity, institutionEntity, VISIT_ID);
        verify(visitService, times(1)).bookVisit(visitEntity, userEntity);
        verify(userService, times(1)).addRecognizedInstitution(userEntity, institutionEntity);
        verifyNoMoreInteractions(userService);
        verifyNoMoreInteractions(institutionService);
        verifyNoMoreInteractions(visitService);
    }
}