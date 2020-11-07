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
    private UserService userService;
    private InstitutionMapper institutionMapper;

    @Autowired
    public InstitutionService(InstitutionRepository institutionRepository, UserService userService, InstitutionMapper institutionMapper) {
        this.institutionRepository = institutionRepository;
        this.userService = userService;
        this.institutionMapper = institutionMapper;
    }

    public List<Institution> getAll() {
        List<InstitutionEntity> all = institutionRepository.findAll();
        return all.stream()
                .map(institutionMapper::convertToInstitution)
                .collect(Collectors.toList());
    }

    public List<Institution> getRecognized(String username) {
        UserEntity userEntity = userService.getUserEntityByUsername(username);
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

    public List<Institution> getWorkPlaces(String username) {
        UserEntity userEntity = userService.getUserEntityByUsername(username);
        return institutionRepository.findByRepresentatives(userEntity).stream()
                .map(institutionMapper::convertToInstitution)
                .collect(Collectors.toList());
    }

    public Institution getWorkPlace(String username, String institutionName) {
        UserEntity userEntity = userService.getUserEntityByUsername(username);
        InstitutionEntity institutionEntity = institutionRepository.findByInstitutionNameAndRepresentatives(institutionName, userEntity)
                .orElseThrow(() -> new InstitutionNotFoundException(username, institutionName));
        return institutionMapper.convertToInstitution(institutionEntity);
    }
}
