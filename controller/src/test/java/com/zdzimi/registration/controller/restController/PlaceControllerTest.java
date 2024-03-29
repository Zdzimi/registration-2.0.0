package com.zdzimi.registration.controller.restController;

import com.zdzimi.registration.controller.link.LinkCreator;
import com.zdzimi.registration.core.model.Place;
import com.zdzimi.registration.data.entity.InstitutionEntity;
import com.zdzimi.registration.service.InstitutionService;
import com.zdzimi.registration.service.PlaceService;
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
class PlaceControllerTest {

    private static final String USERNAME = "Hans";
    private static final String INSTITUTION_NAME = "insName";
    private static final String PLACE_NAME = "place";

    @Mock
    private LoggedUserProvider loggedUserProvider;
    @Mock
    private PlaceService placeService;
    @Mock
    private InstitutionService institutionService;
    @Mock
    private LinkCreator linkCreator;
    @InjectMocks
    private PlaceController placeController;

    @Test
    void shouldGetPlaces() {
        //      given
        InstitutionEntity institutionEntity = new InstitutionEntity();
        Place place = new Place();
        when(institutionService.getInstitutionEntityByInstitutionName(INSTITUTION_NAME)).thenReturn(institutionEntity);
        when(placeService.getPlaces(institutionEntity)).thenReturn(Arrays.asList(place));
        //      when
        List<Place> result = placeController.getPlaces(USERNAME, INSTITUTION_NAME);
        //      then
        assertEquals(1, result.size());
        verify(institutionService, times(1)).getInstitutionEntityByInstitutionName(INSTITUTION_NAME);
        verify(placeService, times(1)).getPlaces(institutionEntity);
        verifyNoMoreInteractions(institutionService);
        verifyNoMoreInteractions(placeService);
    }

    @Test
    void shouldAddNewPlace() {
        //      given
        InstitutionEntity institutionEntity = new InstitutionEntity();
        Place place = new Place();
        when(institutionService.getInstitutionEntityByInstitutionName(INSTITUTION_NAME)).thenReturn(institutionEntity);
        when(placeService.addNewPlace(institutionEntity, place)).thenReturn(place);
        //      when
        Place result = placeController.addNewPlace(place, USERNAME, INSTITUTION_NAME);
        //      then
        assertEquals(place, result);
        verify(institutionService, times(1)).getInstitutionEntityByInstitutionName(INSTITUTION_NAME);
        verify(placeService, times(1)).addNewPlace(institutionEntity, place);
        verifyNoMoreInteractions(institutionService);
        verifyNoMoreInteractions(placeService);
    }

    @Test
    void shouldGetPlace() {
        //      given
        InstitutionEntity institutionEntity = new InstitutionEntity();
        Place place = new Place();
        when(institutionService.getInstitutionEntityByInstitutionName(INSTITUTION_NAME)).thenReturn(institutionEntity);
        when(placeService.getPlace(institutionEntity, PLACE_NAME)).thenReturn(place);
        //      when
        Place result = placeController.getPlace(USERNAME, INSTITUTION_NAME, PLACE_NAME);
        //      then
        assertEquals(place, result);
        verify(institutionService, times(1)).getInstitutionEntityByInstitutionName(INSTITUTION_NAME);
        verify(placeService, times(1)).getPlace(institutionEntity, PLACE_NAME);
        verifyNoMoreInteractions(institutionService);
        verifyNoMoreInteractions(placeService);
    }
}