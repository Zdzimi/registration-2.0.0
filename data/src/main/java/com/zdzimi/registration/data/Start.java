package com.zdzimi.registration.data;

import com.zdzimi.registration.data.entity.InstitutionEntity;
import com.zdzimi.registration.data.repository.InstitutionRepository;
import com.zdzimi.registration.data.repository.PlaceRepository;
import com.zdzimi.registration.data.repository.UserRepository;
import com.zdzimi.registration.data.repository.VisitRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;

@Controller
public class Start {

    private InstitutionRepository institutionRepository;
    private PlaceRepository placeRepository;
    private UserRepository userRepository;
    private VisitRepository visitRepository;

    public Start(InstitutionRepository institutionRepository,
                 PlaceRepository placeRepository,
                 UserRepository userRepository,
                 VisitRepository visitRepository) {
        this.institutionRepository = institutionRepository;
        this.placeRepository = placeRepository;
        this.userRepository = userRepository;
        this.visitRepository = visitRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void insertEntitiesToDatabase() {
        System.out.println("haha");
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
    }
}
