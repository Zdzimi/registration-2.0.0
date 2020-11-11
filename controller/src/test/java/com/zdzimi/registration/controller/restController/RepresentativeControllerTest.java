package com.zdzimi.registration.controller.restController;

import com.zdzimi.registration.core.model.User;
import com.zdzimi.registration.data.entity.InstitutionEntity;
import com.zdzimi.registration.service.InstitutionService;
import com.zdzimi.registration.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class RepresentativeControllerTest {

    private static final String INSTITUTION_NAME = "styk.pl";
    private static final String USERNAME = "Waldek";
    private static final String REPRESENTATIVE_NAME = "Janusz";

    private RepresentativeController representativeController;
    private UserService userService;
    private InstitutionService institutionService;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        institutionService = mock(InstitutionService.class);
        initMocks(this);
        representativeController = new RepresentativeController(userService, institutionService);
    }

    @Test
    void getRepresentatives() {
        //      given
        InstitutionEntity institutionEntity = new InstitutionEntity();
        User user = new User();
        when(institutionService.getInstitutionEntityByInstitutionName(INSTITUTION_NAME)).thenReturn(institutionEntity);
        when(userService.getByWorkPlaces(institutionEntity)).thenReturn(Arrays.asList(user));
        //      when
        List<User> result = representativeController.getRepresentatives(USERNAME, INSTITUTION_NAME);
        //      then
        assertEquals(1, result.size());
        verify(institutionService, times(1)).getInstitutionEntityByInstitutionName(INSTITUTION_NAME);
        verify(userService, times(1)).getByWorkPlaces(institutionEntity);
        verifyNoMoreInteractions(institutionService);
        verifyNoMoreInteractions(userService);
    }

    @Test
    void getRepresentative() {
        //      given
        InstitutionEntity institutionEntity = new InstitutionEntity();
        User user = new User();
        user.setUsername(REPRESENTATIVE_NAME);
        when(institutionService.getInstitutionEntityByInstitutionName(INSTITUTION_NAME)).thenReturn(institutionEntity);
        when(userService.getByUsernameAndWorkPlaces(REPRESENTATIVE_NAME, institutionEntity)).thenReturn(user);
        //      when
        User result = representativeController.getRepresentative(USERNAME, INSTITUTION_NAME, REPRESENTATIVE_NAME);
        //      then
        assertEquals(REPRESENTATIVE_NAME, result.getUsername());
        verify(institutionService, times(1)).getInstitutionEntityByInstitutionName(INSTITUTION_NAME);
        verify(userService, times(1)).getByUsernameAndWorkPlaces(REPRESENTATIVE_NAME, institutionEntity);
        verifyNoMoreInteractions(institutionService);
        verifyNoMoreInteractions(userService);
    }
}