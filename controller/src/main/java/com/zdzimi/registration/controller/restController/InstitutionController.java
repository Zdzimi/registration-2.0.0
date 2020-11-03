package com.zdzimi.registration.controller.restController;

import com.zdzimi.registration.service.mapper.InstitutionMapper;
import com.zdzimi.registration.core.model.Institution;
import com.zdzimi.registration.data.entity.InstitutionEntity;
import com.zdzimi.registration.data.repository.InstitutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registration")
public class InstitutionController {

    private InstitutionRepository institutionRepository;
    private InstitutionMapper institutionMapper;

    @Autowired
    public InstitutionController(InstitutionRepository institutionRepository, InstitutionMapper institutionMapper) {
        this.institutionRepository = institutionRepository;
        this.institutionMapper = institutionMapper;
    }

    @GetMapping("/institution/{institutionName}")
    public Institution getInstitution(@PathVariable String institutionName) {
        InstitutionEntity institutionEntity = institutionRepository.findByInstitutionName(institutionName)
                .orElseThrow(RuntimeException::new);
        return institutionMapper.convertToInstitution(institutionEntity);
    }
}
