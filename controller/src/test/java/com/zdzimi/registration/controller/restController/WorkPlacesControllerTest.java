package com.zdzimi.registration.controller.restController;

import com.zdzimi.registration.core.model.Institution;
import com.zdzimi.registration.service.InstitutionService;
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

    @BeforeEach
    void setUp() {
        institutionService = mock(InstitutionService.class);
        initMocks(this);
        workPlacesController = new WorkPlacesController(institutionService);
    }

    @Test
    void shouldGetWorkPlaces() {
        //      given
        Institution institution = new Institution();
        when(institutionService.getWorkPlaces(USERNAME)).thenReturn(Arrays.asList(institution));
        //      when
        List<Institution> result = workPlacesController.getWorkPlaces(USERNAME);
        //      then
        assertEquals(1, result.size());
        verify(institutionService, times(1)).getWorkPlaces(USERNAME);
        verifyNoMoreInteractions(institutionService);
    }

    @Test
    void shouldGetWorkPlace() {
        //      given
        Institution institution = new Institution();
        institution.setInstitutionName(INSTITUTION_NAME);
        when(institutionService.getWorkPlace(USERNAME, INSTITUTION_NAME)).thenReturn(institution);
        //      when
        Institution result = workPlacesController.getWorkPlace(USERNAME, INSTITUTION_NAME);
        //      then
        assertEquals(INSTITUTION_NAME, result.getInstitutionName());
        verify(institutionService, times(1)).getWorkPlace(USERNAME, INSTITUTION_NAME);
        verifyNoMoreInteractions(institutionService);
    }
}