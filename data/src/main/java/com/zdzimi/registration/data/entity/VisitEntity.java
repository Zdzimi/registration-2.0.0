package com.zdzimi.registration.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zdzimi.registration.data.validator.OnBook;
import com.zdzimi.registration.data.validator.OnDelete;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Null;
import java.sql.Timestamp;

@Entity
@Table(name = "VISIT")
@Getter
@Setter
public class VisitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long visitId;
    private Timestamp visitStart;
    private Timestamp visitEnd;
    @Null(groups = {OnDelete.class, OnBook.class})
    @ManyToOne
    private UserEntity user;
    @ManyToOne
    private UserEntity representative;
    @ManyToOne
    private PlaceEntity place;
    @JsonIgnore
    @ManyToOne
    private InstitutionEntity institution;
}
