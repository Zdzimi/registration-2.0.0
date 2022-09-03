package com.zdzimi.registration.controller.restController;

import com.zdzimi.registration.controller.link.LinkCreator;
import com.zdzimi.registration.data.entity.UserEntity;
import com.zdzimi.registration.service.InstitutionService;
import com.zdzimi.registration.core.model.Institution;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registration/{username}")
@Validated
@CrossOrigin
@RequiredArgsConstructor
public class InstitutionController {

    private final LoggedUserProvider loggedUserProvider;
    private final InstitutionService institutionService;
    private final LinkCreator linkCreator;

    @GetMapping("/institutions/all")
    public List<Institution> getInstitutions(@PathVariable String username) {
        loggedUserProvider.provideLoggedUser(username);
        List<Institution> institutions = institutionService.getAll();
        linkCreator.addLinksToInstitutions(institutions, username);
        return institutions;
    }

    @GetMapping("/institutions/recognized")
    public List<Institution> getRecognizedInstitutions(@PathVariable String username) {
        UserEntity userEntity = loggedUserProvider.provideLoggedUser(username);
        List<Institution> recognizedInstitutions = institutionService.getRecognized(userEntity);
        linkCreator.addLinksToInstitutions(recognizedInstitutions, username);
        return recognizedInstitutions;
    }

    @GetMapping("/institutions/search-by")
    public List<Institution> searchBy(@PathVariable String username,
                                      @RequestParam String institutionName,
                                      @RequestParam String province,
                                      @RequestParam String city,
                                      @RequestParam String typeOfServices) {
        UserEntity userEntity = loggedUserProvider.provideLoggedUser(username);

        // todo

        System.out.println(institutionName);
        System.out.println(province);
        System.out.println(city);
        System.out.println(typeOfServices);
        return null;
    }

    @GetMapping("/institution/{institutionName}")
    public Institution getInstitution(@PathVariable String username, @PathVariable String institutionName) {
        loggedUserProvider.provideLoggedUser(username);
        Institution institution = institutionService.getByInstitutionName(institutionName);
        linkCreator.addLinksToInstitution(institution, username);
        return institution;
    }
}
