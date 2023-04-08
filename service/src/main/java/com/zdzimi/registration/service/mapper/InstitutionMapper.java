package com.zdzimi.registration.service.mapper;

import com.zdzimi.registration.core.model.Institution;
import com.zdzimi.registration.data.entity.InstitutionEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;
import org.springframework.web.util.HtmlUtils;

@RequiredArgsConstructor
@Component
public class InstitutionMapper {

    private final ModelMapper modelMapper;

    public Institution convertToInstitution(InstitutionEntity iE) {
        Institution institution = new Institution();
        institution.setInstitutionName(iE.getInstitutionName());
        institution.setProvince(HtmlUtils.htmlEscape(iE.getProvince()));
        institution.setCity(HtmlUtils.htmlEscape(iE.getCity()));
        institution.setStreet(HtmlUtils.htmlEscape(iE.getStreet()));
        institution.setGateNumber(HtmlUtils.htmlEscape(iE.getGateNumber()));
        institution.setPremisesNumber(HtmlUtils.htmlEscape(iE.getPremisesNumber()));
        institution.setTypeOfService(HtmlUtils.htmlEscape(iE.getTypeOfService()));
        institution.setDescription(HtmlUtils.htmlEscape(iE.getDescription()));
        return institution;
    }

    public InstitutionEntity convertToInstitutionEntity(Institution institution) {
        InstitutionEntity institutionEntity = modelMapper.map(institution, InstitutionEntity.class);
        institutionEntity.setUsers(Collections.emptyList());
        institutionEntity.setRepresentatives(Collections.emptyList());
        institutionEntity.setPlaces(Collections.emptyList());
        return institutionEntity;
    }
}
