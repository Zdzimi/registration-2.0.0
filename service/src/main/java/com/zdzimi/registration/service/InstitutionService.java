package com.zdzimi.registration.service;

import com.zdzimi.registration.core.model.Institution;
import com.zdzimi.registration.data.entity.InstitutionEntity;
import com.zdzimi.registration.data.entity.UserEntity;
import com.zdzimi.registration.data.exception.InstitutionNotFoundException;
import com.zdzimi.registration.data.repository.InstitutionRepository;
import com.zdzimi.registration.service.mapper.InstitutionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InstitutionService {

    private final InstitutionRepository institutionRepository;
    private final InstitutionMapper institutionMapper;

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

    public Institution getWorkPlace(UserEntity representativeEntity, String institutionName) {
        InstitutionEntity institutionEntity = getWorkPlaceEntityByRepresentativeEntityAndInstitutionName(representativeEntity, institutionName);
        return institutionMapper.convertToInstitution(institutionEntity);
    }

    public InstitutionEntity getWorkPlaceEntityByRepresentativeEntityAndInstitutionName(UserEntity representativeEntity, String institutionName) {
        return institutionRepository.findByInstitutionNameAndRepresentatives(institutionName, representativeEntity)
                    .orElseThrow(() -> new InstitutionNotFoundException(representativeEntity.getUsername(), institutionName));
    }

    public List<Institution> getWorkPlaces(UserEntity userEntity) {
        return institutionRepository.findByRepresentatives(userEntity).stream()
                .map(institutionMapper::convertToInstitution)
                .collect(Collectors.toList());
    }

    public InstitutionEntity createNewInstitution(Institution institution) {
        InstitutionEntity institutionEntity = institutionMapper.convertToInstitutionEntity(institution);
        return institutionRepository.save(institutionEntity);
    }

    public List<Institution> searchBy(String institutionName, String province, String city,
        String typeOfServices) {
        return institutionRepository
            .findByInstitutionNameContainsAndProvinceContainsAndCityContainsAndTypeOfServiceContains(
                institutionName, province, city, typeOfServices).stream()
            .map(institutionMapper::convertToInstitution)
            .collect(Collectors.toList());
    }
}
