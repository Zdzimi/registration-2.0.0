package com.zdzimi.registration.controller.restController;

import com.zdzimi.registration.core.model.Visit;
import com.zdzimi.registration.core.model.template.TimetableTemplate;
import com.zdzimi.registration.data.entity.InstitutionEntity;
import com.zdzimi.registration.data.entity.UserEntity;
import com.zdzimi.registration.data.entity.VisitEntity;
import com.zdzimi.registration.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class RepresentativeUtilsControllerTest {

    private static final String USERNAME = "BorisSpassky";
    private static final String INSTITUTION_NAME = "mrTooth";
    private static final int YEAR = 2099;
    private static final int MONTH = 3;
    private static final int DAY_OF_MONTH = 28;
    private static final long VISIT_ID = 234248;


    private RepresentativeUtilsController representativeUtilsController;
    private VisitService visitService;
    private UserService userService;
    private InstitutionService institutionService;
    private TimetableTemplateService timetableTemplateService;
    private VisitEntityGenerator visitEntityGenerator;
    private ConflictAnalyzer conflictAnalyzer;

    @BeforeEach
    void setUp() {
        visitService = mock(VisitService.class);
        userService = mock(UserService.class);
        institutionService = mock(InstitutionService.class);
        timetableTemplateService = mock(TimetableTemplateService.class);
        visitEntityGenerator = mock(VisitEntityGenerator.class);
        conflictAnalyzer = mock(ConflictAnalyzer.class);
        initMocks(this);
        representativeUtilsController = new RepresentativeUtilsController(
                visitService, userService, institutionService, timetableTemplateService, visitEntityGenerator, conflictAnalyzer
        );
    }

    @Test
    void shouldPrepareNextTemplate() {
        //      given
        UserEntity representativeEntity = new UserEntity();
        when(userService.getUserEntityByUsername(USERNAME)).thenReturn(representativeEntity);
        InstitutionEntity institutionEntity = new InstitutionEntity();
        when(institutionService.getWorkPlaceEntityByRepresentativeEntityAndInstitutionName(representativeEntity, INSTITUTION_NAME))
                .thenReturn(institutionEntity);
        Visit visit = new Visit();
        when(visitService.getLastProvidedVisit(representativeEntity, institutionEntity)).thenReturn(visit);
        TimetableTemplate timetableTemplate = new TimetableTemplate();
        when(timetableTemplateService.prepareTemplate(visit)).thenReturn(timetableTemplate);
        //      when
        TimetableTemplate result = representativeUtilsController.prepareNextTemplate(USERNAME, INSTITUTION_NAME);
        //      then
        assertEquals(timetableTemplate, result);
        verify(userService, times(1)).getUserEntityByUsername(USERNAME);
        verifyNoMoreInteractions(userService);
        verify(institutionService, times(1))
                .getWorkPlaceEntityByRepresentativeEntityAndInstitutionName(representativeEntity, INSTITUTION_NAME);
        verifyNoMoreInteractions(institutionService);
        verify(visitService, times(1)).getLastProvidedVisit(representativeEntity, institutionEntity);
        verifyNoMoreInteractions(visitService);
        verify(timetableTemplateService, times(1)).prepareTemplate(visit);
        verifyNoMoreInteractions(timetableTemplateService);
    }

    @Test
    void shouldShowVisitsByYear() {
        //      given
        UserEntity representativeEntity = new UserEntity();
        when(userService.getUserEntityByUsername(USERNAME)).thenReturn(representativeEntity);
        InstitutionEntity institutionEntity = new InstitutionEntity();
        when(institutionService.getWorkPlaceEntityByRepresentativeEntityAndInstitutionName(representativeEntity, INSTITUTION_NAME))
                .thenReturn(institutionEntity);
        Visit visit = new Visit();
        when(visitService.getByRepresentativeAndInstitutionAndYear(representativeEntity, institutionEntity, YEAR)).thenReturn(Arrays.asList(visit));
        //      when
        List<Visit> result = representativeUtilsController.showVisitsByYear(USERNAME, INSTITUTION_NAME, YEAR);
        //      then
        assertEquals(1, result.size());
        verify(userService, times(1)).getUserEntityByUsername(USERNAME);
        verifyNoMoreInteractions(userService);
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
        when(userService.getUserEntityByUsername(USERNAME)).thenReturn(representativeEntity);
        InstitutionEntity institutionEntity = new InstitutionEntity();
        when(institutionService.getWorkPlaceEntityByRepresentativeEntityAndInstitutionName(representativeEntity, INSTITUTION_NAME))
                .thenReturn(institutionEntity);
        Visit visit = new Visit();
        when(visitService.getByRepresentativeAndInstitutionAndYearAndMonth(representativeEntity, institutionEntity, YEAR, MONTH))
                .thenReturn(Arrays.asList(visit));
        //      when
        List<Visit> result = representativeUtilsController.showVisitsByYearAndMonth(USERNAME, INSTITUTION_NAME, YEAR, MONTH);
        //      then
        assertEquals(1, result.size());
        verify(userService, times(1)).getUserEntityByUsername(USERNAME);
        verifyNoMoreInteractions(userService);
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
        TimetableTemplate timetableTemplate = new TimetableTemplate();
        when(timetableTemplateService.prepareTemplate(YEAR, MONTH)).thenReturn(timetableTemplate);
        //      when
        TimetableTemplate result = representativeUtilsController.prepareTemplateByYearAndMonth(YEAR, MONTH);
        //      them
        assertEquals(timetableTemplate, result);
        verify(timetableTemplateService, times(1)).prepareTemplate(YEAR, MONTH);
        verifyNoMoreInteractions(timetableTemplateService);
    }

    @Test
    void shouldShowVisitsByYearAndMonthAndDay() {
        //      given
        UserEntity representativeEntity = new UserEntity();
        when(userService.getUserEntityByUsername(USERNAME)).thenReturn(representativeEntity);
        InstitutionEntity institutionEntity = new InstitutionEntity();
        when(institutionService.getWorkPlaceEntityByRepresentativeEntityAndInstitutionName(representativeEntity, INSTITUTION_NAME))
                .thenReturn(institutionEntity);
        Visit visit = new Visit();
        when(visitService.getByRepresentativeAndInstitutionAndYearAndMonthAndDay(representativeEntity, institutionEntity, YEAR, MONTH, DAY_OF_MONTH))
                .thenReturn(Arrays.asList(visit));
        //      when
        List<Visit> result = representativeUtilsController.showVisitsByYearAndMonthAndDay(USERNAME, INSTITUTION_NAME, YEAR, MONTH, DAY_OF_MONTH);
        //      then
        assertEquals(1, result.size());
        verify(userService, times(1)).getUserEntityByUsername(USERNAME);
        verifyNoMoreInteractions(userService);
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
        when(userService.getUserEntityByUsername(USERNAME)).thenReturn(representativeEntity);
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
        verify(userService, times(1)).getUserEntityByUsername(USERNAME);
        verifyNoMoreInteractions(userService);
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
        when(userService.getUserEntityByUsername(USERNAME)).thenReturn(representativeEntity);
        InstitutionEntity institutionEntity = new InstitutionEntity();
        when(institutionService.getWorkPlaceEntityByRepresentativeEntityAndInstitutionName(representativeEntity, INSTITUTION_NAME))
                .thenReturn(institutionEntity);
        //      when
        representativeUtilsController.deleteVisit(USERNAME, INSTITUTION_NAME, VISIT_ID);
        //      then
        verify(userService, times(1)).getUserEntityByUsername(USERNAME);
        verifyNoMoreInteractions(userService);
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
        when(userService.getUserEntityByUsername(USERNAME)).thenReturn(representativeEntity);

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

        verify(userService, times(1)).getUserEntityByUsername(USERNAME);
        verifyNoMoreInteractions(userService);
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