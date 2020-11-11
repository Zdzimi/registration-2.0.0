package com.zdzimi.registration.core.dto;

import com.zdzimi.registration.core.validation.OnCreate;
import com.zdzimi.registration.core.validation.OnUpdate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

public class VisitDTO {

    @Null(groups = OnCreate.class)
    @NotNull(groups = OnUpdate.class)
    private long visitId;
    @NotNull
    @PastOrPresent
    private LocalDateTime visitDateTime;
    @NotNull
    @Min(1)
    private long visitLength;
    private UserDTO userDTO;
    private UserDTO representativeDTO;
    private UserDTO placeDTO;
}
