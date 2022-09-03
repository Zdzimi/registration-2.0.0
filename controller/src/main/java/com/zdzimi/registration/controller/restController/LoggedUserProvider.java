package com.zdzimi.registration.controller.restController;

import com.zdzimi.registration.data.entity.UserEntity;
import com.zdzimi.registration.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class LoggedUserProvider {

  private final UserService userService;

  @PostAuthorize("returnObject.username == principal.username")
  UserEntity provideLoggedUser(String username) {
    return userService.getUserEntityByUsername(username);
  }
}
