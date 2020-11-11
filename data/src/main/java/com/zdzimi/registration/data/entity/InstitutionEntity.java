package com.zdzimi.registration.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(unique = true)
    private String institutionName;
    private String province;
    private String city;
    private String street;
    private String gateNumber;
    private String premisesNumber;
    private String typeOfService;
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
