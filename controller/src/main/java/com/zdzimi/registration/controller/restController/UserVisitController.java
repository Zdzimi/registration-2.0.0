package com.zdzimi.registration.controller.restController;

import com.zdzimi.registration.controller.link.LinkCreator;
import com.zdzimi.registration.core.model.Visit;
import com.zdzimi.registration.data.entity.UserEntity;
import com.zdzimi.registration.data.entity.VisitEntity;
import com.zdzimi.registration.service.UserService;
import com.zdzimi.registration.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registration/{username}/visits")
public class UserVisitController {

    private VisitService visitService;
    private UserService userService;
    private LinkCreator linkCreator;

    @Autowired
    public UserVisitController(VisitService visitService, UserService userService, LinkCreator linkCreator) {
        this.visitService = visitService;
        this.userService = userService;
        this.linkCreator = linkCreator;
    }

    @GetMapping
    public List<Visit> getUsersVisits(@PathVariable String username) {
        UserEntity userEntity = userService.getUserEntityByUsername(username);
        List<Visit> visits = visitService.getAllByUser(userEntity);
        linkCreator.addLinksToUsersVisits(visits, username);
        return visits;
    }

    @GetMapping("/{visitId}")
    public Visit getUsersVisit(@PathVariable String username, @PathVariable long visitId) {
        UserEntity userEntity = userService.getUserEntityByUsername(username);
        Visit visit = visitService.getByUserAndVisitId(userEntity, visitId);
        linkCreator.addLinksToUsersVisit(visit, username);
        return visit;
    }

    @PatchMapping("/{visitId}")
    public void cancelVisit(@PathVariable String username, @PathVariable long visitId) {
        UserEntity userEntity = userService.getUserEntityByUsername(username);
        VisitEntity visitEntity = visitService.getVisitEntityByUserAndVisitId(userEntity, visitId);
        visitService.cancelVisit(visitEntity);
    }
}
