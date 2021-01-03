package com.zdzimi.registration.controller.link;

import com.zdzimi.registration.controller.restController.UserController;
import com.zdzimi.registration.core.model.Institution;
import com.zdzimi.registration.core.model.Place;
import com.zdzimi.registration.core.model.User;
import com.zdzimi.registration.core.model.Visit;
import com.zdzimi.registration.core.model.template.TimetableTemplate;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Service
public class LinkCreator {

    public void addLinksToUser(User user) {
        user.add(createLinkToAllInstitutions(user),
                createLinkToRecognizedInstitutions(user),
                createLinkToWorkPlaces(user),
                createLinkToVisits(user));
    }

    public void addLinksToInstitutions(List<Institution> institutions, String username) {
        for (Institution institution : institutions) {
            String institutionName = institution.getInstitutionName();
            institution.add(createLinkToInstitution(username, institutionName));
        }
    }

    public void addLinksToInstitution(Institution institution, String username) {
        String institutionName = institution.getInstitutionName();
        institution.add(createLinkToRepresentatives(username, institutionName), createLinkToUser(username));
    }

    public void addLinksToRepresentatives(List<User> representatives, String username, String institutionName) {
        for (User representative : representatives) {
            String representativeUsername = representative.getUsername();
            representative.add(createLinkToRepresentative(username, institutionName, representativeUsername));
        }
    }

    public void addLinksToRepresentative(User representative, String username, String institutionName) {
        @NotNull String representativeUsername = representative.getUsername();
        representative.add(createLinkToTimetable(username, institutionName, representativeUsername),
                createLinkToInstitution(username, institutionName),
                createLinkToUser(username));
    }

    public void addLinksToCurrentVisits(List<Visit> currentVisits, String username, String institutionName, String representativeName) {
        for (Visit visit : currentVisits) {
            Long visitId = visit.getVisitId();
            visit.add(createLinkToCurrentVisit(username, institutionName, representativeName, visitId));
        }
    }

    public void addLinksToCurrentVisit(Visit visit, String username, String institutionName, String representativeName) {
        Long visitId = visit.getVisitId();
        visit.add(createLinkToCurrentVisit(username, institutionName, representativeName, visitId),
                createLinkToTimetable(username, institutionName, representativeName),
                createLinkToInstitution(username, institutionName),
                createLinkToUser(username));
    }

    public void addLinksToUsersVisits(List<Visit> visits, String username) {
        for (Visit visit : visits) {
            Long visitId = visit.getVisitId();
            visit.add(createLinkToUsersVisit(username, visitId));
        }
    }

    public void addLinksToUsersVisit(Visit visit, String username) {
        Long visitId = visit.getVisitId();
        visit.add(createLinkToUsersVisit(username, visitId),
                createLinkToUser(username));
    }

    public void addLinksToWorkPlaces(List<Institution> workPlaces, String username) {
        for (Institution workPlace : workPlaces) {
            @NotNull String institutionName = workPlace.getInstitutionName();
            workPlace.add(createLinkToWorkPlace(username, institutionName));
        }
    }

    public void addLinksToWorkPlace(Institution workPlace, String username) {
        @NotNull String institutionName = workPlace.getInstitutionName();
        workPlace.add(createLinkToPlaces(username, institutionName),
                createLinkToGetNextTemplate(username, institutionName),
                createLinkToCurrentMonthVisits(username, institutionName),
                createLinkToUser(username));
    }

    public void addLinksToPlaces(List<Place> places, String username, String institutionName) {
        for (Place place : places) {
            @NotNull String placeName = place.getPlaceName();
            place.add(createLinkToPlace(username, institutionName, placeName));
        }
    }

    public void addLinksToPlace(Place place, String username, String institutionName) {
        place.add(createLinkToPlace(username, institutionName, place.getPlaceName()),
                createLinkToWorkPlace(username, institutionName),
                createLinkToUser(username));
    }

    public void addLinksToTemplate(TimetableTemplate timetableTemplate, String username, String institutionName) {
        timetableTemplate.add(createLinkToWorkPlace(username, institutionName),
                createLinkToUser(username));
    }

    public void addLinksToRepresentativesVisits(List<Visit> visits, String username, String institutionName) {
        for (Visit visit : visits) {
            visit.add(createLinkToRepresentativesVisit(username, institutionName, visit));
        }
    }

    public void addLinksToRepresentativesVisit(Visit visit, String username, String institutionName) {
        visit.add(createLinkToRepresentativesVisit(username, institutionName, visit),
                createLinkToWorkPlace(username, institutionName),
                createLinkToUser(username));
    }

    private Link createLinkToRepresentativesVisit(String username, String institutionName, Visit visit) {
        int year = visit.getVisitStart().getYear();
        int month = visit.getVisitStart().getMonthValue();
        int day = visit.getVisitStart().getDayOfMonth();
        Long visitId = visit.getVisitId();
        return linkTo(UserController.class)
                .slash(username)
                .slash("work-places")
                .slash(institutionName)
                .slash("year")
                .slash(year)
                .slash("month")
                .slash(month)
                .slash("day")
                .slash(day)
                .slash("visit")
                .slash(visitId)
                .withRel(day + "." + month + "." + year + "-" + visitId);
    }

    private Link createLinkToPlace(String username, String institutionName, String placeName) {
        return linkTo(UserController.class)
                .slash(username)
                .slash("work-places")
                .slash(institutionName)
                .slash("place")
                .slash(placeName)
                .withRel(placeName);
    }

    private Link createLinkToCurrentMonthVisits(String username, String institutionName) {
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        return linkTo(UserController.class)
                .slash(username)
                .slash("work-places")
                .slash(institutionName)
                .slash("year")
                .slash(year)
                .slash("month")
                .slash(month)
                .withRel(month + "." + year);
    }

    private Link createLinkToGetNextTemplate(String username, String institutionName) {
        return linkTo(UserController.class)
                .slash(username)
                .slash("work-places")
                .slash(institutionName)
                .slash("get-next-template")
                .withRel("getNextTemplate");
    }

    private Link createLinkToPlaces(String username, String institutionName) {
        return linkTo(UserController.class)
                .slash(username)
                .slash("work-places")
                .slash(institutionName)
                .slash("place")
                .withRel("place");
    }

    private Link createLinkToWorkPlace(String username, String institutionName) {
        return linkTo(UserController.class)
                .slash(username)
                .slash("work-places")
                .slash(institutionName)
                .withRel(institutionName);
    }

    private Link createLinkToUsersVisit(String username, Long visitId) {
        return linkTo(UserController.class)
                .slash(username)
                .slash("visits")
                .slash(visitId)
                .withRel("visit-" + visitId);
    }

    private Link createLinkToCurrentVisit(String username, String institutionName, String representativeName, Long visitId) {
        return linkTo(UserController.class)
                .slash(username)
                .slash("institution")
                .slash(institutionName)
                .slash("representative")
                .slash(representativeName)
                .slash("timetable")
                .slash(visitId)
                .withRel("visit-" + visitId);
    }

    private Link createLinkToTimetable(String username, String institutionName, String representativeUsername) {
        return linkTo(UserController.class)
                .slash(username)
                .slash("institution")
                .slash(institutionName)
                .slash("representative")
                .slash(representativeUsername)
                .slash("timetable")
                .withRel("timetable");
    }

    private Link createLinkToRepresentative(String username, String institutionName, String representativeUsername) {
        return linkTo(UserController.class)
                .slash(username)
                .slash("institution")
                .slash(institutionName)
                .slash("representative")
                .slash(representativeUsername)
                .withRel(representativeUsername);
    }

    private Link createLinkToUser(String username) {
        return linkTo(UserController.class)
                .slash(username)
                .withRel(username);
    }

    private Link createLinkToRepresentatives(String username, String institutionName) {
        return linkTo(UserController.class)
                .slash(username)
                .slash("institution")
                .slash(institutionName)
                .slash("representative")
                .withRel("representative");
    }

    private Link createLinkToInstitution(String username, String institutionName) {
        return linkTo(UserController.class)
                .slash(username)
                .slash("institution")
                .slash(institutionName)
                .withRel(institutionName);
    }

    private Link createLinkToVisits(User user) {
        return linkTo(UserController.class)
                .slash(user.getUsername())
                .slash("visits")
                .withRel("visits");
    }

    private Link createLinkToWorkPlaces(User user) {
        return linkTo(UserController.class)
                .slash(user.getUsername())
                .slash("work-places")
                .withRel("workPlaces");
    }

    private Link createLinkToRecognizedInstitutions(User user) {
        return linkTo(UserController.class)
                .slash(user.getUsername())
                .slash("institution")
                .slash("recognized")
                .withRel("recognizedInstitutions");
    }

    private Link createLinkToAllInstitutions(User user) {
        return linkTo(UserController.class)
                .slash(user.getUsername())
                .slash("institution")
                .slash("all")
                .withRel("allInstitutions");
    }
}
