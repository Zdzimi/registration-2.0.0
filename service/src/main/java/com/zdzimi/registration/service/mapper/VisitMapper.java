package com.zdzimi.registration.service.mapper;

import com.zdzimi.registration.core.model.Institution;
import com.zdzimi.registration.core.model.User;
import com.zdzimi.registration.core.model.Visit;
import com.zdzimi.registration.data.entity.VisitEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@RequiredArgsConstructor
@Component
public class VisitMapper {

    private final ModelMapper modelMapper;
    private final UserMapper userMapper;
    private final InstitutionMapper institutionMapper;

    public Visit convertToVisit(VisitEntity visitEntity) {
        Visit visit = modelMapper.map(visitEntity, Visit.class);

        Timestamp visitStart = visitEntity.getVisitStart();
        visit.setVisitStart(visitStart.toLocalDateTime());

        Timestamp visitEnd = visitEntity.getVisitEnd();
        visit.setVisitEnd(visitEnd.toLocalDateTime());

        User user = userMapper.convertToUser(visitEntity.getUser());
        visit.setUser(user);

        User representative = userMapper.convertToUser(visitEntity.getRepresentative());
        visit.setRepresentative(representative);

        Institution institution = institutionMapper.convertToInstitution(visitEntity.getInstitution());
        visit.setInstitution(institution);

        return visit;
    }
}
