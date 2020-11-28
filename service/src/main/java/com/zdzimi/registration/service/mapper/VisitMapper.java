package com.zdzimi.registration.service.mapper;

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

    @Autowired
    public VisitMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Visit convertToVisit(VisitEntity visitEntity) {
        Visit visit = modelMapper.map(visitEntity, Visit.class);
        Timestamp visitStart = visitEntity.getVisitStart();
        visit.setVisitStart(visitStart.toLocalDateTime());
        Timestamp visitEnd = visitEntity.getVisitEnd();
        visit.setVisitEnd(visitEnd.toLocalDateTime());
        return visit;
    }

    public VisitEntity convertToVisitEntity(Visit visit) {
        VisitEntity visitEntity = new VisitEntity();
        visitEntity.setVisitId(visit.getVisitId());

        LocalDateTime visitStart = visit.getVisitStart();
        visitEntity.setVisitStart(Timestamp.valueOf(visitStart));

        LocalDateTime visitEnd = visit.getVisitEnd();
        visitEntity.setVisitEnd(Timestamp.valueOf(visitEnd));

        UserMapper userMapper = new UserMapper(modelMapper);
        UserEntity userEntity = userMapper.convertToUserEntity(visit.getUser());
        visitEntity.setUser(userEntity);
        UserEntity representativeEntity = userMapper.convertToUserEntity(visit.getRepresentative());
        visitEntity.setRepresentative(representativeEntity);

        PlaceMapper placeMapper = new PlaceMapper(modelMapper);
        PlaceEntity placeEntity = placeMapper.convertToPlaceEntity(visit.getPlace());
        visitEntity.setPlace(placeEntity);

        InstitutionMapper institutionMapper = new InstitutionMapper(modelMapper);
        InstitutionEntity institutionEntity = institutionMapper.convertToInstitutionEntity(visit.getInstitution());
        visitEntity.setInstitution(institutionEntity);

        return visitEntity;
    }
}
