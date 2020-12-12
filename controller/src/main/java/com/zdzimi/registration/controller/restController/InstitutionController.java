package com.zdzimi.registration.controller.restController;

import com.zdzimi.registration.controller.link.LinkCreator;
import com.zdzimi.registration.data.entity.UserEntity;
import com.zdzimi.registration.service.InstitutionService;
import com.zdzimi.registration.core.model.Institution;
import com.zdzimi.registration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registration/{username}/institution")
@Validated
public class InstitutionController {

    private InstitutionService institutionService;
    private UserService userService;
    private LinkCreator linkCreator;

    @Autowired
    public InstitutionController(InstitutionService institutionService, UserService userService, LinkCreator linkCreator) {
        this.institutionService = institutionService;
        this.userService = userService;
        this.linkCreator = linkCreator;
    }

    @GetMapping("/all")
    public List<Institution> getInstitutions(@PathVariable String username) {
        List<Institution> institutions = institutionService.getAll();
        linkCreator.addLinksToInstitutions(institutions, username);
        return institutions;
    }

    @GetMapping("/recognized")
    public List<Institution> getRecognizedInstitutions(@PathVariable String username) {
        UserEntity userEntity = userService.getUserEntityByUsername(username);
        List<Institution> recognizedInstitutions = institutionService.getRecognized(userEntity);
        linkCreator.addLinksToInstitutions(recognizedInstitutions, username);
        return recognizedInstitutions;
    }

    @GetMapping("/{institutionName}")
    public Institution getInstitution(@PathVariable String username, @PathVariable String institutionName) {
        Institution institution = institutionService.getByInstitutionName(institutionName);
        linkCreator.addLinksToInstitution(institution, username);
        return institution;
    }
}
