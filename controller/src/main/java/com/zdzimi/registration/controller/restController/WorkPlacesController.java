package com.zdzimi.registration.controller.restController;

import com.zdzimi.registration.controller.link.LinkCreator;
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
@RequestMapping("/registration/{username}")
@Validated
@CrossOrigin
public class WorkPlacesController {

    private InstitutionService institutionService;
    private UserService userService;
    private InstitutionMapper institutionMapper;
    private LinkCreator linkCreator;

    @Autowired
    public WorkPlacesController(InstitutionService institutionService,
                                UserService userService,
                                InstitutionMapper institutionMapper,
                                LinkCreator linkCreator) {
        this.institutionService = institutionService;
        this.userService = userService;
        this.institutionMapper = institutionMapper;
        this.linkCreator = linkCreator;
    }

    @GetMapping("/work-places")
    public List<Institution> getWorkPlaces(@PathVariable String username) {
        UserEntity userEntity = userService.getUserEntityByUsername(username);
        List<Institution> workPlaces = institutionService.getWorkPlaces(userEntity);
        linkCreator.addLinksToWorkPlaces(workPlaces, username);
        return workPlaces;
    }

    @GetMapping("/work-place/{institutionName}")
    public Institution getWorkPlace(@PathVariable String username, @PathVariable String institutionName) {
        UserEntity userEntity = userService.getUserEntityByUsername(username);
        Institution workPlace = institutionService.getWorkPlace(userEntity, institutionName);
        linkCreator.addLinksToWorkPlace(workPlace, username);
        return workPlace;
    }

    @PostMapping("/new-work-place")
    @Validated(OnCreate.class)
    public Institution createNewInstitution(@Valid @RequestBody Institution institution,
                                            @PathVariable String username) {
        InstitutionEntity newInstitutionEntity = institutionService.createNewInstitution(institution);
        userService.addWorkPlace(username, newInstitutionEntity);
        Institution newInstitution = institutionMapper.convertToInstitution(newInstitutionEntity);
        linkCreator.addLinksToWorkPlace(newInstitution, username);
        return newInstitution;
    }

    //  todo - inviteRepresentative() {...}
}
