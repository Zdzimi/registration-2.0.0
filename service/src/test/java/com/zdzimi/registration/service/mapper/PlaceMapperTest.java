package com.zdzimi.registration.service.mapper;

import com.zdzimi.registration.core.model.Institution;
import com.zdzimi.registration.core.model.Place;
import com.zdzimi.registration.data.entity.InstitutionEntity;
import com.zdzimi.registration.data.entity.PlaceEntity;
import com.zdzimi.registration.data.entity.VisitEntity;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.Arrays;

import static com.zdzimi.registration.service.mapper.InstitutionMapperTest.*;
import static com.zdzimi.registration.service.mapper.InstitutionMapperTest.getInstitution;
import static org.junit.jupiter.api.Assertions.*;

class PlaceMapperTest {

    static final long PLACE_ID = 3;
    static final String PLACE_NAME = "place_1";

    private PlaceMapper placeMapper = new PlaceMapper(new ModelMapper());

    @Test
    void shouldConvertToPlace() {
        PlaceEntity placeEntity = getPlaceEntity();

        Place place = placeMapper.convertToPlace(placeEntity);

        assertEquals(PLACE_ID, place.getPlaceId());
        assertEquals(PLACE_NAME, place.getPlaceName());

        assertEquals(INSTITUTION_ID, place.getInstitution().getInstitutionId());
        assertEquals(INSTITUTION_NAME, place.getInstitution().getInstitutionName());
        assertEquals(PROVINCE, place.getInstitution().getProvince());
        assertEquals(CITY, place.getInstitution().getCity());
        assertEquals(STREET, place.getInstitution().getStreet());
        assertEquals(GATE_NUMBER, place.getInstitution().getGateNumber());
        assertEquals(PREMISES_NUMBER, place.getInstitution().getPremisesNumber());
        assertEquals(TYPE_OF_SERVICE, place.getInstitution().getTypeOfService());
        assertEquals(DESCRIPTION, place.getInstitution().getDescription());
    }

    @Test
    void shouldConvertToPlaceEntity() {
        Place place = getPlace();

        PlaceEntity placeEntity = placeMapper.convertToPlaceEntity(place);

        assertEquals(PLACE_ID, placeEntity.getPlaceId());
        assertEquals(PLACE_NAME, placeEntity.getPlaceName());
        assertEquals(INSTITUTION_ID, placeEntity.getInstitution().getInstitutionId());
        assertEquals(INSTITUTION_NAME, placeEntity.getInstitution().getInstitutionName());
        assertEquals(PROVINCE, placeEntity.getInstitution().getProvince());
        assertEquals(CITY, placeEntity.getInstitution().getCity());
        assertEquals(STREET, placeEntity.getInstitution().getStreet());
        assertEquals(GATE_NUMBER, placeEntity.getInstitution().getGateNumber());
        assertEquals(PREMISES_NUMBER, placeEntity.getInstitution().getPremisesNumber());
        assertEquals(TYPE_OF_SERVICE, placeEntity.getInstitution().getTypeOfService());
        assertEquals(DESCRIPTION, placeEntity.getInstitution().getDescription());
        assertTrue(placeEntity.getInstitution().getUsers().isEmpty());
        assertTrue(placeEntity.getInstitution().getRepresentatives().isEmpty());
        assertTrue(placeEntity.getInstitution().getPlaces().isEmpty());
        assertTrue(placeEntity.getVisits().isEmpty());
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

        Institution institution = getInstitution();

        place.setInstitution(institution);
        return place;
    }

}