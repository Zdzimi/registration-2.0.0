package com.zdzimi.registration.controller.restController;

import com.zdzimi.registration.controller.link.LinkCreator;
import com.zdzimi.registration.core.model.Place;
import com.zdzimi.registration.core.model.Visit;
import com.zdzimi.registration.core.model.template.TimetableTemplate;
import com.zdzimi.registration.core.model.timetable.MonthTimetable;
import com.zdzimi.registration.data.entity.InstitutionEntity;
import com.zdzimi.registration.data.entity.UserEntity;
import com.zdzimi.registration.data.entity.VisitEntity;
import com.zdzimi.registration.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/registration/{username}/work-place/{institutionName}")
@RequiredArgsConstructor
public class RepresentativeUtilsController {

    private final LoggedUserProvider loggedUserProvider;
    private final VisitService visitService;
    private final InstitutionService institutionService;
    private final PlaceService placeService;
    private final TimetableTemplateService timetableTemplateService;
    private final VisitEntityGenerator visitEntityGenerator;
    private final ConflictAnalyzer conflictAnalyzer;
    private final LinkCreator linkCreator;

    @GetMapping("/get-next-template")
    public TimetableTemplate prepareNextTemplate(@PathVariable String username, @PathVariable String institutionName) {
        UserEntity representativeEntity = loggedUserProvider.provideLoggedUser(username);
        InstitutionEntity institutionEntity = institutionService
                .getWorkPlaceEntityByRepresentativeEntityAndInstitutionName(representativeEntity, institutionName);
        List<Place> places = placeService.getPlaces(institutionEntity);
        Visit lastProvidedVisit = visitService.getLastProvidedVisit(representativeEntity, institutionEntity);
        return timetableTemplateService.prepareTemplate(lastProvidedVisit, places);
    }

    @GetMapping("/year/{year}")
    public List<MonthTimetable> showVisitsByYear(@PathVariable String username,
                                                 @PathVariable String institutionName,
                                                 @PathVariable int year) {
        UserEntity representativeEntity = loggedUserProvider.provideLoggedUser(username);
        InstitutionEntity institutionEntity = institutionService
                .getWorkPlaceEntityByRepresentativeEntityAndInstitutionName(representativeEntity, institutionName);
        List<Visit> visits = visitService.getByRepresentativeAndInstitutionAndYear(representativeEntity, institutionEntity, year);
        linkCreator.addLinksToRepresentativesVisits(visits, username, institutionName);
        return MonthTimetable.createTimetable(visits);
    }

    @GetMapping("/year/{year}/month/{month}")
    public List<MonthTimetable> showVisitsByYearAndMonth(@PathVariable String username,
                                                         @PathVariable String institutionName,
                                                         @PathVariable int year,
                                                         @PathVariable int month) {
        UserEntity representativeEntity = loggedUserProvider.provideLoggedUser(username);
        InstitutionEntity institutionEntity = institutionService
                .getWorkPlaceEntityByRepresentativeEntityAndInstitutionName(representativeEntity, institutionName);
        List<Visit> visits = visitService.getByRepresentativeAndInstitutionAndYearAndMonth(representativeEntity, institutionEntity, year, month);
        linkCreator.addLinksToRepresentativesVisits(visits, username, institutionName);
        return MonthTimetable.createTimetable(visits);
    }

    @GetMapping("/year/{year}/month/{month}/get-template")
    public TimetableTemplate prepareTemplateByYearAndMonth(@PathVariable String username,
                                                           @PathVariable String institutionName,
                                                           @PathVariable int year,
                                                           @PathVariable int month) {
        UserEntity representativeEntity = loggedUserProvider.provideLoggedUser(username);
        InstitutionEntity institutionEntity = institutionService
                .getWorkPlaceEntityByRepresentativeEntityAndInstitutionName(representativeEntity, institutionName);
        List<Place> places = placeService.getPlaces(institutionEntity);
        return timetableTemplateService.prepareTemplate(year, month, places);
    }

    @GetMapping("/year/{year}/month/{month}/day/{day}")
    public List<MonthTimetable> showVisitsByYearAndMonthAndDay(@PathVariable String username,
                                                      @PathVariable String institutionName,
                                                      @PathVariable int year,
                                                      @PathVariable int month,
                                                      @PathVariable int day) {
        UserEntity representativeEntity = loggedUserProvider.provideLoggedUser(username);
        InstitutionEntity institutionEntity = institutionService
                .getWorkPlaceEntityByRepresentativeEntityAndInstitutionName(representativeEntity, institutionName);
        List<Visit> visits = visitService.getByRepresentativeAndInstitutionAndYearAndMonthAndDay(representativeEntity, institutionEntity, year, month, day);
        linkCreator.addLinksToRepresentativesVisits(visits, username, institutionName);
        return MonthTimetable.createTimetable(visits);
    }

    @GetMapping("/year/{year}/month/{month}/day/{day}/visit/{visitId}")
    public Visit showVisit(@PathVariable String username,
                           @PathVariable String institutionName,
                           @PathVariable long visitId) {
        UserEntity representativeEntity = loggedUserProvider.provideLoggedUser(username);
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
        UserEntity representativeEntity = loggedUserProvider.provideLoggedUser(username);
        InstitutionEntity institutionEntity = institutionService
                .getWorkPlaceEntityByRepresentativeEntityAndInstitutionName(representativeEntity, institutionName);
        visitService.delete(representativeEntity, institutionEntity, visitId);
    }

    @PostMapping("/create-timetable")
    public List<Visit> createTimetable(@Valid @RequestBody TimetableTemplate timetableTemplate,
                                       @PathVariable String username,
                                       @PathVariable String institutionName) {
        UserEntity representativeEntity = loggedUserProvider.provideLoggedUser(username);
        InstitutionEntity institutionEntity = institutionService
                .getWorkPlaceEntityByRepresentativeEntityAndInstitutionName(representativeEntity, institutionName);
        List<VisitEntity> visitEntities = visitEntityGenerator.createVisits(timetableTemplate, representativeEntity, institutionEntity);
        conflictAnalyzer.checkConflicts(visitEntities, representativeEntity, institutionEntity);
        return visitService.saveAll(visitEntities);
    }
}
