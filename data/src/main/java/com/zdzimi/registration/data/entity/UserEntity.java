package com.zdzimi.registration.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

@Entity
@Table(name = "USER")
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;
    @Column(unique = true)
    private String username;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String role;
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
