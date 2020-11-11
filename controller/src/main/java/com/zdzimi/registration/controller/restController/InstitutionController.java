package com.zdzimi.registration.controller.restController;

import com.zdzimi.registration.data.entity.UserEntity;
import com.zdzimi.registration.service.InstitutionService;
import com.zdzimi.registration.core.model.Institution;
import com.zdzimi.registration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/registration/{username}/institution")
public class InstitutionController {

    private InstitutionService institutionService;
    private UserService userService;

    @Autowired
    public InstitutionController(InstitutionService institutionService, UserService userService) {
        this.institutionService = institutionService;
        this.userService = userService;
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
}
