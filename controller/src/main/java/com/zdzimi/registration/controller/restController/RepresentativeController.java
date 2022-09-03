package com.zdzimi.registration.controller.restController;

import com.zdzimi.registration.controller.link.LinkCreator;
import com.zdzimi.registration.core.model.User;
import com.zdzimi.registration.data.entity.InstitutionEntity;
import com.zdzimi.registration.service.InstitutionService;
import com.zdzimi.registration.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registration/{username}/institution/{institutionName}/representatives")
@RequiredArgsConstructor
public class RepresentativeController {

    private final UserService userService;
    private final LoggedUserProvider loggedUserProvider;
    private final InstitutionService institutionService;
    private final LinkCreator linkCreator;

    @GetMapping
    public List<User> getRepresentatives (@PathVariable String username, @PathVariable String institutionName) {
        loggedUserProvider.provideLoggedUser(username);
        InstitutionEntity institutionEntity = institutionService.getInstitutionEntityByInstitutionName(institutionName);
        List<User> representatives = userService.getByWorkPlaces(institutionEntity);
        linkCreator.addLinksToRepresentatives(representatives, username, institutionName);
        return representatives;
    }

    @GetMapping("/{representativeName}")
    public User getRepresentative(@PathVariable String username,
                                  @PathVariable String institutionName,
                                  @PathVariable String representativeName) {
        loggedUserProvider.provideLoggedUser(username);
        InstitutionEntity institutionEntity = institutionService.getInstitutionEntityByInstitutionName(institutionName);
        User representative = userService.getByUsernameAndWorkPlaces(representativeName, institutionEntity);
        linkCreator.addLinksToRepresentative(representative, username, institutionName);
        return representative;
    }
}
