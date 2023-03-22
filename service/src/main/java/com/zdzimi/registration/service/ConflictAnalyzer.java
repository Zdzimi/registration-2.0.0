package com.zdzimi.registration.service;

import com.zdzimi.registration.data.entity.InstitutionEntity;
import com.zdzimi.registration.data.entity.UserEntity;
import com.zdzimi.registration.data.entity.VisitEntity;
import com.zdzimi.registration.service.exception.ConflictException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ConflictAnalyzer {

    private final VisitService visitService;

    public boolean checkConflicts(List<VisitEntity> visitEntities,
                                  UserEntity representativeEntity,
                                  InstitutionEntity institutionEntity) {

        List<String> conflicts = new ArrayList<>();

        if (visitEntities.isEmpty()) {
            conflicts.add("Visits not created.");
            throw new ConflictException(conflicts);
        }

        Timestamp firstVisitStart = visitEntities.get(0).getVisitStart();
        Timestamp lastVisitEnd = visitEntities.get(visitEntities.size() - 1).getVisitEnd();

        List<VisitEntity> visitsByRepresentative = visitService
                .getByRepresentativeAndVisitEndIsAfterAndVisitStartIsBefore(representativeEntity, firstVisitStart, lastVisitEnd);

        List<VisitEntity> visitsByInstitution = visitService
                .getByInstitutionAndVisitEndIsAfterAndVisitStartIsBefore(institutionEntity, firstVisitStart, lastVisitEnd);

        for (VisitEntity targetVisitEntity : visitEntities) {
            for (VisitEntity visitByRepresentative : visitsByRepresentative) {
                if (!thereIsNoConflict(targetVisitEntity, visitByRepresentative)) {
                    LocalDateTime visitStart = visitByRepresentative.getVisitStart().toLocalDateTime();
                    LocalDateTime visitEnd = visitByRepresentative.getVisitEnd().toLocalDateTime();
                    String institutionName = visitByRepresentative.getInstitution().getInstitutionName();
                    String placeName = visitByRepresentative.getPlaceName();
                    conflicts.add("Conflict you have visit: " + visitStart + " - " + visitEnd + " in " + institutionName + ", " + placeName);
                }
            }
            for (VisitEntity visitByInstitution : visitsByInstitution) {
                if (!thereIsNoPlaceConflict(targetVisitEntity, visitByInstitution)) {
                    LocalDateTime visitStart = visitByInstitution.getVisitStart().toLocalDateTime();
                    LocalDateTime visitEnd = visitByInstitution.getVisitEnd().toLocalDateTime();
                    String institutionName = visitByInstitution.getInstitution().getInstitutionName();
                    String placeName = visitByInstitution.getPlaceName();
                    conflicts.add("Conflict place booked: " + visitStart + " - " + visitEnd + " in " + institutionName + ", " + placeName);
                }
            }
        }
        if (conflicts.isEmpty()) {
            return true;
        } else {
            throw new ConflictException(conflicts);
        }
    }

    private boolean thereIsNoPlaceConflict(VisitEntity targetVisitEntity, VisitEntity visitByInstitution) {
        if (targetVisitEntity.getPlaceName().equals(visitByInstitution.getPlaceName())) {
            return thereIsNoConflict(targetVisitEntity, visitByInstitution);
        }
        return true;
    }

    private boolean thereIsNoConflict(VisitEntity targetVisitEntity, VisitEntity visitByRepresentative) {
        return firstVisitEndsBeforeSecondStarts(targetVisitEntity, visitByRepresentative) || firstVisitEndsBeforeSecondStarts(visitByRepresentative, targetVisitEntity);
    }

    private boolean firstVisitEndsBeforeSecondStarts(VisitEntity firstVisit, VisitEntity secondVisit) {
        Timestamp firstVisitEnd = firstVisit.getVisitEnd();
        Timestamp secondVisitStart = secondVisit.getVisitStart();
        return firstVisitEnd.before(secondVisitStart) || firstVisitEnd.equals(secondVisitStart);
    }
}
