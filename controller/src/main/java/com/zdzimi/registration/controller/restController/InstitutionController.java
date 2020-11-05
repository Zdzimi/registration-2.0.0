package com.zdzimi.registration.controller.restController;

import com.zdzimi.registration.service.InstitutionService;
import com.zdzimi.registration.core.model.Institution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/registration")
public class InstitutionController {

    private InstitutionService institutionService;

    @Autowired
    public InstitutionController(InstitutionService institutionService) {
        this.institutionService = institutionService;
    }

    @GetMapping("/institution")
    public List<Institution> getInstitutions() {
        return institutionService.getAll();
    }

    @GetMapping("/institution/{institutionName}")
    public Institution getInstitution(@PathVariable String institutionName) {
        return institutionService.getByInstitutionName(institutionName);
    }
}
