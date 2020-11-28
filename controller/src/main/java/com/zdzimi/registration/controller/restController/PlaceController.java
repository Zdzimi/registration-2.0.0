package com.zdzimi.registration.controller.restController;

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

    @Autowired
    public PlaceController(PlaceService placeService, InstitutionService institutionService) {
        this.placeService = placeService;
        this.institutionService = institutionService;
    }

    @GetMapping
    public List<Place> getPlaces(@PathVariable String username,@PathVariable String institutionName) {
        InstitutionEntity institutionEntity = institutionService.getInstitutionEntityByInstitutionName(institutionName);
        return placeService.getPlaces(institutionEntity);
    }

    @PostMapping("/new-place")
    @Validated(OnCreate.class)
    public Place addNewPlace(@Valid @RequestBody Place place, @PathVariable String username, @PathVariable String institutionName) {
        InstitutionEntity institutionEntity = institutionService.getInstitutionEntityByInstitutionName(institutionName);
        return placeService.addNewPlace(institutionEntity, place);
    }

    @GetMapping("/{placeName}")
    public Place getPlace(@PathVariable String username,@PathVariable String institutionName, @PathVariable String placeName) {
        InstitutionEntity institutionEntity = institutionService.getInstitutionEntityByInstitutionName(institutionName);
        return placeService.getPlace(institutionEntity, placeName);
    }

    //  todo - deletePlace() {...}
}
