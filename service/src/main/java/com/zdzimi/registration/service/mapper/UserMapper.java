package com.zdzimi.registration.service.mapper;

import com.zdzimi.registration.core.model.User;
import com.zdzimi.registration.data.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;
import org.springframework.web.util.HtmlUtils;

import static com.zdzimi.registration.core.model.Role.ROLE_USER;

@RequiredArgsConstructor
@Component
public class UserMapper {

    private final ModelMapper modelMapper;

    public User convertToUser(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }
        User user = new User();
        user.setUsername(userEntity.getUsername());
        user.setName(HtmlUtils.htmlEscape(userEntity.getName()));
        user.setSurname(HtmlUtils.htmlEscape(userEntity.getSurname()));
        user.setEmail(userEntity.getEmail());
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
