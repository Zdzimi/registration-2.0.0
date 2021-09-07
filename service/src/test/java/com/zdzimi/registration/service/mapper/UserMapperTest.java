package com.zdzimi.registration.service.mapper;

import com.zdzimi.registration.core.model.Role;
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
    static final String ROLE = "ROLE_USER";
    static final Role ROLE_ROLE = Role.ROLE_USER;

    private UserMapper userMapper = new UserMapper(new ModelMapper());

    @Test
    void shouldConvertToUser() {
        UserEntity userEntity = getUserEntity();

        User result = userMapper.convertToUser(userEntity);

        assertEquals(USERNAME, result.getUsername());
        assertEquals(NAME, result.getName());
        assertEquals(SURNAME, result.getSurname());
        assertEquals(EMAIL, result.getEmail());
        assertNull(result.getPassword());
    }

    @Test
    void shouldConvertToUserEntity() {
        User user = getUser();

        UserEntity result = userMapper.convertToUserEntity(user);

        assertNull(result.getUserId());
        assertEquals(USERNAME, result.getUsername());
        assertEquals(NAME, result.getName());
        assertEquals(SURNAME, result.getSurname());
        assertEquals(EMAIL, result.getEmail());
        assertEquals(PASSWORD, result.getPassword());
        assertEquals(ROLE, result.getRole());
        assertTrue(result.getVisits().isEmpty());
        assertTrue(result.getRecognizedInstitutions().isEmpty());
        assertTrue(result.getWorkPlaces().isEmpty());
        assertTrue(result.getProvidedVisits().isEmpty());
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
        user.setUsername(USERNAME);
        user.setName(NAME);
        user.setSurname(SURNAME);
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);
        return user;
    }
}