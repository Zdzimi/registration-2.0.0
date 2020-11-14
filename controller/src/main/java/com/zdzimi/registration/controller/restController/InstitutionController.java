package com.zdzimi.registration.controller.restController;

import com.zdzimi.registration.core.validation.OnCreate;
import com.zdzimi.registration.data.entity.InstitutionEntity;
import com.zdzimi.registration.data.entity.UserEntity;
import com.zdzimi.registration.service.InstitutionService;
import com.zdzimi.registration.core.model.Institution;
import com.zdzimi.registration.service.UserService;
import com.zdzimi.registration.service.mapper.InstitutionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/registration/{username}/institution")
@Validated
public class InstitutionController {

    private InstitutionService institutionService;
    private UserService userService;
    private InstitutionMapper institutionMapper;

    @Autowired
    public InstitutionController(InstitutionService institutionService, UserService userService, InstitutionMapper institutionMapper) {
        this.institutionService = institutionService;
        this.userService = userService;
        this.institutionMapper = institutionMapper;
    }

    @GetMapping("/all")
    public List<Institution> getInstitutions() {
        return institutionService.getAll();
    }

    @GetMapping("/recognized")
    public List<Institution> getRecognizedInstitutions(@PathVariable String username) {
        UserEntity userEntity = userService.getUserEntityByUsername(username);
        return institutionService.getRecognized(userEntity);
    }

    @GetMapping("/{institutionName}")
    public Institution getInstitution(@PathVariable String institutionName) {
        return institutionService.getByInstitutionName(institutionName);
    }

    @PostMapping("/new-institution")
    @Validated(OnCreate.class)
    public Institution createNewInstitution(@Valid @RequestBody Institution institution,
                                            @PathVariable String username) {
        InstitutionEntity newInstitutionEntity = institutionService.createNewInstitution(institution);
        userService.addWorkPlace(username, newInstitutionEntity);
        return institutionMapper.convertToInstitution(newInstitutionEntity);
    }
}
