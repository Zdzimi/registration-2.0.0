package com.zdzimi.registration.service;

import com.zdzimi.registration.core.model.User;
import com.zdzimi.registration.data.entity.InstitutionEntity;
import com.zdzimi.registration.data.entity.UserEntity;
import com.zdzimi.registration.data.exception.UserNotFoundException;
import com.zdzimi.registration.data.repository.UserRepository;
import com.zdzimi.registration.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository userRepository;
    private InstitutionService institutionService;
    private UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, InstitutionService institutionService, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.institutionService = institutionService;
        this.userMapper = userMapper;
    }

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

    public List<User> getByWorkPlaces(String institutionName) {
        InstitutionEntity institutionEntity = institutionService.getInstitutionEntityByInstitutionName(institutionName);
        return userRepository.findByWorkPlaces(institutionEntity).stream()
                .map(userMapper::convertToUser)
                .collect(Collectors.toList());
    }

    public User getByUsernameAndWorkPlaces(String representativeName, String institutionName) {
        InstitutionEntity institutionEntity = institutionService.getInstitutionEntityByInstitutionName(institutionName);
        UserEntity userEntity = userRepository.findByUsernameAndWorkPlaces(representativeName, institutionEntity)
                .orElseThrow(() -> new UserNotFoundException(representativeName, institutionName));
        return userMapper.convertToUser(userEntity);
    }
}
