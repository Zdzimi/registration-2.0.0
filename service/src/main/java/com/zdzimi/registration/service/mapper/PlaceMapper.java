package com.zdzimi.registration.service.mapper;

import com.zdzimi.registration.core.model.Place;
import com.zdzimi.registration.data.entity.InstitutionEntity;
import com.zdzimi.registration.data.entity.PlaceEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class PlaceMapper {

    private ModelMapper modelMapper;

    @Autowired
    public PlaceMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Place convertToPlace(PlaceEntity placeEntity) {
        return modelMapper.map(placeEntity, Place.class);
    }

    public PlaceEntity convertToPlaceEntity(Place place) {
        PlaceEntity placeEntity = modelMapper.map(place, PlaceEntity.class);
        placeEntity.setVisits(Collections.emptyList());
        return placeEntity;
    }
}
