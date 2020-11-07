package com.zdzimi.registration.data.repository;

import com.zdzimi.registration.data.entity.InstitutionEntity;
import com.zdzimi.registration.data.entity.PlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaceRepository extends JpaRepository<PlaceEntity, Long> {

    List<PlaceEntity> findByInstitution(InstitutionEntity institutionEntity);

    Optional<PlaceEntity> findByInstitutionAndPlaceName(InstitutionEntity institutionEntity, String placeName);
}
