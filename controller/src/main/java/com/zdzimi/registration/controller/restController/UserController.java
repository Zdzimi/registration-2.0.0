package com.zdzimi.registration.controller.restController;

import com.zdzimi.registration.controller.link.LinkCreator;
import com.zdzimi.registration.core.model.ModifiedUser;
import com.zdzimi.registration.core.model.User;
import com.zdzimi.registration.data.entity.UserEntity;
import com.zdzimi.registration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/registration")
@Validated
@CrossOrigin
public class UserController {

    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private LinkCreator linkCreator;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder, LinkCreator linkCreator) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.linkCreator = linkCreator;
    }

    @GetMapping("/{username}")
    public User getUser(@PathVariable String username) {
        User user = userService.getByUsername(username);
        linkCreator.addLinksToUser(user);
        return user;
    }

    @PostMapping("/new-user")
    public User createUser(@Valid @RequestBody User user) {
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        User savedUser = userService.save(user);
        linkCreator.addLinksToUser(savedUser);
        return savedUser;
    }

    @PatchMapping("/{username}/update-user")
    public User updateUser(@Valid @RequestBody ModifiedUser modifiedUser, @PathVariable String username) {
        UserEntity userEntity = userService.getUserEntityByUsername(username);
        if(passwordEncoder.matches(modifiedUser.getOldPassword(), userEntity.getPassword())) {
            modifiedUser.setNewPassword(passwordEncoder.encode(modifiedUser.getNewPassword()));
            return userService.update(userEntity, modifiedUser);
        }
        throw new InvalidPasswordException();
    }
}
