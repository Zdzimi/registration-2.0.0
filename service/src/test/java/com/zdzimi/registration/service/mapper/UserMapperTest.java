package com.zdzimi.registration.service.mapper;

import com.zdzimi.registration.core.model.User;
import com.zdzimi.registration.data.entity.InstitutionEntity;
import com.zdzimi.registration.data.entity.UserEntity;
import com.zdzimi.registration.data.entity.VisitEntity;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    static final long USER_ID = 143;
    static final String USERNAME = "Kundzia";
    static final String NAME = "Kunegunda";
    static final String SURNAME = "Adamska";
    static final String EMAIL = "kundzia@mail.com";
    static final String PASSWORD = "@94gmne&&";
    static final String ROLE = "USER";

    private UserMapper userMapper = new UserMapper(new ModelMapper());

    @Test
    void shouldConvertToUser() {
        UserEntity userEntity = getUserEntity();

        User user = userMapper.convertToUser(userEntity);

        assertEquals(USER_ID, user.getUserId());
        assertEquals(USERNAME, user.getUsername());
        assertEquals(NAME, user.getName());
        assertEquals(SURNAME, user.getSurname());
        assertEquals(EMAIL, user.getEmail());
        assertEquals(PASSWORD, user.getPassword());
        assertEquals(ROLE, user.getRole());
    }

    @Test
    void shouldConvertToUserEntity() {
        User user = getUser();

        UserEntity userEntity = userMapper.convertToUserEntity(user);

        assertEquals(USER_ID, userEntity.getUserId());
        assertEquals(USERNAME, userEntity.getUsername());
        assertEquals(NAME, userEntity.getName());
        assertEquals(SURNAME, userEntity.getSurname());
        assertEquals(EMAIL, userEntity.getEmail());
        assertEquals(PASSWORD, userEntity.getPassword());
        assertEquals(ROLE, userEntity.getRole());
        assertTrue(userEntity.getVisits().isEmpty());
        assertTrue(userEntity.getRecognizedInstitutions().isEmpty());
        assertTrue(userEntity.getWorkPlaces().isEmpty());
        assertTrue(userEntity.getProvidedVisits().isEmpty());
    }

    static UserEntity getUserEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(USER_ID);
        userEntity.setUsername(USERNAME);
        userEntity.setName(NAME);
        userEntity.setSurname(SURNAME);
        userEntity.setEmail(EMAIL);
        userEntity.setPassword(PASSWORD);
        userEntity.setRole(ROLE);
        userEntity.setVisits(Arrays.asList(new VisitEntity(), new VisitEntity()));
        userEntity.setRecognizedInstitutions(Arrays.asList(new InstitutionEntity(), new InstitutionEntity()));
        userEntity.setProvidedVisits(Arrays.asList(new VisitEntity(), new VisitEntity()));
        userEntity.setWorkPlaces(Arrays.asList(new InstitutionEntity(), new InstitutionEntity()));
        return userEntity;
    }

    static User getUser() {
        User user = new User();
        user.setUserId(USER_ID);
        user.setUsername(USERNAME);
        user.setName(NAME);
        user.setSurname(SURNAME);
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);
        user.setRole(ROLE);
        return user;
    }
}