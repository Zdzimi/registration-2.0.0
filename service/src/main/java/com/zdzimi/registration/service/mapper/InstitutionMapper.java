package com.zdzimi.registration.service.mapper;
import com.zdzimi.registration.core.model.Institution;
import com.zdzimi.registration.data.entity.InstitutionEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class InstitutionMapper {

    private ModelMapper modelMapper;

    @Autowired
    public InstitutionMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Institution convertToInstitution(InstitutionEntity institutionEntity) {
        return modelMapper.map(institutionEntity, Institution.class);
    }

    public InstitutionEntity convertToInstitutionEntity(Institution institution) {
        InstitutionEntity institutionEntity = modelMapper.map(institution, InstitutionEntity.class);
        institutionEntity.setUsers(Collections.emptyList());
        institutionEntity.setRepresentatives(Collections.emptyList());
        institutionEntity.setPlaces(Collections.emptyList());
        return institutionEntity;
    }
}