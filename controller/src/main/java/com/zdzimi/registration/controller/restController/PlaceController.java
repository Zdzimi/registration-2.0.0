package com.zdzimi.registration.controller.restController;

import com.zdzimi.registration.core.model.Place;
import com.zdzimi.registration.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registration/{username}/work-places/{institutionName}/place")
public class PlaceController {

    private PlaceService placeService;

    @Autowired
    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping
    public List<Place> getPlaces(@PathVariable String username,@PathVariable String institutionName) {
        return placeService.getPlaces(institutionName);
    }

    @PostMapping("/new-place")
    public Place addNewPlace(@PathVariable String username, @PathVariable String institutionName, @RequestBody Place place) {
        return placeService.addNewPlace(institutionName, place);
    }

    @GetMapping("/{placeName}")
    public Place getPlace(@PathVariable String username,@PathVariable String institutionName, @PathVariable String placeName) {
        return placeService.getPlace(institutionName, placeName);
    }
}
