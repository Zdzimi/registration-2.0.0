package com.zdzimi.registration.service.mapper;

import com.zdzimi.registration.core.model.Place;
import com.zdzimi.registration.data.entity.InstitutionEntity;
import com.zdzimi.registration.data.entity.PlaceEntity;
import com.zdzimi.registration.data.entity.VisitEntity;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.Arrays;

import static com.zdzimi.registration.service.mapper.InstitutionMapperTest.*;
import static org.junit.jupiter.api.Assertions.*;

class PlaceMapperTest {

    static final long PLACE_ID = 3;
    static final String PLACE_NAME = "place_1";

    private PlaceMapper placeMapper = new PlaceMapper(new ModelMapper());

    @Test
    void shouldConvertToPlace() {
        PlaceEntity placeEntity = getPlaceEntity();

        Place result = placeMapper.convertToPlace(placeEntity);

        assertEquals(PLACE_ID, result.getPlaceId());
        assertEquals(PLACE_NAME, result.getPlaceName());
    }

    @Test
    void shouldConvertToPlaceEntity() {
        Place place = getPlace();

        PlaceEntity result = placeMapper.convertToPlaceEntity(place);

        assertEquals(PLACE_ID, result.getPlaceId());
        assertEquals(PLACE_NAME, result.getPlaceName());
        assertNull(result.getInstitution());
        assertTrue(result.getVisits().isEmpty());
    }

    static PlaceEntity getPlaceEntity() {
        PlaceEntity placeEntity = new PlaceEntity();
        placeEntity.setPlaceId(PLACE_ID);
        placeEntity.setPlaceName(PLACE_NAME);

        InstitutionEntity institutionEntity = getInstitutionEntity();

        placeEntity.setInstitution(institutionEntity);
        placeEntity.setVisits(Arrays.asList(new VisitEntity(), new VisitEntity()));
        return placeEntity;
    }

    static Place getPlace() {
        Place place = new Place();
        place.setPlaceId(PLACE_ID);
        place.setPlaceName(PLACE_NAME);
        return place;
    }

}