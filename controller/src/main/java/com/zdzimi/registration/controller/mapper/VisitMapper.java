package com.zdzimi.registration.controller.mapper;

import com.zdzimi.registration.core.model.Visit;
import com.zdzimi.registration.data.entity.PlaceEntity;
import com.zdzimi.registration.data.entity.UserEntity;
import com.zdzimi.registration.data.entity.VisitEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Time;
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

        Date visitDate = visitEntity.getVisitDate();
        Time visitTime = visitEntity.getVisitTime();
        LocalDateTime dateTime = LocalDateTime.of(visitDate.toLocalDate(), visitTime.toLocalTime());

        visit.setVisitDateTime(dateTime);
        return visit;
    }

    public VisitEntity convertToVisitEntity(Visit visit) {
        VisitEntity visitEntity = new VisitEntity();
        visitEntity.setVisitId(visit.getVisitId());

        LocalDateTime visitDateTime = visit.getVisitDateTime();

        Date visitDate = Date.valueOf(visitDateTime.toLocalDate());
        visitEntity.setVisitDate(visitDate);

        Time visitTime = Time.valueOf(visitDateTime.toLocalTime());
        visitEntity.setVisitTime(visitTime);

        visitEntity.setVisitLength(visit.getVisitLength());

        UserMapper userMapper = new UserMapper(modelMapper);
        UserEntity userEntity = userMapper.convertToUserEntity(visit.getUser());
        visitEntity.setUser(userEntity);
        UserEntity representativeEntity = userMapper.convertToUserEntity(visit.getRepresentative());
        visitEntity.setRepresentative(representativeEntity);

        PlaceMapper placeMapper = new PlaceMapper(modelMapper);
        PlaceEntity placeEntity = placeMapper.convertToPlaceEntity(visit.getPlace());
        visitEntity.setPlace(placeEntity);

        return visitEntity;
    }
}
