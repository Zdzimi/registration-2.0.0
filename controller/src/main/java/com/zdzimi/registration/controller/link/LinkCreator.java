package com.zdzimi.registration.controller.link;

import com.zdzimi.registration.controller.restController.UserController;
import com.zdzimi.registration.core.model.Institution;
import com.zdzimi.registration.core.model.Place;
import com.zdzimi.registration.core.model.User;
import com.zdzimi.registration.core.model.Visit;
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
                createLinkToAdvancedSearching(user),
                createLinkToWorkPlaces(user),
                createLinkToVisits(user),
                createLinkToUpdateUser(user),
                createLinkToCreateWorkPlace(user));
    }

    public void addLinksToInstitutions(List<Institution> institutions, String username) {
        for (Institution institution : institutions) {
            String institutionName = institution.getInstitutionName();
            institution.add(createLinkToInstitution(username, institutionName));
        }
    }

    public void addLinksToInstitution(Institution institution, String username) {
        String institutionName = institution.getInstitutionName();
        institution.add(createLinkToRepresentatives(username, institutionName));
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
                createLinkToRepresentatives(username, institutionName));
    }

    public void addLinksToCurrentVisits(List<Visit> currentVisits, String username, String institutionName, String representativeName) {
        for (Visit visit : currentVisits) {
            visit.add(createLinkToCurrentVisit(username, institutionName, representativeName, visit));
        }
    }

    public void addLinksToCurrentVisit(Visit visit, String username, String institutionName, String representativeName) {
        Long visitId = visit.getVisitId();
        visit.add(createLinkToCurrentVisit(username, institutionName, representativeName, visitId),
                createLinkToTimetable(username, institutionName, representativeName),
                createLinkToRepresentatives(username, institutionName));
    }

    public void addLinksToUsersVisits(List<Visit> visits, String username) {
        for (Visit visit : visits) {
            visit.add(createLinkToUsersVisit(username, visit));
        }
    }

    public void addLinksToUsersVisit(Visit visit, String username) {
        Long visitId = visit.getVisitId();
        visit.add(createLinkToUsersVisit(username, visitId));
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
                createLinkToRepresentativesOfWorkPlace(username, institutionName),
                createLinkToGetNextTemplate(username, institutionName),
                createLinkToCurrentYearVisits(username, institutionName),
                createLinkToCurrentMonthVisits(username, institutionName),
                createLinkToCurrentDayVisits(username, institutionName));
    }

    public void addLinksToPlaces(List<Place> places, String username, String institutionName) {
        for (Place place : places) {
            @NotNull String placeName = place.getPlaceName();
            place.add(createLinkToPlace(username, institutionName, placeName));
        }
    }

    public void addLinksToPlace(Place place, String username, String institutionName) {
        place.add(createLinkToPlace(username, institutionName, place.getPlaceName()));
    }

    public void addLinksToRepresentativesVisits(List<Visit> visits, String username, String institutionName) {
        for (Visit visit : visits) {
            visit.add(createLinkToRepresentativesVisit(username, institutionName, visit));
        }
    }

    public void addLinksToRepresentativesVisit(Visit visit, String username, String institutionName) {
        visit.add(createLinkToDeleteVisit(username, institutionName, visit));
    }

    private Link createLinkToDeleteVisit(String username, String institutionName, Visit visit) {
        int year = visit.getVisitStart().getYear();
        int month = visit.getVisitStart().getMonthValue();
        int day = visit.getVisitStart().getDayOfMonth();
        Long visitId = visit.getVisitId();
        return linkTo(UserController.class)
                .slash(username)
                .slash("work-place")
                .slash(institutionName)
                .slash("year")
                .slash(year)
                .slash("month")
                .slash(month)
                .slash("day")
                .slash(day)
                .slash("visit")
                .slash(visitId)
                .withRel("visit");
    }

    private Link createLinkToRepresentativesVisit(String username, String institutionName, Visit visit) {
        int year = visit.getVisitStart().getYear();
        int month = visit.getVisitStart().getMonthValue();
        int day = visit.getVisitStart().getDayOfMonth();
        Long visitId = visit.getVisitId();
        return linkTo(UserController.class)
                .slash(username)
                .slash("work-place")
                .slash(institutionName)
                .slash("year")
                .slash(year)
                .slash("month")
                .slash(month)
                .slash("day")
                .slash(day)
                .slash("visit")
                .slash(visitId)
                .withRel(visit.getVisitStart().toString());
    }

    private Link createLinkToPlace(String username, String institutionName, String placeName) {
        return linkTo(UserController.class)
                .slash(username)
                .slash("work-place")
                .slash(institutionName)
                .slash("place")
                .slash(placeName)
                .withRel(placeName);
    }

    private Link createLinkToCurrentYearVisits(String username, String institutionName) {
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        return linkTo(UserController.class)
                .slash(username)
                .slash("work-place")
                .slash(institutionName)
                .slash("year")
                .slash(year)
                .withRel("currentYear");
    }

    private Link createLinkToCurrentMonthVisits(String username, String institutionName) {
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        return linkTo(UserController.class)
                .slash(username)
                .slash("work-place")
                .slash(institutionName)
                .slash("year")
                .slash(year)
                .slash("month")
                .slash(month)
                .withRel("currentMonth");
    }

    private Link createLinkToCurrentDayVisits(String username, String institutionName) {
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        return linkTo(UserController.class)
                .slash(username)
                .slash("work-place")
                .slash(institutionName)
                .slash("year")
                .slash(year)
                .slash("month")
                .slash(month)
                .slash("day")
                .slash(day)
                .withRel("currentDay");
    }

    private Link createLinkToGetNextTemplate(String username, String institutionName) {
        return linkTo(UserController.class)
                .slash(username)
                .slash("work-place")
                .slash(institutionName)
                .slash("get-next-template")
                .withRel("getNextTemplate");
    }

    private Link createLinkToPlaces(String username, String institutionName) {
        return linkTo(UserController.class)
                .slash(username)
                .slash("work-place")
                .slash(institutionName)
                .slash("places")
                .withRel("places");
    }

    private Link createLinkToRepresentativesOfWorkPlace(String username, String institutionName) {
        return linkTo(UserController.class)
                .slash(username)
                .slash("work-place")
                .slash(institutionName)
                .slash("representatives")
                .withRel("representatives");
    }

    private Link createLinkToWorkPlace(String username, String institutionName) {
        return linkTo(UserController.class)
                .slash(username)
                .slash("work-place")
                .slash(institutionName)
                .withRel(institutionName);
    }

    private Link createLinkToUsersVisit(String username, Long visitId) {
        return linkTo(UserController.class)
                .slash(username)
                .slash("visits")
                .slash(visitId)
                .withRel("visit");
    }

    private Link createLinkToUsersVisit(String username, Visit visit) {
        return linkTo(UserController.class)
                .slash(username)
                .slash("visits")
                .slash(visit.getVisitId())
                .withRel(visit.getVisitStart().toString());
    }

    private Link createLinkToCurrentVisit(String username, String institutionName, String representativeName, Long visitId) {
        return linkTo(UserController.class)
                .slash(username)
                .slash("institution")
                .slash(institutionName)
                .slash("representatives")
                .slash(representativeName)
                .slash("timetable")
                .slash(visitId)
                .withRel("visit");
    }

    private Link createLinkToCurrentVisit(String username, String institutionName, String representativeName, Visit visit) {
        return linkTo(UserController.class)
                .slash(username)
                .slash("institution")
                .slash(institutionName)
                .slash("representatives")
                .slash(representativeName)
                .slash("timetable")
                .slash(visit.getVisitId())
                .withRel(visit.getVisitStart().toString());
    }

    private Link createLinkToTimetable(String username, String institutionName, String representativeUsername) {
        return linkTo(UserController.class)
                .slash(username)
                .slash("institution")
                .slash(institutionName)
                .slash("representatives")
                .slash(representativeUsername)
                .slash("timetable")
                .withRel("timetable");
    }

    private Link createLinkToRepresentative(String username, String institutionName, String representativeUsername) {
        return linkTo(UserController.class)
                .slash(username)
                .slash("institution")
                .slash(institutionName)
                .slash("representatives")
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
                .slash("representatives")
                .withRel("representatives");
    }

    private Link createLinkToInstitution(String username, String institutionName) {
        return linkTo(UserController.class)
                .slash(username)
                .slash("institution")
                .slash(institutionName)
                .withRel(institutionName);
    }

    private Link createLinkToCreateWorkPlace(User user) {
        return linkTo(UserController.class)
                .slash(user.getUsername())
                .slash("new-work-place")
                .withRel("createWorkPlace");
    }

    private Link createLinkToUpdateUser(User user) {
        return linkTo(UserController.class)
                .slash(user.getUsername())
                .slash("update-user")
                .withRel("updateUser");
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

    private Link createLinkToAdvancedSearching(User user) {
        return linkTo(UserController.class)
                .slash(user.getUsername())
                .slash("institutions")
                .slash("search-by?institutionName=pInstitutionName&province=pProvince&city=pCity&typeOfServices=pTypeOfServices")
                .withRel("searchBy");
    }

    private Link createLinkToRecognizedInstitutions(User user) {
        return linkTo(UserController.class)
                .slash(user.getUsername())
                .slash("institutions")
                .slash("recognized")
                .withRel("recognizedInstitutions");
    }

    private Link createLinkToAllInstitutions(User user) {
        return linkTo(UserController.class)
                .slash(user.getUsername())
                .slash("institutions")
                .slash("all")
                .withRel("allInstitutions");
    }
}
