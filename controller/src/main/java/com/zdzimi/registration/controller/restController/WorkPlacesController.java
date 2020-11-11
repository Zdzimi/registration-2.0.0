package com.zdzimi.registration.controller.restController;

import com.zdzimi.registration.core.model.Institution;
import com.zdzimi.registration.data.entity.UserEntity;
import com.zdzimi.registration.service.InstitutionService;
import com.zdzimi.registration.service.UserService;
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
    private UserService userService;

    @Autowired
    public WorkPlacesController(InstitutionService institutionService, UserService userService) {
        this.institutionService = institutionService;
        this.userService = userService;
    }

    @GetMapping
    public List<Institution> getWorkPlaces(@PathVariable String username) {
        UserEntity userEntity = userService.getUserEntityByUsername(username);
        return institutionService.getWorkPlaces(userEntity);
    }

    @GetMapping("/{institutionName}")
    public Institution getWorkPlace(@PathVariable String username, String institutionName) {
        UserEntity userEntity = userService.getUserEntityByUsername(username);
        return institutionService.getWorkPlace(userEntity, institutionName);
    }
}
