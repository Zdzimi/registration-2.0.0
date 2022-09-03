package com.zdzimi.registration.controller.restController;

import com.zdzimi.registration.controller.link.LinkCreator;
import com.zdzimi.registration.core.model.ModifiedUser;
import com.zdzimi.registration.core.model.User;
import com.zdzimi.registration.data.entity.UserEntity;
import com.zdzimi.registration.security.UserPrincipal;
import com.zdzimi.registration.service.UserService;
import com.zdzimi.registration.service.mapper.UserMapper;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/registration")
@Validated
@CrossOrigin
@RequiredArgsConstructor
public class UserController {

    private final LoggedUserProvider loggedUserProvider;
    private final UserMapper userMapper;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final LinkCreator linkCreator;

    @GetMapping("/{username}")
    public User getUser(@PathVariable String username) {
        UserEntity userEntity = loggedUserProvider.provideLoggedUser(username);
        User user = userMapper.convertToUser(userEntity);
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
        UserEntity userEntity = loggedUserProvider.provideLoggedUser(username);
        if(passwordEncoder.matches(modifiedUser.getOldPassword(), userEntity.getPassword())) {
            modifiedUser.setNewPassword(passwordEncoder.encode(modifiedUser.getNewPassword()));
            return userService.update(userEntity, modifiedUser);
        }
        throw new InvalidPasswordException();
    }
}
