package com.zdzimi.registration.service;

import com.zdzimi.registration.core.model.Place;
import com.zdzimi.registration.data.entity.InstitutionEntity;
import com.zdzimi.registration.data.entity.PlaceEntity;
import com.zdzimi.registration.data.entity.VisitEntity;
import com.zdzimi.registration.data.exception.PlaceNotFoundException;
import com.zdzimi.registration.data.repository.PlaceRepository;
import com.zdzimi.registration.service.exception.DeletePlaceException;
import com.zdzimi.registration.service.exception.PlaceNameException;
import com.zdzimi.registration.service.mapper.PlaceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final PlaceMapper placeMapper;
    private final VisitService visitService;

    @Autowired
    public PlaceService(PlaceRepository placeRepository, PlaceMapper placeMapper, VisitService visitService) {
        this.placeRepository = placeRepository;
        this.placeMapper = placeMapper;
        this.visitService = visitService;
    }

    public List<Place> getPlaces(InstitutionEntity institutionEntity) {
        return placeRepository.findByInstitution(institutionEntity).stream()
                .map(placeMapper::convertToPlace)
                .collect(Collectors.toList());
    }

    public Place addNewPlace(InstitutionEntity institutionEntity, Place place) {
        checkIfItDoesNotRepeatItself(institutionEntity, place);
        PlaceEntity placeEntity = placeMapper.convertToPlaceEntity(place);
        placeEntity.setInstitution(institutionEntity);
        PlaceEntity savedPlaceEntity = placeRepository.save(placeEntity);
        return placeMapper.convertToPlace(savedPlaceEntity);
    }

    private void checkIfItDoesNotRepeatItself(InstitutionEntity institutionEntity, Place place) {
        Collection<PlaceEntity> places = institutionEntity.getPlaces();
        for (PlaceEntity placeEntity : places) {
            if (placeEntity.getPlaceName().equals(place.getPlaceName())) {
                throw new PlaceNameException(place.getPlaceName());
            }
        }
    }

    public Place getPlace(InstitutionEntity institutionEntity, String placeName) {
        PlaceEntity placeEntity = getPlaceEntity(institutionEntity, placeName);
        return placeMapper.convertToPlace(placeEntity);
    }

    public PlaceEntity getPlaceEntity(InstitutionEntity institutionEntity, String placeName) {
        return placeRepository.findByInstitutionAndPlaceName(institutionEntity, placeName)
                    .orElseThrow(() -> new PlaceNotFoundException(institutionEntity.getInstitutionName(), placeName));
    }

    //  todo test
    public void delete(InstitutionEntity institutionEntity, PlaceEntity placeEntity) {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        List<VisitEntity> visitEntities = visitService
                .getByInstitutionAndPlaceNameAndVisitEndIsAfter(institutionEntity, placeEntity.getPlaceName(), now);
        if (!visitEntities.isEmpty()) {
            throw new DeletePlaceException(placeEntity.getPlaceName());
        }
        placeRepository.delete(placeEntity);
    }
}
