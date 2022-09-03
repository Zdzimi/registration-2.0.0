package com.zdzimi.registration.controller.restController;

import com.zdzimi.registration.controller.link.LinkCreator;
import com.zdzimi.registration.core.model.Institution;
import com.zdzimi.registration.data.entity.UserEntity;
import com.zdzimi.registration.service.InstitutionService;
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
class InstitutionControllerTest {

    private static final String INSTITUTION_NAME = "KIFF.COM";
    private static final String USERNAME = "Janusz";

    @Mock
    private LoggedUserProvider loggedUserProvider;
    @Mock
    private InstitutionService institutionService;
    @Mock
    private LinkCreator linkCreator;
    @InjectMocks
    private InstitutionController institutionController;

    @Test
    void shouldGetInstitutions() {
        //      given
        Institution institution = new Institution();
        when(institutionService.getAll()).thenReturn(Arrays.asList(institution));
        //      when
        List<Institution> result = institutionController.getInstitutions(USERNAME);
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
        when(loggedUserProvider.provideLoggedUser(USERNAME)).thenReturn(userEntity);
        when(institutionService.getRecognized(userEntity)).thenReturn(Arrays.asList(institution));
        //      when
        List<Institution> result = institutionController.getRecognizedInstitutions(USERNAME);
        //      then
        assertEquals(1, result.size());
        verify(loggedUserProvider, times(1)).provideLoggedUser(USERNAME);
        verify(institutionService, times(1)).getRecognized(userEntity);
        verifyNoMoreInteractions(loggedUserProvider);
        verifyNoMoreInteractions(institutionService);
    }

    @Test
    void shouldGetInstitution() {
        //      given
        Institution institution = new Institution();
        institution.setInstitutionName(INSTITUTION_NAME);
        when(institutionService.getByInstitutionName(INSTITUTION_NAME)).thenReturn(institution);
        //      when
        Institution result = institutionController.getInstitution(USERNAME, INSTITUTION_NAME);
        //      then
        assertEquals(INSTITUTION_NAME, result.getInstitutionName());
        verify(institutionService, times(1)).getByInstitutionName(INSTITUTION_NAME);
        verifyNoMoreInteractions(institutionService);
    }
}