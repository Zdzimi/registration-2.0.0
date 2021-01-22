package com.zdzimi.registration.service;

import com.zdzimi.registration.core.model.Place;
import com.zdzimi.registration.data.entity.InstitutionEntity;
import com.zdzimi.registration.data.entity.PlaceEntity;
import com.zdzimi.registration.data.entity.VisitEntity;
import com.zdzimi.registration.data.exception.PlaceNotFoundException;
import com.zdzimi.registration.data.repository.PlaceRepository;
import com.zdzimi.registration.service.mapper.PlaceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaceService {

    private PlaceRepository placeRepository;
    private PlaceMapper placeMapper;

    @Autowired
    public PlaceService(PlaceRepository placeRepository, PlaceMapper placeMapper) {
        this.placeRepository = placeRepository;
        this.placeMapper = placeMapper;
    }

    public List<Place> getPlaces(InstitutionEntity institutionEntity) {
        return placeRepository.findByInstitution(institutionEntity).stream()
                .map(placeMapper::convertToPlace)
                .collect(Collectors.toList());
    }

    public Place addNewPlace(InstitutionEntity institutionEntity, Place place) {
        PlaceEntity placeEntity = placeMapper.convertToPlaceEntity(place);
        placeEntity.setInstitution(institutionEntity);
        PlaceEntity savedPlaceEntity = placeRepository.save(placeEntity);
        return placeMapper.convertToPlace(savedPlaceEntity);
    }

    public Place getPlace(InstitutionEntity institutionEntity, String placeName) {
        PlaceEntity placeEntity = getPlaceEntity(institutionEntity, placeName);
        return placeMapper.convertToPlace(placeEntity);
    }

    public PlaceEntity getPlaceEntity(InstitutionEntity institutionEntity, String placeName) {
        return placeRepository.findByInstitutionAndPlaceName(institutionEntity, placeName)
                    .orElseThrow(() -> new PlaceNotFoundException(institutionEntity.getInstitutionName(), placeName));
    }

    //  fixme + test
    public void delete(PlaceEntity placeEntity) {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        List<VisitEntity> visitEntities = placeEntity.getVisits().stream()
                .filter(visitEntity -> visitEntity.getVisitStart().after(now))
                .collect(Collectors.toList());
        if (visitEntities.isEmpty()) {
            placeRepository.delete(placeEntity);
        }
    }
}
