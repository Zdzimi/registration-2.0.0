package com.zdzimi.registration.controller.restController;

import com.zdzimi.registration.core.model.User;
import com.zdzimi.registration.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class RepresentativeControllerTest {

    private static final String INSTITUTION_NAME = "styk.pl";
    private static final String USERNAME = "styk.pl";
    private static final String REPRESENTATIVE_NAME = "styk.pl";

    private RepresentativeController representativeController;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        initMocks(this);
        representativeController = new RepresentativeController(userService);
    }

    @Test
    void getRepresentatives() {
        //      given
        User user = new User();
        when(userService.getByWorkPlaces(INSTITUTION_NAME)).thenReturn(Arrays.asList(user));
        //      when
        List<User> result = representativeController.getRepresentatives(USERNAME, INSTITUTION_NAME);
        //      then
        assertEquals(1, result.size());
        verify(userService, times(1)).getByWorkPlaces(INSTITUTION_NAME);
        verifyNoMoreInteractions(userService);
    }

    @Test
    void getRepresentative() {
        //      given
        User user = new User();
        user.setUsername(REPRESENTATIVE_NAME);
        when(userService.getByUsernameAndWorkPlaces(USERNAME, INSTITUTION_NAME)).thenReturn(user);
        //      when
        User result = representativeController.getRepresentative(USERNAME, INSTITUTION_NAME, REPRESENTATIVE_NAME);
        //      then
        assertEquals(REPRESENTATIVE_NAME, result.getUsername());
        verify(userService, times(1)).getByUsernameAndWorkPlaces(REPRESENTATIVE_NAME, INSTITUTION_NAME);
        verifyNoMoreInteractions(userService);
    }
}