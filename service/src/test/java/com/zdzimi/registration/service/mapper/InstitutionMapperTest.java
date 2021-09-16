package com.zdzimi.registration.service.mapper;

import com.zdzimi.registration.core.model.Institution;
import com.zdzimi.registration.data.entity.InstitutionEntity;
import com.zdzimi.registration.data.entity.PlaceEntity;
import com.zdzimi.registration.data.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class InstitutionMapperTest {

    static final long INSTITUTION_ID = 2;
    static final String INSTITUTION_NAME = "Tattoo";
    static final String PROVINCE = "Dolnośląskie";
    static final String CITY = "Wrocław";
    static final String STREET = "Karmelkowa";
    static final String GATE_NUMBER = "1";
    static final String PREMISES_NUMBER= "1";
    static final String TYPE_OF_SERVICE = "tattoo";
    static final String DESCRIPTION = "Any description";

    private InstitutionMapper institutionMapper = new InstitutionMapper(new ModelMapper());

    @Test
    void shouldConvertToInstitution() {
        InstitutionEntity institutionEntity = getInstitutionEntity();

        Institution result = institutionMapper.convertToInstitution(institutionEntity);

        assertEquals(INSTITUTION_NAME, result.getInstitutionName());
        assertEquals(PROVINCE, result.getProvince());
        assertEquals(CITY, result.getCity());
        assertEquals(STREET, result.getStreet());
        assertEquals(GATE_NUMBER, result.getGateNumber());
        assertEquals(PREMISES_NUMBER, result.getPremisesNumber());
        assertEquals(TYPE_OF_SERVICE, result.getTypeOfService());
        assertEquals(DESCRIPTION, result.getDescription());
    }

    @Test
    void shouldConvertToInstitutionEntity() {
        Institution institution = getInstitution();

        InstitutionEntity result = institutionMapper.convertToInstitutionEntity(institution);

        assertEquals(0, result.getInstitutionId());
        assertEquals(INSTITUTION_NAME, result.getInstitutionName());
        assertEquals(PROVINCE, result.getProvince());
        assertEquals(CITY, result.getCity());
        assertEquals(STREET, result.getStreet());
        assertEquals(GATE_NUMBER, result.getGateNumber());
        assertEquals(PREMISES_NUMBER, result.getPremisesNumber());
        assertEquals(TYPE_OF_SERVICE, result.getTypeOfService());
        assertEquals(DESCRIPTION, result.getDescription());
        assertTrue(result.getUsers().isEmpty());
        assertTrue(result.getRepresentatives().isEmpty());
        assertTrue(result.getPlaces().isEmpty());
    }

    static InstitutionEntity getInstitutionEntity() {
        InstitutionEntity institutionEntity = new InstitutionEntity();
        institutionEntity.setInstitutionId(INSTITUTION_ID);
        institutionEntity.setInstitutionName(INSTITUTION_NAME);
        institutionEntity.setProvince(PROVINCE);
        institutionEntity.setCity(CITY);
        institutionEntity.setStreet(STREET);
        institutionEntity.setGateNumber(GATE_NUMBER);
        institutionEntity.setPremisesNumber(PREMISES_NUMBER);
        institutionEntity.setTypeOfService(TYPE_OF_SERVICE);
        institutionEntity.setDescription(DESCRIPTION);
        institutionEntity.setUsers(Arrays.asList(new UserEntity(), new UserEntity()));
        institutionEntity.setRepresentatives(Arrays.asList(new UserEntity(), new UserEntity()));
        institutionEntity.setPlaces(Arrays.asList(new PlaceEntity(), new PlaceEntity()));
        return institutionEntity;
    }

    static Institution getInstitution() {
        Institution institution = new Institution();
        institution.setInstitutionName(INSTITUTION_NAME);
        institution.setProvince(PROVINCE);
        institution.setCity(CITY);
        institution.setStreet(STREET);
        institution.setGateNumber(GATE_NUMBER);
        institution.setPremisesNumber(PREMISES_NUMBER);
        institution.setTypeOfService(TYPE_OF_SERVICE);
        institution.setDescription(DESCRIPTION);
        return institution;
    }
}