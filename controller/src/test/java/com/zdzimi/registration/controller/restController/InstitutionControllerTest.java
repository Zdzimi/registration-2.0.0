package com.zdzimi.registration.controller.restController;

import com.zdzimi.registration.core.model.Institution;
import com.zdzimi.registration.service.InstitutionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class InstitutionControllerTest {

    private static final String INSTITUTION_NAME = "KIFF.COM";

    private InstitutionController institutionController;
    private InstitutionService institutionService;

    @BeforeEach
    void setUp() {
        institutionService = mock(InstitutionService.class);
        initMocks(this);
        institutionController = new InstitutionController(institutionService);
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