package com.zdzimi.registration.controller.restController;

import com.zdzimi.registration.core.model.Visit;
import com.zdzimi.registration.data.entity.UserEntity;
import com.zdzimi.registration.service.UserService;
import com.zdzimi.registration.service.VisitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class UserVisitControllerTest {

    private static final String USERNAME = "Bobby";
    private static final long VISIT_ID = 1;

    private UserVisitController userVisitController;
    private VisitService visitService;
    private UserService userService;

    @BeforeEach
    void setUp() {
        visitService = mock(VisitService.class);
        userService = mock(UserService.class);
        initMocks(this);
        userVisitController = new UserVisitController(visitService, userService);
    }

    @Test
    void shouldGetUsersVisits() {
        //      given
        UserEntity userEntity = new UserEntity();
        Visit visit = new Visit();
        when(userService.getUserEntityByUsername(USERNAME)).thenReturn(userEntity);
        when(visitService.getAllByUser(userEntity)).thenReturn(Arrays.asList(visit));
        //      when
        List<Visit> result = userVisitController.getUsersVisits(USERNAME);
        //      then
        assertEquals(1, result.size());
        verify(userService, times(1)).getUserEntityByUsername(USERNAME);
        verify(visitService, times(1)).getAllByUser(userEntity);
        verifyNoMoreInteractions(userService);
        verifyNoMoreInteractions(visitService);
    }

    @Test
    void shouldGetVisit() {
        //      given
        UserEntity userEntity = new UserEntity();
        Visit visit = new Visit();
        when(userService.getUserEntityByUsername(USERNAME)).thenReturn(userEntity);
        when(visitService.getByUserAndVisitId(userEntity, VISIT_ID)).thenReturn(visit);
        //      when
        Visit result = userVisitController.getUsersVisit(USERNAME, VISIT_ID);
        //      then
        assertEquals(visit, result);
        verify(userService, times(1)).getUserEntityByUsername(USERNAME);
        verify(visitService, times(1)).getByUserAndVisitId(userEntity, VISIT_ID);
        verifyNoMoreInteractions(userService);
        verifyNoMoreInteractions(visitService);
    }
}