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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/registration/{username}/institution/{institutionName}/representatives/{representativeName}/timetable")
public class TimetableController {

    private final VisitService visitService;
    private final InstitutionService institutionService;
    private final UserService userService;
    private final LinkCreator linkCreator;

    @Autowired
    public TimetableController(VisitService visitService, InstitutionService institutionService, UserService userService, LinkCreator linkCreator) {
        this.visitService = visitService;
        this.institutionService = institutionService;
        this.userService = userService;
        this.linkCreator = linkCreator;
    }

    @GetMapping
    public List<MonthTimetable> getVisits(@PathVariable String username,
                                          @PathVariable String institutionName,
                                          @PathVariable String representativeName) {
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
        UserEntity userEntity = userService.getUserEntityByUsername(username);
        UserEntity representativeEntity = userService.getUserEntityByUsername(representativeName);
        InstitutionEntity institutionEntity = institutionService.getInstitutionEntityByInstitutionName(institutionName);
        VisitEntity visitEntity = visitService.getCurrentByVisitIdAndRepresentativeAndInstitution(representativeEntity, institutionEntity, visitId);
        Visit visit = visitService.bookVisit(visitEntity, userEntity);
        userService.addRecognizedInstitution(userEntity, institutionEntity);
        linkCreator.addLinksToCurrentVisit(visit, username, institutionName, representativeName);
        return visit;
    }
}
