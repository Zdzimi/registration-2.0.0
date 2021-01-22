package com.zdzimi.registration.controller.restController;

import com.zdzimi.registration.controller.link.LinkCreator;
import com.zdzimi.registration.core.model.Visit;
import com.zdzimi.registration.core.model.template.TimetableTemplate;
import com.zdzimi.registration.data.entity.InstitutionEntity;
import com.zdzimi.registration.data.entity.UserEntity;
import com.zdzimi.registration.data.entity.VisitEntity;
import com.zdzimi.registration.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/registration/{username}/work-place/{institutionName}")
public class RepresentativeUtilsController {

    private VisitService visitService;
    private UserService userService;
    private InstitutionService institutionService;
    private TimetableTemplateService timetableTemplateService;
    private VisitEntityGenerator visitEntityGenerator;
    private ConflictAnalyzer conflictAnalyzer;
    private LinkCreator linkCreator;

    @Autowired
    public RepresentativeUtilsController(VisitService visitService,
                                         UserService userService,
                                         InstitutionService institutionService,
                                         TimetableTemplateService timetableTemplateService,
                                         VisitEntityGenerator visitEntityGenerator,
                                         ConflictAnalyzer conflictAnalyzer,
                                         LinkCreator linkCreator) {
        this.visitService = visitService;
        this.userService = userService;
        this.institutionService = institutionService;
        this.timetableTemplateService = timetableTemplateService;
        this.visitEntityGenerator = visitEntityGenerator;
        this.conflictAnalyzer = conflictAnalyzer;
        this.linkCreator = linkCreator;
    }

    @GetMapping("/get-next-template")
    public TimetableTemplate prepareNextTemplate(@PathVariable String username, @PathVariable String institutionName) {
        UserEntity representativeEntity = userService.getUserEntityByUsername(username);
        InstitutionEntity institutionEntity = institutionService
                .getWorkPlaceEntityByRepresentativeEntityAndInstitutionName(representativeEntity, institutionName);
        Visit lastProvidedVisit = visitService.getLastProvidedVisit(representativeEntity, institutionEntity);
        TimetableTemplate timetableTemplate = timetableTemplateService.prepareTemplate(lastProvidedVisit);
        linkCreator.addLinksToTemplate(timetableTemplate, username, institutionName);
        return timetableTemplate;
    }

    @GetMapping("/year/{year}")
    public List<Visit> showVisitsByYear(@PathVariable String username,
                                        @PathVariable String institutionName,
                                        @PathVariable int year) {
        UserEntity representativeEntity = userService.getUserEntityByUsername(username);
        InstitutionEntity institutionEntity = institutionService
                .getWorkPlaceEntityByRepresentativeEntityAndInstitutionName(representativeEntity, institutionName);
        List<Visit> visits = visitService.getByRepresentativeAndInstitutionAndYear(representativeEntity, institutionEntity, year);
        linkCreator.addLinksToRepresentativesVisits(visits, username, institutionName);
        return visits;
    }

    @GetMapping("/year/{year}/month/{month}")
    public List<Visit> showVisitsByYearAndMonth(@PathVariable String username,
                                                @PathVariable String institutionName,
                                                @PathVariable int year,
                                                @PathVariable int month) {
        UserEntity representativeEntity = userService.getUserEntityByUsername(username);
        InstitutionEntity institutionEntity = institutionService
                .getWorkPlaceEntityByRepresentativeEntityAndInstitutionName(representativeEntity, institutionName);
        List<Visit> visits = visitService.getByRepresentativeAndInstitutionAndYearAndMonth(representativeEntity, institutionEntity, year, month);
        linkCreator.addLinksToRepresentativesVisits(visits, username, institutionName);
        return visits;
    }

    @GetMapping("/year/{year}/month/{month}/get-template")
    public TimetableTemplate prepareTemplateByYearAndMonth(@PathVariable String username,
                                                           @PathVariable String institutionName,
                                                           @PathVariable int year,
                                                           @PathVariable int month) {
        TimetableTemplate timetableTemplate = timetableTemplateService.prepareTemplate(year, month);
        linkCreator.addLinksToTemplate(timetableTemplate, username, institutionName);
        return timetableTemplate;
    }

    @GetMapping("/year/{year}/month/{month}/day/{day}")
    public List<Visit> showVisitsByYearAndMonthAndDay(@PathVariable String username,
                                                      @PathVariable String institutionName,
                                                      @PathVariable int year,
                                                      @PathVariable int month,
                                                      @PathVariable int day) {
        UserEntity representativeEntity = userService.getUserEntityByUsername(username);
        InstitutionEntity institutionEntity = institutionService
                .getWorkPlaceEntityByRepresentativeEntityAndInstitutionName(representativeEntity, institutionName);
        List<Visit> visits = visitService.getByRepresentativeAndInstitutionAndYearAndMonthAndDay(representativeEntity, institutionEntity, year, month, day);
        linkCreator.addLinksToRepresentativesVisits(visits, username, institutionName);
        return visits;
    }

    @GetMapping("/year/{year}/month/{month}/day/{day}/visit/{visitId}")
    public Visit showVisit(@PathVariable String username,
                           @PathVariable String institutionName,
                           @PathVariable long visitId) {
        UserEntity representativeEntity = userService.getUserEntityByUsername(username);
        InstitutionEntity institutionEntity = institutionService
                .getWorkPlaceEntityByRepresentativeEntityAndInstitutionName(representativeEntity, institutionName);
        Visit visit = visitService.getByRepresentativeAndInstitutionAndVisitId(representativeEntity, institutionEntity, visitId);
        linkCreator.addLinksToRepresentativesVisit(visit, username, institutionName);
        return visit;
    }

    @DeleteMapping("/year/{year}/month/{month}/day/{day}/visit/{visitId}")
    public void deleteVisit(@PathVariable String username,
                            @PathVariable String institutionName,
                            @PathVariable long visitId) {
        UserEntity representativeEntity = userService.getUserEntityByUsername(username);
        InstitutionEntity institutionEntity = institutionService
                .getWorkPlaceEntityByRepresentativeEntityAndInstitutionName(representativeEntity, institutionName);
        visitService.delete(representativeEntity, institutionEntity, visitId);
    }

    @PostMapping("/create-timetable")
    public List<Visit> createTimetable(@Valid @RequestBody TimetableTemplate timetableTemplate,
                                       @PathVariable String username,
                                       @PathVariable String institutionName) {
        UserEntity representativeEntity = userService.getUserEntityByUsername(username);
        InstitutionEntity institutionEntity = institutionService
                .getWorkPlaceEntityByRepresentativeEntityAndInstitutionName(representativeEntity, institutionName);
        List<VisitEntity> visitEntities = visitEntityGenerator.createVisits(timetableTemplate, representativeEntity, institutionEntity);
        conflictAnalyzer.checkConflicts(visitEntities, representativeEntity, institutionEntity);
        List<Visit> visits = visitService.saveAll(visitEntities);
        linkCreator.addLinksToRepresentativesVisits(visits, username, institutionName);
        return visits;
    }
}
