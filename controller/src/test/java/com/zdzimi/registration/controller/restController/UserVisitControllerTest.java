package com.zdzimi.registration.controller.restController;

import com.zdzimi.registration.controller.link.LinkCreator;
import com.zdzimi.registration.core.model.Visit;
import com.zdzimi.registration.data.entity.UserEntity;
import com.zdzimi.registration.data.entity.VisitEntity;
import com.zdzimi.registration.service.VisitService;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserVisitControllerTest {

    private static final String USERNAME = "Bobby";
    private static final long VISIT_ID = 1;

    @Mock
    private LoggedUserProvider loggedUserProvider;
    @Mock
    private VisitService visitService;
    @Mock
    private LinkCreator linkCreator;
    @InjectMocks
    private UserVisitController userVisitController;

    @Test
    void shouldGetUsersVisits() {
        //      given
        UserEntity userEntity = new UserEntity();
        Visit visit = new Visit();
        when(loggedUserProvider.provideLoggedUser(USERNAME)).thenReturn(userEntity);
        when(visitService.getAllByUser(userEntity)).thenReturn(Arrays.asList(visit));
        //      when
        List<Visit> result = userVisitController.getUsersVisits(USERNAME);
        //      then
        assertEquals(1, result.size());
        verify(loggedUserProvider, times(1)).provideLoggedUser(USERNAME);
        verify(visitService, times(1)).getAllByUser(userEntity);
        verifyNoMoreInteractions(loggedUserProvider);
        verifyNoMoreInteractions(visitService);
    }

    @Test
    void shouldGetVisit() {
        //      given
        UserEntity userEntity = new UserEntity();
        Visit visit = new Visit();
        when(loggedUserProvider.provideLoggedUser(USERNAME)).thenReturn(userEntity);
        when(visitService.getByUserAndVisitId(userEntity, VISIT_ID)).thenReturn(visit);
        //      when
        Visit result = userVisitController.getUsersVisit(USERNAME, VISIT_ID);
        //      then
        assertEquals(visit, result);
        verify(loggedUserProvider, times(1)).provideLoggedUser(USERNAME);
        verify(visitService, times(1)).getByUserAndVisitId(userEntity, VISIT_ID);
        verifyNoMoreInteractions(loggedUserProvider);
        verifyNoMoreInteractions(visitService);
    }

    @Test
    void shouldCancelVisit() {
        //      given
        UserEntity userEntity = new UserEntity();
        when(loggedUserProvider.provideLoggedUser(USERNAME)).thenReturn(userEntity);
        VisitEntity visitEntity = new VisitEntity();
        when(visitService.getVisitEntityByUserAndVisitId(userEntity, VISIT_ID)).thenReturn(visitEntity);
        //      when
        userVisitController.cancelVisit(USERNAME, VISIT_ID);
        //      then
        verify(loggedUserProvider, times(1)).provideLoggedUser(USERNAME);
        verify(visitService, times(1)).getVisitEntityByUserAndVisitId(userEntity, VISIT_ID);
        verify(visitService, times(1)).cancelVisit(visitEntity);
        verifyNoMoreInteractions(loggedUserProvider);
        verifyNoMoreInteractions(visitService);
    }
}