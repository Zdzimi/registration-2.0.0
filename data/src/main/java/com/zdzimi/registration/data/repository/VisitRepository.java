package com.zdzimi.registration.data.repository;

import com.zdzimi.registration.data.entity.UserEntity;
import com.zdzimi.registration.data.entity.VisitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VisitRepository extends JpaRepository<VisitEntity, Long> {

    Optional<VisitEntity> findByUserAndVisitId(UserEntity userEntity, long visitId);

    Optional<VisitEntity> findByVisitIdAndRepresentativeAndVisitDateTimeIsAfter(long visitId, UserEntity representativeEntity, Timestamp now);

    List<VisitEntity> findByUser(UserEntity userEntity);

    List<VisitEntity> findByRepresentativeAndVisitDateTimeIsAfter(UserEntity representativeEntity, Timestamp now);
}
