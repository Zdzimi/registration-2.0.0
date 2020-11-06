package com.zdzimi.registration.service;

import com.zdzimi.registration.core.model.User;
import com.zdzimi.registration.data.entity.UserEntity;
import com.zdzimi.registration.data.exception.UserNotFoundException;
import com.zdzimi.registration.data.repository.UserRepository;
import com.zdzimi.registration.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
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
}
