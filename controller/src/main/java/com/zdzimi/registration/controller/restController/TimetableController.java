package com.zdzimi.registration.controller.restController;

import com.zdzimi.registration.core.model.Visit;
import com.zdzimi.registration.data.entity.InstitutionEntity;
import com.zdzimi.registration.data.entity.UserEntity;
import com.zdzimi.registration.data.entity.VisitEntity;
import com.zdzimi.registration.service.InstitutionService;
import com.zdzimi.registration.service.UserService;
import com.zdzimi.registration.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registration/{username}/institution/{institutionName}/representative/{representativeName}/timetable")
public class TimetableController {

    private VisitService visitService;
    private InstitutionService institutionService;
    private UserService userService;

    @Autowired
    public TimetableController(VisitService visitService, InstitutionService institutionService, UserService userService) {
        this.visitService = visitService;
        this.institutionService = institutionService;
        this.userService = userService;
    }

    @GetMapping
    public List<Visit> getVisits(@PathVariable String username,
                                 @PathVariable String institutionName,
                                 @PathVariable String representativeName) {
        UserEntity representativeEntity = userService.getUserEntityByUsername(representativeName);
        InstitutionEntity institutionEntity = institutionService.getInstitutionEntityByInstitutionName(institutionName);
        return visitService.getCurrentVisits(representativeEntity, institutionEntity);
    }

    @GetMapping("/{visitId}")
    public Visit getVisit(@PathVariable String username,
                          @PathVariable String institutionName,
                          @PathVariable String representativeName,
                          @PathVariable long visitId) {
        UserEntity representativeEntity = userService.getUserEntityByUsername(representativeName);
        InstitutionEntity institutionEntity = institutionService.getInstitutionEntityByInstitutionName(institutionName);
        return visitService.getCurrentVisit(representativeEntity, institutionEntity, visitId);
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
        return visit;
    }
}
