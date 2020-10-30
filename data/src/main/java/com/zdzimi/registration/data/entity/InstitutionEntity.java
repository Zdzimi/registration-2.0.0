package com.zdzimi.registration.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "INSTITUTION")
@Getter
@Setter
public class InstitutionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long institutionId;
    @NotNull
    @Column(unique = true)
    private String institutionName;
    @NotNull
    private String province;
    @NotNull
    private String city;
    @NotNull
    private String street;
    @NotNull
    private String gateNumber;
    private String premisesNumber;
    @NotNull
    private String typeOfService;
    @NotNull
    private String description;
    @JsonIgnore
    @ManyToMany(mappedBy = "recognizedInstitutions")
    private Collection<UserEntity> users;
    @JsonIgnore
    @ManyToMany(mappedBy = "workPlaces")
    private Collection<UserEntity> representatives;
    @JsonIgnore
    @OneToMany(mappedBy = "institution")
    private Collection<PlaceEntity> places;
}
