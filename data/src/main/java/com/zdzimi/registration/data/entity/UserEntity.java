package com.zdzimi.registration.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
@Table(name = "USER")
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;
    @NotNull
    @Column(unique = true)
    private String username;
    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    private String email;
    @NotNull
    private String password;
    private String role;    //  todo
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Collection<VisitEntity> visits;
    @JsonIgnore
    @ManyToMany
    private Collection<InstitutionEntity> recognizedInstitutions;
    @JsonIgnore
    @OneToMany(mappedBy = "representative")
    private Collection<VisitEntity> providedVisits;
    @JsonIgnore
    @ManyToMany
    private Collection<InstitutionEntity> workPlaces;
}
