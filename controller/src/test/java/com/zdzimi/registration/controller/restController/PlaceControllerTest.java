package com.zdzimi.registration.controller.restController;

import com.zdzimi.registration.core.model.Place;
import com.zdzimi.registration.service.PlaceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class PlaceControllerTest {

    private static final String USERNAME = "Hans";
    private static final String INSTITUTION_NAME = "insName";
    private static final String PLACE_NAME = "place";

    private PlaceController placeController;
    private PlaceService placeService;

    @BeforeEach
    void setUp() {
        placeService = mock(PlaceService.class);
        initMocks(this);
        placeController = new PlaceController(placeService);
    }

    @Test
    void shouldGetPlaces() {
        //      given
        Place place = new Place();
        when(placeService.getPlaces(INSTITUTION_NAME)).thenReturn(Arrays.asList(place));
        //      when
        List<Place> result = placeController.getPlaces(USERNAME, INSTITUTION_NAME);
        //      then
        assertEquals(1, result.size());
        verify(placeService, times(1)).getPlaces(INSTITUTION_NAME);
        verifyNoMoreInteractions(placeService);
    }

    @Test
    void shouldAddNewPlace() {
        //      given
        Place place = new Place();
        when(placeService.addNewPlace(INSTITUTION_NAME, place)).thenReturn(place);
        //      when
        Place result = placeController.addNewPlace(USERNAME, INSTITUTION_NAME, place);
        //      then
        assertEquals(place, result);
        verify(placeService, times(1)).addNewPlace(INSTITUTION_NAME, place);
        verifyNoMoreInteractions(placeService);
    }

    @Test
    void shouldGetPlace() {
        //      given
        Place place = new Place();
        when(placeService.getPlace(INSTITUTION_NAME, PLACE_NAME)).thenReturn(place);
        //      when
        Place result = placeController.getPlace(USERNAME, INSTITUTION_NAME, PLACE_NAME);
        //      then
        assertEquals(place, result);
        verify(placeService, times(1)).getPlace(INSTITUTION_NAME, PLACE_NAME);
        verifyNoMoreInteractions(placeService);
    }
}