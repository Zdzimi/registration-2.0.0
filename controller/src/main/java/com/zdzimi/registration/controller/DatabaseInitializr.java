package com.zdzimi.registration.controller;

import com.zdzimi.registration.core.model.Place;
import com.zdzimi.registration.core.model.Visit;
import com.zdzimi.registration.core.model.template.Day;
import com.zdzimi.registration.core.model.template.TimetableTemplate;
import com.zdzimi.registration.data.entity.InstitutionEntity;
import com.zdzimi.registration.data.entity.PlaceEntity;
import com.zdzimi.registration.data.entity.UserEntity;
import com.zdzimi.registration.data.entity.VisitEntity;
import com.zdzimi.registration.data.repository.InstitutionRepository;
import com.zdzimi.registration.data.repository.PlaceRepository;
import com.zdzimi.registration.data.repository.UserRepository;
import com.zdzimi.registration.service.ConflictAnalyzer;
import com.zdzimi.registration.service.PlaceService;
import com.zdzimi.registration.service.TimetableTemplateService;
import com.zdzimi.registration.service.VisitEntityGenerator;
import com.zdzimi.registration.service.VisitService;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import java.util.Collections;

@Controller
@RequiredArgsConstructor
public class DatabaseInitializr {

    private static final String ROLE = "ROLE_USER";

    private final InstitutionRepository institutionRepository;
    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PlaceService placeService;
    private final VisitService visitService;
    private final TimetableTemplateService timetableTemplateService;
    private final VisitEntityGenerator visitEntityGenerator;
    private final ConflictAnalyzer conflictAnalyzer;

    @EventListener(ApplicationReadyEvent.class)
    public void insertEntitiesToDatabase() {
        InstitutionEntity barber = new InstitutionEntity();
        barber.setInstitutionName("Barber");
        barber.setProvince("dolnośląskie");
        barber.setCity("Wrocław");
        barber.setStreet("Karmelkowa");
        barber.setGateNumber("123");
        barber.setPremisesNumber("2");
        barber.setTypeOfService("Barber");
        barber.setDescription("some description...");
        institutionRepository.save(barber);

        PlaceEntity barberPlaceOne = new PlaceEntity();
        barberPlaceOne.setPlaceName("krzesło nr 1");
        barberPlaceOne.setInstitution(barber);
        placeRepository.save(barberPlaceOne);

        PlaceEntity barberPlaceTwo = new PlaceEntity();
        barberPlaceTwo.setPlaceName("krzesło nr 2");
        barberPlaceTwo.setInstitution(barber);
        placeRepository.save(barberPlaceTwo);

        InstitutionEntity tattoo = new InstitutionEntity();
        tattoo.setInstitutionName("Tattoo");
        tattoo.setProvince("dolnośląskie");
        tattoo.setCity("Wrocław");
        tattoo.setStreet("Piłsudskiego");
        tattoo.setGateNumber("35");
        tattoo.setPremisesNumber("1.a");
        tattoo.setTypeOfService("Tattoo");
        tattoo.setDescription("some description...");
        institutionRepository.save(tattoo);

        PlaceEntity tattooPlaceOne = new PlaceEntity();
        tattooPlaceOne.setPlaceName("stanowisko 1");
        tattooPlaceOne.setInstitution(tattoo);
        placeRepository.save(tattooPlaceOne);

        PlaceEntity tattooPlaceTwo = new PlaceEntity();
        tattooPlaceTwo.setPlaceName("stanowisko 2");
        tattooPlaceTwo.setInstitution(tattoo);
        placeRepository.save(tattooPlaceTwo);

        UserEntity adam = new UserEntity();
        adam.setUsername("adamKaban");
        adam.setName("Adam");
        adam.setSurname("Kaban");
        adam.setEmail("kaban@mail.com");
        adam.setPassword(passwordEncoder.encode("Pass123"));
        adam.setRole(ROLE);
        adam.setRecognizedInstitutions(Collections.singletonList(barber));
        adam.setWorkPlaces(Arrays.asList(tattoo, barber));
        userRepository.save(adam);

        for (int i = 0; i < 3; i++) {
            TimetableTemplate timetableTemplate = prepareNextTemplate(adam, tattoo);
            Collection<Day> days = timetableTemplate.getDays();
            timetableTemplate.setVisitLength(60);
            for (Day day : days) {
                day.setPlaceName(tattooPlaceOne.getPlaceName());
                day.setWorkStart(LocalTime.of(8,0));
                day.setWorkEnd(LocalTime.of(14,0));
            }
            createTimetable(timetableTemplate, adam, tattoo);
        }

        for (int i = 0; i < 3; i++) {
            TimetableTemplate timetableTemplate = prepareNextTemplate(adam, barber);
            Collection<Day> days = timetableTemplate.getDays();
            timetableTemplate.setVisitLength(60);
            for (Day day : days) {
                day.setPlaceName(barberPlaceOne.getPlaceName());
                day.setWorkStart(LocalTime.of(16,0));
                day.setWorkEnd(LocalTime.of(20,0));
            }
            createTimetable(timetableTemplate, adam, barber);
        }

        UserEntity ela = new UserEntity();
        ela.setUsername("elaKulak");
        ela.setName("Ela");
        ela.setSurname("Kulak");
        ela.setEmail("kulak@mail.com");
        ela.setPassword(passwordEncoder.encode("Pass123"));
        ela.setRole(ROLE);
        ela.setWorkPlaces(Collections.singletonList(barber));
        userRepository.save(ela);

        for (int i = 0; i < 3; i++) {
            TimetableTemplate timetableTemplate = prepareNextTemplate(ela, barber);
            Collection<Day> days = timetableTemplate.getDays();
            timetableTemplate.setVisitLength(60);
            for (Day day : days) {
                day.setPlaceName(barberPlaceOne.getPlaceName());
                day.setWorkStart(LocalTime.of(8,0));
                day.setWorkEnd(LocalTime.of(16,0));
            }
            createTimetable(timetableTemplate, ela, barber);
        }

        UserEntity janusz = new UserEntity();
        janusz.setUsername("januszNowaczek");
        janusz.setName("Janusz");
        janusz.setSurname("Nowaczek");
        janusz.setEmail("nowaczekjanusz@mail.com");
        janusz.setPassword(passwordEncoder.encode("Pass123"));
        janusz.setRole(ROLE);
        userRepository.save(janusz);

        UserEntity ola = new UserEntity();
        ola.setUsername("olaRak");
        ola.setName("Ola");
        ola.setSurname("Rak");
        ola.setEmail("rakola@mail.com");
        ola.setPassword(passwordEncoder.encode("Pass123"));
        ola.setRole(ROLE);
        ola.setRecognizedInstitutions(Collections.singletonList(tattoo));
        userRepository.save(ola);
    }

    private TimetableTemplate prepareNextTemplate(UserEntity userEntity, InstitutionEntity institutionEntity) {
        List<Place> places = placeService.getPlaces(institutionEntity);
        Visit lastProvidedVisit = visitService.getLastProvidedVisit(userEntity, institutionEntity);
        return timetableTemplateService.prepareTemplate(lastProvidedVisit, places);
    }

    private void createTimetable(@Valid TimetableTemplate timetableTemplate, UserEntity representativeEntity, InstitutionEntity institutionEntity) {
        List<VisitEntity> visitEntities = visitEntityGenerator.createVisits(timetableTemplate, representativeEntity, institutionEntity);
        conflictAnalyzer.checkConflicts(visitEntities, representativeEntity, institutionEntity);
        visitService.saveAll(visitEntities);
    }
}
