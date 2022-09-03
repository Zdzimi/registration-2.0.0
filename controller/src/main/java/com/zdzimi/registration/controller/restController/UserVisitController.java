package com.zdzimi.registration.controller.restController;

import com.zdzimi.registration.controller.link.LinkCreator;
import com.zdzimi.registration.core.model.Visit;
import com.zdzimi.registration.data.entity.UserEntity;
import com.zdzimi.registration.data.entity.VisitEntity;
import com.zdzimi.registration.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registration/{username}/visits")
@RequiredArgsConstructor
public class UserVisitController {

    private final LoggedUserProvider loggedUserProvider;
    private final VisitService visitService;
    private final LinkCreator linkCreator;

    @GetMapping
    public List<Visit> getUsersVisits(@PathVariable String username) {
        UserEntity userEntity = loggedUserProvider.provideLoggedUser(username);
        List<Visit> visits = visitService.getAllByUser(userEntity);
        linkCreator.addLinksToUsersVisits(visits, username);
        return visits;
    }

    @GetMapping("/{visitId}")
    public Visit getUsersVisit(@PathVariable String username, @PathVariable long visitId) {
        UserEntity userEntity = loggedUserProvider.provideLoggedUser(username);
        Visit visit = visitService.getByUserAndVisitId(userEntity, visitId);
        linkCreator.addLinksToUsersVisit(visit, username);
        return visit;
    }

    @PatchMapping("/{visitId}")
    public void cancelVisit(@PathVariable String username, @PathVariable long visitId) {
        UserEntity userEntity = loggedUserProvider.provideLoggedUser(username);
        VisitEntity visitEntity = visitService.getVisitEntityByUserAndVisitId(userEntity, visitId);
        visitService.cancelVisit(visitEntity);
    }
}
