package com.zdzimi.registration.service;

import com.zdzimi.registration.core.model.Institution;
import com.zdzimi.registration.data.entity.InstitutionEntity;
import com.zdzimi.registration.data.exception.InstitutionNotFoundException;
import com.zdzimi.registration.data.repository.InstitutionRepository;
import com.zdzimi.registration.service.mapper.InstitutionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstitutionService {

    private InstitutionRepository institutionRepository;
    private InstitutionMapper institutionMapper;

    @Autowired
    public InstitutionService(InstitutionRepository institutionRepository, InstitutionMapper institutionMapper) {
        this.institutionRepository = institutionRepository;
        this.institutionMapper = institutionMapper;
    }

    public Institution getInstitutionByName(String institutionName) {
        InstitutionEntity institutionEntity = institutionRepository.findByInstitutionName(institutionName)
                .orElseThrow(() -> new InstitutionNotFoundException(institutionName));
        return institutionMapper.convertToInstitution(institutionEntity);
    }
}
