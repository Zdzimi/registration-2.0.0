package com.zdzimi.registration.controller.restController;

import com.zdzimi.registration.controller.link.LinkCreator;
import com.zdzimi.registration.core.model.User;
import com.zdzimi.registration.data.entity.UserEntity;
import com.zdzimi.registration.service.UserService;
import com.zdzimi.registration.service.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class UserControllerTest {

    private static final long USER_ID = 1;
    private static final String USERNAME = "KunegundaRojek";

    private LoggedUserProvider loggedUserProvider;
    private final UserMapper userMapper = new UserMapper(new ModelMapper());
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private UserController userController;

    @BeforeEach
    void init() {
        loggedUserProvider = mock(LoggedUserProvider.class);
        userService = mock(UserService.class);
        passwordEncoder = mock(PasswordEncoder.class);
        LinkCreator linkCreator = mock(LinkCreator.class);
        initMocks(this);
        userController = new UserController(loggedUserProvider, userMapper, userService, passwordEncoder, linkCreator);
    }

    @Test
    void shouldGetUser() {
        //      given
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(USERNAME);
        when(loggedUserProvider.provideLoggedUser(USERNAME)).thenReturn(userEntity);
        //      when
        User result = userController.getUser(USERNAME);
        //      then
        assertEquals(USERNAME, result.getUsername());
        verify(loggedUserProvider, times(1)).provideLoggedUser(USERNAME);
        verifyNoMoreInteractions(loggedUserProvider);
    }

    @Test
    void shouldSaveUser() {
        //      given
        User userBeforeSave = new User();
        userBeforeSave.setUsername(USERNAME);
        User userAfterSave = new User();
        userAfterSave.setUsername(USERNAME);
        when(passwordEncoder.encode(userBeforeSave.getPassword())).thenReturn("Pass");
        when(userService.save(userBeforeSave)).thenReturn(userAfterSave);
        //      when
        User result = userController.createUser(userBeforeSave);
        //      then
        assertEquals(userAfterSave, result);
        verify(userService, times(1)).save(userBeforeSave);
        verifyNoMoreInteractions(userService);
    }
}