package com.zdzimi.registration.data.repository;

import com.zdzimi.registration.data.entity.UserEntity;
import com.zdzimi.registration.data.entity.VisitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitRepository extends JpaRepository<VisitEntity, Long> {
    List<VisitEntity> findByUser(UserEntity userEntity);

    VisitEntity findByUserAndVisitId(UserEntity userEntity, long visitId);
}
