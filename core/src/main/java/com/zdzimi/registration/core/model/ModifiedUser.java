package com.zdzimi.registration.core.model;

import com.zdzimi.registration.core.validation.OnlyLettersAndDigits;
import com.zdzimi.registration.core.validation.Password;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ModifiedUser {

    @NotNull
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
    private String newPassword;
    private String oldPassword;
    @Null
    private Role role;
}
