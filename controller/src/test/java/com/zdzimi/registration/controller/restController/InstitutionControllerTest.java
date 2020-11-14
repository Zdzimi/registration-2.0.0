package com.zdzimi.registration.controller.restController;

import com.zdzimi.registration.core.model.Institution;
import com.zdzimi.registration.data.entity.InstitutionEntity;
import com.zdzimi.registration.data.entity.UserEntity;
import com.zdzimi.registration.service.InstitutionService;
import com.zdzimi.registration.service.UserService;
import com.zdzimi.registration.service.mapper.InstitutionMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class InstitutionControllerTest {

    private static final String INSTITUTION_NAME = "KIFF.COM";
    private static final String USERNAME = "Janusz";

    private InstitutionController institutionController;
    private InstitutionService institutionService;
    private UserService userService;
    private InstitutionMapper institutionMapper = new InstitutionMapper(new ModelMapper());

    @BeforeEach
    void setUp() {
        institutionService = mock(InstitutionService.class);
        userService = mock(UserService.class);
        initMocks(this);
        institutionController = new InstitutionController(institutionService, userService, institutionMapper);
    }

    @Test
    void shouldGetInstitutions() {
        //      given
        Institution institution = new Institution();
        when(institutionService.getAll()).thenReturn(Arrays.asList(institution));
        //      when
        List<Institution> result = institutionController.getInstitutions();
        //      then
        assertEquals(1, result.size());
        verify(institutionService, times(1)).getAll();
        verifyNoMoreInteractions(institutionService);
    }

    @Test
    void shouldGetRecognizedInstitutions() {
        //      given
        UserEntity userEntity = new UserEntity();
        Institution institution = new Institution();
        when(userService.getUserEntityByUsername(USERNAME)).thenReturn(userEntity);
        when(institutionService.getRecognized(userEntity)).thenReturn(Arrays.asList(institution));
        //      when
        List<Institution> result = institutionController.getRecognizedInstitutions(USERNAME);
        //      then
        assertEquals(1, result.size());
        verify(userService, times(1)).getUserEntityByUsername(USERNAME);
        verify(institutionService, times(1)).getRecognized(userEntity);
        verifyNoMoreInteractions(userService);
        verifyNoMoreInteractions(institutionService);
    }

    @Test
    void shouldGetInstitution() {
        //      given
        Institution institution = new Institution();
        institution.setInstitutionName(INSTITUTION_NAME);
        when(institutionService.getByInstitutionName(INSTITUTION_NAME)).thenReturn(institution);
        //      when
        Institution result = institutionController.getInstitution(INSTITUTION_NAME);
        //      then
        assertEquals(INSTITUTION_NAME, result.getInstitutionName());
        verify(institutionService, times(1)).getByInstitutionName(INSTITUTION_NAME);
        verifyNoMoreInteractions(institutionService);
    }
    @Test
    void shouldCreateNewInstitution() {
        //      given
        Institution institutionBeforeCreate = new Institution();
        InstitutionEntity institutionEntityAfterCreate = new InstitutionEntity();
        when(institutionService.createNewInstitution(institutionBeforeCreate)).thenReturn(institutionEntityAfterCreate);
        //      when
        institutionController.createNewInstitution(institutionBeforeCreate, USERNAME);
        //      then
        verify(userService, times(1)).addWorkPlace(USERNAME, institutionEntityAfterCreate);
        verify(institutionService, times(1)).createNewInstitution(institutionBeforeCreate);
        verifyNoMoreInteractions(userService);
        verifyNoMoreInteractions(institutionService);
    }
}