package com.zdzimi.registration.service;

import com.zdzimi.registration.core.model.Visit;
import com.zdzimi.registration.data.comparator.VisitComparator;
import com.zdzimi.registration.data.entity.InstitutionEntity;
import com.zdzimi.registration.data.entity.UserEntity;
import com.zdzimi.registration.data.entity.VisitEntity;
import com.zdzimi.registration.data.exception.VisitNotFoundException;
import com.zdzimi.registration.data.repository.VisitRepository;
import com.zdzimi.registration.data.validator.OnBook;
import com.zdzimi.registration.data.validator.OnCancel;
import com.zdzimi.registration.data.validator.OnDelete;
import com.zdzimi.registration.service.mapper.VisitMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Validated
@Service
public class VisitService {

    private final VisitRepository visitRepository;
    private final VisitMapper visitMapper;

    @Autowired
    public VisitService(VisitRepository visitRepository, VisitMapper visitMapper) {
        this.visitRepository = visitRepository;
        this.visitMapper = visitMapper;
    }

    public List<Visit> getAllByUser(UserEntity userEntity) {
        return visitRepository.findByUser(userEntity).stream()
                .map(visitMapper::convertToVisit)
                .collect(Collectors.toList());
    }

    public Visit getByUserAndVisitId(UserEntity userEntity, long visitId) {
        VisitEntity visitEntity = getVisitEntityByUserAndVisitId(userEntity, visitId);
        return visitMapper.convertToVisit(visitEntity);
    }

    public VisitEntity getVisitEntityByUserAndVisitId(UserEntity userEntity, long visitId) {
        return visitRepository.findByUserAndVisitId(userEntity, visitId)
                    .orElseThrow(() -> new VisitNotFoundException(visitId));
    }

    public List<Visit> getCurrentVisits(UserEntity representativeEntity, InstitutionEntity institutionEntity) {
        //  todo test
        return getCurrentByRepresentativeAndInstitution(representativeEntity, institutionEntity).stream()
                .map(visitMapper::convertToVisit)
                .collect(Collectors.toList());
    }

    public Visit getCurrentVisit(UserEntity representativeEntity, InstitutionEntity institutionEntity, long visitId) {
        //  todo test
        VisitEntity visitEntity = getCurrentByVisitIdAndRepresentativeAndInstitution(representativeEntity, institutionEntity, visitId);
        return visitMapper.convertToVisit(visitEntity);
    }

    @Validated(OnBook.class)
    public Visit bookVisit(@Valid VisitEntity visitEntity, UserEntity userEntity) {
        //  todo test
        visitEntity.setUser(userEntity);
        VisitEntity updatedVisitEntity = visitRepository.save(visitEntity);
        return visitMapper.convertToVisit(updatedVisitEntity);
    }

    public Visit getLastProvidedVisit(UserEntity representativeEntity, InstitutionEntity institutionEntity) {
        //  todo test
        Optional<VisitEntity> max = getCurrentByRepresentativeAndInstitution(representativeEntity, institutionEntity).stream()
                .max(new VisitComparator());
        return max.map(visitEntity -> visitMapper.convertToVisit(visitEntity)).orElse(null);
    }

    public VisitEntity getCurrentByVisitIdAndRepresentativeAndInstitution(UserEntity representativeEntity,
                                                                          InstitutionEntity institutionEntity,
                                                                          long visitId) {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        return visitRepository
                .findByVisitIdAndRepresentativeAndInstitutionAndVisitStartIsAfter(visitId, representativeEntity, institutionEntity, now)
                .orElseThrow(() -> new VisitNotFoundException(visitId));
    }

    public List<Visit> getByRepresentativeAndInstitutionAndYear(UserEntity representativeEntity,
                                                                InstitutionEntity institutionEntity,
                                                                int year) {
        Timestamp dateMin = Timestamp.valueOf(LocalDateTime.of(year, 1, 1, 0, 0));
        Timestamp dateMax = Timestamp.valueOf(LocalDateTime.of(year, 12, 31, 23, 59, 59));
        return getByRepresentativeAndInstitutionAndVisitStartBetween(representativeEntity, institutionEntity, dateMin, dateMax);
    }

    public List<Visit> getByRepresentativeAndInstitutionAndYearAndMonth(UserEntity representativeEntity,
                                                                        InstitutionEntity institutionEntity,
                                                                        int year,
                                                                        int month) {
        LocalDateTime firstDay = LocalDateTime.of(year, month, 1, 0, 0);
        Timestamp dateMin = Timestamp.valueOf(firstDay);
        int length = firstDay.getMonth().length(firstDay.toLocalDate().isLeapYear());
        Timestamp dateMax = Timestamp.valueOf(LocalDateTime.of(year, month, length, 23, 59, 59));
        return getByRepresentativeAndInstitutionAndVisitStartBetween(representativeEntity, institutionEntity, dateMin, dateMax);
    }

    public List<Visit> getByRepresentativeAndInstitutionAndYearAndMonthAndDay(UserEntity representativeEntity,
                                                                              InstitutionEntity institutionEntity,
                                                                              int year,
                                                                              int month,
                                                                              int day) {
        Timestamp dateMin = Timestamp.valueOf(LocalDateTime.of(year, month, day, 0, 0));
        Timestamp dateMax = Timestamp.valueOf(LocalDateTime.of(year, month, day, 23, 59, 59));
        return getByRepresentativeAndInstitutionAndVisitStartBetween(representativeEntity, institutionEntity, dateMin, dateMax);
    }

    public Visit getByRepresentativeAndInstitutionAndVisitId(UserEntity representativeEntity,
                                                             InstitutionEntity institutionEntity,
                                                             long visitId) {
        VisitEntity visitEntity = getVisitEntityByRepresentativeAndInstitutionAndVisitId(
                representativeEntity, institutionEntity, visitId);
        return visitMapper.convertToVisit(visitEntity);
    }

    public void delete(UserEntity representativeEntity, InstitutionEntity institutionEntity, long visitId) {
        VisitEntity visitEntity = getVisitEntityByRepresentativeAndInstitutionAndVisitId(
                representativeEntity, institutionEntity, visitId);
        delete(visitEntity);
    }

    public List<VisitEntity> getByRepresentativeAndVisitEndIsAfterAndVisitStartIsBefore(UserEntity representativeEntity,
                                                                                        Timestamp firstVisitStart,
                                                                                        Timestamp lastVisitEnd) {
        return visitRepository
                .findByRepresentativeAndVisitEndIsAfterAndVisitStartIsBefore(representativeEntity, firstVisitStart, lastVisitEnd);
    }

    public List<VisitEntity> getByInstitutionAndVisitEndIsAfterAndVisitStartIsBefore(InstitutionEntity institutionEntity,
                                                                                     Timestamp firstVisitStart,
                                                                                     Timestamp lastVisitEnd) {
        return visitRepository
                .findByInstitutionAndVisitEndIsAfterAndVisitStartIsBefore(institutionEntity, firstVisitStart, lastVisitEnd);
    }

    public List<VisitEntity> getByInstitutionAndPlaceNameAndVisitEndIsAfter(InstitutionEntity institutionEntity,
                                                                            String placeName,
                                                                            Timestamp now) {
        return visitRepository.findByInstitutionAndPlaceNameAndVisitEndIsAfter(institutionEntity, placeName, now);
    }

    public List<Visit> saveAll(List<VisitEntity> visitEntities) {
        List<VisitEntity> savedEntities = visitRepository.saveAll(visitEntities);
        return savedEntities.stream().map(visitMapper::convertToVisit).collect(Collectors.toList());
    }

    @Validated(OnCancel.class)
    public void cancelVisit(@Valid VisitEntity visitEntity) {
        visitEntity.setUser(null);
        visitRepository.save(visitEntity);
    }

    private List<VisitEntity> getCurrentByRepresentativeAndInstitution(UserEntity representativeEntity,
                                                                       InstitutionEntity institutionEntity) {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        return visitRepository.findByRepresentativeAndInstitutionAndVisitStartIsAfter(representativeEntity, institutionEntity, now);
    }

    private List<Visit> getByRepresentativeAndInstitutionAndVisitStartBetween(UserEntity representativeEntity,
                                                                              InstitutionEntity institutionEntity,
                                                                              Timestamp dateMin,
                                                                              Timestamp dateMax) {
        return visitRepository.findByRepresentativeAndInstitutionAndVisitStartBetween(
                representativeEntity, institutionEntity, dateMin, dateMax).stream()
                .map(visitMapper::convertToVisit)
                .collect(Collectors.toList());
    }

    private VisitEntity getVisitEntityByRepresentativeAndInstitutionAndVisitId(UserEntity representativeEntity,
                                                                               InstitutionEntity institutionEntity,
                                                                               long visitId) {
        return visitRepository.findByVisitIdAndRepresentativeAndInstitution(visitId, representativeEntity, institutionEntity)
                .orElseThrow(() -> new VisitNotFoundException(visitId));
    }

    @Validated(OnDelete.class)
    private void delete(@Valid VisitEntity visitEntity) {
        visitRepository.delete(visitEntity);
    }
}
