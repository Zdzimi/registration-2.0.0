package com.zdzimi.registration.controller.restController;

import com.zdzimi.registration.controller.link.LinkCreator;
import com.zdzimi.registration.core.model.Visit;
import com.zdzimi.registration.core.model.timetable.MonthTimetable;
import com.zdzimi.registration.data.entity.InstitutionEntity;
import com.zdzimi.registration.data.entity.UserEntity;
import com.zdzimi.registration.data.entity.VisitEntity;
import com.zdzimi.registration.service.InstitutionService;
import com.zdzimi.registration.service.UserService;
import com.zdzimi.registration.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/registration/{username}/institution/{institutionName}/representatives/{representativeName}/timetable")
@RequiredArgsConstructor
public class TimetableController {

    private final LoggedUserProvider loggedUserProvider;
    private final UserService userService;
    private final VisitService visitService;
    private final InstitutionService institutionService;
    private final LinkCreator linkCreator;

    @GetMapping
    public List<MonthTimetable> getVisits(@PathVariable String username,
                                          @PathVariable String institutionName,
                                          @PathVariable String representativeName) {
        loggedUserProvider.provideLoggedUser(username);
        UserEntity representativeEntity = userService.getUserEntityByUsername(representativeName);
        InstitutionEntity institutionEntity = institutionService.getInstitutionEntityByInstitutionName(institutionName);
        List<Visit> currentVisits = visitService.getCurrentVisits(representativeEntity, institutionEntity);
        linkCreator.addLinksToCurrentVisits(currentVisits, username, institutionName, representativeName);
        return MonthTimetable.createTimetable(currentVisits);
    }

    @GetMapping("/{visitId}")
    public Visit getVisit(@PathVariable String username,
                          @PathVariable String institutionName,
                          @PathVariable String representativeName,
                          @PathVariable long visitId) {
        loggedUserProvider.provideLoggedUser(username);
        UserEntity representativeEntity = userService.getUserEntityByUsername(representativeName);
        InstitutionEntity institutionEntity = institutionService.getInstitutionEntityByInstitutionName(institutionName);
        Visit visit = visitService.getCurrentVisit(representativeEntity, institutionEntity, visitId);
        linkCreator.addLinksToCurrentVisit(visit, username, institutionName, representativeName);
        return visit;
    }

    @PatchMapping("/{visitId}")
    public Visit bookVisit(@PathVariable String username,
                           @PathVariable String institutionName,
                           @PathVariable String representativeName,
                           @PathVariable long visitId) {
        UserEntity userEntity = loggedUserProvider.provideLoggedUser(username);
        UserEntity representativeEntity = userService.getUserEntityByUsername(representativeName);
        InstitutionEntity institutionEntity = institutionService.getInstitutionEntityByInstitutionName(institutionName);
        VisitEntity visitEntity = visitService.getCurrentByVisitIdAndRepresentativeAndInstitution(representativeEntity, institutionEntity, visitId);
        Visit visit = visitService.bookVisit(visitEntity, userEntity);
        userService.addRecognizedInstitution(userEntity, institutionEntity);
        linkCreator.addLinksToCurrentVisit(visit, username, institutionName, representativeName);
        return visit;
    }
}
