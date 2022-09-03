package com.zdzimi.registration.service;

import com.zdzimi.registration.core.model.ModifiedUser;
import com.zdzimi.registration.core.model.User;
import com.zdzimi.registration.data.entity.InstitutionEntity;
import com.zdzimi.registration.data.entity.UserEntity;
import com.zdzimi.registration.data.exception.UserNotFoundException;
import com.zdzimi.registration.data.repository.UserRepository;
import com.zdzimi.registration.service.exception.RepresentativeAlreadyInvitedException;
import com.zdzimi.registration.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public User getByUsername(String username) {
        UserEntity userEntity = getUserEntityByUsername(username);
        return userMapper.convertToUser(userEntity);
    }

    public UserEntity getUserEntityByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    public User save(User user) {
        UserEntity userEntity = userMapper.convertToUserEntity(user);
        UserEntity savedUserEntity = userRepository.save(userEntity);
        return userMapper.convertToUser(savedUserEntity);
    }

    public List<User> getByWorkPlaces(InstitutionEntity institutionEntity) {
        return userRepository.findByWorkPlaces(institutionEntity).stream()
                .map(userMapper::convertToUser)
                .collect(Collectors.toList());
    }

    public User getByUsernameAndWorkPlaces(String representativeName, InstitutionEntity institutionEntity) {
        UserEntity userEntity = userRepository.findByUsernameAndWorkPlaces(representativeName, institutionEntity)
                .orElseThrow(() -> new UserNotFoundException(representativeName, institutionEntity.getInstitutionName()));
        return userMapper.convertToUser(userEntity);
    }

    public void addWorkPlace(String username, InstitutionEntity institutionEntity) {
        UserEntity userEntity = getUserEntityByUsername(username);
        userEntity.getWorkPlaces().add(institutionEntity);
        userRepository.save(userEntity);
    }

    public User inviteUserToWorkPlace(String username, InstitutionEntity institutionEntity) {
        // todo test
        checkIfItDoesNotRepeatItself(username, institutionEntity);
        UserEntity userEntity = getUserEntityByUsername(username);
        userEntity.getWorkPlaces().add(institutionEntity);
        UserEntity savedUserEntity = userRepository.save(userEntity);
        return userMapper.convertToUser(savedUserEntity);
    }

    private void checkIfItDoesNotRepeatItself(String username, InstitutionEntity institutionEntity) throws RepresentativeAlreadyInvitedException {
        List<UserEntity> representatives = userRepository.findByWorkPlaces(institutionEntity);
        for (UserEntity userEntity : representatives) {
            if (userEntity.getUsername().equals(username)) {
                throw new RepresentativeAlreadyInvitedException(username);
            }
        }
    }

    public void addRecognizedInstitution(UserEntity userEntity, InstitutionEntity institutionEntity) {
        if (!userEntity.getRecognizedInstitutions().contains(institutionEntity)) {
            userEntity.getRecognizedInstitutions().add(institutionEntity);
            userRepository.save(userEntity);
        }
    }

    public User update(UserEntity userEntity, ModifiedUser modifiedUser) {
        userEntity.setUsername(modifiedUser.getUsername());
        userEntity.setName(modifiedUser.getName());
        userEntity.setSurname(modifiedUser.getSurname());
        userEntity.setEmail(modifiedUser.getEmail());
        userEntity.setPassword(modifiedUser.getNewPassword());
        UserEntity savedUser = userRepository.save(userEntity);
        return userMapper.convertToUser(savedUser);
    }
}
