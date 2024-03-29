package com.zdzimi.registration.controller.restController;

import com.zdzimi.registration.controller.link.LinkCreator;
import com.zdzimi.registration.core.model.Place;
import com.zdzimi.registration.core.model.Visit;
import com.zdzimi.registration.core.model.template.TimetableTemplate;
import com.zdzimi.registration.core.model.timetable.MonthTimetable;
import com.zdzimi.registration.data.entity.InstitutionEntity;
import com.zdzimi.registration.data.entity.UserEntity;
import com.zdzimi.registration.data.entity.VisitEntity;
import com.zdzimi.registration.service.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RepresentativeUtilsControllerTest {

    private static final String USERNAME = "BorisSpassky";
    private static final String INSTITUTION_NAME = "mrTooth";
    private static final int YEAR = 2099;
    private static final int MONTH = 3;
    private static final int DAY_OF_MONTH = 28;
    private static final long VISIT_ID = 234248;

    @Mock
    private LoggedUserProvider loggedUserProvider;
    @Mock
    private VisitService visitService;
    @Mock
    private InstitutionService institutionService;
    @Mock
    private PlaceService placeService;
    @Mock
    private TimetableTemplateService timetableTemplateService;
    @Mock
    private VisitEntityGenerator visitEntityGenerator;
    @Mock
    private ConflictAnalyzer conflictAnalyzer;
    @Mock
    private LinkCreator linkCreator;
    @InjectMocks
    private RepresentativeUtilsController representativeUtilsController;

    @Test
    void shouldPrepareNextTemplate() {
        //      given
        UserEntity representativeEntity = new UserEntity();
        when(loggedUserProvider.provideLoggedUser(USERNAME)).thenReturn(representativeEntity);
        InstitutionEntity institutionEntity = new InstitutionEntity();
        when(institutionService.getWorkPlaceEntityByRepresentativeEntityAndInstitutionName(representativeEntity, INSTITUTION_NAME))
                .thenReturn(institutionEntity);
        List<Place> places = Arrays.asList(new Place());
        when(placeService.getPlaces(institutionEntity)).thenReturn(places);
        Visit visit = new Visit();
        when(visitService.getLastProvidedVisit(representativeEntity, institutionEntity)).thenReturn(visit);
        TimetableTemplate timetableTemplate = new TimetableTemplate();
        when(timetableTemplateService.prepareTemplate(visit, places)).thenReturn(timetableTemplate);
        //      when
        TimetableTemplate result = representativeUtilsController.prepareNextTemplate(USERNAME, INSTITUTION_NAME);
        //      then
        assertEquals(timetableTemplate, result);
        verify(loggedUserProvider, times(1)).provideLoggedUser(USERNAME);
        verifyNoMoreInteractions(loggedUserProvider);
        verify(institutionService, times(1))
                .getWorkPlaceEntityByRepresentativeEntityAndInstitutionName(representativeEntity, INSTITUTION_NAME);
        verifyNoMoreInteractions(institutionService);
        verify(visitService, times(1)).getLastProvidedVisit(representativeEntity, institutionEntity);
        verifyNoMoreInteractions(visitService);
        verify(timetableTemplateService, times(1)).prepareTemplate(visit, places);
        verifyNoMoreInteractions(timetableTemplateService);
    }

    @Test
    void shouldShowVisitsByYear() {
        //      given
        UserEntity representativeEntity = new UserEntity();
        when(loggedUserProvider.provideLoggedUser(USERNAME)).thenReturn(representativeEntity);
        InstitutionEntity institutionEntity = new InstitutionEntity();
        when(institutionService.getWorkPlaceEntityByRepresentativeEntityAndInstitutionName(representativeEntity, INSTITUTION_NAME))
                .thenReturn(institutionEntity);
        Visit visit = new Visit();
        visit.setVisitStart(LocalDateTime.now());
        when(visitService.getByRepresentativeAndInstitutionAndYear(representativeEntity, institutionEntity, YEAR)).thenReturn(Arrays.asList(visit));
        //      when
        List<MonthTimetable> result = representativeUtilsController.showVisitsByYear(USERNAME, INSTITUTION_NAME, YEAR);
        //      then
        assertEquals(1, result.size());
        verify(loggedUserProvider, times(1)).provideLoggedUser(USERNAME);
        verifyNoMoreInteractions(loggedUserProvider);
        verify(institutionService, times(1))
                .getWorkPlaceEntityByRepresentativeEntityAndInstitutionName(representativeEntity, INSTITUTION_NAME);
        verifyNoMoreInteractions(institutionService);
        verify(visitService, times(1)).getByRepresentativeAndInstitutionAndYear(representativeEntity, institutionEntity, YEAR);
        verifyNoMoreInteractions(visitService);
    }

    @Test
    void shouldShowVisitsByYearAndMonth() {
        //      given
        UserEntity representativeEntity = new UserEntity();
        when(loggedUserProvider.provideLoggedUser(USERNAME)).thenReturn(representativeEntity);
        InstitutionEntity institutionEntity = new InstitutionEntity();
        when(institutionService.getWorkPlaceEntityByRepresentativeEntityAndInstitutionName(representativeEntity, INSTITUTION_NAME))
                .thenReturn(institutionEntity);
        Visit visit = new Visit();
        visit.setVisitStart(LocalDateTime.now());
        when(visitService.getByRepresentativeAndInstitutionAndYearAndMonth(representativeEntity, institutionEntity, YEAR, MONTH))
                .thenReturn(Arrays.asList(visit));
        //      when
        List<MonthTimetable> result = representativeUtilsController.showVisitsByYearAndMonth(USERNAME, INSTITUTION_NAME, YEAR, MONTH);
        //      then
        assertEquals(1, result.size());
        verify(loggedUserProvider, times(1)).provideLoggedUser(USERNAME);
        verifyNoMoreInteractions(loggedUserProvider);
        verify(institutionService, times(1))
                .getWorkPlaceEntityByRepresentativeEntityAndInstitutionName(representativeEntity, INSTITUTION_NAME);
        verifyNoMoreInteractions(institutionService);
        verify(visitService, times(1))
                .getByRepresentativeAndInstitutionAndYearAndMonth(representativeEntity, institutionEntity, YEAR, MONTH);
        verifyNoMoreInteractions(visitService);
    }

    @Test
    void shouldGetTemplateByYearAndMonth() {
        //      given
        UserEntity representativeEntity = new UserEntity();
        when(loggedUserProvider.provideLoggedUser(USERNAME)).thenReturn(representativeEntity);
        InstitutionEntity institutionEntity = new InstitutionEntity();
        when(institutionService.getWorkPlaceEntityByRepresentativeEntityAndInstitutionName(representativeEntity, INSTITUTION_NAME))
                .thenReturn(institutionEntity);
        List<Place> places = Arrays.asList(new Place());
        when(placeService.getPlaces(institutionEntity)).thenReturn(places);
        TimetableTemplate timetableTemplate = new TimetableTemplate();
        when(timetableTemplateService.prepareTemplate(YEAR, MONTH, places)).thenReturn(timetableTemplate);
        //      when
        TimetableTemplate result = representativeUtilsController.prepareTemplateByYearAndMonth(USERNAME, INSTITUTION_NAME, YEAR, MONTH);
        //      them
        assertEquals(timetableTemplate, result);
        verify(timetableTemplateService, times(1)).prepareTemplate(YEAR, MONTH, places);
        verifyNoMoreInteractions(timetableTemplateService);
    }

    @Test
    void shouldShowVisitsByYearAndMonthAndDay() {
        //      given
        UserEntity representativeEntity = new UserEntity();
        when(loggedUserProvider.provideLoggedUser(USERNAME)).thenReturn(representativeEntity);
        InstitutionEntity institutionEntity = new InstitutionEntity();
        when(institutionService.getWorkPlaceEntityByRepresentativeEntityAndInstitutionName(representativeEntity, INSTITUTION_NAME))
                .thenReturn(institutionEntity);
        Visit visit = new Visit();
        visit.setVisitStart(LocalDateTime.now());
        when(visitService.getByRepresentativeAndInstitutionAndYearAndMonthAndDay(representativeEntity, institutionEntity, YEAR, MONTH, DAY_OF_MONTH))
                .thenReturn(Arrays.asList(visit));
        //      when
        List<MonthTimetable> result = representativeUtilsController.showVisitsByYearAndMonthAndDay(USERNAME, INSTITUTION_NAME, YEAR, MONTH, DAY_OF_MONTH);
        //      then
        assertEquals(1, result.size());
        verify(loggedUserProvider, times(1)).provideLoggedUser(USERNAME);
        verifyNoMoreInteractions(loggedUserProvider);
        verify(institutionService, times(1))
                .getWorkPlaceEntityByRepresentativeEntityAndInstitutionName(representativeEntity, INSTITUTION_NAME);
        verifyNoMoreInteractions(institutionService);
        verify(visitService, times(1))
                .getByRepresentativeAndInstitutionAndYearAndMonthAndDay(representativeEntity, institutionEntity, YEAR, MONTH, DAY_OF_MONTH);
        verifyNoMoreInteractions(visitService);
    }

    @Test
    void shouldShowVisit() {
        //      given
        UserEntity representativeEntity = new UserEntity();
        when(loggedUserProvider.provideLoggedUser(USERNAME)).thenReturn(representativeEntity);
        InstitutionEntity institutionEntity = new InstitutionEntity();
        when(institutionService.getWorkPlaceEntityByRepresentativeEntityAndInstitutionName(representativeEntity, INSTITUTION_NAME))
                .thenReturn(institutionEntity);
        Visit visit = new Visit();
        when(visitService.getByRepresentativeAndInstitutionAndVisitId(representativeEntity, institutionEntity, VISIT_ID))
                .thenReturn(visit);
        //      when
        Visit result = representativeUtilsController.showVisit(USERNAME, INSTITUTION_NAME, VISIT_ID);
        //      then
        assertEquals(visit, result);
        verify(loggedUserProvider, times(1)).provideLoggedUser(USERNAME);
        verifyNoMoreInteractions(loggedUserProvider);
        verify(institutionService, times(1))
                .getWorkPlaceEntityByRepresentativeEntityAndInstitutionName(representativeEntity, INSTITUTION_NAME);
        verifyNoMoreInteractions(institutionService);
        verify(visitService, times(1)).getByRepresentativeAndInstitutionAndVisitId(representativeEntity, institutionEntity, VISIT_ID);
        verifyNoMoreInteractions(visitService);
    }

    @Test
    void shouldDeleteVisit() {
        //      given
        UserEntity representativeEntity = new UserEntity();
        when(loggedUserProvider.provideLoggedUser(USERNAME)).thenReturn(representativeEntity);
        InstitutionEntity institutionEntity = new InstitutionEntity();
        when(institutionService.getWorkPlaceEntityByRepresentativeEntityAndInstitutionName(representativeEntity, INSTITUTION_NAME))
                .thenReturn(institutionEntity);
        //      when
        representativeUtilsController.deleteVisit(USERNAME, INSTITUTION_NAME, VISIT_ID);
        //      then
        verify(loggedUserProvider, times(1)).provideLoggedUser(USERNAME);
        verifyNoMoreInteractions(loggedUserProvider);
        verify(institutionService, times(1))
                .getWorkPlaceEntityByRepresentativeEntityAndInstitutionName(representativeEntity, INSTITUTION_NAME);
        verifyNoMoreInteractions(institutionService);
        verify(visitService, times(1)).delete(representativeEntity, institutionEntity, VISIT_ID);
        verifyNoMoreInteractions(visitService);
    }

    @Test
    void shouldCreateTimetable() {
        //      given
        UserEntity representativeEntity = new UserEntity();
        when(loggedUserProvider.provideLoggedUser(USERNAME)).thenReturn(representativeEntity);

        InstitutionEntity institutionEntity = new InstitutionEntity();
        when(institutionService.getWorkPlaceEntityByRepresentativeEntityAndInstitutionName(representativeEntity, INSTITUTION_NAME))
                .thenReturn(institutionEntity);

        TimetableTemplate timetableTemplate = new TimetableTemplate();
        VisitEntity visitEntity = new VisitEntity();
        List<VisitEntity> visitEntities = Arrays.asList(visitEntity);
        when(visitEntityGenerator.createVisits(timetableTemplate, representativeEntity, institutionEntity)).thenReturn(visitEntities);

        when(conflictAnalyzer.checkConflicts(visitEntities, representativeEntity, institutionEntity)).thenReturn(true);

        Visit visit = new Visit();
        when(visitService.saveAll(visitEntities)).thenReturn(Arrays.asList(visit));
        //      when
        List<Visit> result = representativeUtilsController.createTimetable(timetableTemplate, USERNAME, INSTITUTION_NAME);
        //      then

        verify(loggedUserProvider, times(1)).provideLoggedUser(USERNAME);
        verifyNoMoreInteractions(loggedUserProvider);
        verify(institutionService, times(1))
                .getWorkPlaceEntityByRepresentativeEntityAndInstitutionName(representativeEntity, INSTITUTION_NAME);
        verifyNoMoreInteractions(institutionService);
        verify(visitEntityGenerator, times(1)).createVisits(timetableTemplate, representativeEntity, institutionEntity);
        verifyNoMoreInteractions(visitEntityGenerator);
        verify(conflictAnalyzer, times(1)).checkConflicts(visitEntities, representativeEntity, institutionEntity);
        verifyNoMoreInteractions(conflictAnalyzer);
        verify(visitService, times(1)).saveAll(visitEntities);
        verifyNoMoreInteractions(visitService);
    }
}