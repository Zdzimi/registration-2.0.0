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
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
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

    private static final String PATH = "static/data.csv";

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
    public void bootstrapDatabase() {
        Optional<String> allFile = readFile();
        if (allFile.isPresent()) {
            String[] lines = allFile.get().split("\n");
            for (String line : lines) {
                String[] data = line.split(",");
                fillData(data);
            }
        }
    }

    private Optional<String> readFile() {
        InputStream resourceAsStream = getClass().getClassLoader()
            .getResourceAsStream(PATH);
        try {
            if (resourceAsStream != null) {
                return Optional.of(new String(resourceAsStream.readAllBytes(), StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private void fillData(String[] data) {
        InstitutionEntity institutionEntity = createInstitutionEntity(Arrays.copyOfRange(data, 0, 8));
        institutionRepository.save(institutionEntity);

        PlaceEntity placeEntityFirst = createPlaceEntity(institutionEntity, data[8]);
        placeRepository.save(placeEntityFirst);

        PlaceEntity placeEntitySecond = createPlaceEntity(institutionEntity, data[9]);
        placeRepository.save(placeEntitySecond);

        UserEntity userEntityFirst = createUserEntity(Arrays.copyOfRange(data, 10, 15), institutionEntity);
        userRepository.save(userEntityFirst);

        createTimetable(institutionEntity, placeEntityFirst, userEntityFirst, 8, 14);

        UserEntity userEntitySecond = createUserEntity(Arrays.copyOfRange(data, 15, 20), institutionEntity);
        userRepository.save(userEntitySecond);

        createTimetable(institutionEntity, placeEntitySecond, userEntitySecond, 9, 16);
    }

    private UserEntity createUserEntity(String[] data, InstitutionEntity institutionEntity) {
        UserEntity userEntityFirst = new UserEntity();
        userEntityFirst.setUsername(data[0]);
        userEntityFirst.setName(data[1]);
        userEntityFirst.setSurname(data[2]);
        userEntityFirst.setEmail(data[3]);
        userEntityFirst.setPassword(passwordEncoder.encode(data[4]));
        userEntityFirst.setRole("ROLE_USER");
        userEntityFirst.setWorkPlaces(Collections.singletonList(institutionEntity));
        return userEntityFirst;
    }

    private PlaceEntity createPlaceEntity(InstitutionEntity institutionEntity, String placeName) {
        PlaceEntity placeEntityFirst = new PlaceEntity();
        placeEntityFirst.setPlaceName(placeName);
        placeEntityFirst.setInstitution(institutionEntity);
        return placeEntityFirst;
    }

    private InstitutionEntity createInstitutionEntity(String[] data) {
        InstitutionEntity institutionEntity = new InstitutionEntity();
        institutionEntity.setInstitutionName(data[0]);
        institutionEntity.setProvince(data[1]);
        institutionEntity.setCity(data[2]);
        institutionEntity.setStreet(data[3]);
        institutionEntity.setGateNumber(data[4]);
        institutionEntity.setPremisesNumber(data[5]);
        institutionEntity.setTypeOfService(data[6]);
        institutionEntity.setDescription(data[7]);
        return institutionEntity;
    }

    private void createTimetable(InstitutionEntity institutionEntity, PlaceEntity tattooPlaceOne,
        UserEntity userEntity, int timeFrom, int timeTo) {
        for (int i = 0; i < 3; i++) {
            TimetableTemplate timetableTemplate = prepareNextTemplate(userEntity,
                institutionEntity);
            Collection<Day> days = timetableTemplate.getDays();
            timetableTemplate.setVisitLength(60);
            for (Day day : days) {
                day.setPlaceName(tattooPlaceOne.getPlaceName());
                day.setWorkStart(LocalTime.of(timeFrom, 0));
                day.setWorkEnd(LocalTime.of(timeTo, 0));
            }
            createTimetable(timetableTemplate, userEntity, institutionEntity);
        }
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
