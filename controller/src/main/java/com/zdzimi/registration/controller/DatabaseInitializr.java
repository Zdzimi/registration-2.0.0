package com.zdzimi.registration.controller;

import com.zdzimi.registration.data.entity.InstitutionEntity;
import com.zdzimi.registration.data.entity.PlaceEntity;
import com.zdzimi.registration.data.entity.UserEntity;
import com.zdzimi.registration.data.repository.InstitutionRepository;
import com.zdzimi.registration.data.repository.PlaceRepository;
import com.zdzimi.registration.data.repository.UserRepository;
import com.zdzimi.registration.data.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import java.util.Collections;

@Controller
public class DatabaseInitializr {

    private static final String ROLE = "ROLE_USER";

    private InstitutionRepository institutionRepository;
    private PlaceRepository placeRepository;
    private UserRepository userRepository;
    private VisitRepository visitRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public DatabaseInitializr(InstitutionRepository institutionRepository,
                              PlaceRepository placeRepository,
                              UserRepository userRepository,
                              VisitRepository visitRepository,
                              PasswordEncoder passwordEncoder) {
        this.institutionRepository = institutionRepository;
        this.placeRepository = placeRepository;
        this.userRepository = userRepository;
        this.visitRepository = visitRepository;
        this.passwordEncoder = passwordEncoder;
    }

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
        adam.setWorkPlaces(Collections.singletonList(tattoo));
        userRepository.save(adam);

        UserEntity ela = new UserEntity();
        ela.setUsername("elaKulak");
        ela.setName("Ela");
        ela.setSurname("Kulak");
        ela.setEmail("kulak@mail.com");
        ela.setPassword(passwordEncoder.encode("Pass123"));
        ela.setRole(ROLE);
        ela.setWorkPlaces(Collections.singletonList(barber));
        userRepository.save(ela);

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
}
