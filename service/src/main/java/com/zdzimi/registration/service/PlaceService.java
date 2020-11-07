package com.zdzimi.registration.service;

import com.zdzimi.registration.core.model.Institution;
import com.zdzimi.registration.core.model.Place;
import com.zdzimi.registration.data.entity.InstitutionEntity;
import com.zdzimi.registration.data.entity.PlaceEntity;
import com.zdzimi.registration.data.exception.PlaceNotFoundException;
import com.zdzimi.registration.data.repository.PlaceRepository;
import com.zdzimi.registration.service.mapper.PlaceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaceService {

    private PlaceRepository placeRepository;
    private InstitutionService institutionService;
    private PlaceMapper placeMapper;

    @Autowired
    public PlaceService(PlaceRepository placeRepository, InstitutionService institutionService, PlaceMapper placeMapper) {
        this.placeRepository = placeRepository;
        this.institutionService = institutionService;
        this.placeMapper = placeMapper;
    }

    public List<Place> getPlaces(String institutionName) {
        InstitutionEntity institutionEntity = institutionService.getInstitutionEntityByInstitutionName(institutionName);
        return placeRepository.findByInstitution(institutionEntity).stream()
                .map(placeMapper::convertToPlace)
                .collect(Collectors.toList());
    }

    public Place addNewPlace(String institutionName, Place place) {
        Institution institution = institutionService.getByInstitutionName(institutionName);
        place.setInstitution(institution);
        PlaceEntity placeEntity = placeMapper.convertToPlaceEntity(place);
        PlaceEntity savedPlaceEntity = placeRepository.save(placeEntity);
        return placeMapper.convertToPlace(savedPlaceEntity);
    }

    public Place getPlace(String institutionName, String placeName) {
        InstitutionEntity institutionEntity = institutionService.getInstitutionEntityByInstitutionName(institutionName);
        PlaceEntity placeEntity = placeRepository.findByInstitutionAndPlaceName(institutionEntity, placeName)
                .orElseThrow(() -> new PlaceNotFoundException(institutionName, placeName));
        return placeMapper.convertToPlace(placeEntity);
    }
}
