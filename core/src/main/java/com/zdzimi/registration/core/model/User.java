package com.zdzimi.registration.core.model;

import com.zdzimi.registration.core.validation.OnCreate;
import com.zdzimi.registration.core.validation.OnUpdate;
import com.zdzimi.registration.core.validation.OnlyLettersAndDigits;
import com.zdzimi.registration.core.validation.Password;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Getter
@Setter
public class User {

    @Null(groups = OnCreate.class)
    @NotNull(groups = OnUpdate.class)
    private Long userId;
    @NotNull
    @OnlyLettersAndDigits
    private String username;
    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    @Email
    private String email;
    @NotNull
    @Password
    private String password;
    @Null(groups = OnCreate.class)
    @NotNull(groups = OnUpdate.class)
    private Role role;
}
