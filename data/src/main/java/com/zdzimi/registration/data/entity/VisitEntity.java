package com.zdzimi.registration.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "VISIT")
@Getter
@Setter
public class VisitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long visitId;
    private Timestamp visitDateTime;
    private long visitLength;
    @ManyToOne
    private UserEntity user;
    @ManyToOne
    private UserEntity representative;
    @ManyToOne
    private PlaceEntity place;
}
