package com.zdzimi.registration.controller.restController;

import com.zdzimi.registration.core.model.Role;
import com.zdzimi.registration.core.model.User;
import com.zdzimi.registration.core.validation.OnCreate;
import com.zdzimi.registration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/registration")
@Validated
public class UserController {

    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/{username}")
    public User getUser(@PathVariable String username) {
        return userService.getByUsername(username);
    }

    @PostMapping("/new-user")
    @Validated(OnCreate.class)
    public User createUser(@Valid @RequestBody User user) {
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.ROLE_USER);
        return userService.save(user);
    }
}
