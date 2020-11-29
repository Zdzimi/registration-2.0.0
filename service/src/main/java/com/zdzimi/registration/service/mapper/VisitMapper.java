package com.zdzimi.registration.service.mapper;

import com.zdzimi.registration.core.model.Institution;
import com.zdzimi.registration.core.model.Place;
import com.zdzimi.registration.core.model.User;
import com.zdzimi.registration.core.model.Visit;
import com.zdzimi.registration.data.entity.InstitutionEntity;
import com.zdzimi.registration.data.entity.PlaceEntity;
import com.zdzimi.registration.data.entity.UserEntity;
import com.zdzimi.registration.data.entity.VisitEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
public class VisitMapper {

    private ModelMapper modelMapper;
    private UserMapper userMapper;
    private PlaceMapper placeMapper;
    private InstitutionMapper institutionMapper;

    @Autowired
    public VisitMapper(ModelMapper modelMapper, UserMapper userMapper, PlaceMapper placeMapper, InstitutionMapper institutionMapper) {
        this.modelMapper = modelMapper;
        this.userMapper = userMapper;
        this.placeMapper = placeMapper;
        this.institutionMapper = institutionMapper;
    }

    public Visit convertToVisit(VisitEntity visitEntity) {
        Visit visit = modelMapper.map(visitEntity, Visit.class);

        Timestamp visitStart = visitEntity.getVisitStart();
        visit.setVisitStart(visitStart.toLocalDateTime());

        Timestamp visitEnd = visitEntity.getVisitEnd();
        visit.setVisitEnd(visitEnd.toLocalDateTime());

        User user = userMapper.convertToUser(visitEntity.getUser());
        visit.setUser(user);

        User representative = userMapper.convertToUser(visitEntity.getRepresentative());
        visit.setRepresentative(representative);

        Place place = placeMapper.convertToPlace(visitEntity.getPlace());
        visit.setPlace(place);

        Institution institution = institutionMapper.convertToInstitution(visitEntity.getInstitution());
        visit.setInstitution(institution);

        return visit;
    }

    public VisitEntity convertToVisitEntity(Visit visit) {
        VisitEntity visitEntity = new VisitEntity();
        visitEntity.setVisitId(visit.getVisitId());

        LocalDateTime visitStart = visit.getVisitStart();
        visitEntity.setVisitStart(Timestamp.valueOf(visitStart));

        LocalDateTime visitEnd = visit.getVisitEnd();
        visitEntity.setVisitEnd(Timestamp.valueOf(visitEnd));

        UserEntity userEntity = userMapper.convertToUserEntity(visit.getUser());
        visitEntity.setUser(userEntity);

        UserEntity representativeEntity = userMapper.convertToUserEntity(visit.getRepresentative());
        visitEntity.setRepresentative(representativeEntity);

        PlaceEntity placeEntity = placeMapper.convertToPlaceEntity(visit.getPlace());
        visitEntity.setPlace(placeEntity);

        InstitutionEntity institutionEntity = institutionMapper.convertToInstitutionEntity(visit.getInstitution());
        visitEntity.setInstitution(institutionEntity);

        return visitEntity;
    }
}
