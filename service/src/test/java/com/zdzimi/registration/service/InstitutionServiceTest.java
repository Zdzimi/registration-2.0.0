package com.zdzimi.registration.service;

import com.zdzimi.registration.core.model.Institution;
import com.zdzimi.registration.data.entity.InstitutionEntity;
import com.zdzimi.registration.data.entity.UserEntity;
import com.zdzimi.registration.data.exception.InstitutionNotFoundException;
import com.zdzimi.registration.data.repository.InstitutionRepository;
import com.zdzimi.registration.service.mapper.InstitutionMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class InstitutionServiceTest {

    private static final long INSTITUTION_ID = 1;
    private static final String INSTITUTION_NAME = "SIFT.UI";
    private static final String USERNAME = "Jan";

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
    void shouldGetAll() {
        //      given
        InstitutionEntity institutionEntity = new InstitutionEntity();
        when(institutionRepository.findAll()).thenReturn(Arrays.asList(institutionEntity));
        //      when
        List<Institution> result = institutionService.getAll();
        //      then
        assertEquals(1, result.size());
        verify(institutionRepository, times(1)).findAll();
        verifyNoMoreInteractions(institutionRepository);
    }

    @Test
    void shouldGetRecognized() {
        //      given
        UserEntity userEntity = new UserEntity();
        InstitutionEntity institutionEntity = new InstitutionEntity();
        when(institutionRepository.findByUsers(userEntity)).thenReturn(Arrays.asList(institutionEntity));
        //      when
        List<Institution> result = institutionService.getRecognized(userEntity);
        //      then
        assertEquals(1, result.size());
        verify(institutionRepository, times(1)).findByUsers(userEntity);
        verifyNoMoreInteractions(institutionRepository);
    }

    @Test
    void shouldGetByInstitutionName() {
        //      given
        InstitutionEntity institutionEntity = new InstitutionEntity();
        institutionEntity.setInstitutionId(INSTITUTION_ID);
        institutionEntity.setInstitutionName(INSTITUTION_NAME);
        when(institutionRepository.findByInstitutionName(INSTITUTION_NAME)).thenReturn(Optional.of(institutionEntity));
        //      when
        Institution institution = institutionService.getByInstitutionName(INSTITUTION_NAME);
        //      then
        assertEquals(INSTITUTION_ID, institution.getInstitutionId());
        assertEquals(INSTITUTION_NAME, institution.getInstitutionName());
        verify(institutionRepository, times(1)).findByInstitutionName(INSTITUTION_NAME);
        verifyNoMoreInteractions(institutionRepository);
    }

    @Test
    void shouldGetInstitutionEntityByInstitutionName() {
        //      given
        InstitutionEntity institutionEntity = new InstitutionEntity();
        when(institutionRepository.findByInstitutionName(INSTITUTION_NAME)).thenReturn(Optional.of(institutionEntity));
        //      when
        InstitutionEntity result = institutionService.getInstitutionEntityByInstitutionName(INSTITUTION_NAME);
        //      then
        assertEquals(institutionEntity, result);
    }

    @Test
    void shouldThrowInstitutionNotFoundException() {
        //      given
        when(institutionRepository.findByInstitutionName(INSTITUTION_NAME)).thenReturn(Optional.empty());
        //      when
        InstitutionNotFoundException exception = assertThrows(
                InstitutionNotFoundException.class, () -> institutionService.getByInstitutionName(INSTITUTION_NAME)
        );
        //      then
        assertEquals("Could not find institution: " + INSTITUTION_NAME, exception.getMessage());
        verify(institutionRepository, times(1)).findByInstitutionName(INSTITUTION_NAME);
        verifyNoMoreInteractions(institutionRepository);
    }

    @Test
    void shouldGetWorkPlace() {
        //      given
        UserEntity userEntity = new UserEntity();
        InstitutionEntity institutionEntity = new InstitutionEntity();
        institutionEntity.setInstitutionName(INSTITUTION_NAME);
        when(institutionRepository.findByInstitutionNameAndRepresentatives(INSTITUTION_NAME, userEntity)).thenReturn(Optional.of(institutionEntity));
        //      when
        Institution result = institutionService.getWorkPlace(userEntity, INSTITUTION_NAME);
        //      then
        assertEquals(INSTITUTION_NAME, result.getInstitutionName());
        verify(institutionRepository, times(1)).findByInstitutionNameAndRepresentatives(INSTITUTION_NAME, userEntity);
        verifyNoMoreInteractions(institutionRepository);
    }

    @Test
    void shouldThrowInstitutionNotFoundException_whenFindByInstitutionNameAndRepresentatives() {
        //      given
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(USERNAME);
        when(institutionRepository.findByInstitutionNameAndRepresentatives(INSTITUTION_NAME, userEntity)).thenReturn(Optional.empty());
        //      when
        InstitutionNotFoundException exception = assertThrows(
                InstitutionNotFoundException.class, () -> institutionService.getWorkPlace(userEntity, INSTITUTION_NAME)
        );
        //      then
        assertEquals("Could not find institution: " + INSTITUTION_NAME + " where " + USERNAME + " is representative", exception.getMessage());
        verify(institutionRepository, times(1)).findByInstitutionNameAndRepresentatives(INSTITUTION_NAME, userEntity);
        verifyNoMoreInteractions(institutionRepository);
    }

    @Test
    void shouldGetWorkPlaces() {
        //      given
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(USERNAME);
        InstitutionEntity institutionEntity = new InstitutionEntity();
        institutionEntity.setInstitutionName(INSTITUTION_NAME);
        when(institutionRepository.findByRepresentatives(userEntity)).thenReturn(Arrays.asList(institutionEntity));
        //      when
        List<Institution> result = institutionService.getWorkPlaces(userEntity);
        //      then
        assertEquals(1, result.size());
        assertEquals(INSTITUTION_NAME, result.get(0).getInstitutionName());
        verify(institutionRepository, times(1)).findByRepresentatives(userEntity);
        verifyNoMoreInteractions(institutionRepository);
    }
}