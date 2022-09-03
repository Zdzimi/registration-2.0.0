package com.zdzimi.registration.controller.restController;

import com.zdzimi.registration.controller.link.LinkCreator;
import com.zdzimi.registration.core.model.Place;
import com.zdzimi.registration.data.entity.InstitutionEntity;
import com.zdzimi.registration.data.entity.PlaceEntity;
import com.zdzimi.registration.service.InstitutionService;
import com.zdzimi.registration.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/registration/{username}/work-place/{institutionName}")
@Validated
@RequiredArgsConstructor
public class PlaceController {

    private final LoggedUserProvider loggedUserProvider;
    private final PlaceService placeService;
    private final InstitutionService institutionService;
    private final LinkCreator linkCreator;

    @GetMapping("/places")
    public List<Place> getPlaces(@PathVariable String username,@PathVariable String institutionName) {
        loggedUserProvider.provideLoggedUser(username);
        InstitutionEntity institutionEntity = institutionService.getInstitutionEntityByInstitutionName(institutionName);
        List<Place> places = placeService.getPlaces(institutionEntity);
        linkCreator.addLinksToPlaces(places, username, institutionName);
        return places;
    }

    @PostMapping("/new-place")
    public Place addNewPlace(@Valid @RequestBody Place place, @PathVariable String username, @PathVariable String institutionName) {
        loggedUserProvider.provideLoggedUser(username);
        InstitutionEntity institutionEntity = institutionService.getInstitutionEntityByInstitutionName(institutionName);
        return placeService.addNewPlace(institutionEntity, place);
    }

    @GetMapping("/place/{placeName}")
    public Place getPlace(@PathVariable String username, @PathVariable String institutionName, @PathVariable String placeName) {
        loggedUserProvider.provideLoggedUser(username);
        InstitutionEntity institutionEntity = institutionService.getInstitutionEntityByInstitutionName(institutionName);
        Place place = placeService.getPlace(institutionEntity, placeName);
        linkCreator.addLinksToPlace(place, username, institutionName);
        return place;
    }

    @DeleteMapping("/place/{placeName}")
    public void deletePlace(@PathVariable String username, @PathVariable String institutionName, @PathVariable String placeName) {
        loggedUserProvider.provideLoggedUser(username);
        InstitutionEntity institutionEntity = institutionService.getInstitutionEntityByInstitutionName(institutionName);
        PlaceEntity placeEntity = placeService.getPlaceEntity(institutionEntity, placeName);
        placeService.delete(institutionEntity, placeEntity);
    }
}
