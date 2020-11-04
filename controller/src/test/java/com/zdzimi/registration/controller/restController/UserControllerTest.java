package com.zdzimi.registration.controller.restController;

import com.zdzimi.registration.core.model.User;
import com.zdzimi.registration.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class UserControllerTest {

    private static final long USER_ID = 1;
    private static final String USERNAME = "KunegundaRojek";

    private UserController userController;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        initMocks(this);
        userController = new UserController(userService);
    }

    @Test
    void shouldGetUser() {
        //      given
        User user = new User();
        user.setUserId(USER_ID);
        user.setUsername(USERNAME);
        when(userService.getByUsername(USERNAME)).thenReturn(user);
        //      when
        User result = userController.getUser(USERNAME);
        //      then
        assertEquals(USER_ID, result.getUserId());
        assertEquals(USERNAME, result.getUsername());
        verify(userService, times(1)).getByUsername(USERNAME);
        verifyNoMoreInteractions(userService);
    }
}