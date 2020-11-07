package com.zdzimi.registration.controller.restController;

import com.zdzimi.registration.core.model.User;
import com.zdzimi.registration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/registration/{username}/institution/{institutionName}/representative")
public class RepresentativeController {

    private UserService userService;

    @Autowired
    public RepresentativeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getRepresentatives (@PathVariable String username, @PathVariable String institutionName) {
        return userService.getByWorkPlaces(institutionName);
    }

    @GetMapping("/{representativeName}")
    public User getRepresentative(@PathVariable String username,
                                  @PathVariable String institutionName,
                                  @PathVariable String representativeName) {
        return userService.getByUsernameAndWorkPlaces(representativeName, institutionName);
    }
}
