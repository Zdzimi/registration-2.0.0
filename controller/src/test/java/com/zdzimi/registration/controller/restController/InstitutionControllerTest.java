package com.zdzimi.registration.controller.restController;

import com.zdzimi.registration.core.model.Institution;
import com.zdzimi.registration.data.entity.InstitutionEntity;
import com.zdzimi.registration.service.InstitutionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    @BeforeEach
    void setUp() {
        institutionService = mock(InstitutionService.class);
        initMocks(this);
        institutionController = new InstitutionController(institutionService);
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
        Institution institution = new Institution();
        when(institutionService.getRecognized(USERNAME)).thenReturn(Arrays.asList(institution));
        //      when
        List<Institution> result = institutionController.getRecognizedInstitutions(USERNAME);
        //      then
        assertEquals(1, result.size());
        verify(institutionService, times(1)).getRecognized(USERNAME);
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
}