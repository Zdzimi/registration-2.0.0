package com.zdzimi.registration.service.mapper;

import com.zdzimi.registration.core.model.Place;
import com.zdzimi.registration.data.entity.InstitutionEntity;
import com.zdzimi.registration.data.entity.PlaceEntity;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static com.zdzimi.registration.service.mapper.InstitutionMapperTest.*;
import static org.junit.jupiter.api.Assertions.*;

class PlaceMapperTest {

    static final long PLACE_ID = 3;
    static final String PLACE_NAME = "place_1";

    private final PlaceMapper placeMapper = new PlaceMapper(new ModelMapper());

    @Test
    void shouldConvertToPlace() {
        PlaceEntity placeEntity = getPlaceEntity();

        Place result = placeMapper.convertToPlace(placeEntity);

        assertEquals(PLACE_NAME, result.getPlaceName());
    }

    @Test
    void shouldConvertToPlaceEntity() {
        Place place = getPlace();

        PlaceEntity result = placeMapper.convertToPlaceEntity(place);

        assertEquals(0, result.getPlaceId());
        assertEquals(PLACE_NAME, result.getPlaceName());
        assertNull(result.getInstitution());
    }

    static PlaceEntity getPlaceEntity() {
        PlaceEntity placeEntity = new PlaceEntity();
        placeEntity.setPlaceId(PLACE_ID);
        placeEntity.setPlaceName(PLACE_NAME);

        InstitutionEntity institutionEntity = getInstitutionEntity();
        placeEntity.setInstitution(institutionEntity);
        return placeEntity;
    }

    static Place getPlace() {
        Place place = new Place();
        place.setPlaceName(PLACE_NAME);
        return place;
    }

}