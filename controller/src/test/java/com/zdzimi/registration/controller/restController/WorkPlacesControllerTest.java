package com.zdzimi.registration.controller.restController;

import com.zdzimi.registration.core.model.Institution;
import com.zdzimi.registration.data.entity.UserEntity;
import com.zdzimi.registration.service.InstitutionService;
import com.zdzimi.registration.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class WorkPlacesControllerTest {

    private static final String USERNAME = "Dupek";
    private static final String INSTITUTION_NAME = "uuX";

    private WorkPlacesController workPlacesController;
    private InstitutionService institutionService;
    private UserService userService;

    @BeforeEach
    void setUp() {
        institutionService = mock(InstitutionService.class);
        userService = mock(UserService.class);
        initMocks(this);
        workPlacesController = new WorkPlacesController(institutionService, userService);
    }

    @Test
    void shouldGetWorkPlaces() {
        //      given
        UserEntity userEntity = new UserEntity();
        Institution institution = new Institution();
        when(userService.getUserEntityByUsername(USERNAME)).thenReturn(userEntity);
        when(institutionService.getWorkPlaces(userEntity)).thenReturn(Arrays.asList(institution));
        //      when
        List<Institution> result = workPlacesController.getWorkPlaces(USERNAME);
        //      then
        assertEquals(1, result.size());
        verify(userService, times(1)).getUserEntityByUsername(USERNAME);
        verify(institutionService, times(1)).getWorkPlaces(userEntity);
        verifyNoMoreInteractions(userService);
        verifyNoMoreInteractions(institutionService);
    }

    @Test
    void shouldGetWorkPlace() {
        //      given
        UserEntity userEntity = new UserEntity();
        Institution institution = new Institution();
        institution.setInstitutionName(INSTITUTION_NAME);
        when(userService.getUserEntityByUsername(USERNAME)).thenReturn(userEntity);
        when(institutionService.getWorkPlace(userEntity, INSTITUTION_NAME)).thenReturn(institution);
        //      when
        Institution result = workPlacesController.getWorkPlace(USERNAME, INSTITUTION_NAME);
        //      then
        assertEquals(INSTITUTION_NAME, result.getInstitutionName());
        verify(userService, times(1)).getUserEntityByUsername(USERNAME);
        verify(institutionService, times(1)).getWorkPlace(userEntity, INSTITUTION_NAME);
        verifyNoMoreInteractions(userService);
        verifyNoMoreInteractions(institutionService);
    }
}