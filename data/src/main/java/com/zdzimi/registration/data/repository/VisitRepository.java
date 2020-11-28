package com.zdzimi.registration.data.repository;

import com.zdzimi.registration.data.entity.InstitutionEntity;
import com.zdzimi.registration.data.entity.UserEntity;
import com.zdzimi.registration.data.entity.VisitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface VisitRepository extends JpaRepository<VisitEntity, Long> {

    Optional<VisitEntity> findByUserAndVisitId(UserEntity userEntity, long visitId);

    Optional<VisitEntity> findByVisitIdAndRepresentativeAndInstitution(
            long visitId, UserEntity representativeEntity, InstitutionEntity institutionEntity
    );

    Optional<VisitEntity> findByVisitIdAndRepresentativeAndInstitutionAndVisitStartIsAfter(
            long visitId, UserEntity representativeEntity, InstitutionEntity institutionEntity, Timestamp now
    );

    List<VisitEntity> findByUser(UserEntity userEntity);

    List<VisitEntity> findByRepresentativeAndInstitutionAndVisitStartIsAfter(
            UserEntity representativeEntity, InstitutionEntity institutionEntity, Timestamp now
    );

    List<VisitEntity> findByRepresentativeAndInstitutionAndVisitStartBetween(
            UserEntity representativeEntity, InstitutionEntity institutionEntity, Timestamp dateMin, Timestamp dateMax
    );

    List<VisitEntity> findByRepresentativeAndVisitEndIsAfterAndVisitStartIsBefore(
            UserEntity representativeEntity, Timestamp firstVisitStart, Timestamp lastVisitEnd
    );

    List<VisitEntity> findByInstitutionAndVisitEndIsAfterAndVisitStartIsBefore(
            InstitutionEntity institutionEntity, Timestamp firstVisitStart, Timestamp lastVisitEnd
    );
}
