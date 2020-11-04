package com.zdzimi.registration.service;

import com.zdzimi.registration.core.model.User;
import com.zdzimi.registration.data.entity.UserEntity;
import com.zdzimi.registration.data.repository.UserRepository;
import com.zdzimi.registration.service.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class UserServiceTest {

    private static final long USER_ID = 101;
    private static final String USERNAME = "MICHAILTALL";

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
}