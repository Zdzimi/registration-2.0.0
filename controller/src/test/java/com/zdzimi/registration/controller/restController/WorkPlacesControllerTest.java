package com.zdzimi.registration.controller.restController;

import com.zdzimi.registration.controller.link.LinkCreator;
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

class WorkPlacesControllerTest {

    private static final String USERNAME = "Dupek";
    private static final String INSTITUTION_NAME = "uuX";

    private WorkPlacesController workPlacesController;
    private InstitutionService institutionService;
    private UserService userService;
    private InstitutionMapper institutionMapper = new InstitutionMapper(new ModelMapper());
    private LinkCreator linkCreator;

    @BeforeEach
    void setUp() {
        institutionService = mock(InstitutionService.class);
        userService = mock(UserService.class);
        linkCreator = mock(LinkCreator.class);
        initMocks(this);
        workPlacesController = new WorkPlacesController(institutionService, userService, institutionMapper, linkCreator);
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

    @Test
    void shouldCreateNewInstitution() {
        //      given
        Institution institutionBeforeCreate = new Institution();
        InstitutionEntity institutionEntityAfterCreate = new InstitutionEntity();
        when(institutionService.createNewInstitution(institutionBeforeCreate)).thenReturn(institutionEntityAfterCreate);
        //      when
        Institution result = workPlacesController.createNewInstitution(institutionBeforeCreate, USERNAME);
        //      then
        verify(userService, times(1)).addWorkPlace(USERNAME, institutionEntityAfterCreate);
        verify(institutionService, times(1)).createNewInstitution(institutionBeforeCreate);
        verifyNoMoreInteractions(userService);
        verifyNoMoreInteractions(institutionService);
    }
}