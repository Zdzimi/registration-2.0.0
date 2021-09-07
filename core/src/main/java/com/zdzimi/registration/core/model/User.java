package com.zdzimi.registration.core.model;

import com.zdzimi.registration.core.validation.OnlyLettersAndDigits;
import com.zdzimi.registration.core.validation.Password;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.EntityModel;

import javax.validation.constraints.*;

@Getter
@Setter
public class User extends EntityModel {

    @OnlyLettersAndDigits
    private String username;
    @NotBlank(message = "Pole jest wymagane.")
    @Size(min = 3, message = "Pole musi zawierać conajmniej trzy znaki.")
    private String name;
    @NotBlank(message = "Pole jest wymagane.")
    @Size(min = 3, message = "Pole musi zawierać conajmniej trzy znaki.")
    private String surname;
    @NotBlank(message = "Pole jest wymagane.")
    @Email(message = "Niepoprawny adres e-mail.")
    private String email;
    @Password
    private String password;
}
