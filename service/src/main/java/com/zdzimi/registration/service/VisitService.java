package com.zdzimi.registration.service;

import com.zdzimi.registration.core.model.Visit;
import com.zdzimi.registration.data.entity.UserEntity;
import com.zdzimi.registration.data.entity.VisitEntity;
import com.zdzimi.registration.data.repository.VisitRepository;
import com.zdzimi.registration.service.mapper.VisitMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        VisitEntity visitEntity = visitRepository.findByUserAndVisitId(userEntity, visitId);
        return visitMapper.convertToVisit(visitEntity);
    }
}
