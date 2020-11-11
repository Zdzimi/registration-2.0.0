package com.zdzimi.registration.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "PLACE")
@Getter
@Setter
public class PlaceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long placeId;
    private String placeName;
    @ManyToOne
    private InstitutionEntity institution;
    @JsonIgnore
    @OneToMany(mappedBy = "place")
    private Collection<VisitEntity> visits;
}
