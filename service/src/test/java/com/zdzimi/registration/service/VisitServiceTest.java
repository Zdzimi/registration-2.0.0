package com.zdzimi.registration.service;

import com.zdzimi.registration.core.model.Visit;
import com.zdzimi.registration.data.entity.InstitutionEntity;
import com.zdzimi.registration.data.entity.UserEntity;
import com.zdzimi.registration.data.entity.VisitEntity;
import com.zdzimi.registration.data.exception.VisitNotFoundException;
import com.zdzimi.registration.data.repository.VisitRepository;
import com.zdzimi.registration.service.mapper.VisitMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.modelmapper.ModelMapper;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class VisitServiceTest {

    private static final LocalDateTime LOCAL_DATE_TIME_START = LocalDateTime.of(1999,4,30,12,30);
    private static final LocalDateTime LOCAL_DATE_TIME_END = LocalDateTime.of(1999,4,30,13,30);
    private static final Timestamp TIMESTAMP_START = Timestamp.valueOf(LOCAL_DATE_TIME_START);
    private static final Timestamp TIMESTAMP_END = Timestamp.valueOf(LOCAL_DATE_TIME_END);
    private static final long VISIT_ID = 131423;

    private static final int YEAR = 2050;
    private static final int MONTH = 3;
    private static final int DAY_OF_MONTH = 4;

    private VisitService visitService;
    private VisitRepository visitRepository;
    private VisitMapper visitMapper = new VisitMapper(new ModelMapper());

    @BeforeEach
    void setUp() {
        visitRepository = mock(VisitRepository.class);
        initMocks(this);
        visitService = new VisitService(visitRepository, visitMapper);
    }

    @Test
    void shouldGetAllByUser() {
        //      given
        UserEntity userEntity = new UserEntity();
        VisitEntity visitEntity = new VisitEntity();
        visitEntity.setVisitStart(TIMESTAMP_START);
        visitEntity.setVisitEnd(TIMESTAMP_END);
        when(visitRepository.findByUser(userEntity)).thenReturn(Arrays.asList(visitEntity));
        //      when
        List<Visit> result = visitService.getAllByUser(userEntity);
        //      then
        assertEquals(1, result.size());
        assertEquals(LOCAL_DATE_TIME_START, result.get(0).getVisitStart());
        assertEquals(LOCAL_DATE_TIME_END, result.get(0).getVisitEnd());
        verify(visitRepository, times(1)).findByUser(userEntity);
        verifyNoMoreInteractions(visitRepository);
    }

    @Test
    void shouldGetByUserAndVisitId() {
        //      given
        UserEntity userEntity = new UserEntity();
        VisitEntity visitEntity = new VisitEntity();
        visitEntity.setVisitId(VISIT_ID);
        visitEntity.setVisitStart(TIMESTAMP_START);
        visitEntity.setVisitEnd(TIMESTAMP_END);
        when(visitRepository.findByUserAndVisitId(userEntity, VISIT_ID)).thenReturn(Optional.of(visitEntity));
        //      when
        Visit result = visitService.getByUserAndVisitId(userEntity, VISIT_ID);
        //      then
        assertEquals(VISIT_ID, result.getVisitId());
        assertEquals(LOCAL_DATE_TIME_START, result.getVisitStart());
        assertEquals(LOCAL_DATE_TIME_END, result.getVisitEnd());
        verify(visitRepository, times(1)).findByUserAndVisitId(userEntity, VISIT_ID);
        verifyNoMoreInteractions(visitRepository);
    }

    @Test
    void shouldThrowVisitNotFoundException() {
        //      given
        UserEntity userEntity = new UserEntity();
        when(visitRepository.findByUserAndVisitId(userEntity, VISIT_ID)).thenReturn(Optional.empty());
        //      when
        VisitNotFoundException exception = assertThrows(
                VisitNotFoundException.class, () -> visitService.getByUserAndVisitId(userEntity, VISIT_ID)
        );
        //      then
        assertEquals("Could not find: " + VISIT_ID, exception.getMessage());
        verify(visitRepository, times(1)).findByUserAndVisitId(userEntity, VISIT_ID);
        verifyNoMoreInteractions(visitRepository);
    }

    @Test
    void shouldGetCurrentVisits() {
        //      given
        //      when
        //      then
    }

    @Test
    void shouldGetCurrentVisit() {
        //      given
        //      when
        //      then
    }

    @Test
    void shouldBookVisit() {
        //      given
        //      when
        //      then
    }

    @Test
    void shouldGetLastProvidedVisit() {
        //      given
        //      when
        //      then
    }

    @Test
    void shouldGetCurrentByVisitIdAndRepresentativeAndInstitution() {
        //      given
        //      when
        //      then
    }

    @Test
    void shouldGetByRepresentativeAndInstitutionAndYear() {
        //      given
        UserEntity representativeEntity = new UserEntity();
        InstitutionEntity institutionEntity = new InstitutionEntity();
        Timestamp dateMin = Timestamp.valueOf(LocalDateTime.of(YEAR, 1, 1, 0, 0));
        Timestamp dateMax = Timestamp.valueOf(LocalDateTime.of(YEAR, 12, 31, 23, 59, 59));
        VisitEntity visitEntity = new VisitEntity();
        visitEntity.setVisitStart(TIMESTAMP_START);
        visitEntity.setVisitEnd(TIMESTAMP_END);
        when(visitRepository.findByRepresentativeAndInstitutionAndVisitStartBetween(representativeEntity, institutionEntity, dateMin, dateMax))
                .thenReturn(Arrays.asList(visitEntity));
        //      when
        List<Visit> result = visitService.getByRepresentativeAndInstitutionAndYear(representativeEntity, institutionEntity, YEAR);
        //      then
        assertEquals(1, result.size());
        assertEquals(LOCAL_DATE_TIME_START, result.get(0).getVisitStart());
        assertEquals(LOCAL_DATE_TIME_END, result.get(0).getVisitEnd());
        verify(visitRepository, times(1))
                .findByRepresentativeAndInstitutionAndVisitStartBetween(representativeEntity, institutionEntity, dateMin, dateMax);
        verifyNoMoreInteractions(visitRepository);
    }

    @Test
    void shouldGetByRepresentativeAndInstitutionAndYearAndMonth() {
        //      given
        UserEntity representativeEntity = new UserEntity();
        InstitutionEntity institutionEntity = new InstitutionEntity();
        Timestamp dateMin = Timestamp.valueOf(LocalDateTime.of(YEAR, MONTH, 1, 0, 0));
        Timestamp dateMax = Timestamp.valueOf(LocalDateTime.of(YEAR, MONTH, 31, 23, 59, 59));
        VisitEntity visitEntity = new VisitEntity();
        visitEntity.setVisitStart(TIMESTAMP_START);
        visitEntity.setVisitEnd(TIMESTAMP_END);
        when(visitRepository.findByRepresentativeAndInstitutionAndVisitStartBetween(representativeEntity, institutionEntity, dateMin, dateMax))
                .thenReturn(Arrays.asList(visitEntity));
        //      when
        List<Visit> result = visitService.getByRepresentativeAndInstitutionAndYearAndMonth(representativeEntity, institutionEntity, YEAR, MONTH);
        //      then
        assertEquals(1, result.size());
        assertEquals(LOCAL_DATE_TIME_START, result.get(0).getVisitStart());
        assertEquals(LOCAL_DATE_TIME_END, result.get(0).getVisitEnd());
        verify(visitRepository, times(1))
                .findByRepresentativeAndInstitutionAndVisitStartBetween(representativeEntity, institutionEntity, dateMin, dateMax);
        verifyNoMoreInteractions(visitRepository);
    }

    @Test
    void shouldGetByRepresentativeAndInstitutionAndYearAndMonthAndDay() {
        //      given
        UserEntity representativeEntity = new UserEntity();
        InstitutionEntity institutionEntity = new InstitutionEntity();
        Timestamp dateMin = Timestamp.valueOf(LocalDateTime.of(YEAR, MONTH, DAY_OF_MONTH, 0, 0));
        Timestamp dateMax = Timestamp.valueOf(LocalDateTime.of(YEAR, MONTH, DAY_OF_MONTH, 23, 59, 59));
        VisitEntity visitEntity = new VisitEntity();
        visitEntity.setVisitStart(TIMESTAMP_START);
        visitEntity.setVisitEnd(TIMESTAMP_END);
        when(visitRepository.findByRepresentativeAndInstitutionAndVisitStartBetween(representativeEntity, institutionEntity, dateMin, dateMax))
                .thenReturn(Arrays.asList(visitEntity));
        //      when
        List<Visit> result = visitService
                .getByRepresentativeAndInstitutionAndYearAndMonthAndDay(representativeEntity, institutionEntity, YEAR, MONTH, DAY_OF_MONTH);
        //      then
        assertEquals(1, result.size());
        assertEquals(LOCAL_DATE_TIME_START, result.get(0).getVisitStart());
        assertEquals(LOCAL_DATE_TIME_END, result.get(0).getVisitEnd());
        verify(visitRepository, times(1))
                .findByRepresentativeAndInstitutionAndVisitStartBetween(representativeEntity, institutionEntity, dateMin, dateMax);
        verifyNoMoreInteractions(visitRepository);
    }

    @Test
    void shouldGetByRepresentativeAndInstitutionAndVisitId() {
        //      given
        UserEntity representativeEntity = new UserEntity();
        InstitutionEntity institutionEntity = new InstitutionEntity();
        VisitEntity visitEntity = new VisitEntity();
        visitEntity.setVisitStart(TIMESTAMP_START);
        visitEntity.setVisitEnd(TIMESTAMP_END);
        when(visitRepository.findByVisitIdAndRepresentativeAndInstitution(VISIT_ID, representativeEntity, institutionEntity))
                .thenReturn(Optional.of(visitEntity));
        //      when
        Visit result = visitService.getByRepresentativeAndInstitutionAndVisitId(representativeEntity, institutionEntity, VISIT_ID);
        //      then
        assertEquals(LOCAL_DATE_TIME_START, result.getVisitStart());
        assertEquals(LOCAL_DATE_TIME_END, result.getVisitEnd());
        verify(visitRepository, times(1))
                .findByVisitIdAndRepresentativeAndInstitution(VISIT_ID, representativeEntity, institutionEntity);
        verifyNoMoreInteractions(visitRepository);
    }

    @Test
    void shouldThrowVisitNotFoundException_whenGetByRepresentativeAndInstitutionAndVisitId() {
        //      given
        UserEntity representativeEntity = new UserEntity();
        InstitutionEntity institutionEntity = new InstitutionEntity();
        when(visitRepository.findByVisitIdAndRepresentativeAndInstitution(VISIT_ID, representativeEntity, institutionEntity))
                .thenReturn(Optional.empty());
        //      when
        VisitNotFoundException exception = assertThrows(
                VisitNotFoundException.class,
                () -> visitService.getByRepresentativeAndInstitutionAndVisitId(representativeEntity, institutionEntity, VISIT_ID)
        );
        //      then
        assertEquals("Could not find: " + VISIT_ID, exception.getMessage());
        verify(visitRepository, times(1))
                .findByVisitIdAndRepresentativeAndInstitution(VISIT_ID, representativeEntity, institutionEntity);
        verifyNoMoreInteractions(visitRepository);
    }

    @Test
    void shouldDelete() {
        //      given
        UserEntity representativeEntity = new UserEntity();
        InstitutionEntity institutionEntity = new InstitutionEntity();
        VisitEntity visitEntity = new VisitEntity();
        visitEntity.setVisitStart(TIMESTAMP_START);
        visitEntity.setVisitEnd(TIMESTAMP_END);
        when(visitRepository.findByVisitIdAndRepresentativeAndInstitution(VISIT_ID, representativeEntity, institutionEntity))
                .thenReturn(Optional.of(visitEntity));
        //      when
        visitService.delete(representativeEntity, institutionEntity, VISIT_ID);
        //      then
        verify(visitRepository, times(1))
                .findByVisitIdAndRepresentativeAndInstitution(VISIT_ID, representativeEntity, institutionEntity);
        verify(visitRepository, times(1))
                .delete(visitEntity);
        verifyNoMoreInteractions(visitRepository);
    }

    @Test
    void shouldGetByRepresentativeAndVisitEndIsAfterAndVisitStartIsBefore() {
        //      given
        UserEntity representativeEntity = new UserEntity();
        VisitEntity visitEntity = new VisitEntity();
        when(visitRepository.findByRepresentativeAndVisitEndIsAfterAndVisitStartIsBefore(representativeEntity, TIMESTAMP_START, TIMESTAMP_END))
                .thenReturn(Arrays.asList(visitEntity));
        //      when
        List<VisitEntity> result = visitService
                .getByRepresentativeAndVisitEndIsAfterAndVisitStartIsBefore(representativeEntity, TIMESTAMP_START, TIMESTAMP_END);
        //      then
        assertEquals(visitEntity, result.get(0));
        verify(visitRepository, times(1))
                .findByRepresentativeAndVisitEndIsAfterAndVisitStartIsBefore(representativeEntity, TIMESTAMP_START, TIMESTAMP_END);
        verifyNoMoreInteractions(visitRepository);
    }

    @Test
    void shouldGetByInstitutionAndVisitEndIsAfterAndVisitStartIsBefore() {
        //      given
        VisitEntity visitEntity = new VisitEntity();
        InstitutionEntity institutionEntity = new InstitutionEntity();
        when(visitRepository.findByInstitutionAndVisitEndIsAfterAndVisitStartIsBefore(institutionEntity, TIMESTAMP_START, TIMESTAMP_END))
                .thenReturn(Arrays.asList(visitEntity));
        //      when
        List<VisitEntity> result = visitService
                .getByInstitutionAndVisitEndIsAfterAndVisitStartIsBefore(institutionEntity, TIMESTAMP_START, TIMESTAMP_END);
        //      then
        assertEquals(visitEntity, result.get(0));
        verify(visitRepository, times(1))
                .findByInstitutionAndVisitEndIsAfterAndVisitStartIsBefore(institutionEntity, TIMESTAMP_START, TIMESTAMP_END);
        verifyNoMoreInteractions(visitRepository);

    }

    @Test
    void shouldSaveAll() {
        //      given
        VisitEntity visitEntity = new VisitEntity();
        visitEntity.setVisitStart(TIMESTAMP_START);
        visitEntity.setVisitEnd(TIMESTAMP_END);
        List<VisitEntity> entities = Arrays.asList(visitEntity);
        when(visitRepository.saveAll(ArgumentMatchers.anyCollection())).thenReturn(entities);
        //      when
        List<Visit> result = visitService.saveAll(entities);
        //      then
        assertEquals(1, result.size());
        assertEquals(LOCAL_DATE_TIME_START, result.get(0).getVisitStart());
        assertEquals(LOCAL_DATE_TIME_END, result.get(0).getVisitEnd());
        verify(visitRepository, times(1)).saveAll(entities);
        verifyNoMoreInteractions(visitRepository);
    }

    @Test
    void shouldCancelVisit() {
        //      given
        VisitEntity visitEntity = new VisitEntity();
        visitEntity.setVisitStart(Timestamp.valueOf(LocalDateTime.now().plusMinutes(10)));
        visitEntity.setVisitEnd((Timestamp.valueOf(LocalDateTime.now().plusMinutes(40))));
        //      when
        visitService.cancelVisit(visitEntity);
        //      then
        verify(visitRepository, times(1)).save(visitEntity);
        verifyNoMoreInteractions(visitRepository);
    }
}