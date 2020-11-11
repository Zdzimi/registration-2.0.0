package com.zdzimi.registration.service;

import com.zdzimi.registration.core.model.Place;
import com.zdzimi.registration.data.entity.InstitutionEntity;
import com.zdzimi.registration.data.entity.PlaceEntity;
import com.zdzimi.registration.data.exception.PlaceNotFoundException;
import com.zdzimi.registration.data.repository.PlaceRepository;
import com.zdzimi.registration.service.mapper.PlaceMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class PlaceServiceTest {

    private static final String INSTITUTION_NAME = "FHX.WW";
    private static final String PLACE_NAME = "room no 1";

    private PlaceService placeService;
    private PlaceRepository placeRepository;
    private PlaceMapper placeMapper = new PlaceMapper(new ModelMapper());

    @BeforeEach
    void setUp() {
        placeRepository = mock(PlaceRepository.class);
        initMocks(this);
        placeService = new PlaceService(placeRepository, placeMapper);
    }

    @Test
    void shouldGetPlaces() {
        //      given
        InstitutionEntity institutionEntity = new InstitutionEntity();
        PlaceEntity placeEntity = new PlaceEntity();
        when(placeRepository.findByInstitution(institutionEntity)).thenReturn(Arrays.asList(placeEntity));
        //      when
        List<Place> result = placeService.getPlaces(institutionEntity);
        //      then
        verify(placeRepository, times(1)).findByInstitution(institutionEntity);
        verifyNoMoreInteractions(placeRepository);
    }

    @Test
    void shouldAddNewPlace() {
        //      given
        InstitutionEntity institutionEntity = new InstitutionEntity();
        institutionEntity.setInstitutionName(INSTITUTION_NAME);

        Place placeBeforeSave = new Place();
        placeBeforeSave.setPlaceName(PLACE_NAME);

        PlaceEntity placeEntityAfterSave = new PlaceEntity();
        when(placeRepository.save(ArgumentMatchers.any(PlaceEntity.class))).thenReturn(placeEntityAfterSave);
        //      when
        Place result = placeService.addNewPlace(institutionEntity, placeBeforeSave);
        //      then
        verify(placeRepository, times(1)).save(ArgumentMatchers.any(PlaceEntity.class));
        verifyNoMoreInteractions(placeRepository);
    }

    @Test
    void shouldGetPlace() {
        //      given
        InstitutionEntity institutionEntity = new InstitutionEntity();
        PlaceEntity placeEntity = new PlaceEntity();
        when(placeRepository.findByInstitutionAndPlaceName(institutionEntity, PLACE_NAME)).thenReturn(Optional.of(placeEntity));
        //      when
        Place result = placeService.getPlace(institutionEntity, PLACE_NAME);
        //      then
        verify(placeRepository, times(1)).findByInstitutionAndPlaceName(institutionEntity, PLACE_NAME);
        verifyNoMoreInteractions(placeRepository);
    }

    @Test
    void shouldThrowPlaceNotFoundException() {
        //      given
        InstitutionEntity institutionEntity = new InstitutionEntity();
        institutionEntity.setInstitutionName(INSTITUTION_NAME);
        when(placeRepository.findByInstitutionAndPlaceName(institutionEntity, PLACE_NAME)).thenReturn(Optional.empty());
        //      when
        PlaceNotFoundException exception = assertThrows(
                PlaceNotFoundException.class, () -> placeService.getPlace(institutionEntity, PLACE_NAME)
        );
        //      then
        assertEquals("Could not find result for institution: " + INSTITUTION_NAME + ", and place: " + PLACE_NAME, exception.getMessage());
        verify(placeRepository, times(1)).findByInstitutionAndPlaceName(institutionEntity, PLACE_NAME);
        verifyNoMoreInteractions(placeRepository);
    }
}