package com.zdzimi.registration.service.mapper;

import com.zdzimi.registration.core.model.User;
import com.zdzimi.registration.data.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;

import static com.zdzimi.registration.core.model.Role.ROLE_USER;

@Component
public class UserMapper {

    private ModelMapper modelMapper;

    @Autowired
    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public User convertToUser(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }
        User user = modelMapper.map(userEntity, User.class);
        user.setPassword(null);
        return user;
    }

    public UserEntity convertToUserEntity(User user) {
        UserEntity userEntity = modelMapper.map(user, UserEntity.class);
        userEntity.setRole(ROLE_USER.name());
        userEntity.setVisits(Collections.emptyList());
        userEntity.setRecognizedInstitutions(Collections.emptyList());
        userEntity.setProvidedVisits(Collections.emptyList());
        userEntity.setWorkPlaces(Collections.emptyList());
        return userEntity;
    }
}
