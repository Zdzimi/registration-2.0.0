package com.zdzimi.registration.controller.link;

import com.zdzimi.registration.core.model.Institution;
import com.zdzimi.registration.core.model.Place;
import com.zdzimi.registration.core.model.User;
import com.zdzimi.registration.core.model.Visit;
import com.zdzimi.registration.core.model.template.TimetableTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Links;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LinkCreatorTest {

    private User user;
    private Institution barber;
    private Institution tattoo;
    private User representativeAnna;
    private User representativeBarbara;
    private Visit visitFirst;
    private Visit visitSecond;
    private Place placeFirst;
    private Place placeSecond;
    private TimetableTemplate timetableTemplate;

    {
        user = new User();
        user.setUsername("Adrianna");

        tattoo = new Institution();
        tattoo.setInstitutionName("tattoo");

        barber = new Institution();
        barber.setInstitutionName("barber");

        representativeAnna = new User();
        representativeAnna.setUsername("Anna");

        representativeBarbara = new User();
        representativeBarbara.setUsername("Barbara");

        visitFirst = new Visit();
        visitFirst.setVisitId(1L);
        visitFirst.setVisitStart(LocalDateTime.of(2020,2,2,10,30));

        visitSecond = new Visit();
        visitSecond.setVisitId(2L);
        visitSecond.setVisitStart(LocalDateTime.of(2030,3,3,13,0));

        placeFirst = new Place();
        placeFirst.setPlaceName("roomNo1");

        placeSecond = new Place();
        placeSecond.setPlaceName("roomNo2");

        timetableTemplate = new TimetableTemplate();
    }

    private LinkCreator linkCreator = new LinkCreator();

    @Test
    void shouldAddLinks() {
        linkCreator.addLinksToUser(user);
        Links links = user.getLinks();
        Link allInstitutionsLink = links.getLink("allInstitutions").get();
        Link recognizedInstitutionsLink = links.getLink("recognizedInstitutions").get();
        Link searchByLink = links.getLink("searchBy").get();
        Link workPlacesLink = links.getLink("workPlaces").get();
        Link visitsLink = links.getLink("visits").get();
        Link updateUserLink = links.getLink("updateUser").get();
        Link createWorkPlacesLink = links.getLink("createWorkPlace").get();

        assertEquals("/registration/Adrianna/institutions/all", allInstitutionsLink.getHref());
        assertEquals("allInstitutions", allInstitutionsLink.getRel().value());

        assertEquals("/registration/Adrianna/institutions/recognized", recognizedInstitutionsLink.getHref());
        assertEquals("recognizedInstitutions", recognizedInstitutionsLink.getRel().value());

        assertEquals("/registration/Adrianna/institutions/search-by?institutionName=pInstitutionName&province=pProvince&city=pCity&typeOfServices=pTypeOfServices", searchByLink.getHref());
        assertEquals("searchBy", searchByLink.getRel().value());

        assertEquals("/registration/Adrianna/work-places", workPlacesLink.getHref());
        assertEquals("workPlaces", workPlacesLink.getRel().value());

        assertEquals("/registration/Adrianna/visits", visitsLink.getHref());
        assertEquals("visits", visitsLink.getRel().value());

        assertEquals("/registration/Adrianna/update-user", updateUserLink.getHref());
        assertEquals("updateUser", updateUserLink.getRel().value());

        assertEquals("/registration/Adrianna/new-work-place", createWorkPlacesLink.getHref());
        assertEquals("createWorkPlace", createWorkPlacesLink.getRel().value());
    }

    @Test
    void shouldAddLinksToInstitutions() {
        List<Institution> institutions = Arrays.asList(tattoo, barber);

        linkCreator.addLinksToInstitutions(institutions, user.getUsername());

        Link tattooLink = (Link) tattoo.getLink(tattoo.getInstitutionName()).get();
        Link barberLink = (Link) barber.getLink(barber.getInstitutionName()).get();

        assertEquals("/registration/Adrianna/institution/barber", barberLink.getHref());
        assertEquals("barber", barberLink.getRel().value());

        assertEquals("/registration/Adrianna/institution/tattoo", tattooLink.getHref());
        assertEquals("tattoo", tattooLink.getRel().value());
    }

    @Test
    void shouldAddLinksToInstitution() {
        linkCreator.addLinksToInstitution(barber, user.getUsername());

        Link representativesLink = (Link) barber.getLink("representatives").get();

        assertEquals("/registration/Adrianna/institution/barber/representatives", representativesLink.getHref());
        assertEquals("representatives", representativesLink.getRel().value());
    }

    @Test
    void shouldAddLinksToRepresentatives() {
        List<User> representatives = Arrays.asList(representativeAnna, representativeBarbara);

        linkCreator.addLinksToRepresentatives(representatives, user.getUsername(), barber.getInstitutionName());

        Link annaLink = (Link) representativeAnna.getLink(representativeAnna.getUsername()).get();
        Link barbaraLink = (Link) representativeBarbara.getLink(representativeBarbara.getUsername()).get();

        assertEquals("/registration/Adrianna/institution/barber/representatives/Anna", annaLink.getHref());
        assertEquals(representativeAnna.getUsername(), annaLink.getRel().value());

        assertEquals("/registration/Adrianna/institution/barber/representatives/Barbara", barbaraLink.getHref());
        assertEquals(representativeBarbara.getUsername(), barbaraLink.getRel().value());
    }

    @Test
    void shouldAddLinksToRepresentative() {
        linkCreator.addLinksToRepresentative(representativeAnna, user.getUsername(), barber.getInstitutionName());

        Link timetableLink = (Link) representativeAnna.getLink("timetable").get();
        Link representatives = (Link) representativeAnna.getLink("representatives").get();

        assertEquals("/registration/Adrianna/institution/barber/representatives/Anna/timetable", timetableLink.getHref());
        assertEquals("timetable", timetableLink.getRel().value());

        assertEquals("/registration/Adrianna/institution/barber/representatives", representatives.getHref());
        assertEquals("representatives", representatives.getRel().value());
    }

    @Test
    void shouldAddLinksToCurrentVisits() {
        List<Visit> visits = Arrays.asList(visitFirst, visitSecond);

        linkCreator.addLinksToCurrentVisits(visits, user.getUsername(), barber.getInstitutionName(), representativeAnna.getUsername());

        Link firstLink = (Link) visitFirst.getLink(visitFirst.getVisitStart().toString()).get();
        Link secondLink = (Link) visitSecond.getLink(visitSecond.getVisitStart().toString()).get();

        assertEquals("/registration/Adrianna/institution/barber/representatives/Anna/timetable/" + visitFirst.getVisitId(), firstLink.getHref());
        assertEquals(visitFirst.getVisitStart().toString(), firstLink.getRel().value());

        assertEquals("/registration/Adrianna/institution/barber/representatives/Anna/timetable/" + visitSecond.getVisitId(), secondLink.getHref());
        assertEquals(visitSecond.getVisitStart().toString(), secondLink.getRel().value());
    }

    @Test
    void shouldAddLinksToCurrentVisit() {
        linkCreator.addLinksToCurrentVisit(visitFirst, user.getUsername(), barber.getInstitutionName(), representativeAnna.getUsername());

        Link visitLink = (Link) visitFirst.getLink("visit").get();
        Link timetableLink = (Link) visitFirst.getLink("timetable").get();
        Link representatives = (Link) visitFirst.getLink("representatives").get();

        assertEquals("/registration/Adrianna/institution/barber/representatives/Anna/timetable/" + visitFirst.getVisitId(), visitLink.getHref());
        assertEquals("visit", visitLink.getRel().value());

        assertEquals("/registration/Adrianna/institution/barber/representatives/Anna/timetable", timetableLink.getHref());
        assertEquals("timetable", timetableLink.getRel().value());

        assertEquals("/registration/Adrianna/institution/barber/representatives", representatives.getHref());
        assertEquals("representatives", representatives.getRel().value());
    }

    @Test
    void shouldAddLinksToUsersVisits() {
        List<Visit> visits = Arrays.asList(visitFirst, visitSecond);

        linkCreator.addLinksToUsersVisits(visits, user.getUsername());

        Link firstLink = (Link) visitFirst.getLink(visitFirst.getVisitStart().toString()).get();
        Link secondLink = (Link) visitSecond.getLink(visitSecond.getVisitStart().toString()).get();

        assertEquals("/registration/Adrianna/visits/" + visitFirst.getVisitId(), firstLink.getHref());
        assertEquals(visitFirst.getVisitStart().toString(), firstLink.getRel().value());

        assertEquals("/registration/Adrianna/visits/" + visitSecond.getVisitId(), secondLink.getHref());
        assertEquals(visitSecond.getVisitStart().toString(), secondLink.getRel().value());
    }

    @Test
    void shouldAddLinksToUsersVisit() {
        linkCreator.addLinksToUsersVisit(visitFirst, user.getUsername());

        Link link = (Link) visitFirst.getLink("visit").get();

        assertEquals("/registration/Adrianna/visits/" + visitFirst.getVisitId(), link.getHref());
        assertEquals("visit", link.getRel().value());
    }

    @Test
    void shouldAddLinksToWorkPlaces() {
        List<Institution> workPlaces = Arrays.asList(barber, tattoo);

        linkCreator.addLinksToWorkPlaces(workPlaces, user.getUsername());

        Link barberLink = (Link) barber.getLink(barber.getInstitutionName()).get();
        Link tattooLink = (Link) tattoo.getLink(tattoo.getInstitutionName()).get();

        assertEquals("/registration/Adrianna/work-place/" + barber.getInstitutionName(), barberLink.getHref());
        assertEquals("/registration/Adrianna/work-place/" + tattoo.getInstitutionName(), tattooLink.getHref());

        assertEquals(barber.getInstitutionName(), barberLink.getRel().value());
        assertEquals(tattoo.getInstitutionName(), tattooLink.getRel().value());
    }

    @Test
    void shouldAddLinksToWorkPlace() {
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();

        linkCreator.addLinksToWorkPlace(barber, user.getUsername());

        Link placeLink = (Link) barber.getLink("places").get();
        Link representativesLink = (Link) barber.getLink("representatives").get();
        Link templateLink = (Link) barber.getLink("getNextTemplate").get();
        Link yearLink = (Link) barber.getLink("currentYear").get();
        Link monthLink = (Link) barber.getLink("currentMonth").get();
        Link dayLink = (Link) barber.getLink("currentDay").get();

        assertEquals("/registration/Adrianna/work-place/barber/places", placeLink.getHref());
        assertEquals("/registration/Adrianna/work-place/barber/representatives", representativesLink.getHref());
        assertEquals("/registration/Adrianna/work-place/barber/get-next-template", templateLink.getHref());
        assertEquals("/registration/Adrianna/work-place/barber/year/" + year, yearLink.getHref());
        assertEquals("/registration/Adrianna/work-place/barber/year/" + year + "/month/" + month, monthLink.getHref());
        assertEquals("/registration/Adrianna/work-place/barber/year/" + year + "/month/" + month + "/day/" + day, dayLink.getHref());

        assertEquals("places", placeLink.getRel().value());
        assertEquals("representatives", representativesLink.getRel().value());
        assertEquals("getNextTemplate", templateLink.getRel().value());
        assertEquals("currentYear", yearLink.getRel().value());
        assertEquals("currentMonth", monthLink.getRel().value());
        assertEquals("currentDay", dayLink.getRel().value());
    }

    @Test
    void shouldAddLinksToPlaces() {
        List<Place> places = Arrays.asList(placeFirst, placeSecond);

        linkCreator.addLinksToPlaces(places, user.getUsername(), tattoo.getInstitutionName());

        Link placeFirstLink = (Link) placeFirst.getLink(placeFirst.getPlaceName()).get();
        Link placeSecondLink = (Link) placeSecond.getLink(placeSecond.getPlaceName()).get();

        assertEquals("/registration/Adrianna/work-place/tattoo/place/" + placeFirst.getPlaceName(), placeFirstLink.getHref());
        assertEquals("/registration/Adrianna/work-place/tattoo/place/" + placeSecond.getPlaceName(), placeSecondLink.getHref());

        assertEquals(placeFirst.getPlaceName(), placeFirstLink.getRel().value());
        assertEquals(placeSecond.getPlaceName(), placeSecondLink.getRel().value());
    }

    @Test
    void shouldAddLinksToPlace() {
        linkCreator.addLinksToPlace(placeFirst, user.getUsername(), tattoo.getInstitutionName());

        Link placeLink = (Link) placeFirst.getLink(placeFirst.getPlaceName()).get();

        assertEquals("/registration/Adrianna/work-place/tattoo/place/" + placeFirst.getPlaceName(), placeLink.getHref());

        assertEquals(placeFirst.getPlaceName(), placeLink.getRel().value());
    }

    @Test
    void shouldAddLinksToRepresentativesVisits() {
        List<Visit> visits = Arrays.asList(visitFirst, visitSecond);

        linkCreator.addLinksToRepresentativesVisits(visits, user.getUsername(), tattoo.getInstitutionName());

        int visitFirstYear = visitFirst.getVisitStart().getYear();
        int visitFirstMonth = visitFirst.getVisitStart().getMonthValue();
        int visitFirstDay = visitFirst.getVisitStart().getDayOfMonth();
        Long visitFirstId = visitFirst.getVisitId();

        int visitSecondYear = visitSecond.getVisitStart().getYear();
        int visitSecondMonth = visitSecond.getVisitStart().getMonthValue();
        int visitSecondDay = visitSecond.getVisitStart().getDayOfMonth();
        Long visitSecondId = visitSecond.getVisitId();

        Link visitFirstLink = (Link) visitFirst.getLink(visitFirstDay + "." + visitFirstMonth + "." + visitFirstYear + "-" + visitFirstId).get();
        Link visitSecondLink = (Link) visitSecond.getLink(visitSecondDay + "." + visitSecondMonth + "." + visitSecondYear + "-" + visitSecondId).get();

        assertEquals("/registration/Adrianna/work-place/tattoo/year/" + visitFirstYear + "/month/" + visitFirstMonth + "/day/" + visitFirstDay + "/visit/" + visitFirstId, visitFirstLink.getHref());
        assertEquals("/registration/Adrianna/work-place/tattoo/year/" + visitSecondYear + "/month/" + visitSecondMonth + "/day/" + visitSecondDay + "/visit/" + visitSecondId, visitSecondLink.getHref());

        assertEquals(visitFirstDay + "." + visitFirstMonth + "." + visitFirstYear + "-" + visitFirstId, visitFirstLink.getRel().value());
        assertEquals(visitSecondDay + "." + visitSecondMonth + "." + visitSecondYear + "-" + visitSecondId, visitSecondLink.getRel().value());
    }

    @Test
    void shouldAddLinksToRepresentativesVisit() {
        linkCreator.addLinksToRepresentativesVisit(visitFirst, user.getUsername(), tattoo.getInstitutionName());

        int visitFirstYear = visitFirst.getVisitStart().getYear();
        int visitFirstMonth = visitFirst.getVisitStart().getMonthValue();
        int visitFirstDay = visitFirst.getVisitStart().getDayOfMonth();
        Long visitFirstId = visitFirst.getVisitId();

        Link visitLink = (Link) visitFirst.getLink(visitFirstDay + "." + visitFirstMonth + "." + visitFirstYear + "-" + visitFirstId).get();

        assertEquals("/registration/Adrianna/work-place/tattoo/year/" + visitFirstYear + "/month/" + visitFirstMonth + "/day/" + visitFirstDay + "/visit/" + visitFirstId, visitLink.getHref());

        assertEquals(visitFirstDay + "." + visitFirstMonth + "." + visitFirstYear + "-" + visitFirstId, visitLink.getRel().value());
    }
}