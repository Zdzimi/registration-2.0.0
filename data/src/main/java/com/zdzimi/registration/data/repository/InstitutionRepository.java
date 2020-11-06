package com.zdzimi.registration.data.repository;

import com.zdzimi.registration.data.entity.InstitutionEntity;
import com.zdzimi.registration.data.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InstitutionRepository extends JpaRepository<InstitutionEntity, Long> {

    Optional<InstitutionEntity> findByInstitutionName(String institutionName);

    List<InstitutionEntity> findByUsers(UserEntity userEntity);
}
