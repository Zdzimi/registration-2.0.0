package com.zdzimi.registration.service;

import com.zdzimi.registration.core.model.Visit;
import com.zdzimi.registration.data.entity.InstitutionEntity;
import com.zdzimi.registration.data.entity.UserEntity;
import com.zdzimi.registration.data.entity.VisitEntity;
import com.zdzimi.registration.data.exception.VisitNotFoundException;
import com.zdzimi.registration.data.repository.VisitRepository;
import com.zdzimi.registration.service.mapper.VisitMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VisitService {

    private VisitRepository visitRepository;
    private VisitMapper visitMapper;

    @Autowired
    public VisitService(VisitRepository visitRepository, VisitMapper visitMapper) {
        this.visitRepository = visitRepository;
        this.visitMapper = visitMapper;
    }

    public List<Visit> getAllByUser(UserEntity userEntity) {
        return visitRepository.findByUser(userEntity).stream()
                .map(visitMapper::convertToVisit)
                .collect(Collectors.toList());
    }

    public Visit getByUserAndVisitId(UserEntity userEntity, long visitId) {
        VisitEntity visitEntity = visitRepository.findByUserAndVisitId(userEntity, visitId)
                .orElseThrow(() -> new VisitNotFoundException(visitId));
        return visitMapper.convertToVisit(visitEntity);
    }

    public List<Visit> getCurrentVisits(UserEntity representativeEntity, InstitutionEntity institutionEntity) {
        //  todo test
        return getByRepresentativeAndVisitDateTimeIsAfter(representativeEntity).stream()
                .filter(visitEntity -> isVisitToInstitution(institutionEntity, visitEntity))
                .map(visitMapper::convertToVisit)
                .collect(Collectors.toList());
    }

    public Visit getCurrentVisit(UserEntity representativeEntity, InstitutionEntity institutionEntity, long visitId) {
        //  todo test
        VisitEntity visitEntity = getCurrentVisitEntity(representativeEntity, visitId);
        if (isVisitToInstitution(institutionEntity, visitEntity)) {
            return visitMapper.convertToVisit(visitEntity);
        }
        throw new VisitNotFoundException(visitId);
    }

    public Visit bookVisit(UserEntity userEntity, UserEntity representativeEntity, InstitutionEntity institutionEntity, long visitId) {
        VisitEntity visitEntity = getCurrentVisitEntity(representativeEntity, visitId);
        if (isVisitToInstitution(institutionEntity, visitEntity)) {
            visitEntity.setUser(userEntity);
            VisitEntity updatedVisitEntity = visitRepository.save(visitEntity);
            return visitMapper.convertToVisit(updatedVisitEntity);
        }
        throw new VisitNotFoundException(visitId);
    }

    private List<VisitEntity> getByRepresentativeAndVisitDateTimeIsAfter(UserEntity representativeEntity) {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        return visitRepository.findByRepresentativeAndVisitDateTimeIsAfter(representativeEntity, now);
    }

    private boolean isVisitToInstitution(InstitutionEntity institutionEntity, VisitEntity visitEntity) {
        return visitEntity.getPlace().getInstitution().getInstitutionId() == institutionEntity.getInstitutionId();
    }

    private VisitEntity getCurrentVisitEntity(UserEntity representativeEntity, long visitId) {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        return visitRepository
                .findByVisitIdAndRepresentativeAndVisitDateTimeIsAfter(visitId, representativeEntity, now)
                .orElseThrow(() -> new VisitNotFoundException(visitId));
    }
}
