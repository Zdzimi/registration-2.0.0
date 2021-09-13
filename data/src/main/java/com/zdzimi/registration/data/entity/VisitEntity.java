package com.zdzimi.registration.data.entity;

import com.zdzimi.registration.data.validator.OnBook;
import com.zdzimi.registration.data.validator.OnCancel;
import com.zdzimi.registration.data.validator.OnDelete;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Future;
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
    @Future(groups = {OnCancel.class, OnDelete.class, OnBook.class})
    private Timestamp visitStart;
    private Timestamp visitEnd;
    @Null(groups = {OnDelete.class, OnBook.class})
    @ManyToOne
    private UserEntity user;
    @ManyToOne
    private UserEntity representative;
    @ManyToOne
    private PlaceEntity place;
    @ManyToOne
    private InstitutionEntity institution;
}
