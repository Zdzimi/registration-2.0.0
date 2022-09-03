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

    List<InstitutionEntity> findByRepresentatives(UserEntity userEntity);

    Optional<InstitutionEntity> findByInstitutionNameAndRepresentatives(String institutionName, UserEntity userEntity);

    List<InstitutionEntity> findByInstitutionNameContainsAndProvinceContainsAndCityContainsAndTypeOfServiceContains(String institutionName, String province, String city, String typeOfServices);
}
