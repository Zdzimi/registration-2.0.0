package com.zdzimi.registration.data.repository;

import com.zdzimi.registration.data.entity.InstitutionEntity;
import com.zdzimi.registration.data.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

    List<UserEntity> findByWorkPlaces(InstitutionEntity institutionEntity);

    Optional<UserEntity> findByUsernameAndWorkPlaces(String username, InstitutionEntity institutionEntity);
}
