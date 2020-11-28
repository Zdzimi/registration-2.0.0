package com.zdzimi.registration.controller.restController;

import com.zdzimi.registration.core.model.Institution;
import com.zdzimi.registration.core.validation.OnCreate;
import com.zdzimi.registration.data.entity.InstitutionEntity;
import com.zdzimi.registration.data.entity.UserEntity;
import com.zdzimi.registration.service.InstitutionService;
import com.zdzimi.registration.service.UserService;
import com.zdzimi.registration.service.mapper.InstitutionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/registration/{username}/work-places")
public class WorkPlacesController {

    private InstitutionService institutionService;
    private UserService userService;
    private InstitutionMapper institutionMapper;

    @Autowired
    public WorkPlacesController(InstitutionService institutionService, UserService userService, InstitutionMapper institutionMapper) {
        this.institutionService = institutionService;
        this.userService = userService;
        this.institutionMapper = institutionMapper;
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

    @PostMapping("/new-institution")
    @Validated(OnCreate.class)
    public Institution createNewInstitution(@Valid @RequestBody Institution institution,
                                            @PathVariable String username) {
        InstitutionEntity newInstitutionEntity = institutionService.createNewInstitution(institution);
        userService.addWorkPlace(username, newInstitutionEntity);
        return institutionMapper.convertToInstitution(newInstitutionEntity);
    }

    //  todo - inviteRepresentative() {...}
}
