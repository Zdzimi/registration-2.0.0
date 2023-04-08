package com.zdzimi.registration.service.mapper;

import com.zdzimi.registration.core.model.Place;
import com.zdzimi.registration.data.entity.PlaceEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.util.HtmlUtils;

@RequiredArgsConstructor
@Component
public class PlaceMapper {

    private final ModelMapper modelMapper;

    public Place convertToPlace(PlaceEntity placeEntity) {
        Place place = new Place();
        place.setPlaceName(HtmlUtils.htmlEscape(placeEntity.getPlaceName()));
        return place;
    }

    public PlaceEntity convertToPlaceEntity(Place place) {
        return modelMapper.map(place, PlaceEntity.class);
    }
}
