package com.zdzimi.registration.controller.restController;

import com.zdzimi.registration.controller.link.LinkCreator;
import com.zdzimi.registration.core.model.Place;
import com.zdzimi.registration.core.validation.OnCreate;
import com.zdzimi.registration.data.entity.InstitutionEntity;
import com.zdzimi.registration.service.InstitutionService;
import com.zdzimi.registration.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/registration/{username}/work-places/{institutionName}/place")
@Validated
public class PlaceController {

    private PlaceService placeService;
    private InstitutionService institutionService;
    private LinkCreator linkCreator;

    @Autowired
    public PlaceController(PlaceService placeService, InstitutionService institutionService, LinkCreator linkCreator) {
        this.placeService = placeService;
        this.institutionService = institutionService;
        this.linkCreator = linkCreator;
    }

    @GetMapping
    public List<Place> getPlaces(@PathVariable String username,@PathVariable String institutionName) {
        InstitutionEntity institutionEntity = institutionService.getInstitutionEntityByInstitutionName(institutionName);
        List<Place> places = placeService.getPlaces(institutionEntity);
        linkCreator.addLinksToPlaces(places, username, institutionName);
        return places;
    }

    @PostMapping("/new-place")
    @Validated(OnCreate.class)
    public Place addNewPlace(@Valid @RequestBody Place place, @PathVariable String username, @PathVariable String institutionName) {
        InstitutionEntity institutionEntity = institutionService.getInstitutionEntityByInstitutionName(institutionName);
        Place savedPlace = placeService.addNewPlace(institutionEntity, place);
        linkCreator.addLinksToPlace(savedPlace, username, institutionName);
        return savedPlace;
    }

    @GetMapping("/{placeName}")
    public Place getPlace(@PathVariable String username,@PathVariable String institutionName, @PathVariable String placeName) {
        InstitutionEntity institutionEntity = institutionService.getInstitutionEntityByInstitutionName(institutionName);
        Place place = placeService.getPlace(institutionEntity, placeName);
        linkCreator.addLinksToPlace(place, username, institutionName);
        return place;
    }

    //  todo - deletePlace() {...}
}
