package com.zdzimi.registration.controller.restController;

import com.zdzimi.registration.core.model.Visit;
import com.zdzimi.registration.data.entity.UserEntity;
import com.zdzimi.registration.service.UserService;
import com.zdzimi.registration.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/registration/{username}/visits")
public class UserVisitController {

    private VisitService visitService;
    private UserService userService;

    @Autowired
    public UserVisitController(VisitService visitService, UserService userService) {
        this.visitService = visitService;
        this.userService = userService;
    }

    @GetMapping
    public List<Visit> getUsersVisits(@PathVariable String username) {
        UserEntity userEntity = userService.getUserEntityByUsername(username);
        return visitService.getAllByUser(userEntity);
    }

    @GetMapping("/{visitId}")
    public Visit getVisit(@PathVariable String username, @PathVariable long visitId) {
        UserEntity userEntity = userService.getUserEntityByUsername(username);
        return visitService.getByUserAndVisitId(userEntity, visitId);
    }

    //  todo cancelVisit()
}
