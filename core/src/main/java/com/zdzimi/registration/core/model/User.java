package com.zdzimi.registration.core.model;

import com.zdzimi.registration.core.validation.OnCreate;
import com.zdzimi.registration.core.validation.OnUpdate;
import com.zdzimi.registration.core.validation.OnlyLettersAndDigits;
import com.zdzimi.registration.core.validation.Password;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.EntityModel;

import javax.validation.constraints.*;

@Getter
@Setter
public class User extends EntityModel {

    @Null
    private Long userId;
    @OnlyLettersAndDigits
    private String username;
    @Size(min = 3, message = "Pole musi zawierać conajmniej trzy znaki.")
    private String name;
    @Size(min = 3, message = "Pole musi zawierać conajmniej trzy znaki.")
    private String surname;
    @Email(message = "Niepoprawny adres e-mail")
    private String email;
    @Password
    private String password;
    @Null
    private Role role;
}
