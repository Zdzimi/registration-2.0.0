package com.zdzimi.registration.controller.restController;

import com.zdzimi.registration.controller.link.LinkCreator;
import com.zdzimi.registration.core.model.Institution;
import com.zdzimi.registration.data.entity.InstitutionEntity;
import com.zdzimi.registration.data.entity.UserEntity;
import com.zdzimi.registration.service.InstitutionService;
import com.zdzimi.registration.service.UserService;
import com.zdzimi.registration.service.mapper.InstitutionMapper;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class WorkPlacesControllerTest {

    private static final String USERNAME = "Dupek";
    private static final String INSTITUTION_NAME = "uuX";


    private LoggedUserProvider loggedUserProvider;
    private InstitutionService institutionService;
    private UserService userService;
    private final InstitutionMapper institutionMapper = new InstitutionMapper(new ModelMapper());;
    private WorkPlacesController workPlacesController;

    @BeforeEach
    void setUp() {
        loggedUserProvider = mock(LoggedUserProvider.class);
        institutionService = mock(InstitutionService.class);
        userService = mock(UserService.class);
        LinkCreator linkCreator = mock(LinkCreator.class);
        initMocks(this);
        workPlacesController = new WorkPlacesController(loggedUserProvider, institutionService, userService, institutionMapper,
            linkCreator);
    }

    @Test
    void shouldGetWorkPlaces() {
        //      given
        UserEntity userEntity = new UserEntity();
        Institution institution = new Institution();
        when(loggedUserProvider.provideLoggedUser(USERNAME)).thenReturn(userEntity);
        when(institutionService.getWorkPlaces(userEntity)).thenReturn(
            Collections.singletonList(institution));
        //      when
        List<Institution> result = workPlacesController.getWorkPlaces(USERNAME);
        //      then
        assertEquals(1, result.size());
        verify(loggedUserProvider, times(1)).provideLoggedUser(USERNAME);
        verify(institutionService, times(1)).getWorkPlaces(userEntity);
        verifyNoMoreInteractions(loggedUserProvider);
        verifyNoMoreInteractions(institutionService);
    }

    @Test
    void shouldGetWorkPlace() {
        //      given
        UserEntity userEntity = new UserEntity();
        Institution institution = new Institution();
        institution.setInstitutionName(INSTITUTION_NAME);
        when(loggedUserProvider.provideLoggedUser(USERNAME)).thenReturn(userEntity);
        when(institutionService.getWorkPlace(userEntity, INSTITUTION_NAME)).thenReturn(institution);
        //      when
        Institution result = workPlacesController.getWorkPlace(USERNAME, INSTITUTION_NAME);
        //      then
        assertEquals(INSTITUTION_NAME, result.getInstitutionName());
        verify(loggedUserProvider, times(1)).provideLoggedUser(USERNAME);
        verify(institutionService, times(1)).getWorkPlace(userEntity, INSTITUTION_NAME);
        verifyNoMoreInteractions(loggedUserProvider);
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