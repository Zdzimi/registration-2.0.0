package com.zdzimi.registration.controller.mapper;

import com.zdzimi.registration.core.model.User;
import com.zdzimi.registration.data.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class UserMapper {

    private ModelMapper modelMapper;

    @Autowired
    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public User convertToUser(UserEntity userEntity) {
        return modelMapper.map(userEntity, User.class);
    }

    public UserEntity convertToUserEntity(User user) {
        UserEntity userEntity = modelMapper.map(user, UserEntity.class);
        userEntity.setVisits(Collections.emptyList());
        userEntity.setRecognizedInstitutions(Collections.emptyList());
        userEntity.setProvidedVisits(Collections.emptyList());
        userEntity.setWorkPlaces(Collections.emptyList());
        return userEntity;
    }
}
