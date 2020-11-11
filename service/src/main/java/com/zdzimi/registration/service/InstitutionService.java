package com.zdzimi.registration.service;

import com.zdzimi.registration.core.model.Institution;
import com.zdzimi.registration.data.entity.InstitutionEntity;
import com.zdzimi.registration.data.entity.UserEntity;
import com.zdzimi.registration.data.exception.InstitutionNotFoundException;
import com.zdzimi.registration.data.repository.InstitutionRepository;
import com.zdzimi.registration.service.mapper.InstitutionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InstitutionService {

    private InstitutionRepository institutionRepository;
    private InstitutionMapper institutionMapper;

    @Autowired
    public InstitutionService(InstitutionRepository institutionRepository, InstitutionMapper institutionMapper) {
        this.institutionRepository = institutionRepository;
        this.institutionMapper = institutionMapper;
    }

    public List<Institution> getAll() {
        List<InstitutionEntity> all = institutionRepository.findAll();
        return all.stream()
                .map(institutionMapper::convertToInstitution)
                .collect(Collectors.toList());
    }

    public List<Institution> getRecognized(UserEntity userEntity) {
        return institutionRepository.findByUsers(userEntity).stream()
                .map(institutionMapper::convertToInstitution)
                .collect(Collectors.toList());
    }

    public Institution getByInstitutionName(String institutionName) {
        InstitutionEntity institutionEntity = getInstitutionEntityByInstitutionName(institutionName);
        return institutionMapper.convertToInstitution(institutionEntity);
    }

    public InstitutionEntity getInstitutionEntityByInstitutionName(String institutionName) {
        return institutionRepository.findByInstitutionName(institutionName)
                .orElseThrow(() -> new InstitutionNotFoundException(institutionName));
    }

    public Institution getWorkPlace(UserEntity userEntity, String institutionName) {
        InstitutionEntity institutionEntity = institutionRepository.findByInstitutionNameAndRepresentatives(institutionName, userEntity)
                .orElseThrow(() -> new InstitutionNotFoundException(userEntity.getUsername(), institutionName));
        return institutionMapper.convertToInstitution(institutionEntity);
    }

    public List<Institution> getWorkPlaces(UserEntity userEntity) {
        return institutionRepository.findByRepresentatives(userEntity).stream()
                .map(institutionMapper::convertToInstitution)
                .collect(Collectors.toList());
    }
}
