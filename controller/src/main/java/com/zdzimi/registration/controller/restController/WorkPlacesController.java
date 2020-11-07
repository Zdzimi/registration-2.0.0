package com.zdzimi.registration.controller.restController;

import com.zdzimi.registration.core.model.Institution;
import com.zdzimi.registration.service.InstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/registration/{username}/work-places")
public class WorkPlacesController {

    private InstitutionService institutionService;

    @Autowired
    public WorkPlacesController(InstitutionService institutionService) {
        this.institutionService = institutionService;
    }

    @GetMapping
    public List<Institution> getWorkPlaces(@PathVariable String username) {
        return institutionService.getWorkPlaces(username);
    }

    @GetMapping("/{institutionName}")
    public Institution getWorkPlace(@PathVariable String username, String institutionName) {
        return institutionService.getWorkPlace(username, institutionName);
    }
}
