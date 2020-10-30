package com.zdzimi.registration.core.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private long userId;
    private String username;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String role;    //  todo
}
