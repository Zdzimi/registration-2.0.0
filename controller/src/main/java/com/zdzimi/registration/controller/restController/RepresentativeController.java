package com.zdzimi.registration.controller.restController;

import com.zdzimi.registration.controller.link.LinkCreator;
import com.zdzimi.registration.core.model.User;
import com.zdzimi.registration.data.entity.InstitutionEntity;
import com.zdzimi.registration.service.InstitutionService;
import com.zdzimi.registration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registration/{username}/institution/{institutionName}/representatives")
public class RepresentativeController {

    private UserService userService;
    private InstitutionService institutionService;
    private LinkCreator linkCreator;

    @Autowired
    public RepresentativeController(UserService userService, InstitutionService institutionService, LinkCreator linkCreator) {
        this.userService = userService;
        this.institutionService = institutionService;
        this.linkCreator = linkCreator;
    }

    @GetMapping
    public List<User> getRepresentatives (@PathVariable String username, @PathVariable String institutionName) {
        InstitutionEntity institutionEntity = institutionService.getInstitutionEntityByInstitutionName(institutionName);
        List<User> representatives = userService.getByWorkPlaces(institutionEntity);
        linkCreator.addLinksToRepresentatives(representatives, username, institutionName);
        return representatives;
    }

    @GetMapping("/{representativeName}")
    public User getRepresentative(@PathVariable String username,
                                  @PathVariable String institutionName,
                                  @PathVariable String representativeName) {
        InstitutionEntity institutionEntity = institutionService.getInstitutionEntityByInstitutionName(institutionName);
        User representative = userService.getByUsernameAndWorkPlaces(representativeName, institutionEntity);
        linkCreator.addLinksToRepresentative(representative, username, institutionName);
        return representative;
    }
}
