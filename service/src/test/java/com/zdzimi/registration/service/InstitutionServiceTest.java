package com.zdzimi.registration.service;

import com.zdzimi.registration.core.model.Institution;
import com.zdzimi.registration.data.entity.InstitutionEntity;
import com.zdzimi.registration.data.exception.InstitutionNotFoundException;
import com.zdzimi.registration.data.repository.InstitutionRepository;
import com.zdzimi.registration.service.mapper.InstitutionMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class InstitutionServiceTest {

    private static final long INSTITUTION_ID = 1;
    private static final String INSTITUTION_NAME = "SIFT.UI";

    private InstitutionService institutionService;
    private InstitutionRepository institutionRepository;
    private InstitutionMapper institutionMapper = new InstitutionMapper(new ModelMapper());

    @BeforeEach
    void setUp() {
        institutionRepository = mock(InstitutionRepository.class);
        initMocks(this);
        institutionService = new InstitutionService(institutionRepository, institutionMapper);
    }

    @Test
    void shouldGetInstitutionByName() {
        //      given
        InstitutionEntity institutionEntity = new InstitutionEntity();
        institutionEntity.setInstitutionId(INSTITUTION_ID);
        institutionEntity.setInstitutionName(INSTITUTION_NAME);
        when(institutionRepository.findByInstitutionName(INSTITUTION_NAME)).thenReturn(Optional.of(institutionEntity));
        //      when
        Institution institution = institutionService.getInstitutionByName(INSTITUTION_NAME);
        //      then
        assertEquals(INSTITUTION_ID, institution.getInstitutionId());
        assertEquals(INSTITUTION_NAME, institution.getInstitutionName());
        verify(institutionRepository, times(1)).findByInstitutionName(INSTITUTION_NAME);
        verifyNoMoreInteractions(institutionRepository);
    }

    @Test
    void shouldThrowInstitutionNotFoundException() {
        //      given
        when(institutionRepository.findByInstitutionName(INSTITUTION_NAME)).thenReturn(Optional.empty());
        //      when
        InstitutionNotFoundException exception = assertThrows(
                InstitutionNotFoundException.class, () -> institutionService.getInstitutionByName(INSTITUTION_NAME)
        );
        //      then
        assertEquals("Could not find institution: " + INSTITUTION_NAME, exception.getMessage());
        verify(institutionRepository, times(1)).findByInstitutionName(INSTITUTION_NAME);
        verifyNoMoreInteractions(institutionRepository);
    }
}