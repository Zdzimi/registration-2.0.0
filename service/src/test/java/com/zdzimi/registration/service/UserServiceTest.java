package com.zdzimi.registration.service;

import com.zdzimi.registration.core.model.User;
import com.zdzimi.registration.data.entity.InstitutionEntity;
import com.zdzimi.registration.data.entity.UserEntity;
import com.zdzimi.registration.data.exception.UserNotFoundException;
import com.zdzimi.registration.data.repository.UserRepository;
import com.zdzimi.registration.service.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class UserServiceTest {

    private static final long USER_ID = 101;
    private static final String USERNAME = "MICHAILTALL";
    private static final String INSTITUTION_NAME = "chocolate factory";

    private UserService userService;
    private UserRepository userRepository;
    private UserMapper userMapper = new UserMapper(new ModelMapper());

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        initMocks(this);
        userService = new UserService(userRepository, userMapper);
    }

    @Test
    void getUserByUsername() {
        //      given
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(USER_ID);
        userEntity.setUsername(USERNAME);
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(userEntity));
        //      when
        User result = userService.getByUsername(USERNAME);
        //      then
        assertEquals(USER_ID, result.getUserId());
        assertEquals(USERNAME, result.getUsername());
        verify(userRepository, times(1)).findByUsername(USERNAME);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldGetUserEntityByUsername() {
        //      given
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(USERNAME);
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(userEntity));
        //      when
        UserEntity result = userService.getUserEntityByUsername(USERNAME);
        //      then
        assertEquals(USERNAME, result.getUsername());
        verify(userRepository, times(1)).findByUsername(USERNAME);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldThrowUserNotFoundException() {
        //      given
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.empty());
        //      when
        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class, () -> userService.getUserEntityByUsername(USERNAME)
            );
        //      then
        assertEquals("Could not find user: " + USERNAME, exception.getMessage());
        verify(userRepository, times(1)).findByUsername(USERNAME);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldSave() {
        //      given
        User userBeforeSave = new User();

        UserEntity userEntityAfterSave = new UserEntity();
        userEntityAfterSave.setUserId(USER_ID);
        userEntityAfterSave.setUsername(USERNAME);

        when(userRepository.save(ArgumentMatchers.any(UserEntity.class))).thenReturn(userEntityAfterSave);
        //      when
        User result = userService.save(userBeforeSave);
        //      then
        assertEquals(USERNAME, result.getUsername());
        verify(userRepository, times(1)).save(ArgumentMatchers.any(UserEntity.class));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldGetByWorkPlaces() {
        //      given
        InstitutionEntity institutionEntity = new InstitutionEntity();
        institutionEntity.setInstitutionName(INSTITUTION_NAME);
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(USERNAME);
        when(userRepository.findByWorkPlaces(institutionEntity)).thenReturn(Arrays.asList(userEntity));
        //      when
        List<User> result = userService.getByWorkPlaces(institutionEntity);
        //      then
        assertEquals(1, result.size());
        assertEquals(USERNAME, result.get(0).getUsername());
        verify(userRepository, times(1)).findByWorkPlaces(institutionEntity);
        verifyNoMoreInteractions(userRepository);

    }

    @Test
    void shouldGetByUsernameAndWorkPlaces() {
        //      given
        InstitutionEntity institutionEntity = new InstitutionEntity();
        institutionEntity.setInstitutionName(INSTITUTION_NAME);
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(USERNAME);
        when(userRepository.findByUsernameAndWorkPlaces(USERNAME, institutionEntity)).thenReturn(Optional.of(userEntity));
        //      when
        User result = userService.getByUsernameAndWorkPlaces(USERNAME, institutionEntity);
        //      then
        assertEquals(USERNAME, result.getUsername());
        verify(userRepository, times(1)).findByUsernameAndWorkPlaces(USERNAME, institutionEntity);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldThrowUserNotFoundException_NotFoundByUsernameAndWorkPlaces() {
        //      given
        InstitutionEntity institutionEntity = new InstitutionEntity();
        institutionEntity.setInstitutionName(INSTITUTION_NAME);
        when(userRepository.findByUsernameAndWorkPlaces(USERNAME, institutionEntity)).thenReturn(Optional.empty());
        //      when
        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class, () -> userService.getByUsernameAndWorkPlaces(USERNAME, institutionEntity)
        );
        //      then
        assertEquals("Could not find representative: " + USERNAME + " in " + INSTITUTION_NAME, exception.getMessage());
        verify(userRepository, times(1)).findByUsernameAndWorkPlaces(USERNAME, institutionEntity);
        verifyNoMoreInteractions(userRepository);
    }
}